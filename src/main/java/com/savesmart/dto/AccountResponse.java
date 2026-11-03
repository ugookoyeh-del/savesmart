package com.savesmart.dto;

import com.savesmart.model.Account;
import java.time.Instant;

public record AccountResponse(Long id, String fullName, String email, Instant createdAt) {
    public static AccountResponse from(Account account) {
        return new AccountResponse(account.getId(), account.getFullName(), account.getEmail(), account.getCreatedAt());
    }
}
