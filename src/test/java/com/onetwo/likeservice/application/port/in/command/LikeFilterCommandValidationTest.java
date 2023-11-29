package com.onetwo.likeservice.application.port.in.command;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;

class LikeFilterCommandValidationTest {

    private final int category = 1;
    private final long targetId = 3;
    private final String userId = "testUserId";
    private final Instant filterStartDate = Instant.parse("2000-01-01T00:00:00Z");
    private final Instant filterEndDate = Instant.parse("4000-01-01T00:00:00Z");
    private final PageRequest pageRequest = PageRequest.of(0, 20);

    @Test
    @DisplayName("[단위][Command Validation] Like Filter Command Validation test - 성공 테스트")
    void likeFilterCommandValidationSuccessTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new LikeFilterCommand(userId, category, targetId, filterStartDate, filterEndDate, pageRequest));
    }

    @Test
    @DisplayName("[단위][Command Validation] Like Filter Command Validation null test - 성공 테스트")
    void likeFilterCommandValidationNullSuccessTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new LikeFilterCommand(null, null, null, null, null, pageRequest));
    }

    @Test
    @DisplayName("[단위][Command Validation] Like Filter Command Validation Target null test - 성공 테스트")
    void likeFilterCommandValidationTargetNullSuccessTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new LikeFilterCommand(userId, null, null, null, null, pageRequest));
    }

    @Test
    @DisplayName("[단위][Command Validation] Like Filter Command Validation user id null test - 성공 테스트")
    void likeFilterCommandValidationUserIdNullSuccessTest() {
        //given when then
        Assertions.assertDoesNotThrow(() -> new LikeFilterCommand(null, category, targetId, null, null, pageRequest));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("[단위][Command Validation] Delete Like Command pageable Validation fail test - 실패 테스트")
    void likeFilterCommandValidationPageableNullSuccessTest(PageRequest testPageable) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException.class, () -> new LikeFilterCommand(null, null, null, null, null, testPageable));
    }
}