package me.ponlawat.domain.user;

import me.ponlawat.domain.user.dto.*;

import java.util.List;

public interface UserService {
    User register(UserRegisterRequest userRegisterDto);
    UserLoginResponse login(UserLoginRequest userLoginRequest);
    User profile(Long id);
    UserApiKeyResponse createApiKey(User user);
    List<User> listUser();
    User getUser(long id);
    void removeUser(long id);
    User updateUser(long id, UserEditRequest userEditRequest);
}
