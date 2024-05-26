-- 데이터베이스 생성
CREATE DATABASE ssatandard_db;

-- 데이터베이스 사용 설정
USE ssatandard_db;

-- 사용자 테이블 생성
CREATE TABLE `users` (
  `userId` varchar(20) NOT NULL,  -- 4~16자, 알파벳 대/소문자 & 숫자
  `password` varchar(20) NOT NULL,  -- 8~20자, 알파벳 대/소문자 & 숫자, 특수문자
  `userName` varchar(20) NOT NULL,  -- 2~20자, 한글만 허용
  `phoneNumber` varchar(20) NOT NULL UNIQUE,  -- 3자리-[3,4]자리-4자리 숫자 포맷
  `isAdmin` TINYINT(1) NOT NULL DEFAULT 0,  -- 관리자(1), 일반 유저(0) 구분
  `address` varchar(255) NOT NULL,  -- 사용자의 주소 정보
  `image` varchar(255) DEFAULT 'https://static.vecteezy.com/system/resources/previews/008/442/086/non_2x/illustration-of-human-icon-user-symbol-icon-modern-design-on-blank-background-free-vector.jpg',  -- 기본 프로필 이미지 URL
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 계정 생성 일시
  PRIMARY KEY (`userId`)  -- 기본 키 설정
);

-- 제품 테이블 생성
CREATE TABLE `products` (
  `productId` int NOT NULL AUTO_INCREMENT,  -- 자동 증가되는 제품 ID
  `productName` varchar(30) NOT NULL,  -- 제품 이름
  `productPrice` int NOT NULL,  -- 제품 가격
  `productStock` int NOT NULL,  -- 제품 재고 수량
  `productSold` int NOT NULL DEFAULT 0,  -- 판매된 제품 수량
  `productInfo` TEXT NOT NULL,  -- 제품 설명
  `categoryId` int NOT NULL,  -- 카테고리 ID
  `image` VARCHAR(255) DEFAULT 'https://fiverr-res.cloudinary.com/images/q_auto,f_auto/gigs/263015180/original/59b151ebc48c01bf14852a50bdb003b97a713f60/choose-words-no-one-is-going-to-forget.jpg',  -- 제품 이미지 URL
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 제품 등록 일시
  PRIMARY KEY (`productId`)  -- 기본 키 설정
);

-- 장바구니 테이블 생성
CREATE TABLE `carts` (
  `cartId` int NOT NULL AUTO_INCREMENT,  -- 자동 증가되는 장바구니 ID
  `userId` varchar(20) NOT NULL,  -- 사용자 ID (외래 키)
  `productId` int NOT NULL,  -- 제품 ID (외래 키)
  `productName` varchar(30) NOT NULL,  -- 제품 이름
  `productPrice` int NOT NULL,  -- 제품 가격
  `amount` int NOT NULL DEFAULT 1,  -- 제품 수량
  `image` VARCHAR(255),  -- 제품 이미지 URL
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 장바구니 추가 일시
  PRIMARY KEY (`cartId`),  -- 기본 키 설정
  FOREIGN KEY (`userId`) REFERENCES `users`(`userId`) ON DELETE CASCADE,  -- 사용자 삭제 시 연쇄 삭제
  FOREIGN KEY (`productId`) REFERENCES `products`(`productId`) ON DELETE CASCADE  -- 제품 삭제 시 연쇄 삭제
);

-- 주문 테이블 생성
CREATE TABLE `orders` (
  `orderId` int NOT NULL AUTO_INCREMENT,  -- 자동 증가되는 주문 ID
  `userId` varchar(20),  -- 사용자 ID (외래 키)
  `productId` int,  -- 제품 ID (외래 키)
  `productName` varchar(30) NOT NULL,  -- 제품 이름
  `productPrice` int NOT NULL,  -- 제품 가격
  `amount` int NOT NULL DEFAULT 1,  -- 주문 수량
  `totalPrice` int NOT NULL,  -- 총 가격
  `image` VARCHAR(255),  -- 제품 이미지 URL
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 주문 일시
  PRIMARY KEY (`orderId`),  -- 기본 키 설정
  FOREIGN KEY (`userId`) REFERENCES `users`(`userId`) ON DELETE SET NULL,  -- 사용자 삭제 시 ID를 NULL로 설정
  FOREIGN KEY (`productId`) REFERENCES `products`(`productId`) ON DELETE SET NULL  -- 제품 삭제 시 ID를 NULL로 설정
);
