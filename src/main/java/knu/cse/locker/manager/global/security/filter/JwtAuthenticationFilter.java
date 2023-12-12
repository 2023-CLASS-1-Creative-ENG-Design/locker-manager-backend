package knu.cse.locker.manager.global.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import knu.cse.locker.manager.domain.account.repository.AccountRepository;
import knu.cse.locker.manager.global.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

/* 
 * JwtAuthenticationFilter.java
 *
 * @note JWT 인증 필터
 *
 */

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getMethod().equals("OPTIONS")){
            chain.doFilter(request, response);
            return;
        }

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token, accountRepository);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
