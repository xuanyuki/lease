package cc.xianyu.lease.common.utils;

import cc.xianyu.lease.common.exception.LeaseException;
import cc.xianyu.lease.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;


public class JwtUtil {

  private static final SecretKey secretKey = Keys.hmacShaKeyFor("咸鱼鱼🐟🐟🐟🐟🐟🐟咸鱼鱼".getBytes());

  public static String getJwtToken(String username, String userId) {

    String jwt = Jwts.builder()
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
            .subject("后台管理系统登录")
            .claim("userId", userId)
            .claim("username", username)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact();
    return jwt;
  }


  public static void parseToken(String token) {
    try {
      if (token == null || token.isEmpty()) {
        throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);
      }
      Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
    } catch (ExpiredJwtException e) {
      throw new LeaseException(ResultCodeEnum.TOKEN_EXPIRED);
    } catch (JwtException e) {
      throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);
    }

  }


  public static void main(String[] args) {
    System.out.println(getJwtToken("admin", "1"));
  }
}
