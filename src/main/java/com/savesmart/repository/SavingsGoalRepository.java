package com.savesmart.repository;

import com.savesmart.model.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {
    List<SavingsGoal> findAllByAccountEmailOrderByCreatedAtDesc(String email);
    Optional<SavingsGoal> findByIdAndAccountEmail(Long id, String email);
}
