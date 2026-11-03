package com.savesmart.repository;

import com.savesmart.model.SavingsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavingsTransactionRepository extends JpaRepository<SavingsTransaction, Long> {
    List<SavingsTransaction> findAllByGoalIdAndGoalAccountEmailOrderByCreatedAtDesc(Long goalId, String email);
    List<SavingsTransaction> findAllByGoalAccountEmailOrderByCreatedAtDesc(String email);
}
