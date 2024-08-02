package com.bank.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Сущность, представляющая банковский счёт
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit", schema = "account")
public class Audit {

    /**
     * Технический идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Тип сущности
     */
    @Column(name = "entity_type", length = 40)
    private String entityType;

    /**
     * Тип операции
     */
    @Column(name = "operation_type")
    private String operationType;

    /**
     * Кем создан
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * Кем изменён
     */
    @Column(name = "modified_by")
    private String modifiedBy;

    /**
     * Когда создан
     */
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    /**
     * Когда изменён
     */
    @Column(name = "modified_at")
    private ZonedDateTime modifiedAt;

    /**
     * JSON, заполняется при изменении
     */
    @Column(name = "new_entity_json", columnDefinition = "TEXT")
    private String newEntityJson;

    /**
     * JSON, заполняется при изменении и создании
     */
    @Column(name = "entity_json", columnDefinition = "TEXT")
    private String entityJson;
}
