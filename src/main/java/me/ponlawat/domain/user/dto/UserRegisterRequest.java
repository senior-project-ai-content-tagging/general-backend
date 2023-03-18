package me.ponlawat.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    @Email
    private String email;
    @Length(min = 6, max = 60)
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}