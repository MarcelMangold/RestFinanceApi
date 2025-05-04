package com.mysticalducks.rest.finance.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApiError {
	DATA_NOT_FOUND(1, "Data not found"),
    USER_NOT_FOUND(2, "User not found"),
    ICON_NOT_FOUND(3, "Icon not found"),
    CATEGORY_NOT_FOUND(4, "Category not found"),
    TRANSACTION_NOT_FOUND(5, "Transaction not found"),
	PARTY_NOT_FUND(6, "Transaction not found"),
	PARTY_MEMBER_NOT_FOUND(7, "Party member not found"),
	FINANCE_INFORMATION_NOT_FOUND(8, "Finance information not found"),
	DATABASE_IS_NOT_AVAILABLE(9, "Database is not available");

    private int code;
    private String message;

    ApiError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
	

