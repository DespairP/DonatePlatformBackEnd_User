package org.wangtianyu.userPlatform.Configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.wangtianyu.userPlatform.Filter.CorsFilter;
import org.wangtianyu.userPlatform.Filter.UserPasswordLoginFilter;
import org.wangtianyu.userPlatform.Model.MessageWrapper;
import org.wangtianyu.userPlatform.Security.NotLoginApiEntryPoint;
import org.wangtianyu.userPlatform.Security.PlatformUserDetailService;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Configuration
@PropertySource("classpath:Secret/JwtSecret.properties")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService platformUserDetailsService;

    @Value("${jwt.secret}")
    private String secret;

    public SpringSecurityConfig(UserDetailsService platformUserDetailsService) {
        this.platformUserDetailsService = platformUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/login","/api/login/test","/api/login/register","/logout").permitAll()
                .antMatchers("/api/projects/exhibit","/api/project/info/**","/api/project/**","/api/projects/list","/api/projects/search").permitAll()
                .antMatchers("/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .logout().and()
                .exceptionHandling().authenticationEntryPoint(new NotLoginApiEntryPoint())
        ;
        http.addFilterAt(userPasswordLoginFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    /**静态资源和POTION方法的过滤*/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/resource/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(platformUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean("userDetailService")
    public UserDetailsService userDetailsService(){
        return new PlatformUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        DelegatingPasswordEncoder encoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        encoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
        return encoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public UserPasswordLoginFilter userPasswordLoginFilter(){
        UserPasswordLoginFilter filter =  new UserPasswordLoginFilter();
        filter.setAuthenticationSuccessHandler(this::onAuthenticationSuccess);
        filter.setAuthenticationFailureHandler(this::onAuthenticationFailure);
        filter.setFilterProcessesUrl("/api/login");
        return filter;
    }

    private void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        Map<String,String> payload = ImmutableMap.of("username",authentication.getName());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,1);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String jwt = JWT.create().withPayload(payload).withExpiresAt(calendar.getTime()).sign(algorithm);
        response.addHeader("USER-TOKEN","Bearer "+jwt);
        MessageWrapper<Object> resultWrapper = new MessageWrapper<>(MessageWrapper.BasicStatus.SUCCESS, authentication.getPrincipal(),"登录成功");
        PrintWriter out = response.getWriter();
        String result = new Gson().toJson(resultWrapper);
        out.write(result);
        out.flush();
        out.close();
    }

    private void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        MessageWrapper<String> resultWrapper = new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,exception.getMessage(),exception.getMessage());
        PrintWriter out = response.getWriter();
        String result = new Gson().toJson(resultWrapper);
        out.write(result);
        out.flush();
        out.close();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.addAllowedHeader("*");
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CorsFilter corsFilter(){
        return new CorsFilter();
    }

}
