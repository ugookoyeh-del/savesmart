package com.savesmart.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "savings_goals")
public class SavingsGoal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal targetAmount;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    private LocalDate targetDate;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected SavingsGoal() {}

    public SavingsGoal(Account account, String name, String description, BigDecimal targetAmount, LocalDate targetDate) {
        this.account = account;
        this.name = name;
        this.description = description;
        this.targetAmount = targetAmount;
        this.targetDate = targetDate;
    }

    public void add(BigDecimal amount) { currentAmount = currentAmount.add(amount); }
    public void subtract(BigDecimal amount) { currentAmount = currentAmount.subtract(amount); }
    public Long getId() { return id; }
    public Account getAccount() { return account; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getTargetAmount() { return targetAmount; }
    public BigDecimal getCurrentAmount() { return currentAmount; }
    public LocalDate getTargetDate() { return targetDate; }
    public Instant getCreatedAt() { return createdAt; }
}
