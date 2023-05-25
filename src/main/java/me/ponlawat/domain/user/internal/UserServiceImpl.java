package me.ponlawat.domain.user.internal;

import io.quarkus.panache.common.Sort;
import lombok.Setter;
import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.UserRepository;
import me.ponlawat.domain.user.UserRole;
import me.ponlawat.domain.user.UserService;
import me.ponlawat.domain.user.dto.*;
import me.ponlawat.domain.user.exception.UserAlreadyExistException;
import me.ponlawat.domain.user.exception.UserNotFoundException;
import me.ponlawat.domain.user.exception.UserUnauthorizedException;
import me.ponlawat.infrastructure.auth.AuthContextImpl;
import me.ponlawat.infrastructure.crypto.ApiKeyGenerator;
import me.ponlawat.infrastructure.crypto.PasswordEncrypter;
import me.ponlawat.infrastructure.provider.http.HttpErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Setter
public class UserServiceImpl implements UserService {
    @Inject
    UserRepository userRepository;
    @Inject
    PasswordEncrypter passwordEncrypter;
    @Inject
    AuthContextImpl auth;
    @Inject
    ApiKeyGenerator apiKeyGenerator;

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    @Override
    @Transactional
    public User register(UserRegisterRequest userRegisterRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(userRegisterRequest.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistException();
        }

        String hashedPassword = passwordEncrypter.hash(userRegisterRequest.getPassword());
        String apiKey = apiKeyGenerator.generate();
        User newUser = User.builder()
                .email(userRegisterRequest.getEmail())
                .password(hashedPassword)
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .role(UserRole.MEMBER)
                .apiKey(apiKey)
                .build();

        userRepository.persist(newUser);
        return newUser;
    }

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(userLoginRequest.getEmail());
        if (optionalUser.isEmpty() ||
                !passwordEncrypter.verify(optionalUser.get().getPassword(), userLoginRequest.getPassword())) {
            throw new HttpErrorException(Response.Status.UNAUTHORIZED,  "Email or password is invalid");
        }

        String jwtToken = auth.sign(optionalUser.get());
        UserLoginResponse response = new UserLoginResponse(jwtToken);

        return response;
    }

    @Override
    public User profile(Long id) {
        Optional<User> optionalUser = userRepository.findByIdOptional(id);
        if (optionalUser.isEmpty()) {
            throw new UserUnauthorizedException();
        }

        return optionalUser.get();
    }

    @Override
    @Transactional
    public UserApiKeyResponse createApiKey(User user) {
        String apiKey = apiKeyGenerator.generate();
        user.setApiKey(apiKey);
        userRepository.getEntityManager().merge(user);

        UserApiKeyResponse response = new UserApiKeyResponse(apiKey);

        return response;
    }

    @Override
    public List<User> listUser() {
        return userRepository.listAll(Sort.by("id"));
    }

    @Override
    public User getUser(long id) {
        Optional<User> optionalUser = userRepository.findByIdOptional(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        return optionalUser.get();
    }

    @Override
    @Transactional
    public void removeUser(long id) {
        Optional<User> optionalUser = userRepository.findByIdOptional(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        userRepository.delete(optionalUser.get());
    }

    @Override
    @Transactional
    public User updateUser(long id, UserEditRequest userEditRequest) {
        Optional<User> optionalUser = userRepository.findByIdOptional(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        User user = optionalUser.get();
        user.setFirstName(userEditRequest.getFirstName());
        user.setLastName(userEditRequest.getLastName());
        user.setApiKey(userEditRequest.getApiKey());
        user.setRole(userEditRequest.getRole());

        userRepository.persist(user);

        return user;
    }
}
