package knu.cse.locker.manager.global.config;

import knu.cse.locker.manager.domain.account.repository.AccountRepository;
import knu.cse.locker.manager.global.security.filter.JwtAuthenticationFilter;
import knu.cse.locker.manager.global.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspection) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable);

        httpSecurity
                .authorizeRequests()
                    .antMatchers("/admin/**").hasAnyRole("MANAGER")
                    .antMatchers("/accounts/**").hasAnyRole("STUDENT", "MANAGER")
                    .antMatchers("/records/**").hasAnyRole("STUDENT", "MANAGER")
                    .antMatchers("/lockers/**").hasAnyRole("STUDENT", "MANAGER")
                    .antMatchers("/auth/**", "/hardware/**", "/swagger-ui/**").permitAll();

        httpSecurity
                .addFilterBefore(new JwtAuthenticationFilter(accountRepository, jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .httpBasic().disable();

        return httpSecurity.build();
    }
}
