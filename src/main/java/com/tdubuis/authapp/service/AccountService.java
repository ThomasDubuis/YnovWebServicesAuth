package com.tdubuis.authapp.service;

import com.tdubuis.authapp.dto.request.AccountRequest;
import com.tdubuis.authapp.entity.Account;
import com.tdubuis.authapp.exception.ElementNotFoundException;
import com.tdubuis.authapp.repository.AccountRepository;
import com.tdubuis.authapp.utils.factory.AccountFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AccountService {

    private static final String UNABLE_TO_FIND_ACCOUNT = "Unable to find Account [id = %s]";

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Account createAccount(AccountRequest accountRequest) {
        String hashedPassword = passwordEncoder.encode(accountRequest.getPassword());
        Account account = AccountFactory.toAccount(accountRequest, hashedPassword);
        return accountRepository.save(account);
    }
    public Account getAccount(String uid) {
        return accountRepository.findById(uid).orElseThrow(() -> new ElementNotFoundException(String.format(UNABLE_TO_FIND_ACCOUNT, uid)));
    }
    public Account updateAccount(String uid, AccountRequest accountRequest) {
        Optional<Account> accountOptional = accountRepository.findById(uid);
        if (accountOptional.isPresent()) {
            if (accountRequest.getPassword() != null && !accountRequest.getPassword().isBlank()) {
                //TODO : Essayé de passr le passwordEncoder qu'une seul fois (par exemple directement dans l'entity ou lors d'un save)
                accountRequest.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
            }
            Account account = accountOptional.get();
            account.updateIfNotNull(accountRequest);
            return accountRepository.save(account);
        }else {
            throw new ElementNotFoundException(String.format(UNABLE_TO_FIND_ACCOUNT, uid));
        }
    }
}
