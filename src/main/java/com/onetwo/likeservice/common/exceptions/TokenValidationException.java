package com.onetwo.likeservice.common.exceptions;

import com.onetwo.likeservice.common.jwt.JwtCode;
import lombok.Getter;

@Getter
public class TokenValidationException extends RuntimeException {

    public TokenValidationException(JwtCode code) {
        super(code.getValue());
    }
}
