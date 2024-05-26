package com.ssafy.ssatandard.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssatandard.model.dao.OrderDao;
import com.ssafy.ssatandard.model.dto.Order;
import com.ssafy.ssatandard.model.dto.SearchCondition;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderDao orderDao;

	@Autowired
	public OrderServiceImpl(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	/**
	 * 주문을 생성하는 로직을 처리합니다.
	 *
	 * @param info 주문 정보를 담은 맵
	 * @return 성공적으로 생성되었으면 true, 그렇지 않으면 false
	 */
	@Override
	public boolean insertOrder(Map<String, String> info) {
		return orderDao.insertOrder(info) == 1;
	}

	/**
	 * 모든 주문을 조회합니다.
	 *
	 * @return 모든 주문의 리스트
	 */
	@Override
	public List<Order> selectAll() {
		return orderDao.selectAll();
	}

	/**
	 * 특정 사용자의 주문을 조회합니다.
	 *
	 * @param id 사용자의 ID
	 * @return 사용자의 주문 리스트
	 */
	@Override
	public List<Order> searchByUserId(String id) {
		return orderDao.searchByUserId(id);
	}

	/**
	 * 검색 조건에 따라 주문을 조회합니다.
	 *
	 * @param searchCondition 검색 조건
	 * @return 검색 조건에 맞는 주문 리스트
	 */
	@Override
	public List<Order> search(SearchCondition searchCondition) {
		return orderDao.search(searchCondition);
	}
}
