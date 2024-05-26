<template>
    <footer class="footer">
        <div class="footer-content">
            <p class="brand-name">SSATANDARD</p>
            <p class="representatives">공동대표: 김윤홍, 김주형</p>
            <p class="representatives">자문: 민예림, 육예진</p>
            <p class="address">서울특별시 강남구 테헤란로 212 멀티캠퍼스 서울캠퍼스</p>
            <p class="business-info">사업자 등록번호: 123-45-67890</p>
            <p class="additional-info">고객센터: 02-1234-5678 | 이메일: info@ssatandard.com</p>
        </div>
        <div class="social-media">
            <a href="#" class="social-icon facebook"><i class="bi bi-facebook"></i></a>
            <a href="#" class="social-icon twitter"><i class="bi bi-twitter"></i></a>
            <a href="#" class="social-icon instagram"><i class="bi bi-instagram"></i></a>
        </div>
    </footer>
</template>

<script setup>
// JavaScript 로직이 필요한 경우 여기에 작성
</script>

<style scoped>
.footer {
    color: rgb(79, 79, 79);
    background-color: rgb(217, 217, 217);
    padding: 20px;
    text-align: center;
}

.footer-content p {
    margin: 5px 0;
}

.brand-name {
    font-size: 20px;
    font-weight: bold;
}

.social-media {
    margin-top: 10px;
}

.social-icon i {
    font-size: 24px;
    margin: 0 10px;
}

.social-icon.facebook i {
    color: #3b5998; /* Facebook의 공식 파란색 */
}

.social-icon.twitter i {
    color: #1DA1F2; /* Twitter의 공식 하늘색 */
}

.social-icon.instagram i {
    color: linear-gradient(45deg, #833ab4, #fd1d1d, #fcb045); /* Instagram 그라디언트 */
    background: -webkit-linear-gradient(45deg, #833ab4, #fd1d1d, #fcb045); /* Chrome, Safari */
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}
</style>
