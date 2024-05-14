package com.tdubuis.authapp.utils.factory;

import com.tdubuis.authapp.dto.request.AccountRequest;
import com.tdubuis.authapp.dto.response.AccountResponse;
import com.tdubuis.authapp.entity.Account;

public final class AccountFactory {
    private AccountFactory() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    public static Account toAccount(AccountRequest accountRequest, String hashedPassword) {
        return new Account()
                .setLogin(accountRequest.getLogin())
                .setPassword(hashedPassword)
                .setRole(accountRequest.getRole())
                .setStatus(accountRequest.getStatus());
    }
    public static AccountResponse toAccountResponse(Account account) {
        return new AccountResponse(account.getUid(), account.getLogin(), account.getRole(), account.getCreatedAt(), account.getUpdatedAt());
    }
}
