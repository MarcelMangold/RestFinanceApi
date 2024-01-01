package com.mysticalducks.rest.finance.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApiError {
	DATA_NOT_FOUND(1, "Data not found"),
    USER_NOT_FOUND(2, "User not found"),
    CHAT_NOT_FOUND(3, "Chat not found"),
    ICON_NOT_FOUND(4, "Icon not found"),
    CATEGORY_NOT_FOUND(5, "Category not found"),
    TRANSACTION_NOT_FOUND(5, "Transaction not found");

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
	

