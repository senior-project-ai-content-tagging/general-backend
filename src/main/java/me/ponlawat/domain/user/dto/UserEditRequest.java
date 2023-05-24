package me.ponlawat.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.ponlawat.domain.user.UserRole;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEditRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private UserRole role;
    @NotBlank
    private String apiKey;
}
