package org.wangtianyu.userPlatform.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.wangtianyu.userPlatform.Apis.UserLoginController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.Reader;

@Component
public class UserPasswordLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationBean authenticationBean = null;


    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //不支持Post方法就抛出异常
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        authenticationBean = new ObjectMapper().readValue(request.getInputStream(),AuthenticationBean.class);
        if(authenticationBean == null) authenticationBean = new AuthenticationBean("","");
        String username = obtainUsername(request);
        username = username = (username != null) ? username : "";
        username = username.trim();
        String password = obtainPassword(request);
        password = (password != null) ? password : "";
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        AuthenticationManager authenticationManager = getAuthenticationManager();
        return authenticationManager.authenticate(authRequest);
    }


    @SneakyThrows
    @Override
    protected String obtainUsername(HttpServletRequest request) {
        if(request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) return authenticationBean.getUsername();
        if(request.getContentType().equals(MediaType.TEXT_HTML_VALUE)) return super.obtainUsername(request);
        return super.obtainUsername(request);
    }

    @SneakyThrows
    @Override
    protected String obtainPassword(HttpServletRequest request) {
        if(request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) return authenticationBean.getPassword();
        if(request.getContentType().equals(MediaType.TEXT_HTML_VALUE)) return super.obtainPassword(request);
        return super.obtainUsername(request);
    }

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
@Data
@AllArgsConstructor
@NoArgsConstructor
class AuthenticationBean{
    private String username;
    private String password;
}