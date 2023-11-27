package com.onetwo.likeservice.application.port.in.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

class DeleteLikeCommandValidationTest {

    private final String userId = "testUserId";
    private final int category = 1;
    private final long targetId = 11;

    @Test
    @DisplayName("[단위][Command Validation] Delete Like Command Validation test - 성공 테스트")
    void deleteLikeCommandValidationTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new DeleteLikeCommand(userId, category, targetId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("[단위][Command Validation] Delete Like Command user Id Validation fail test - 실패 테스트")
    void deleteLikeCommandUserIdValidationFailTest(String testUserId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new DeleteLikeCommand(testUserId, category, targetId));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Delete Like Command category Validation fail test - 실패 테스트")
    void deleteLikeCommandCategoryValidationFailTest(Integer testCategory) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new DeleteLikeCommand(userId, testCategory, targetId));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Delete Like Command target Id Validation fail test - 실패 테스트")
    void deleteLikeCommandTargetIdValidationFailTest(Long testTargetId) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new DeleteLikeCommand(userId, category, testTargetId));
    }
}