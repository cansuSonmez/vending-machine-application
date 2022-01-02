package com.exercise.vendingmachine.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class FilterConfig  implements Filter {

    public static String IP_ADDRESS;
    public static String SESSION_ID;
    public static String URL_ADDRESS;
    public static String USER_AGENT;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        // IP Address
        IP_ADDRESS = req.getRemoteAddr();

        // Session
        SESSION_ID = req.getSession().getId();

        // Url
        URL_ADDRESS = req.getRequestURL().toString();

        //User Agent
        USER_AGENT = req.getHeader("User-Agent") != null
                ? req.getHeader("User-Agent")
                : req.getHeader("user-agent");

        System.out.println(IP_ADDRESS + SESSION_ID + "rest bilgileri" +
                "");
        filterChain.doFilter(req, res);
    }
}
