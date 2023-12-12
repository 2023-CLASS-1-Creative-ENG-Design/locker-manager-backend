package knu.cse.locker.manager.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/* 
 * NotFoundException.java
 *
 * @note 존재하지 않는 리소스에 접근할 때 발생하는 예외
 *
 */

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
