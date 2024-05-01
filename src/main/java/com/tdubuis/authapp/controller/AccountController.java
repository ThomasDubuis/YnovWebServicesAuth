package com.tdubuis.authapp.controller;

import com.tdubuis.authapp.dto.request.AccountRequest;
import com.tdubuis.authapp.dto.response.AccountResponse;
import com.tdubuis.authapp.entity.Account;
import com.tdubuis.authapp.service.AccountService;
import com.tdubuis.authapp.utils.factory.AccountFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Slf4j
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest) {
        Account account = accountService.createAccount(accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountFactory.toAccountResponse(account));
    }

    @GetMapping("/{uid}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String uid) {
        Account account = accountService.getAccount(uid);
        return ResponseEntity.status(HttpStatus.OK).body(AccountFactory.toAccountResponse(account));
    }
    @PutMapping("/{uid}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable String uid, @RequestBody AccountRequest accountRequest) {
        Account account = accountService.updateAccount(uid, accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountFactory.toAccountResponse(account));
    }
}
