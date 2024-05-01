package com.tdubuis.authapp.dto.request;

import com.tdubuis.authapp.entity.Role;
import com.tdubuis.authapp.entity.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class AccountRequest {

    @NotBlank(message = "Login can't be empty")
    private String login;
    @NotBlank(message = "Password can't be empty")
    private String password;

    private List<Role> roles;
    @NotBlank(message = "Status can't be empty")
    private Status status;
}
