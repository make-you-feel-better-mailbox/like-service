package com.onetwo.likeservice.common.exceptions;

import lombok.Getter;

@Getter
public class BadResponseException extends RuntimeException {

    public BadResponseException(String message) {
        super(message);
    }
}
