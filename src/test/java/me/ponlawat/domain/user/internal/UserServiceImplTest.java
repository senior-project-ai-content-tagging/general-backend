package me.ponlawat.domain.user.internal;

import io.quarkus.test.junit.QuarkusTest;
import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.UserRepository;
import me.ponlawat.domain.user.UserRole;
import me.ponlawat.domain.user.dto.UserLoginRequest;
import me.ponlawat.domain.user.dto.UserLoginResponse;
import me.ponlawat.domain.user.dto.UserRegisterRequest;
import me.ponlawat.domain.user.exception.UserAlreadyExistException;
import me.ponlawat.domain.user.exception.UserUnauthorizedException;
import me.ponlawat.infrastructure.auth.AuthToken;
import me.ponlawat.infrastructure.crypto.PasswordEncrypter;
import me.ponlawat.infrastructure.provider.http.HttpErrorException;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.mockito.Mockito.*;

class UserServiceImplTest {
    UserServiceImpl underTest;
    UserRepository mockUserRepository;
    PasswordEncrypter mockPasswordEncrypter;
    AuthToken mockAuth;

    @BeforeEach
    void setUp() {
        mockUserRepository = mock(UserRepository.class);
        mockPasswordEncrypter = mock(PasswordEncrypter.class);
        mockAuth = mock(AuthToken.class);

        underTest = new UserServiceImpl();
        underTest.setUserRepository(mockUserRepository);
        underTest.setPasswordEncrypter(mockPasswordEncrypter);
        underTest.setAuth(mockAuth);
    }

    @Nested
    @DisplayName("Test Register")
    class TestRegister {
        private UserRegisterRequest userRegisterRequest;

        @BeforeEach
        void setUp() {
            userRegisterRequest = new UserRegisterRequest();
            userRegisterRequest.setEmail("test@email.com");
            userRegisterRequest.setFirstName("testFirst");
            userRegisterRequest.setLastName("testLast");
            userRegisterRequest.setPassword("testPass");
        }

        @Test
        void testRegister() {
            when(mockUserRepository.findByEmail("test@email.com")).thenReturn(Optional.ofNullable(null));
            when(mockPasswordEncrypter.hash("testPass")).thenReturn("hashedPassword");

            User result = underTest.register(userRegisterRequest);

            verify(mockUserRepository, times(1)).persist(refEq(new User(null,
                    "test@email.com",
                    "hashedPassword",
                    "testFirst",
                    "testLast",
                    UserRole.MEMBER)));

            Assertions.assertEquals("test@email.com", result.getEmail());
            Assertions.assertEquals("testFirst", result.getFirstName());
            Assertions.assertEquals("testLast", result.getLastName());
            Assertions.assertEquals(UserRole.MEMBER, result.getRole());
        }

        @Test
        void testRegisterUserAlreadyExist() {
            when(mockUserRepository.findByEmail("test@email.com")).thenReturn(Optional.ofNullable(new User()));

            Assertions.assertThrowsExactly(UserAlreadyExistException.class, () -> {
                underTest.register(userRegisterRequest);
            });
            verify(mockUserRepository, never()).persist(Mockito.any(User.class));
            verify(mockPasswordEncrypter, never()).hash(Mockito.any(String.class));
        }
    }

    @Nested
    @DisplayName("Test Login")
    class TestLogin {
        private UserLoginRequest userLoginRequest;
        private User user;

        @BeforeEach
        void setUp() {
            userLoginRequest = new UserLoginRequest();
            userLoginRequest.setEmail("test@email.com");
            userLoginRequest.setPassword("123456");
            user = User.builder()
                    .id(123L)
                    .email("test@email.com")
                    .password("hashedPassword")
                    .firstName("testFirst")
                    .lastName("testLast")
                    .role(UserRole.MEMBER)
                    .build();
        }

        @Test
        void testLoginCorrect() {
            when(mockUserRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));
            when(mockPasswordEncrypter.verify(user.getPassword(), userLoginRequest.getPassword())).thenReturn(true);
            when(mockAuth.sign(user)).thenReturn("jwtToken");

            UserLoginResponse result = underTest.login(userLoginRequest);

            Assertions.assertEquals("jwtToken", result.getToken());
        }

        @Test
        void testLoginInvalidPassword() {
            when(mockUserRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));
            when(mockPasswordEncrypter.verify(user.getPassword(), userLoginRequest.getPassword())).thenReturn(false);

            HttpErrorException exception = Assertions.assertThrowsExactly(HttpErrorException.class, () -> {
                underTest.login(userLoginRequest);
            });

            Assertions.assertEquals(Response.Status.UNAUTHORIZED, exception.getHttpStatus());
            Assertions.assertEquals("Email or password is invalid", exception.getMessage());
        }

        @Test
        void testLoginNotFoundUser() {
            when(mockUserRepository.findByEmail("test@email.com")).thenReturn(Optional.ofNullable(null));
            when(mockPasswordEncrypter.verify(user.getPassword(), userLoginRequest.getPassword())).thenReturn(false);

            HttpErrorException exception = Assertions.assertThrowsExactly(HttpErrorException.class, () -> {
                underTest.login(userLoginRequest);
            });

            Assertions.assertEquals(Response.Status.UNAUTHORIZED, exception.getHttpStatus());
            Assertions.assertEquals("Email or password is invalid", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Test Profile")
    class TestProfile {
       private User user;

        @BeforeEach
        void setUp() {
            user = User.builder()
                    .id(123L)
                    .email("test@email.com")
                    .password("hashedPassword")
                    .firstName("testFirst")
                    .lastName("testLast")
                    .role(UserRole.MEMBER)
                    .build();
        }

        @Test
        void testGetProfile() {
            when(mockUserRepository.findByIdOptional(1L)).thenReturn(Optional.of(user));

            User result =  underTest.profile(1L);

            Assertions.assertEquals(user, result);
        }

        @Test
        void testGetProfileNotFoundUser() {
            when(mockUserRepository.findByIdOptional(1L)).thenReturn(Optional.ofNullable(null));

            Assertions.assertThrowsExactly(UserUnauthorizedException.class, () -> {
                underTest.profile(1L);
            });
        }
    }
}