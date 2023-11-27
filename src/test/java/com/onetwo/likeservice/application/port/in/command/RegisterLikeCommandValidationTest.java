package com.onetwo.likeservice.application.port.in.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

class RegisterLikeCommandValidationTest {

    private final String userId = "testUserId";
    private final int category = 1;
    private final long targetId = 11;

    @Test
    @DisplayName("[단위][Command Validation] Register Like Command Validation test - 성공 테스트")
    void registerLikeCommandValidationTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new RegisterLikeCommand(userId, category, targetId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Register Like Command user Id Validation fail test - 실패 테스트")
    void registerLikeCommandUserIdValidationFailTest(String testUserId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterLikeCommand(testUserId, category, targetId));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Register Like Command category Validation fail test - 실패 테스트")
    void registerLikeCommandCategoryValidationFailTest(Integer testCategory) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterLikeCommand(userId, testCategory, targetId));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Register Like Command target Id Validation fail test - 실패 테스트")
    void registerLikeCommandTargetIdValidationFailTest(Long testTargetId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new RegisterLikeCommand(userId, category, testTargetId));
    }
}