package com.roommate.roommate.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final UserDetailService userDetailService;

    @Value("${spring.jwt.secret}")
    private String secretKey = "secretKey";

    //토큰 만료 기간 60분, 토큰이 무한정으로 사용되면 안되기 때문
    private final long tokenValidMillisecond = 1000L * 60 * 60;

    @PostConstruct
    protected void init(){
        log.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        //secretKey를 Base64로 인코딩
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        log.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    public String createToken(String uid, String role){
        log.info("[createToken] 토큰 생성 시작");
        //토큰의 키가 되는 subject를 중복되지 않는 고유한 값으로 지정
        Claims claims = Jwts.claims().setSubject(uid);
        claims.put("role",role);
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+tokenValidMillisecond))
                //서명할 때 사용되는 알고리즘은 HS256, 키는 위에서 지정한 값으로 진행
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        log.info("[createToken] 토큰 생성 완료");
        return token;
    }

    //토큰으로 인증 객체를 얻기 위한 메소드
    public Authentication getAuthentication(String token){
        log.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        //실제 DB에 저장되어 있는 회원 객체를 끌고와 인증처리를 진행
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUsername(token));

        log.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails email : {}", userDetails.getUsername());
        //시큐리티에서 AuthenticationToken을 만들어서 반환
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    //Id를 얻기위해 실제로 토큰을 디코딩
    public String getUsername(String token){
        log.info("[getEmail] 토큰 기반 회원 구별 정보 추출");

        //지정된 SecretKey를 통해 서명된 JWT를 해석하여 subject를 끌고와 리턴하여 이를 통해 인증 객체 가져온다.
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

        log.info("[getEmail] 토큰 기반 회원 구별 정보 추출 완료, info : {}",info);
        return info;
    }

    //토큰 HTTP 헤더에 저장되어 계속적으로 이동 - 토큰을 사용하기 위해 실제로 Header에서 꺼내오는 메소드
    public String resolveToken(HttpServletRequest request){
        log.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("JWT");
    }

    //토큰 만료됐는지 확인
    public boolean validateToken(String token){
        log.info("[validateToken] 토큰 유효 체크 시작");
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            //토큰을 디코딩하여 만료시간을 끌고와 현재시간과 비교해 확인
            return !claims.getBody().getExpiration().before(new Date());
        }catch(Exception e){
            log.info("[validateToken] 토큰 유효 체크 예외 발생");
            return false;
        }
    }
}
