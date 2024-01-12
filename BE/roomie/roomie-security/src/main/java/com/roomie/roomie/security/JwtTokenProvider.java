package com.roomie.roomie.security;

import com.roomie.roomie.exception.CustomException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.roomie.roomie.exception.ExceptionCode.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final UserDetailService userDetailService;

    @Value("${spring.jwt.secret}")
    private String secretKey = "secretKey";
    private Long time= 1209600000L;

    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    @PostConstruct
    protected void init(){
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createToken(Long userId, String role, String type, Long time){

        long now = (new Date()).getTime();
        Date validity = new Date(now + time);

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("role", role)
                .claim("type", type)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();
    }

    public String generateAccessToken(Long userId, String role){
        return createToken(userId, role,"ACEESS_TOKEN", time);
    }

    public String generateRefreshToken(Long userId, String role){
        return createToken(userId, role,"REFRESH_TOKEN", time);
    }


    //토큰으로 인증 객체를 얻기 위한 메소드
    public Authentication getAuthentication(String token){
        //실제 DB에 저장되어 있는 회원 객체를 끌고와 인증처리를 진행
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUsername(token));

        if (userDetails != null) {
            //시큐리티에서 AuthenticationToken을 만들어서 반환
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } else {
            throw new CustomException(null,USER_NOT_FOUND);
        }
    }

    //Id를 얻기위해 실제로 토큰을 디코딩
    public String getUsername(String token){

        //지정된 SecretKey를 통해 서명된 JWT를 해석하여 subject를 끌고와 리턴하여 이를 통해 인증 객체 가져온다.
        String info = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody().getSubject();

        return info;
    }

    //토큰 HTTP 헤더에 저장되어 계속적으로 이동 - 토큰을 사용하기 위해 실제로 Header에서 꺼내오는 메소드
    public String resolveToken(HttpServletRequest request){
        log.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("JWT");
    }

    //토큰 만료됐는지 확인
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            //토큰을 디코딩하여 만료시간을 끌고와 현재시간과 비교해 확인
            return true;
        }catch (SignatureException e) {
            log.error("SignatureException occurred: {}", e.getMessage());
            throw new CustomException(e,JWT_CHARACTER_INVALID);
        } catch (ExpiredJwtException e) {
            throw new CustomException(e,EXPRIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(e,UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new CustomException(e,INCORRECT_TOKEN);
        }catch(Exception e){
            return false;
        }

    }
}
