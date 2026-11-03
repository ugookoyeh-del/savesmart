package com.savesmart.dto;

import com.savesmart.model.SavingsGoal;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record GoalResponse(Long id, String name, String description, BigDecimal targetAmount,
                           BigDecimal currentAmount, BigDecimal progressPercent,
                           LocalDate targetDate, Instant createdAt) {
    public static GoalResponse from(SavingsGoal goal) {
        BigDecimal progress = goal.getCurrentAmount().multiply(new BigDecimal("100"))
                .divide(goal.getTargetAmount(), 2, java.math.RoundingMode.HALF_UP);
        if (progress.compareTo(new BigDecimal("100")) > 0) progress = new BigDecimal("100.00");
        return new GoalResponse(goal.getId(), goal.getName(), goal.getDescription(),
                goal.getTargetAmount(), goal.getCurrentAmount(), progress, goal.getTargetDate(), goal.getCreatedAt());
    }
}
