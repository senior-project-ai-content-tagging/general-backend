package me.ponlawat.domain.user.internal;

import lombok.Setter;
import me.ponlawat.domain.user.User;
import me.ponlawat.domain.user.UserRepository;
import me.ponlawat.domain.user.UserRole;
import me.ponlawat.domain.user.UserService;
import me.ponlawat.domain.user.dto.UserApiKeyResponse;
import me.ponlawat.domain.user.dto.UserLoginRequest;
import me.ponlawat.domain.user.dto.UserLoginResponse;
import me.ponlawat.domain.user.dto.UserRegisterRequest;
import me.ponlawat.domain.user.exception.UserAlreadyExistException;
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
            LOG.info("User already exist");
            throw new UserAlreadyExistException();
        }

        String hashedPassword = passwordEncrypter.hash(userRegisterRequest.getPassword());

        User newUser = User.builder()
                .email(userRegisterRequest.getEmail())
                .password(hashedPassword)
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .role(UserRole.MEMBER)
                .build();

        userRepository.persist(newUser);
        LOG.info("User registration success");
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
        LOG.info("test login");

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
}
