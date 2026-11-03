package com.savesmart.controller;

import com.savesmart.dto.*;
import com.savesmart.service.SavingsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SavingsController {
    private final SavingsService savings;
    public SavingsController(SavingsService savings) { this.savings = savings; }

    @PostMapping("/goals")
    @ResponseStatus(HttpStatus.CREATED)
    public GoalResponse createGoal(Authentication auth, @Valid @RequestBody CreateGoalRequest request) {
        return GoalResponse.from(savings.createGoal(auth.getName(), request));
    }

    @GetMapping("/goals")
    public List<GoalResponse> goals(Authentication auth) {
        return savings.listGoals(auth.getName()).stream().map(GoalResponse::from).toList();
    }

    @PostMapping("/goals/{goalId}/deposits")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse deposit(Authentication auth, @PathVariable Long goalId,
                                        @Valid @RequestBody MoneyRequest request) {
        return TransactionResponse.from(savings.deposit(auth.getName(), goalId, request));
    }

    @PostMapping("/goals/{goalId}/withdrawals")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse withdraw(Authentication auth, @PathVariable Long goalId,
                                          @Valid @RequestBody MoneyRequest request) {
        return TransactionResponse.from(savings.withdraw(auth.getName(), goalId, request));
    }

    @GetMapping("/goals/{goalId}/transactions")
    public List<TransactionResponse> goalHistory(Authentication auth, @PathVariable Long goalId) {
        return savings.goalHistory(auth.getName(), goalId).stream().map(TransactionResponse::from).toList();
    }

    @GetMapping("/transactions")
    public List<TransactionResponse> allHistory(Authentication auth) {
        return savings.allHistory(auth.getName()).stream().map(TransactionResponse::from).toList();
    }
}
