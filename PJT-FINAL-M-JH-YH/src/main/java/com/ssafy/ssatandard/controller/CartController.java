package com.ssafy.ssatandard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssatandard.model.dto.Cart;
import com.ssafy.ssatandard.model.dto.Product;
import com.ssafy.ssatandard.model.service.CartService;
import com.ssafy.ssatandard.model.service.OrderService;
import com.ssafy.ssatandard.model.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

/**
 * 장바구니와 관련된 요청을 처리합니다.
 */
@RestController
@RequestMapping("/cart")
@Tag(name = "Cart Controller", description = "장바구니 관련 작업을 처리하는 컨트롤러입니다.")
public class CartController {

    // 응답을 편하게 하기 위해 상수로 지정
    private static final String SUCCESS = "성공";
    private static final String FAIL = "실패";

    // CART
    private final CartService cartService;
    // ORDER
    private final OrderService orderService;
    // PRODUCT
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, OrderService orderService, ProductService productService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.productService = productService;
    }

    /**
     * 장바구니에 있는 모든 항목을 조회하는 메소드입니다.
     * 
     * @param session HTTP 세션
     * @return 장바구니 항목 리스트
     */
    @GetMapping("")
    @Operation(summary = "장바구니 조회", description = "로그인된 사용자의 모든 장바구니 항목을 조회합니다.")
    public ResponseEntity<?> selectAllByUserId(HttpSession session) {
        // session에 있는 "loginUserId"를 꺼내온 후
        String userId = (String) session.getAttribute("loginUserId");
        List<Cart> list = cartService.selectAll(userId);
        if (list == null || list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 장바구니에서 개별 항목을 삭제하는 메소드입니다.
     * 
     * @param cartId 삭제할 장바구니 항목 ID
     * @return 삭제 처리 결과
     */
    @DeleteMapping("")
    @Operation(summary = "장바구니 항목 삭제", description = "장바구니에서 개별 항목을 삭제합니다.")
    public ResponseEntity<String> delete(@RequestParam int cartId) {
        if (cartService.deleteCart(cartId)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL, HttpStatus.NOT_FOUND);
    }

    /**
     * 해당 유저의 장바구니를 초기화(모든 항목 삭제)하는 메소드입니다.
     * 
     * @param session HTTP 세션
     * @return 초기화 처리 결과
     */
    @DeleteMapping("/all")
    @Operation(summary = "장바구니 초기화", description = "해당 사용자의 장바구니를 초기화합니다.")
    public ResponseEntity<String> deleteAll(HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        cartService.deleteAll(loginUserId);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    /**
     * 장바구니 항목의 수량을 조정하는 메소드입니다.
     * 
     * @param cartId 수정할 장바구니 항목 ID
     * @param amount 수정할 수량
     * @return 수정 처리 결과
     */
    @PutMapping("/{cartId}")
    @Operation(summary = "장바구니 항목 수량 수정", description = "장바구니 항목의 수량을 수정합니다.")
    public ResponseEntity<String> update(@PathVariable int cartId, @RequestParam int amount) {
        if (cartService.updateCart(cartId, amount)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
    }

    /**
     * 장바구니의 정보를 결제 데이터로 전송하는 메소드입니다.
     * 
     * @param session HTTP 세션
     * @return 주문 생성 결과
     */
    @PostMapping("/order")
    @Operation(summary = "주문 생성", description = "장바구니의 정보를 결제 데이터로 전송합니다.")
    public ResponseEntity<?> insertOrder(HttpSession session) {
        
        // 1. 로그인된 사용자 ID 가져오기
        String loginUserId = (String) session.getAttribute("loginUserId");
        
        // 2. 해당 사용자의 모든 장바구니 항목 조회
        List<Cart> allCarts = cartService.selectAll(loginUserId);
        
        // 3. 장바구니 항목들을 하나씩 처리
        for (Cart cart : allCarts) {
            
            // 3.1 상품 가격 및 총 가격 계산
            int price = productService.selectOne(cart.getProductId()).getProductPrice(); // 가격
            int totalPrice = cart.getAmount() * price; // 상품 가격 * 수량
            
            // 3.2 주문 정보를 맵에 담기
            Map<String, String> cartInfo = new ConcurrentHashMap<>();
            cartInfo.put("userId", loginUserId);
            cartInfo.put("productId", String.valueOf(cart.getProductId()));
            cartInfo.put("productName", String.valueOf(cart.getProductName()));
            cartInfo.put("productPrice", String.valueOf(price));
            cartInfo.put("amount", String.valueOf(cart.getAmount()));
            cartInfo.put("totalPrice", String.valueOf(totalPrice));
            cartInfo.put("image", String.valueOf(cart.getImage()));
            
            // 3.3 재고 업데이트를 위한 상품 정보 가져오기
            Product p = productService.selectOne(cart.getProductId()); // 해당 상품을 가져와서
            Map<String, String> stockInfo = new ConcurrentHashMap<>();
            
            // 3.4 재고 및 판매 수량 계산
            int orgStock = p.getProductStock();
            int orgSold = p.getProductSold();
            int newStock = p.getProductStock() - cart.getAmount();
            if (newStock < 0) {
                return new ResponseEntity<>("Failed to create Order", HttpStatus.BAD_REQUEST);
            }
            int newSold = p.getProductSold() + cart.getAmount();
            
            // 3.5 재고 및 판매 수량 정보를 맵에 담기
            stockInfo.put("productId", String.valueOf(p.getProductId()));
            stockInfo.put("productStock", String.valueOf(newStock));
            stockInfo.put("productSold", String.valueOf(newSold));
            
            // 3.6 상품 재고 업데이트
            int result = productService.updateProduct(stockInfo);
            
            // 3.7 주문 정보 삽입
            boolean flag = orderService.insertOrder(cartInfo);
            
            // 3.8 성공 시 장바구니에서 해당 항목 삭제, 실패 시 재고 원상 복구
            if (flag && result > 0) {
                cartService.deleteCart(cart.getCartId());
            } else {
                stockInfo.replace("productStock", String.valueOf(orgStock));
                stockInfo.replace("productSold", String.valueOf(orgSold));
                productService.updateProduct(stockInfo);
                return new ResponseEntity<>("Failed to create Order", HttpStatus.BAD_REQUEST);
            }
        }

        // 4. 모든 처리가 성공적으로 완료된 경우 응답 반환
        return new ResponseEntity<>("Order Created Successfully", HttpStatus.CREATED);
    }
}
