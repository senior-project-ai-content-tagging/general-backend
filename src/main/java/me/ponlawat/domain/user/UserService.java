package me.ponlawat.domain.user;

import me.ponlawat.domain.user.dto.UserApiKeyResponse;
import me.ponlawat.domain.user.dto.UserLoginRequest;
import me.ponlawat.domain.user.dto.UserLoginResponse;
import me.ponlawat.domain.user.dto.UserRegisterRequest;

public interface UserService {
    User register(UserRegisterRequest userRegisterDto);
    UserLoginResponse login(UserLoginRequest userLoginRequest);
    User profile(Long id);
    UserApiKeyResponse createApiKey(User user);
}
