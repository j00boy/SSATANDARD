package com.ssafy.ssatandard.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ssatandard.model.dao.ProductDao;
import com.ssafy.ssatandard.model.dto.Product;
import com.ssafy.ssatandard.model.dto.SearchCondition;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    /**
     * 상품을 등록합니다.
     *
     * @param product 등록할 상품 정보
     * @return 성공적으로 등록되었으면 true, 그렇지 않으면 false
     */
    @Transactional
    @Override
    public boolean insertProduct(Product product) {
        return productDao.insertProduct(product) == 1;
    }

    /**
     * 모든 상품을 조회합니다.
     *
     * @return 모든 상품 리스트
     */
    @Override
    public List<Product> selectAll() {
        return productDao.selectAll();
    }

    /**
     * 특정 카테고리에 속한 모든 상품을 조회합니다.
     *
     * @param id 카테고리 ID
     * @return 해당 카테고리에 속한 상품 리스트
     */
    @Override
    public List<Product> searchByCategoryId(int id) {
        return productDao.searchByCategoryId(id);
    }

    /**
     * 특정 상품의 상세 정보를 조회합니다.
     *
     * @param id 조회할 상품의 ID
     * @return 조회된 상품
     */
    @Override
    public Product selectOne(int id) {
        return productDao.selectOne(id);
    }

    /**
     * 특정 상품의 이름을 조회합니다.
     *
     * @param productId 조회할 상품의 ID
     * @return 조회된 상품의 이름
     */
    @Override
    public String searchProductName(int productId) {
        return productDao.searchProductName(productId);
    }

    /**
     * 검색 조건에 따라 상품을 조회합니다.
     *
     * @param searchCondition 검색 조건
     * @return 검색 조건에 맞는 상품 리스트
     */
    @Override
    public List<Product> search(SearchCondition searchCondition) {
        return productDao.search(searchCondition);
    }

    /**
     * 상품의 재고 정보를 업데이트합니다.
     *
     * @param info 수정할 재고 정보
     * @return 성공적으로 수정된 항목의 개수
     */
    @Transactional
    @Override
    public int updateProduct(Map<String, String> info) {
        return productDao.updateProduct(info);
    }

    /**
     * 특정 상품을 삭제합니다.
     *
     * @param productId 삭제할 상품의 ID
     * @return 성공적으로 삭제된 항목의 개수
     */
    @Override
    public int deleteProduct(int productId) {
        return productDao.deleteProduct(productId);
    }

    /**
     * 특정 상품의 정보를 수정합니다.
     *
     * @param info 수정할 상품 정보
     * @return 성공적으로 수정된 항목의 개수
     */
    @Override
    public int modifyProduct(Map<String, String> info) {
        return productDao.modifyProduct(info);
    }
}
