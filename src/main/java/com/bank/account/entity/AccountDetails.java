package com.bank.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Сущность, представляющая банковский счёт
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_details", schema = "account")
public class AccountDetails {

    /**
     * Технический идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Технический идентификатор паспорта
     */
    @Column(name = "passport_id", nullable = false)
    private Long passportId;

    /**
     * Номер счёта
     */
    @Column(name = "account_number", nullable = false, unique = true)
    private Long accountNumber;

    /**
     * Технический идентификатор на реквизиты банка
     */
    @Column(name = "bank_details_id", nullable = false)
    private Long bankDetailsId;

    /**
     * Деньги, которые лежат на счёте
     */
    @Column(name = "money", nullable = false, precision = 20, scale = 2)
    private BigDecimal money;

    /**
     * Флаг, показывающий есть ли минус на счёте
     */
    @Column(name = "negative_balance", nullable = false)
    private Boolean negativeBalance;

    /**
     * Технический идентификатор профиля
     */
    @Column(name = "profile_id", nullable = false)
    private Long profileId;
}
