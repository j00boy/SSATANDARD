package com.ssafy.ssatandard.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.ssatandard.model.dto.Cart;

public interface CartService {

    /**
     * 장바구니에 상품을 추가합니다.
     *
     * @param cart 추가할 장바구니 항목
     * @return 성공적으로 추가되었으면 true, 그렇지 않으면 false
     */
    boolean insertCart(Cart cart);

    /**
     * 특정 사용자의 장바구니를 조회합니다.
     *
     * @param userId 사용자의 ID
     * @return 사용자의 모든 장바구니 항목 리스트
     */
    List<Cart> selectAll(String userId);

    /**
     * 장바구니 항목의 수량을 수정합니다.
     *
     * @param cartId 수정할 장바구니 항목의 ID
     * @param amount 수정할 수량
     * @return 성공적으로 수정되었으면 true, 그렇지 않으면 false
     */
    boolean updateCart(int cartId, int amount);

    /**
     * 특정 장바구니 항목을 삭제합니다.
     *
     * @param cartId 삭제할 장바구니 항목의 ID
     * @return 성공적으로 삭제되었으면 true, 그렇지 않으면 false
     */
    boolean deleteCart(int cartId);

    /**
     * 특정 사용자의 모든 장바구니 항목을 삭제합니다.
     *
     * @param userId 사용자의 ID
     * @return 성공적으로 삭제되었으면 true, 그렇지 않으면 false
     */
    boolean deleteAll(String userId);

    /**
     * 특정 장바구니 항목을 조회합니다.
     *
     * @param cartId 조회할 장바구니 항목의 ID
     * @return 조회된 장바구니 항목
     */
    Cart selectOne(int cartId);

    /**
     * 특정 사용자의 특정 상품에 대한 장바구니 항목을 조회합니다.
     *
     * @param info 사용자 ID와 상품 ID를 포함한 맵
     * @return 조회된 장바구니 항목
     */
    Cart searchByUserIdAndproductId(Map<String, String> info);
}
