package com.ssafy.ssatandard.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ssatandard.model.dao.CartDao;
import com.ssafy.ssatandard.model.dto.Cart;

/**
 * @Service : 이 클래스가 서비스 레이어를 나타내며, 스프링의 빈으로 관리됨을 나타냅니다.
 */
@Service
public class CartServiceImpl implements CartService {

    private CartDao cartDao;

    @Autowired
    public CartServiceImpl(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    /**
     * 장바구니에 상품을 추가합니다.
     *
     * @param cart 추가할 장바구니 항목
     * @return 성공적으로 추가되었으면 true, 그렇지 않으면 false
     */
    @Transactional
    @Override
    public boolean insertCart(Cart cart) {
        return cartDao.insertCart(cart) == 1;
    }

    /**
     * 특정 사용자의 모든 장바구니 항목을 조회합니다.
     *
     * @param userId 사용자의 ID
     * @return 사용자의 모든 장바구니 항목 리스트
     */
    @Override
    public List<Cart> selectAll(String userId) {
        return cartDao.selectAll(userId);
    }

    /**
     * 장바구니 항목의 수량을 수정합니다.
     *
     * @param cartId 수정할 장바구니 항목의 ID
     * @param amount 수정할 수량
     * @return 성공적으로 수정되었으면 true, 그렇지 않으면 false
     */
    @Override
    public boolean updateCart(int cartId, int amount) {
        return cartDao.updateCart(cartId, amount) == 1;
    }

    /**
     * 특정 장바구니 항목을 삭제합니다.
     *
     * @param cartId 삭제할 장바구니 항목의 ID
     * @return 성공적으로 삭제되었으면 true, 그렇지 않으면 false
     */
    @Override
    public boolean deleteCart(int cartId) {
        return cartDao.deleteCart(cartId) == 1;
    }

    /**
     * 특정 사용자의 모든 장바구니 항목을 삭제합니다.
     *
     * @param userId 사용자의 ID
     * @return 성공적으로 삭제되었으면 true, 그렇지 않으면 false
     */
    @Override
    public boolean deleteAll(String userId) {
        return cartDao.deleteAll(userId) == 1;
    }

    /**
     * 특정 장바구니 항목을 조회합니다.
     *
     * @param cartId 조회할 장바구니 항목의 ID
     * @return 조회된 장바구니 항목
     */
    @Override
    public Cart selectOne(int cartId) {
        return cartDao.selectOne(cartId);
    }

    /**
     * 특정 사용자의 특정 상품에 대한 장바구니 항목을 조회합니다.
     *
     * @param info 사용자 ID와 상품 ID를 포함한 맵
     * @return 조회된 장바구니 항목
     */
    @Override
    public Cart searchByUserIdAndproductId(Map<String, String> info) {
        return cartDao.searchByUserIdAndProductId(info);
    }
}
