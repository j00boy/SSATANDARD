package com.ssafy.ssatandard.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssatandard.model.dao.UserDao;
import com.ssafy.ssatandard.model.dto.Order;
import com.ssafy.ssatandard.model.dto.SearchCondition;
import com.ssafy.ssatandard.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	@Autowired
	UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 새로운 사용자를 추가합니다.
	 *
	 * @param user 추가할 사용자 정보
	 * @return 성공적으로 추가된 사용자의 개수
	 */
	@Override
	public int insertUser(User user) {
		return userDao.insertUser(user);
	}

	/**
	 * 사용자를 로그인합니다.
	 *
	 * @param info 로그인할 사용자 정보
	 * @return 로그인된 사용자
	 */
	@Override
	public User selectOne(Map<String, String> info) {
		return userDao.selectOne(info);
	}

	/**
	 * 사용자를 탈퇴시킵니다.
	 *
	 * @param userId 탈퇴할 사용자 ID
	 * @return 성공적으로 탈퇴된 사용자의 개수
	 */
	@Override
	public int deleteUser(String userId) {
		return userDao.deleteUser(userId);
	}

	/**
	 * 사용자의 정보를 수정합니다.
	 *
	 * @param info 수정할 사용자 정보
	 * @return 성공적으로 수정된 사용자의 개수
	 */
	@Override
	public int updateUser(Map<String, String> info) {
		return userDao.updateUser(info);
	}

	/**
	 * 모든 사용자의 정보를 조회합니다.
	 *
	 * @return 모든 사용자 리스트
	 */
	@Override
	public List<User> selectAll() {
		return userDao.selectAll();
	}

	/**
	 * 특정 사용자 ID로 사용자를 조회합니다.
	 *
	 * @param userId 조회할 사용자 ID
	 * @return 조회된 사용자
	 */
	@Override
	public User searchByUserId(String userId) {
		return userDao.searchByUserId(userId);
	}

	/**
	 * 검색 조건에 따라 주문을 조회합니다.
	 *
	 * @param searchCondition 검색 조건
	 * @return 검색 조건에 맞는 주문 리스트
	 */
	@Override
	public List<User> search(SearchCondition searchCondition) {
		return userDao.search(searchCondition);
	}
}
