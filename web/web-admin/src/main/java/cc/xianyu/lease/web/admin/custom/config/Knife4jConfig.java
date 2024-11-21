package cc.xianyu.lease.web.admin.custom.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI().info(
            new Info().title("后台管理").version("1.0").description("后台管理系统接口")
    );
  }

  @Bean
  public GroupedOpenApi systemAPI() {
    return GroupedOpenApi.builder().group("系统管理")
            .pathsToMatch("/admin/system/**")
            .build();
  }

  @Bean
  GroupedOpenApi loginAPI() {
    return GroupedOpenApi.builder().group("登录管理")
            .pathsToMatch("/admin/login/**", "/admin/info")
            .build();
  }

  @Bean
  GroupedOpenApi apartmentAPI() {
    return GroupedOpenApi.builder().group("公寓管理")
            .pathsToMatch("/admin/apartment/**",
                    "/admin/room/**",
                    "/admin/label/**",
                    "/admin/facility/**",
                    "/admin/fee/**",
                    "/admin/attr/**",
                    "/admin/payment/**",
                    "/admin/region/**",
                    "/admin/term/**",
                    "/admin/file/**")
            .build();
  }

  @Bean
  GroupedOpenApi leaseAPI() {
    return GroupedOpenApi.builder().group("租赁管理")
            .pathsToMatch("/admin/appointment/**",
                    "/admin/agreement/**")
            .build();
  }


  @Bean
  GroupedOpenApi userAPI() {
    return GroupedOpenApi.builder().group("用户管理")
            .pathsToMatch("/admin/user/**")
            .build();
  }
}
