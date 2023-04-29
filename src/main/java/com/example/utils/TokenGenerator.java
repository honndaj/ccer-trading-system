package com.example.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenGenerator {

    public static String genToken(String userId, String sign) {
        return JWT.create().withAudience(userId)
                .withExpiresAt(DateUtil.offsetHour(new Date(), 6))
                .sign(Algorithm.HMAC256(sign));
    }
}
