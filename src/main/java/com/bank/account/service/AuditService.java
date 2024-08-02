package com.bank.account.service;

import com.bank.account.entity.AccountDetails;
import com.bank.account.entity.Audit;
import com.bank.account.repository.AuditRepository;
import com.bank.account.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Сервис аудита
 */
@Service
public class AuditService {

    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Transactional(readOnly = true)
    public List<Audit> getAll() {
        return auditRepository.findAll();
    }

    /**
     * Метод фиксирует создание новой сущности {@link AccountDetails}
     * @param account создаваемый банковский счёт
     */
    @Transactional
    public Audit auditCreation(AccountDetails account) {
        Audit audit = new Audit();

        audit.setEntityType("AccountDetails");
        audit.setOperationType("CREATE");
        audit.setCreatedBy(account.getAccountNumber().toString());
        audit.setCreatedAt(ZonedDateTime.now());
        audit.setEntityJson(JsonUtils.toJson(account));

        return auditRepository.save(audit);
    }

    /**
     * Метод фиксирует обновление сущности {@link AccountDetails}
     * @param updatedAccount обновленный банковский счёт
     * @param account старый банковский счёт
     */
    @Transactional
    public Audit auditUpdate(AccountDetails updatedAccount, AccountDetails account) {
        Audit audit = new Audit();

        audit.setEntityType("AccountDetails");
        audit.setOperationType("UPDATE");
        audit.setCreatedBy(updatedAccount.getAccountNumber().toString());
        audit.setCreatedAt(ZonedDateTime.now());
        audit.setEntityJson(JsonUtils.toJson(account));
        audit.setNewEntityJson(JsonUtils.toJson(updatedAccount));

        return auditRepository.save(audit);
    }

    /**
     * Метод фиксирует удаление сущности {@link AccountDetails}
     * @param account удаляемый банковский счёт
     */
    @Transactional
    public Audit auditDelete(AccountDetails account) {
        Audit audit = new Audit();

        audit.setEntityType("AccountDetails");
        audit.setOperationType("DELETE");
        audit.setCreatedBy(account.getAccountNumber().toString());
        audit.setCreatedAt(ZonedDateTime.now());
        audit.setEntityJson(JsonUtils.toJson(account));

        return auditRepository.save(audit);
    }
}
