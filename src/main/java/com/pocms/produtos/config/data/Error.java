package com.pocms.produtos.config.data;

import java.util.Date;
import java.util.Objects;

public class Error {

    private final Date timestamp = new Date();
    private final String status;
    private final String userMessage;
    private final String developerMessage;

    public Error(String status, String userMessage, String developerMessage) {
        this.status = Objects.requireNonNull(status);
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }
}
