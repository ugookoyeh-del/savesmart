package com.savesmart.dto;

import com.savesmart.model.SavingsTransaction;
import com.savesmart.model.TransactionType;
import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponse(Long id, Long goalId, TransactionType type,
                                  BigDecimal amount, String note, Instant createdAt) {
    public static TransactionResponse from(SavingsTransaction tx) {
        return new TransactionResponse(tx.getId(), tx.getGoal().getId(), tx.getType(),
                tx.getAmount(), tx.getNote(), tx.getCreatedAt());
    }
}
