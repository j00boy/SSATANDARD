package com.ssafy.ssatandard.model.dto;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Data : Lombok을 사용하여 getter, setter, toString, equals, hashCode 메소드를 자동으로 생성
 * @AllArgsConstructor : 모든 필드를 매개변수로 갖는 생성자를 자동으로 생성
 * @NoArgsConstructor : 매개변수가 없는 기본 생성자를 자동으로 생성
 * @JsonAutoDetect : JSON 직렬화/역직렬화 시 필드의 접근 수준을 설정
 * fieldVisibility = Visibility.ANY는 모든 필드를 직렬화/역직렬화할 수 있도록 설정
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Schema(description="Cart DTO")
public class Cart {
	private int cartId;
	private String userId;
	private int productId;
	private String productName;
	private int productPrice;
	private int amount;
	private String image;
	private String regDate;
	
	/**
	 * 이 생성자는 주어진 필드로 Cart 객체를 초기화합니다.
	 *
	 * @param userId 사용자 ID
	 * @param productId 상품 ID
	 * @param productName 상품 이름
	 * @param productPrice 상품 가격
	 * @param amount 상품 수량
	 * @param image 상품 이미지
	 */
	@Autowired
	public Cart(String userId, int productId, String productName, int productPrice, int amount, String image) {
		this.userId = userId;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.amount = amount;
		this.image = image;
	}
}
