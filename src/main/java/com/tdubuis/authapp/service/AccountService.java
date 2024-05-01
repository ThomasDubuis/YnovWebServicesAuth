package com.tdubuis.authapp.service;

import com.tdubuis.authapp.dto.request.AccountRequest;
import com.tdubuis.authapp.entity.Account;
import com.tdubuis.authapp.entity.Role;
import com.tdubuis.authapp.exception.ElementNotFoundException;
import com.tdubuis.authapp.repository.AccountRepository;
import com.tdubuis.authapp.utils.factory.AccountFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AccountService {

    private static final String UNABLE_TO_FIND_ACCOUNT = "Unable to find Account [id = %s]";

    private final AccountRepository accountRepository;

    public Account createAccount(AccountRequest accountRequest) {
        Account account = AccountFactory.toAccount(accountRequest);
        if (account.getRoles().contains(Role.ROLE_ADMIN) && !account.getRoles().contains(Role.ROLE_USER)) {
            account.getRoles().add(Role.ROLE_USER);
        }
        return accountRepository.save(account);
    }
    public Account getAccount(String uid) {
        return accountRepository.findById(uid).orElseThrow(() -> new ElementNotFoundException(String.format(UNABLE_TO_FIND_ACCOUNT, uid)));
    }
    public Account updateAccount(String uid, AccountRequest accountRequest) {
        Optional<Account> accountOptional = accountRepository.findById(uid);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.updateIfNotNull(accountRequest);
            if (account.getRoles().contains(Role.ROLE_ADMIN) && !account.getRoles().contains(Role.ROLE_USER)) {
                account.getRoles().add(Role.ROLE_USER);
            }
            return accountRepository.save(account);
        }else {
            throw new ElementNotFoundException(String.format(UNABLE_TO_FIND_ACCOUNT, uid));
        }
    }
}
