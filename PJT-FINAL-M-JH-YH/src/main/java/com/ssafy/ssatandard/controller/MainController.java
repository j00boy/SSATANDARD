package com.ssafy.ssatandard.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssatandard.model.dto.User;
import com.ssafy.ssatandard.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

/**
 * 여기서는 기본 경로 -> 메인 페이지("")에 매핑됩니다.
 */
@RestController
@RequestMapping("")
@Tag(name = "Main Controller", description = "회원가입, 로그인, 로그아웃을 처리하는 컨트롤러입니다.")
public class MainController {

    private UserService userService;

    @Autowired
    MainController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입 요청을 처리하는 메소드입니다.
     * 
     * @param requestUser 회원가입 요청 정보
     * @return 회원가입 처리 결과
     */
    @Transactional
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "사용자 회원가입 요청을 처리합니다.")
    public ResponseEntity<?> regist(@RequestBody User requestUser) {
        int result = userService.insertUser(requestUser);
        if (result > 0) {
            System.out.println("회원가입 성공");
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 로그인 요청을 처리하는 메소드입니다.
     * 
     * @param loginInfo 로그인 요청 정보 (userId, password)
     * @param session HTTP 세션
     * @return 로그인 처리 결과
     * @throws Exception 예외 처리
     */
    @Transactional
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 로그인 요청을 처리합니다.")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginInfo, HttpSession session) throws Exception {
        User requestUser = userService.selectOne(loginInfo);
        if (requestUser != null) {
            session.setAttribute("loginUserId", requestUser.getUserId());
            session.setAttribute("isAdmin", requestUser.getIsAdmin());
            return new ResponseEntity<>(requestUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid userId or password", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 로그아웃 요청을 처리하는 메소드입니다.
     * 
     * @param session HTTP 세션
     * @return 로그아웃 처리 결과
     */
    @GetMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자 로그아웃 요청을 처리합니다.")
    public ResponseEntity<?> logout(HttpSession session) {
        session.removeAttribute("loginUserId");
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }
}
