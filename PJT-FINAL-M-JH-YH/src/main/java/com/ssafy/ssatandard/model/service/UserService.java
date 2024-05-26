package com.ssafy.ssatandard.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.ssatandard.model.dto.Order;
import com.ssafy.ssatandard.model.dto.SearchCondition;
import com.ssafy.ssatandard.model.dto.User;

public interface UserService {
    
    /**
     * 새로운 사용자를 등록합니다.
     *
     * @param user 등록할 사용자 정보
     * @return 성공적으로 등록된 사용자의 개수
     */
    int insertUser(User user);

    /**
     * 사용자를 로그인합니다.
     *
     * @param info 로그인할 사용자 정보
     * @return 로그인된 사용자
     */
    User selectOne(Map<String, String> info);
    
    /**
     * 특정 사용자 ID로 사용자를 조회합니다.
     *
     * @param userId 조회할 사용자 ID
     * @return 조회된 사용자
     */
    User searchByUserId(String userId);

    /**
     * 특정 사용자를 삭제합니다.
     *
     * @param id 삭제할 사용자 ID
     * @return 성공적으로 삭제된 사용자의 개수
     */
    int deleteUser(String id);

    /**
     * 사용자의 정보를 수정합니다.
     *
     * @param info 수정할 사용자 정보
     * @return 성공적으로 수정된 사용자의 개수
     */
    int updateUser(Map<String, String> info);
    
    /**
     * 모든 사용자의 정보를 조회합니다.
     *
     * @return 모든 사용자 리스트
     */
    List<User> selectAll();
    
    /**
     * 검색 조건에 따라 주문을 조회합니다.
     *
     * @param searchCondition 검색 조건
     * @return 검색 조건에 맞는 주문 리스트
     */
    List<User> search(SearchCondition searchCondition);
}
