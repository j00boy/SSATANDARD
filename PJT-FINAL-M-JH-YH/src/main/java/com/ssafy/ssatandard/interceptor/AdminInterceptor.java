package com.ssafy.ssatandard.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * AdminInterceptor 클래스는 관리자인지 여부를 확인하는 인터셉터입니다.
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {
    
    /**
     * preHandle 메소드는 컨트롤러의 핸들러 메소드가 호출되기 전에 호출됩니다.
     * 이 메소드는 요청을 가로채 관리자인지 여부를 확인합니다.
     * 
     * @param request  HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     * @param handler  핸들러 객체
     * @return true - 요청이 계속 처리됨, false - 요청 처리 중단
     * @throws Exception 예외 발생 시 처리
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        // 1. 세션에서 loginUser 정보 가져오기
        HttpSession session = request.getSession();
        String loginUserId = (String) session.getAttribute("loginUserId");
        String isAdmin = (String) session.getAttribute("isAdmin");
        
        // 2. loginUser가 null이면 로그인이 되어있지 않은 상태
        if (loginUserId == null) {
            return false; // 요청 처리 중단
        }
        
        // 3. loginUser가 일반 사용자인지 확인
        if (isAdmin.equals("0")) {
            return false; // 요청 처리 중단
        }
        
        return true; // 통과
    }
}
