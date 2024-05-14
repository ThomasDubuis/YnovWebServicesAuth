package com.tdubuis.authapp.repository;

import com.tdubuis.authapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findAccountByLogin(String login);
}
