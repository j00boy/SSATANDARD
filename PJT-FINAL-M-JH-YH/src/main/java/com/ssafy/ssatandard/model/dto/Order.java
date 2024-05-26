package com.ssafy.ssatandard.model.dto;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Schema(description="Order DTO")
public class Order {
	private int orderId;
	private String userId;
	private int productId;
	private String productName;
	private int productPrice;
	private int amount;
	private int totalPrice;
	private String image;
	private String regDate;
	
	@Autowired
	public Order(int orderId, String userId, String productName, int productPrice, int amount, int totalPrice,
			String regDate) {
		this.orderId = orderId;
		this.userId = userId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.amount = amount;
		this.totalPrice = totalPrice;
		this.regDate = regDate;
	}
}
