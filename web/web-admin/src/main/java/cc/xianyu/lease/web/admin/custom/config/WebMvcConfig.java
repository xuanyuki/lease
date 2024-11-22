package cc.xianyu.lease.web.admin.custom.config;

import cc.xianyu.lease.web.admin.custom.interception.AuthInterception;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new AuthInterception())
            .addPathPatterns("/admin/**")
            .excludePathPatterns("/admin/login/**");
  }
}
