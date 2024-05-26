package com.ssafy.ssatandard.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.ssatandard.model.dto.Product;
import com.ssafy.ssatandard.model.dto.SearchCondition;

public interface ProductDao {

    /**
     * 검색 조건에 따라 게시글을 조회합니다.
     * 
     * @param searchCondition 검색 조건
     * @return 검색 조건에 맞는 게시글 리스트
     */
    List<Product> search(SearchCondition searchCondition);

    /**
     * 새로운 상품을 등록합니다.
     * 
     * @param product 등록할 상품 정보
     * @return 성공적으로 등록된 상품의 개수
     */
    int insertProduct(Product product);

    /**
     * 모든 상품을 조회합니다. (관리자용)
     * 
     * @return 모든 상품 리스트
     */
    List<Product> selectAll();

    /**
     * 특정 카테고리에 속한 상품을 조회합니다. (메인용)
     * 
     * @param categoryId 카테고리 ID
     * @return 해당 카테고리에 속한 상품 리스트
     */
    List<Product> searchByCategoryId(int categoryId);

    /**
     * 특정 상품의 상세 정보를 조회합니다.
     * 
     * @param id 조회할 상품의 ID
     * @return 조회된 상품
     */
    Product selectOne(int id);

    /**
     * 상품의 재고 정보를 수정합니다.
     * 
     * @param info 수정할 재고 정보
     * @return 성공적으로 수정된 항목의 개수
     */
    int updateProduct(Map<String, String> info);

    /**
     * 특정 상품의 이름을 조회합니다.
     * 
     * @param productId 조회할 상품의 ID
     * @return 조회된 상품의 이름
     */
    String searchProductName(int productId);
    
    /**
     * 특정 상품을 삭제합니다.
     * 
     * @param productId 삭제할 상품의 ID
     * @return 성공적으로 삭제된 항목의 개수
     */
    int deleteProduct(int productId);
    
    /**
     * 특정 상품의 정보를 수정합니다.
     * 
     * @param info 수정할 상품 정보
     * @return 성공적으로 수정된 항목의 개수
     */
    int modifyProduct(Map<String, String> info);
}
