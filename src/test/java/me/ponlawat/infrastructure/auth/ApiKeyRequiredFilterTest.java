package me.ponlawat.infrastructure.auth;

import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.UserRepository;
import me.ponlawat.domain.user.exception.UserUnauthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.container.ContainerRequestContext;

import java.util.Optional;

import static org.mockito.Mockito.*;

class ApiKeyRequiredFilterTest {
    ApiKeyRequiredFilter underTest;
    UserRepository mockUserRepository;
    AuthContextImpl mockAuthContext;
    ContainerRequestContext mockContainerRequestContext;

    @BeforeEach
    void setUp() {
        mockUserRepository = mock(UserRepository.class);
        mockAuthContext = mock(AuthContextImpl.class);
        mockContainerRequestContext = mock(ContainerRequestContext.class);

        underTest = new ApiKeyRequiredFilter();
        underTest.setUserRepository(mockUserRepository);
        underTest.setAuthContext(mockAuthContext);
    }

    @Test
    void testMissingApiKey() {
        when(mockContainerRequestContext.getHeaderString("X-API-KEY")).thenReturn("");

        Assertions.assertThrowsExactly(UserUnauthorizedException.class, () -> {
            underTest.filter(mockContainerRequestContext);
        });
    }

    @Test
    void testApiKeyNotFound() {
        when(mockContainerRequestContext.getHeaderString("X-API-KEY")).thenReturn("123");
        when(mockUserRepository.findByApiKey("123")).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrowsExactly(UserUnauthorizedException.class, () -> {
            underTest.filter(mockContainerRequestContext);
        });
    }

    @Test
    void testApiKey() {
        User user = new User();
        user.setId(1L);
        when(mockContainerRequestContext.getHeaderString("X-API-KEY")).thenReturn("123");
        when(mockUserRepository.findByApiKey("123")).thenReturn(Optional.of(user));

        underTest.filter(mockContainerRequestContext);

        verify(mockAuthContext, times(1)).setUser(user);
    }
}