package com.ssafy.ssatandard.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Schema(description="SearchCondition DTO")
public class SearchCondition {
	private String categoryId = "none"; // 카테고리 ID (기본값: "none")
	private String key = "none"; // 검색 키 (기본값: "none")
	private String word; // 검색어
	private String orderBy = "none"; // 정렬 기준 (기본값: "none")
	private String orderByDir; // 정렬 방향 (ASC 또는 DESC)
}
