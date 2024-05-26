package com.ssafy.ssatandard.model.dto;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Schema(description="Product DTO")
public class Product {
	private int productId;
	private String productName;
	private int productPrice;
	private int productStock;
	private int productSold;
	private String productInfo;
	private int categoryId;
	private String image;
	private String regDate;

	@Autowired
	public Product(String productName, int productPrice, int productStock, String productInfo, int categoryId,
			String image) {
		this.productName = productName;
		this.productPrice = productPrice;
		this.productStock = productStock;
		this.productInfo = productInfo;
		this.categoryId = categoryId;
		this.image = image;
	}
}
