package knu.cse.locker.manager.infra.authcode;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Service;

import knu.cse.locker.manager.global.exception.NotFoundException;
import knu.cse.locker.manager.global.utils.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * AuthcodeService.java
 *
 * @note 인증 코드 생성 및 검증을 위한 서비스
 *
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthcodeService {
    private final RedisUtil redisUtil;

    public String createCode() {
        int LENGTH = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < LENGTH; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("인증 코드 생성 오류");
            return null;
        }
    }

    public void validCode(String email, String code) {
        String getCode = (String) redisUtil.get(email);

        if (!getCode.equals(code)) {
            throw new IllegalStateException("유효하지 않는 인증코드");
        }

        redisUtil.set(code, code);
        redisUtil.expire(code, 300);
    }

    public void checkedFromAuthCode(String code) {
        String _code = (String) redisUtil.get(code);

        if (!code.equals(_code)) {
            throw new NotFoundException("이메일 인증을 하지 않은 유저가 이메일 설정을 시도합니다");
        }
    }

    public void delCodeFromRedis(String code) {
        redisUtil.del(code);
    }

}
