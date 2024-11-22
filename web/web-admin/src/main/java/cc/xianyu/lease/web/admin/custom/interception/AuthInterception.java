package cc.xianyu.lease.web.admin.custom.interception;

import cc.xianyu.lease.common.exception.LeaseException;
import cc.xianyu.lease.common.result.ResultCodeEnum;
import cc.xianyu.lease.common.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterception implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String token = request.getHeader("Authorization");
    JwtUtil.parseToken(token);
    return true;
  }
}
