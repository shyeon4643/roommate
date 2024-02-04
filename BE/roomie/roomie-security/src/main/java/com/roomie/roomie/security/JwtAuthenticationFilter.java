package com.roomie.roomie.security;

import com.roomie.roomie.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.roomie.roomie.exception.ExceptionCode.INVALID_TOKEN;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    //인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 탐
    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 헤더에서 토큰 받아오기
            String token = jwtTokenProvider.resolveToken(servletRequest);

            // 토큰이 유효하다면
            if (token != null && jwtTokenProvider.validateToken(token)) {
                // 토큰으로부터 유저 정보를 받아
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                // SecurityContext에 Authentication 객체 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // 다음 Filter 실행, 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (RuntimeException e) {
            throw new CustomException(e, INVALID_TOKEN);
        }

    }
}