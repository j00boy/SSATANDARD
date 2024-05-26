package com.ssafy.ssatandard.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.ssatandard.model.dto.Order;
import com.ssafy.ssatandard.model.dto.Product;
import com.ssafy.ssatandard.model.dto.SearchCondition;

public interface OrderDao {
    
    /**
     * 새로운 주문을 생성합니다.
     * 
     * @param info 주문 정보를 담은 맵
     * @return 성공적으로 생성된 주문의 개수
     */
    int insertOrder(Map<String, String> info);

    /**
     * 모든 주문을 조회합니다.
     * 
     * @return 모든 주문의 리스트
     */
    List<Order> selectAll();

    /**
     * 특정 사용자의 주문을 조회합니다.
     * 
     * @param id 사용자의 ID
     * @return 사용자의 주문 리스트
     */
    List<Order> searchByUserId(String id);
    
    /**
     * 검색 조건에 따라 주문을 조회합니다.
     * 
     * @param searchCondition 검색 조건
     * @return 검색 조건에 맞는 주문 리스트
     */
    List<Order> search(SearchCondition searchCondition);
}
