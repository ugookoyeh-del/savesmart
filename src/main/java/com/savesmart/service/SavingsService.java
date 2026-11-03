package com.savesmart.service;

import com.savesmart.dto.CreateGoalRequest;
import com.savesmart.dto.MoneyRequest;
import com.savesmart.model.*;
import com.savesmart.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SavingsService {
    private final AccountRepository accounts;
    private final SavingsGoalRepository goals;
    private final SavingsTransactionRepository transactions;

    public SavingsService(AccountRepository accounts, SavingsGoalRepository goals,
                          SavingsTransactionRepository transactions) {
        this.accounts = accounts;
        this.goals = goals;
        this.transactions = transactions;
    }

    @Transactional
    public SavingsGoal createGoal(String email, CreateGoalRequest request) {
        Account account = accounts.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return goals.save(new SavingsGoal(account, request.name().trim(), request.description(),
                request.targetAmount(), request.targetDate()));
    }

    @Transactional(readOnly = true)
    public List<SavingsGoal> listGoals(String email) {
        return goals.findAllByAccountEmailOrderByCreatedAtDesc(email);
    }

    @Transactional
    public SavingsTransaction deposit(String email, Long goalId, MoneyRequest request) {
        SavingsGoal goal = ownedGoal(email, goalId);
        goal.add(request.amount());
        return transactions.save(new SavingsTransaction(goal, TransactionType.DEPOSIT, request.amount(), request.note()));
    }

    @Transactional
    public SavingsTransaction withdraw(String email, Long goalId, MoneyRequest request) {
        SavingsGoal goal = ownedGoal(email, goalId);
        if (goal.getCurrentAmount().compareTo(request.amount()) < 0) {
            throw new IllegalArgumentException("Insufficient saved balance for this withdrawal");
        }
        goal.subtract(request.amount());
        return transactions.save(new SavingsTransaction(goal, TransactionType.WITHDRAWAL, request.amount(), request.note()));
    }

    @Transactional(readOnly = true)
    public List<SavingsTransaction> goalHistory(String email, Long goalId) {
        ownedGoal(email, goalId);
        return transactions.findAllByGoalIdAndGoalAccountEmailOrderByCreatedAtDesc(goalId, email);
    }

    @Transactional(readOnly = true)
    public List<SavingsTransaction> allHistory(String email) {
        return transactions.findAllByGoalAccountEmailOrderByCreatedAtDesc(email);
    }

    private SavingsGoal ownedGoal(String email, Long goalId) {
        return goals.findByIdAndAccountEmail(goalId, email)
                .orElseThrow(() -> new IllegalArgumentException("Savings goal not found"));
    }
}
