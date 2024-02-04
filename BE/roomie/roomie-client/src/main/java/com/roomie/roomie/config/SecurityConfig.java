package com.roomie.roomie.config;

import com.roomie.roomie.security.CustomAccessDeniedHandler;
import com.roomie.roomie.security.CustomAuthenticationEntryPoint;
import com.roomie.roomie.security.JwtAuthenticationFilter;
import com.roomie.roomie.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity // 웹 보안 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//사이트 간 요청 위조
                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/join", "/").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
    }
}
