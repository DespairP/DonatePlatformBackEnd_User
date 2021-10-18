package org.wangtianyu.userPlatform.Security;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.wangtianyu.userPlatform.Model.MessageWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class NotLoginApiEntryPoint implements AuthenticationEntryPoint {
    private MessageWrapper<String> wrapper = new MessageWrapper<>(MessageWrapper.BasicStatus.FAILED,null,null);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        wrapper.setMessage(authException.getMessage());
        wrapper.setData("authentication required");
        String result = new Gson().toJson(wrapper);
        out.write(result);
        out.flush();
        out.close();
    }
}
