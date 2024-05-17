package com.tdubuis.authapp.controller;

import com.tdubuis.authapp.dto.request.AccountRequest;
import com.tdubuis.authapp.dto.response.AccountResponse;
import com.tdubuis.authapp.entity.Account;
import com.tdubuis.authapp.entity.Role;
import com.tdubuis.authapp.exception.ForbiddenException;
import com.tdubuis.authapp.service.AccountService;
import com.tdubuis.authapp.utils.factory.AccountFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Slf4j
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest) {
        checkIfAdmin();
        Account account = accountService.createAccount(accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountFactory.toAccountResponse(account));
    }

    @GetMapping("/{uid}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String uid) {
        uid = getUidFromTokenAndCheckIfAdmin(uid);
        Account account = accountService.getAccount(uid);
        return ResponseEntity.status(HttpStatus.OK).body(AccountFactory.toAccountResponse(account));
    }

    @PutMapping("/{uid}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable String uid, @RequestBody AccountRequest accountRequest) {
        uid = getUidFromTokenAndCheckIfAdmin(uid);
        Account account = accountService.updateAccount(uid, accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountFactory.toAccountResponse(account));
    }


    private String getUidFromTokenAndCheckIfAdmin(String uid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authUid = ((Account)authentication.getPrincipal()).getUid();
        if (uid.equals("me")) {
            uid = authUid;
        }
        Account account = accountService.getAccount(uid);

        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(Role.ROLE_ADMIN.name())) && !account.getUid().equals(authUid)) {
            throw new ForbiddenException("Il est nécessaire d'etre admin ou d'etre le propriietaire du compte");
        }
        return uid;
    }

    private void checkIfAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(Role.ROLE_ADMIN.name()))) {
            throw new ForbiddenException("Il est nécessaire d'etre admin");
        }
    }
}
