package knu.cse.locker.manager.auth.controller;

import knu.cse.locker.manager.account.service.AccountService;
import knu.cse.locker.manager.auth.dto.TokenDto;
import knu.cse.locker.manager.auth.dto.request.LoginRequestDto;
import knu.cse.locker.manager.auth.dto.request.RegisterRequestDto;
import knu.cse.locker.manager.auth.dto.response.LoginResponseDto;
import knu.cse.locker.manager.auth.service.AuthService;
import knu.cse.locker.manager.utils.api.ApiUtil;
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
        String schoolNum = loginRequestDto.getSchoolNum();
        String password = loginRequestDto.getPassword();
        LoginResponseDto loginResponseDto = authService.login(schoolNum, password);

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
    public ApiUtil.ApiSuccessResult<String> isValidSchoolNum(
            @RequestParam("school-num") String schoolNum
    ) {
        if (accountService.isAccountBySchoolNum(schoolNum)) {
            throw new IllegalStateException("중복된 학번 입니다.");
        }

        return ApiUtil.success("사용 가능한 학번 입니다.");
    }
}