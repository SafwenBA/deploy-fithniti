package com.team.fithniti.demo.config.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;


@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authException) throws IOException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//        final String expired = (String) httpServletRequest.getAttribute("expired");
//        System.out.println(expired);
//        if (expired!=null){
//            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,expired);
//        }
//        else{
//            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid Credentials");
//        }
    }

}
