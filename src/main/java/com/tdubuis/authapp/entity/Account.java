package com.tdubuis.authapp.entity;

import com.tdubuis.authapp.dto.request.AccountRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uid;

    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    public void updateIfNotNull(AccountRequest accountRequest) {
        if (accountRequest.getLogin() != null && !accountRequest.getLogin().isBlank()) {
            this.login = accountRequest.getLogin();
        }
        if (accountRequest.getPassword() != null && !accountRequest.getPassword().isBlank()) {
            this.password = accountRequest.getPassword();
        }
        if (accountRequest.getStatus() != null) {
            this.status = accountRequest.getStatus();
        }
        if (accountRequest.getRole() != null) {
            this.role = accountRequest.getRole();
        }
    }
}
