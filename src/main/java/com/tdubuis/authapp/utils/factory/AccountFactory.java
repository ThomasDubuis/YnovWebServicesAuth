package com.tdubuis.authapp.utils.factory;

import com.tdubuis.authapp.dto.request.AccountRequest;
import com.tdubuis.authapp.dto.response.AccountResponse;
import com.tdubuis.authapp.entity.Account;

public class AccountFactory {
    public static Account toAccount(AccountRequest accountRequest) {
        return new Account()
                .setLogin(accountRequest.getLogin())
                .setPassword(accountRequest.getPassword())
                .setRoles(accountRequest.getRoles())
                .setStatus(accountRequest.getStatus());
    }
    public static AccountResponse toAccountResponse(Account account) {
        return new AccountResponse(account.getUid(), account.getLogin(), account.getRoles(), account.getCreatedAt(), account.getUpdatedAt());
    }
}
