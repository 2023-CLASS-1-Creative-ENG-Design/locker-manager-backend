package knu.cse.locker.manager.domain.auth.controller;

import knu.cse.locker.manager.domain.account.service.AccountService;
import knu.cse.locker.manager.domain.auth.dto.TokenDto;
import knu.cse.locker.manager.domain.auth.dto.request.LoginRequestDto;
import knu.cse.locker.manager.domain.auth.dto.request.RegisterRequestDto;
import knu.cse.locker.manager.domain.auth.dto.response.LoginResponseDto;
import knu.cse.locker.manager.domain.auth.service.AuthService;
import knu.cse.locker.manager.global.utils.api.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AccountService accountService;
    @PostMapping("/register")
    public ApiUtil.ApiSuccessResult<Long> signUp(
            @Valid @RequestBody RegisterRequestDto requestDto
    ){

        return ApiUtil.success(authService.registerForStudent(requestDto));
    }

    @PostMapping("/login")
    public ApiUtil.ApiSuccessResult<ResponseEntity<LoginResponseDto>> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto
    ){
        String schoolNumber = loginRequestDto.getSchoolNumber();
        String password = loginRequestDto.getPassword();
        LoginResponseDto loginResponseDto = authService.login(schoolNumber, password);

        TokenDto tokenDto = loginResponseDto.getTokenDto();

        String authorization = tokenDto.getGrantType() + " " + tokenDto.getAccessToken();

        return ApiUtil.success(ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .body(loginResponseDto));
    }

    @GetMapping("/email-valid")
    public ApiUtil.ApiSuccessResult<String> isValidEmail(
            @RequestParam("email") String email
    ) {
        if (accountService.isAccountByEmail(email)) {
            throw new IllegalStateException("중복된 이메일 입니다.");
        }

        return ApiUtil.success("사용 가능한 이메일 입니다.");
    }

    @GetMapping("/school-num-valid")
    public ApiUtil.ApiSuccessResult<String> isValidSchoolNumber(
            @RequestParam("school-num") String schoolNumber
    ) {
        if (accountService.isAccountBySchoolNumber(schoolNumber)) {
            throw new IllegalStateException("중복된 학번 입니다.");
        }

        return ApiUtil.success("사용 가능한 학번 입니다.");
    }
}