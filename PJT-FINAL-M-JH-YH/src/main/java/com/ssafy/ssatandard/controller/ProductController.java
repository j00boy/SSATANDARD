package com.ssafy.ssatandard.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssatandard.model.dto.Cart;
import com.ssafy.ssatandard.model.dto.Product;
import com.ssafy.ssatandard.model.dto.SearchCondition;
import com.ssafy.ssatandard.model.service.CartService;
import com.ssafy.ssatandard.model.service.OrderService;
import com.ssafy.ssatandard.model.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

/**
 * 상품과 관련된 요청을 처리합니다.
 */
@RestController
@RequestMapping("/product")
@Tag(name = "Product Controller", description = "상품 관련 작업을 처리하는 컨트롤러입니다.")
public class ProductController {

    // 서비스 객체를 싱글턴으로 선언
    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;

    @Autowired
    public ProductController(ProductService productService, CartService cartService, OrderService orderService) {
        this.productService = productService;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    /**
     * 상품 검색 기능을 담당하는 메소드입니다.
     * 전체 검색과 조건 검색, 정렬 조건 등을 모두 수행하는 메서드
     * 
     * @param condition : 검색 조건
     * @return : 검색된 상품 리스트
     */
    @GetMapping("")
    @Operation(summary="상품 검색", description="전체 상품 검색과 조건 검색을 수행합니다.")
    public ResponseEntity<?> list(@ModelAttribute SearchCondition condition) {
        List<Product> list = productService.search(condition);
        if (list == null || list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 카테고리별 물품 조회를 담당하는 메소드입니다.
     * 
     * @param categoryId 카테고리 ID
     * @return 해당 카테고리에 속하는 상품 리스트
     */
    @GetMapping("/{categoryId}")
    @Operation(summary="카테고리별 상품 조회", description="특정 카테고리에 속하는 상품들을 조회합니다.")
    public ResponseEntity<?> selectAllByCategoryId(@PathVariable("categoryId") int categoryId) {
        List<Product> list = productService.searchByCategoryId(categoryId);
        if (list == null || list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 특정 상품의 상세 정보를 조회하는 메소드입니다.
     * 
     * @param session HTTP 세션
     * @param productId 상품 ID
     * @return 해당 상품의 상세 정보
     */
    @GetMapping("/detail/{productId}/info")
    @Operation(summary="상품 상세 정보 조회", description="특정 상품의 상세 정보를 조회합니다.")
    public ResponseEntity<?> searchByProductId(HttpSession session, @PathVariable("productId") int productId) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Product product = productService.selectOne(productId);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * 특정 사용자와 상품에 대한 카트 정보를 조회하는 메소드입니다.
     * 
     * @param session HTTP 세션
     * @param productId 상품 ID
     * @return 해당 사용자와 상품에 대한 카트 정보
     */
    @GetMapping("/detail/{productId}")
    @Operation(summary="카트 정보 조회", description="로그인 유저와 상품에 대해 이미 장바구니에 있는지 카트 정보를 조회합니다.")
    public ResponseEntity<?> searchByUserIdAndProductId(HttpSession session, @PathVariable("productId") int productId) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Map<String, String> info = new ConcurrentHashMap<>();
        info.put("userId", loginUserId);
        info.put("productId", String.valueOf(productId));
        Cart tmpCart = cartService.searchByUserIdAndproductId(info);

        if (tmpCart != null) {
            return new ResponseEntity<>(tmpCart, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * 즉시 구매 요청을 처리하는 메소드입니다.
     * 
     * @param session HTTP 세션 : 로그인 정보 파악
     * @param productId 상품 ID
     * @param request 요청 정보 (구매 수량 등)
     * @return 주문 생성 결과
     */
    @Transactional
    @PostMapping("detail/{productId}/direct")
    @Operation(summary="즉시 구매 요청", description="사용자가 즉시 구매 요청을 수행합니다.")
    public ResponseEntity<?> directOrder(HttpSession session, @PathVariable("productId") int productId, @RequestBody Map<String, Object> request) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Product product = productService.selectOne(productId);
        if (product.getProductStock() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Integer amount = (Integer) request.get("amount");
        if (amount == null || amount.equals(0)) {
            return new ResponseEntity<>("Required parameter 'amount' is not present.", HttpStatus.BAD_REQUEST);
        }

        int totalPrice = amount * product.getProductPrice();
        Map<String, String> info = new ConcurrentHashMap<>();
        info.put("userId", loginUserId);
        info.put("productId", String.valueOf(productId));
        info.put("productName", product.getProductName());
        info.put("productPrice", String.valueOf(product.getProductPrice()));
        info.put("amount", String.valueOf(amount));
        info.put("totalPrice", String.valueOf(totalPrice));
        info.put("image", product.getImage());

        Map<String, String> stockInfo = new ConcurrentHashMap<>();
        int orgStock = product.getProductStock();
        int orgSold = product.getProductSold();
        int newStock = orgStock - amount;
        if (newStock < 0) {
            return new ResponseEntity<>("Failed to create Order", HttpStatus.BAD_REQUEST);
        }
        int newSold = orgSold + amount;

        stockInfo.put("productId", String.valueOf(product.getProductId()));
        stockInfo.put("productStock", String.valueOf(newStock));
        stockInfo.put("productSold", String.valueOf(newSold));

        int result = productService.updateProduct(stockInfo);
        if (result < 1) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        boolean flag = orderService.insertOrder(info);
        if (flag) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            stockInfo.replace("productStock", String.valueOf(orgStock));
            stockInfo.replace("productSold", String.valueOf(orgSold));
            productService.updateProduct(stockInfo);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 장바구니에 상품을 추가하는 메소드입니다.
     * 
     * @param session HTTP 세션
     * @param productId 상품 ID
     * @param request 요청 정보 (추가할 수량 등)
     * @return 추가된 장바구니 항목
     */
    @Transactional
    @PostMapping("/detail/{productId}")
    @Operation(summary="장바구니 추가", description="장바구니에 상품을 추가합니다.")
    public ResponseEntity<?> insert(HttpSession session, @PathVariable("productId") int productId,
                                    @RequestBody Map<String, Object> request) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Integer amount = (Integer) request.get("amount");
        if (amount == null) {
            return new ResponseEntity<>("Required parameter 'amount' is not present.", HttpStatus.BAD_REQUEST);
        }

        Map<String, String> info = new ConcurrentHashMap<>();
        info.put("userId", loginUserId);
        info.put("productId", String.valueOf(productId));
        Cart tmpCart = cartService.searchByUserIdAndproductId(info);

        if (tmpCart != null) {
            cartService.updateCart(tmpCart.getCartId(), amount);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            Product product = productService.selectOne(productId);
            Cart cart = new Cart(loginUserId, productId, product.getProductName(), product.getProductPrice(), amount, product.getImage());
            cartService.insertCart(cart);
            return new ResponseEntity<>(cart, HttpStatus.CREATED);
        }
    }
}
