package me.ponlawat.domain.user.internal;

import lombok.Setter;
import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.UserRepository;
import me.ponlawat.domain.user.UserRole;
import me.ponlawat.domain.user.UserService;
import me.ponlawat.domain.user.dto.UserRegisterDto;
import me.ponlawat.domain.user.exception.UserAlreadyExistException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
@Setter
public class UserServiceImpl implements UserService {
    @Inject
    UserRepository userRepository;

    @Override
    @Transactional
    public User register(UserRegisterDto userRegisterDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userRegisterDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistException();
        }

        User newUser = User.builder()
                .email(userRegisterDto.getEmail())
                .password(userRegisterDto.getPassword())
                .firstName(userRegisterDto.getFirstName())
                .lastName(userRegisterDto.getLastName())
                .role(UserRole.MEMBER)
                .build();

        userRepository.persist(newUser);
        System.out.println(newUser);

        return newUser;
    }
}
