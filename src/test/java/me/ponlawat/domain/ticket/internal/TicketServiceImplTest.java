package me.ponlawat.domain.ticket.internal;

import me.ponlawat.domain.content.Content;
import me.ponlawat.domain.content.ContentRepository;
import me.ponlawat.domain.ticket.Ticket;
import me.ponlawat.domain.ticket.TicketRepository;
import me.ponlawat.domain.ticket.TicketStatus;
import me.ponlawat.domain.ticket.TicketType;
import me.ponlawat.domain.ticket.dto.TicketWeblinkRequest;
import me.ponlawat.domain.ticket.dto.TicketWeblinkResponse;
import me.ponlawat.domain.user.User;
import org.junit.jupiter.api.*;

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

            TicketWeblinkResponse result = underTest.submitWeblink(user, ticketWeblinkRequest);

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
        }
    }
}