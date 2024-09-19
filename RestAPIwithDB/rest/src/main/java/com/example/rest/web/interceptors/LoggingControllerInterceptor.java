package com.example.rest.web.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoggingControllerInterceptor implements HandlerInterceptor
{
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
        log.info("LoggingControllerInterceptor -> Ответ отправлен клиенту");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        log.info("LoggingControllerInterceptor -> Запрос обработан. Подготовка к отправке клиенту");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        log.info("LoggingControllerInterceptor -> Запрос готовится к отправке в контроллер");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
