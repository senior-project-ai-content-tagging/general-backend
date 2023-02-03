package me.ponlawat.domain.user;

import me.ponlawat.domain.user.dto.UserRegisterDto;

public interface UserService {
    User register(UserRegisterDto userRegisterDto);
}
