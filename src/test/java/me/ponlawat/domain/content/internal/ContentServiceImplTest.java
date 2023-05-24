package me.ponlawat.domain.content.internal;

import me.ponlawat.domain.content.Content;
import me.ponlawat.domain.content.ContentRepository;
import me.ponlawat.domain.content.exception.ContentNotFoundException;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContentServiceImplTest {
    ContentServiceImpl underTest;
    ContentRepository mockContentRepository;

    @BeforeEach
    void setUp() {
        mockContentRepository = mock(ContentRepository.class);

        underTest = new ContentServiceImpl();
        underTest.setContentRepository(mockContentRepository);
    }

    @Nested
    @DisplayName("Get content by id")
    class TestGetContentById {
        @Test
        void testGetContentFound() {
            Content mockContent = new Content();
            mockContent.setId(1L);
            when(mockContentRepository.findByIdOptional(1L)).thenReturn(Optional.of(mockContent));

            Content result = underTest.getContentById(1L);

            Assertions.assertEquals(mockContent.getId(), result.getId());
        }

        @Test
        void testGetContentNotFound() {
            when(mockContentRepository.findByIdOptional(1L)).thenReturn(Optional.ofNullable(null));

            Assertions.assertThrowsExactly(ContentNotFoundException.class, () -> {
                underTest.getContentById(1L);
            });
        }
    }

}