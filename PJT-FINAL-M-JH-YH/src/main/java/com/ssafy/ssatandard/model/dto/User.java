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
@Schema(description="User DTO")
public class User {
	private String userId;
	private String password;
	private String userName;
	private String phoneNumber;
	private String isAdmin;
	private String address;
	private String image;
	private String regDate;
	private int totalOrderPrice;
	
	@Autowired
	public User(String userId, String password, String userName, String phoneNumber, String address) {
		this.userId = userId;
		this.password = password;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
}
