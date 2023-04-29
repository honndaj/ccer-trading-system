package com.example.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.common.SystemString;
import com.example.entity.User;
import com.example.exception.ServiceException;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        User user;
        JWTVerifier jwtVerifier;


        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        if (StrUtil.isBlank(token)) {
            throw new ServiceException(SystemString.AUTH_NOT_SATISFY, "无token，请重新登录");
        }

        try {
            user = userService.getById(JWT.decode(token).getAudience().get(0));
        } catch (JWTDecodeException j) {
            throw new ServiceException(SystemString.AUTH_NOT_SATISFY, "token验证失败，请重新登录");
        }

        System.out.println("++++++++++++++++++++++从token中获得用户名，并查数据库++++++++++++++++++");
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("++++++++++++++++++++++结束token验证++++++++++++++++++++++++++++++++++");

        if (user == null) {
            throw new ServiceException(SystemString.AUTH_NOT_SATISFY, "用户不存在，请重新登录");
        }

        try {
            JWT.require(Algorithm.HMAC256(user.getPassword())).build().verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(SystemString.AUTH_NOT_SATISFY, "token验证失败，请重新登录");
        }
        return true;
    }
}
