package com.tdubuis.authapp.repository;

import com.tdubuis.authapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
