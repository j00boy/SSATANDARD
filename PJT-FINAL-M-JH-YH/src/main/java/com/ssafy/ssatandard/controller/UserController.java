package com.ssafy.ssatandard.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssatandard.model.dto.Order;
import com.ssafy.ssatandard.model.dto.User;
import com.ssafy.ssatandard.model.service.OrderService;
import com.ssafy.ssatandard.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

/**
 * 여기서는 "/mypage" URL 패턴에 매핑됩니다.
 */
@RestController
@RequestMapping("/mypage")
@Tag(name = "User Controller", description = "사용자 관련 작업을 처리하는 컨트롤러입니다.")
public class UserController {
    
    private final UserService userService;
    private final OrderService orderService;
    
    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }
    
    /**
     * 내 주문 조회를 담당하는 메소드입니다.
     * 
     * @param session HTTP 세션
     * @return 해당 사용자의 주문 리스트
     */
    @GetMapping("/orders")
    @Operation(summary="내 주문 조회", description="해당 사용자의 주문을 조회한다.")
    public ResponseEntity<?> checkMyOrders(HttpSession session) {
        String userId = (String) session.getAttribute("loginUserId");
        List<Order> distinctOrders = orderService.searchByUserId(userId);
        return new ResponseEntity<>(distinctOrders, HttpStatus.OK);
    }
    
    /**
     * 회원 탈퇴 요청을 처리하는 메소드입니다.
     * 
     * @param session HTTP 세션
     * @param request 요청 정보 (userId 등)
     * @return 탈퇴 처리 결과
     */
    @Transactional
    @PostMapping("/withdraw")
    @Operation(summary="회원 탈퇴", description="해당 사용자의 정보를 삭제한다.")
    public ResponseEntity<?> withdrawUser(HttpSession session, @RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String loginUserId = (String) session.getAttribute("loginUserId");
        User loginUser = userService.searchByUserId(loginUserId);

        if (loginUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        if (loginUser.getUserId().equals(userId)) {
            userService.deleteUser(userId);
            session.invalidate();
            return ResponseEntity.ok("Withdrawal && Logged out successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to withdraw");
        }
    }
    
    /**
     * 내 정보 수정을 담당하는 메소드입니다.
     * 
     * @param session HTTP 세션
     * @param userInfo 수정할 사용자 정보
     * @return 수정 처리 결과
     */
    @PutMapping("/edit")
    @Operation(summary="회원 정보 수정", description = "사용자 정보를 수정한다.")
    public ResponseEntity<?> modifyUserInfo(HttpSession session, @RequestBody Map<String, String> userInfo) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        int result = userService.updateUser(userInfo);
        if (result > 0) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
    
    /**
     * 내 정보 수정을 위한 현재 로그인된 사용자의 정보를 조회하는 메소드입니다.
     * 
     * @param session HTTP 세션
     * @return 로그인된 사용자 정보
     */
    @GetMapping("/edit")
    @Operation(summary="사용자 정보 조회", description = "내 정보 수정을 위한 현재 로그인된 사용자 정보를 조회한다.")
    public ResponseEntity<?> getLoginUser(HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        User loginUser = userService.searchByUserId(loginUserId);
        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }
    
    /**
     * 내 정보를 조회하는 메소드입니다.
     * 
     * @param session HTTP 세션
     * @return 로그인된 사용자 정보
     */
    @GetMapping("/myInfo")
    @Operation(summary="내 정보 조회", description = "현재 로그인된 사용자의 정보를 조회한다.")
    public ResponseEntity<?> getMyInfo(HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        User loginUser = userService.searchByUserId(loginUserId);
        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }
}
