package com.example.mispro.Interceptor;

import org.aopalliance.intercept.Interceptor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {

        if (request.getSession().getAttribute("user")==null)
        {
            request.getRequestDispatcher("/word/login").forward(request,response);
            return false;
        }
        else {
            return true;
        }

    }
}
