package me.ponlawat.domain.ticket.internal;

import me.ponlawat.domain.content.Content;
import me.ponlawat.domain.content.ContentRepository;
import me.ponlawat.domain.content.exception.ContentNotFoundException;
import me.ponlawat.domain.ticket.Ticket;
import me.ponlawat.domain.ticket.TicketRepository;
import me.ponlawat.domain.ticket.TicketStatus;
import me.ponlawat.domain.ticket.TicketType;
import me.ponlawat.domain.ticket.dto.TicketWeblinkRequest;
import me.ponlawat.domain.ticket.dto.TicketRequestResponse;
import me.ponlawat.domain.ticket.exception.TicketNotFoundException;
import me.ponlawat.domain.user.User;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.mockito.Mockito.*;

class TicketServiceImplTest {

    TicketServiceImpl underTest;
    TicketRepository mockTicketRepository;
    ContentRepository mockContentRepository;

    @BeforeEach
    void setUp() {
        mockTicketRepository = mock(TicketRepository.class);
        mockContentRepository = mock(ContentRepository.class);

        underTest = new TicketServiceImpl();
        underTest.setTicketRepository(mockTicketRepository);
        underTest.setContentRepository(mockContentRepository);
    }

    @Nested
    @DisplayName("Test Submit Weblink")
    class TestSubmitWeblink {

        private TicketWeblinkRequest ticketWeblinkRequest;
        private User user;
        private static final String TARGET_URL = "http://localhost/123/123";

        @Test
        void testSubmitWeblink() {
            ticketWeblinkRequest = new TicketWeblinkRequest(TARGET_URL);
            user = new User();
            user.setId(1L);

            Content expectedContent = new Content();
            expectedContent.setOriginalUrl(TARGET_URL);

            TicketRequestResponse result = underTest.submitWeblink(user, ticketWeblinkRequest);

            verify(mockContentRepository, times(1)).persistAndFlush(expectedContent);
            verify(mockTicketRepository, times(1)).persist(new Ticket(
                    null,
                    user,
                    TicketType.USER,
                    expectedContent,
                    TicketStatus.OPEN
            ));
            Assertions.assertEquals(TARGET_URL, expectedContent.getOriginalUrl());
            Assertions.assertEquals(TicketStatus.OPEN, result.getStatus());
            Assertions.assertEquals(expectedContent.getId(), result.getContentId());
        }
    }

    @Nested
    @DisplayName("Get ticket by id")
    class TestGetTicketById {
        @Test
        void testGetTicketFound() {
            Ticket mockTicket = new Ticket();
            mockTicket.setId(1L);
            when(mockTicketRepository.findByIdOptional(1L)).thenReturn(Optional.of(mockTicket));

            Ticket result = underTest.getTicketById(1L);

            Assertions.assertEquals(mockTicket.getId(), result.getId());
        }

        @Test
        void testGetTicketNotFound() {
            when(mockTicketRepository.findByIdOptional(1L)).thenReturn(Optional.ofNullable(null));

            Assertions.assertThrowsExactly(TicketNotFoundException.class, () -> {
                underTest.getTicketById(1L);
            });
        }
    }
}