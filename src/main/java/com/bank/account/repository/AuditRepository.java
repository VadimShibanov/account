package com.bank.account.repository;

import com.bank.account.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий аудита, реализующийся на основе {@link org.springframework.data.jpa.repository.JpaRepository}
 */
@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
}
