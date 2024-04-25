package com.ck.backend.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    public static void main(String[] args) {
//        eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTYiLCJleHAiOjE3MDI4MjUzMDYsImlhdCI6MTcwMjc4OTMwNn0.IZQPR3gHweyIIBltpJh-oJIZc_RrSS9rVDs72HMVOK0
        String x = JwtUtil.generateToken("长沙");
        System.out.println(x);
        System.out.println(JwtUtil.extractUsername(x));
//        extractAllClaims
    }

    // 定义一个密钥
    private static final String SECRET_KEY = "secret";

    // 从token中提取用户名
    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 从token中提取过期时间
    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 从token中提取指定的属性
    public static <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims);
    }

    // 解析token中的所有属性
    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // 判断token是否过期
    private static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 根据用户信息生成token
    public static String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // 创建token
    private static String createToken(Map<String, Object> claims, String subject) {

// 创建一个Jwts.builder()对象，用于构建token
        return Jwts.builder()
                // 设置token中的claims
                .setClaims(claims).setSubject(subject)
                // 设置token的签发时间
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 设置token的过期时间
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                // 使用HS256算法签名，并设置签名密钥
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    // 验证token是否有效
    public static Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}

// 定义一个函数式接口，用于解析Claims
@FunctionalInterface
interface ClaimsResolver<T> {
    T resolve(Claims claims);
}
