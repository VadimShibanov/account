package com.bank.account.service;

import com.bank.account.entity.AccountDetails;
import com.bank.account.repository.AccountDetailsRepository;
import com.bank.account.exception.AccountNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис банковских счетов
 */
@Service
public class AccountDetailsService {

    private final AccountDetailsRepository accountDetailsRepository;

    private final AuditService auditService;

    public AccountDetailsService(AccountDetailsRepository accountDetailsRepository, AuditService auditService) {
        this.accountDetailsRepository = accountDetailsRepository;
        this.auditService = auditService;
    }

    /**
     * Метод возвращает всё имеющиеся банковские счета
     */
    @Transactional(readOnly = true)
    public List<AccountDetails> getAll() {
        return accountDetailsRepository.findAll();
    }

    /**
     * Метод возвращает банковский счёт по заданному идентификатору
     * @param id технический идентификатор
     *@throws AccountNotFoundException если аккаунт по заданному идентификатору не найден
     */
    @Transactional(readOnly = true)
    public AccountDetails getById(Long id) {
        return accountDetailsRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public AccountDetails getByAccountNumber(Long accountNumber) {
        return accountDetailsRepository.findByAccountNumber(accountNumber);
    }

    /**
     * Метод сохраняет заданный банковский счёт
     * @param account счёт, который необходимо сохранить
     * @return сохраненный счёт с назначенным техническим идентификатором
     */
    @Transactional
    public AccountDetails create(AccountDetails account) {
        AccountDetails savedAccount = accountDetailsRepository.save(account);
        auditService.auditCreation(savedAccount);

        return savedAccount;
    }

    /**
     * Метод обновляет данные о банковском счёте по заданному идентификатору.
     * @param id технический идентификатор изменяемого счёта
     * @param updatedAccount счёт с новыми данными
     * @return обновленный счёт
     * @throws AccountNotFoundException если счёт по заданному идентификатору не найден
     */
    @Transactional
    public AccountDetails update(Long id, AccountDetails updatedAccount) {
        AccountDetails account = accountDetailsRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + id + " not found"));
        auditService.auditUpdate(updatedAccount, account);

        account.setPassportId(updatedAccount.getPassportId());
        account.setAccountNumber(updatedAccount.getAccountNumber());
        account.setBankDetailsId(updatedAccount.getBankDetailsId());
        account.setMoney(updatedAccount.getMoney());
        account.setNegativeBalance(updatedAccount.getNegativeBalance());
        account.setProfileId(updatedAccount.getProfileId());

        return account;
    }

    /**
     * Метод удаляет банковский счёт по заданному идентификатору
     * @param id технический идентификатор
     * @throws AccountNotFoundException если счёт по заданному идентификатору не найден
     */
    @Transactional
    public AccountDetails delete(Long id) {
        AccountDetails account = accountDetailsRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + id + " not found"));
        auditService.auditDelete(account);
        accountDetailsRepository.delete(account);
        return account;
    }
}
