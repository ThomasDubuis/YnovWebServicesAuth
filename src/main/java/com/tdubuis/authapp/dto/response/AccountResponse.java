package com.tdubuis.authapp.dto.response;

import com.tdubuis.authapp.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class AccountResponse {

    private String uid;

    private String login;

    private Role role;

    private Date createdAt;

    private Date updatedAt;
}
