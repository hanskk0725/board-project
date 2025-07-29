package com.toyproject.board.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    @Email
    @NotEmpty
    private String email;

    @NotNull
    private String password;

    @NotEmpty
    private String nickname;

    public UserRegisterDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
