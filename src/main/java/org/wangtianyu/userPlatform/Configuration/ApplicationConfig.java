package org.wangtianyu.userPlatform.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**拦截器配置，注意拦截器在过滤器之后，所以主要Cors的实现为{@link org.springframework.web.filter.CorsFilter}实现*/
@Configuration
public class ApplicationConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("Content-Type,Token")
                .allowedMethods("GET,POST,PUT,DELETE,OPTIONS")
                .allowCredentials(true); //允许携带Cookie
    }
}
