package com.onetwo.likeservice.application.port.in.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class CountLikeCommandValidationTest {

    private final int category = 1;
    private final long targetId = 11;

    @Test
    @DisplayName("[단위][Command Validation] Count Like Command Validation test - 성공 테스트")
    void countLikeCommandValidationTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new CountLikeCommand(category, targetId));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Count Like Command category Validation fail test - 실패 테스트")
    void countLikeCommandCategoryValidationFailTest(Integer testCategory) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new CountLikeCommand(testCategory, targetId));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Count Like Command target Id Validation fail test - 실패 테스트")
    void countLikeCommandTargetIdValidationFailTest(Long testTargetId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new CountLikeCommand(category, testTargetId));
    }
}