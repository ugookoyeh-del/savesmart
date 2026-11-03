package com.savesmart.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "savings_transactions")
public class SavingsTransaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "goal_id", nullable = false)
    private SavingsGoal goal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    @Column(length = 250)
    private String note;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected SavingsTransaction() {}

    public SavingsTransaction(SavingsGoal goal, TransactionType type, BigDecimal amount, String note) {
        this.goal = goal;
        this.type = type;
        this.amount = amount;
        this.note = note;
    }

    public Long getId() { return id; }
    public SavingsGoal getGoal() { return goal; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public String getNote() { return note; }
    public Instant getCreatedAt() { return createdAt; }
}
