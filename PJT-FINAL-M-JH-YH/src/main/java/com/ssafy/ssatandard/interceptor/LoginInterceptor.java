package com.ssafy.ssatandard.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * LoginInterceptor 클래스는 로그인 여부를 확인하는 인터셉터입니다.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    
    /**
     * 이 메소드는 요청을 가로채 로그인 여부를 확인합니다.
     * 
     * @param request  HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     * @param handler  핸들러 객체
     * @return true - 요청이 계속 처리됨, false - 요청 처리가 중단됨
     * @throws Exception 예외 발생 시 처리
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        HttpSession session = request.getSession();
        String loginUserId = (String) session.getAttribute("loginUserId");
        
        if (loginUserId == null) {
            return false; // 요청 처리 중단 -> 이 부분은 Vue에서 다른 페이지로 리다이렉트 처리 필요
        }
        
        return true; // 통과
    }
}
