package com.ssafy.ssatandard.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssatandard.model.dto.Order;
import com.ssafy.ssatandard.model.dto.Product;
import com.ssafy.ssatandard.model.dto.SearchCondition;
import com.ssafy.ssatandard.model.dto.User;
import com.ssafy.ssatandard.model.service.CartService;
import com.ssafy.ssatandard.model.service.OrderService;
import com.ssafy.ssatandard.model.service.ProductService;
import com.ssafy.ssatandard.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

/**
 * @RestController : 해당 클래스가 RESTful 웹 서비스의 컨트롤러
 * 각 메소드가 HTTP 요청을 처리하고 JSON 형식의 응답을 반환
 *
 * @RequestMapping : 클래스의 요청 URL 패턴을 지정
 * 여기서는 "/admin" URL 패턴에 매핑됩니다.
 */
@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Controller", description = "관리자 관련 작업을 처리하는 컨트롤러입니다.")
public class AdminController {

    private final CartService cartService;
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public AdminController(CartService cartService, OrderService orderService, ProductService productService,
                           UserService userService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
    }

    /**
     * 전체 주문을 조회하는 메소드입니다.
     * 
     * @param condition 검색 조건
     * @return 전체 주문 리스트
     */
    @GetMapping("/orders")
    @Operation(summary = "전체 주문 조회", description = "전체 주문을 조회합니다.")
    public ResponseEntity<?> listOrders(@ModelAttribute SearchCondition condition) {
        List<Order> list = orderService.search(condition);
        if (list == null || list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 관리자 페이지에서 상품을 등록하는 메소드입니다.
     * 
     * @param product 등록할 상품 정보
     * @return 등록 결과
     */
    @Transactional
    @PostMapping("/regist")
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    public ResponseEntity<?> insertProduct(@RequestBody Product product) {
        if (productService.insertProduct(product)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 관리자 페이지에서 모든 상품을 조회하거나 검색 조건에 따라 필터링하는 메소드입니다.
     * 
     * @param condition 검색 조건
     * @return 상품 리스트
     */
    @GetMapping("/products")
    @Operation(summary = "상품 조회", description = "모든 상품을 조회하거나 검색 조건에 따라 필터링합니다.")
    public ResponseEntity<?> list(@ModelAttribute SearchCondition condition) {
        List<Product> list = productService.search(condition);
        if (list == null || list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 관리자 페이지에서 회원을 검색하는 메소드입니다.
     * 
     * @param condition 검색 조건
     * @return 회원 리스트
     */
    @GetMapping("/users")
    @Operation(summary = "회원 조회", description = "회원 검색 조건에 따라 회원을 검색합니다.")
    public ResponseEntity<?> listUsers(@ModelAttribute SearchCondition condition) {
        List<User> list = userService.search(condition);
        if (list == null || list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 등록된 상품을 수정하는 메소드입니다.
     * 
     * @param productInfo 수정할 상품 정보
     * @return 수정 결과
     */
    @Transactional
    @PutMapping("/products/edit")
    @Operation(summary = "상품 수정", description = "등록된 상품 정보를 수정합니다.")
    public ResponseEntity<?> modifyProduct(@RequestBody Map<String, String> productInfo) {
        int result = productService.modifyProduct(productInfo);
        if (result > 0) {
            System.out.println("상품 정보 수정 성공");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    /**
     * 등록된 상품을 삭제하는 메소드입니다.
     * 
     * @param productId 삭제할 상품 ID
     * @return 삭제 결과
     */
    @Transactional
    @DeleteMapping("/products")
    @Operation(summary = "상품 삭제", description = "등록된 상품을 삭제합니다.")
    public ResponseEntity<?> deleteProduct(@RequestParam("productId") int productId) {
        int result = productService.deleteProduct(productId);
        if (result > 0) {
            System.out.println("상품 삭제 성공");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
