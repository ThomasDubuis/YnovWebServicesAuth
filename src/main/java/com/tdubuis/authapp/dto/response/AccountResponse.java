package com.tdubuis.authapp.dto.response;

import com.tdubuis.authapp.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
public class AccountResponse {

    private String uid;

    private String login;

    private List<Role> roles;

    private Date createdAt;

    private Date updatedAt;
}
