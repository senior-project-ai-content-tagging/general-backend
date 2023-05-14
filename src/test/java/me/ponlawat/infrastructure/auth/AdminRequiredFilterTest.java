package me.ponlawat.infrastructure.auth;

import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.UserRole;
import me.ponlawat.domain.user.exception.UserForbiddenRoleException;
import me.ponlawat.domain.user.exception.UserUnauthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.container.ContainerRequestContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdminRequiredFilterTest {
    private AdminRequiredFilter underTest;
    private AuthContext mockAuthContext;
    private ContainerRequestContext mockContainerRequestContext;

    @BeforeEach
    void setUp() {
        underTest = new AdminRequiredFilter();
        mockAuthContext = mock(AuthContext.class);
        mockContainerRequestContext = mock(ContainerRequestContext.class);

        underTest.setAuthContext(mockAuthContext);
    }

    @Test
    void testAdminRequiredAdminRole() {
        User user = new User();
        user.setRole(UserRole.ADMIN);
        when(mockAuthContext.getUser()).thenReturn(user);

        Assertions.assertDoesNotThrow(() -> {
            underTest.filter(mockContainerRequestContext);
        });
    }

    @Test
    void testAdminRequiredMemberRole() {
        User user = new User();
        user.setRole(UserRole.MEMBER);
        when(mockAuthContext.getUser()).thenReturn(user);

        Assertions.assertThrowsExactly(UserForbiddenRoleException.class, () -> {
            underTest.filter(mockContainerRequestContext);
        });
    }

    @Test
    void testAdminRequiredMissingUser() {
        when(mockAuthContext.getUser()).thenReturn(null);

        Assertions.assertThrowsExactly(UserUnauthorizedException.class, () -> {
            underTest.filter(mockContainerRequestContext);
        });
    }
}