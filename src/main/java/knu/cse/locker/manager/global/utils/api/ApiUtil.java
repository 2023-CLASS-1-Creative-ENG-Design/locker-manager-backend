package knu.cse.locker.manager.global.utils.api;

import lombok.Getter;

/* 
 * ApiUtil.java
 *
 * @note API 응답에 사용되는 유틸 클래스
 *
 * @see knu.cse.locker.manager.domain.account.controller.AccountController#updateAccountEmail(ChangeEmailRequestDto, Authentication)
 *
 */

public class ApiUtil {

    public static <T> ApiSuccessResult<T> success(T response) {
        return new ApiSuccessResult<>(response);
    }

    public static <T> ApiErrorResult<T> error(int code, T message) {
        return new ApiErrorResult<>(code, message);
    }

    @Getter
    public static class ApiSuccessResult<T> {
        private final T response;

        private ApiSuccessResult(T response) {
            this.response = response;
        }

    }

    public static class ApiErrorResult<T> {
        private final int statusCode;

        private ApiErrorResult(int statusCode, T message) {
            this.statusCode = statusCode;
        }

        public int getStatus() {
            return statusCode;
        }

    }

}
