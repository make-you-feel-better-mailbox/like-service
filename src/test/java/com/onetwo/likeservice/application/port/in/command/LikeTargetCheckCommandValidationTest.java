package com.onetwo.likeservice.application.port.in.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

class LikeTargetCheckCommandValidationTest {

    private final String userId = "testUserId";
    private final int category = 1;
    private final long targetId = 3;

    @Test
    @DisplayName("[단위][Command Validation] Like Target Check Command Validation test - 성공 테스트")
    void likeTargetCheckCommandValidationTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new LikeTargetCheckCommand(userId, category, targetId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Like Target Check Command category Validation fail test - 실패 테스트")
    void likeTargetCheckCommandCategoryValidationFailTest(String testUserId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new LikeTargetCheckCommand(testUserId, category, targetId));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Like Target Check Command category Validation fail test - 실패 테스트")
    void likeTargetCheckCommandCategoryValidationFailTest(Integer testCategory) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new LikeTargetCheckCommand(userId, testCategory, targetId));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Like Target Check Command target Id Validation fail test - 실패 테스트")
    void likeTargetCheckCommandTargetIdValidationFailTest(Long testTargetId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new LikeTargetCheckCommand(userId, category, testTargetId));
    }
}