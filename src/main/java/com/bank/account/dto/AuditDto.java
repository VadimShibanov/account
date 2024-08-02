package com.bank.account.dto;

import lombok.Data;

import java.time.ZonedDateTime;

/**
 * Класс для передачи и принятия данных об аудите
 * @see com.bank.account.entity.Audit
 */
@Data
public class AuditDto {
    private Long id;
    private String entityType;
    private String operationType;
    private String createdBy;
    private String modifiedBy;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;
    private String newEntityJson;
    private String entityJson;
}
