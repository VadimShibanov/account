package com.bank.account.repository;

import com.bank.account.entity.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий банковских счетов, реализующийся на основе {@link org.springframework.data.jpa.repository.JpaRepository}
 */
@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long> {

    AccountDetails findByAccountNumber(Long accountNumber);
}
