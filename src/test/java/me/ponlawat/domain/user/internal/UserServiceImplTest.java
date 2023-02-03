package me.ponlawat.domain.user.internal;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.UserRepository;
import me.ponlawat.domain.user.UserRole;
import me.ponlawat.domain.user.dto.UserRegisterDto;
import me.ponlawat.domain.user.exception.UserAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import java.util.Optional;

import static org.mockito.Mockito.*;

@QuarkusTest
class UserServiceImplTest {
    @Inject
    UserServiceImpl underTest;
    @InjectMock
    UserRepository userRepository;

    @Test
    void TestRegister() {
        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.ofNullable(null));

        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .email("test@email.com")
                .firstName("testFirst")
                .lastName("testLast")
                .password("testPass")
                .build();

        underTest.register(userRegisterDto);

        verify(userRepository, times(1)).persist(refEq(new User(null,
                "test@email.com",
                "testPass",
                "testFirst",
                "testLast",
                UserRole.MEMBER)));
    }

    @Test
    void TestRegisterUserAlreadyExist() {
        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.ofNullable(new User()));

        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .email("test@email.com")
                .firstName("testFirst")
                .lastName("testLast")
                .password("testPass")
                .build();

        Assertions.assertThrowsExactly(UserAlreadyExistException.class, () -> {
            underTest.register(userRegisterDto);
        });
        verify(userRepository, never()).persist(Mockito.any(User.class));
    }
}