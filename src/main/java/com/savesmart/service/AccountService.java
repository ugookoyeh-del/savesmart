package com.savesmart.service;

import com.savesmart.dto.RegisterRequest;
import com.savesmart.model.Account;
import com.savesmart.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    private final AccountRepository accounts;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accounts, PasswordEncoder passwordEncoder) {
        this.accounts = accounts;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Account register(RegisterRequest request) {
        String email = request.email().toLowerCase().trim();
        if (accounts.existsByEmail(email)) throw new IllegalArgumentException("An account with this email already exists");
        return accounts.save(new Account(request.fullName().trim(), email, passwordEncoder.encode(request.password())));
    }
}
