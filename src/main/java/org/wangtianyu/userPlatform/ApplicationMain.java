package org.wangtianyu.userPlatform;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.wangtianyu.userPlatform.Utils.FileOSSUploadUtil;

import javax.servlet.Filter;

@Configuration
@EnableWebMvc
@ComponentScan
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(value = "org.wangtianyu.userPlatform.Service.Impl")
public class ApplicationMain {
    //DO NOTHING

    @Bean
    public Filter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public FileOSSUploadUtil fileOSSUploadUtil(){
        return new FileOSSUploadUtil();
    }
}