package com.roommate.roommate.feign.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "oauth2.kakao")
@ConstructorBinding
public class KakaoProperties {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
