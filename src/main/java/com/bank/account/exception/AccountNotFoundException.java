package com.bank.account.exception;

/**
 * Исключение, бросаемое, если банковский счёт {@link com.bank.account.entity.AccountDetails} не найден
 */
public class AccountNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Account not found";

    public AccountNotFoundException() {
        this(DEFAULT_MESSAGE);
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotFoundException(Throwable cause) {
        this(DEFAULT_MESSAGE, cause);
    }
}
