<template>
  <div class="container" @keyup.enter="login"> <!-- 엔터 키 리스너 추가 -->
    <div class="form-floating mb-3">
      <input type="text" class="form-control input-form" :class="userIdValid" id="userId" placeholder="아이디"
        v-model="userId">
      <label for="userId">아이디</label>
    </div>
    <div class="form-floating mb-3">
      <input type="password" class="form-control input-form" :class="passwordValid" id="password" placeholder="비밀번호"
        v-model="password">
      <label for="password">비밀번호</label>
    </div>
    <div class="btn-box">
      <button class="btn my-button" @click="store.hideLoginModal">닫기</button>
      <button class="btn my-button" @click="login">로그인</button>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from "@/stores/user";
import { ref, computed } from "vue";

const store = useUserStore();

// 정규 표현식:
// id: 4~16자 알파벳 대/소문자, 숫자
// pw: 8~20자 알파벳 대/소문자, 숫자, 특수문자
const userIdCondition = /^[a-zA-Z0-9]{4,16}$/;
const passwordCondition = /^[a-zA-Z0-9!@#$%^&*()_+]{8,20}$/;

const userId = ref('');
const password = ref('');

// 로그인 시 유효성 검증
const userIdValid = computed(() => {
  if (userId.value == '') {
    return '';
  } else {
    return userIdCondition.test(userId.value) ? 'is-valid' : 'is-invalid';
  }
});

const passwordValid = computed(() => {
  if (password.value == '') {
    return '';
  } else {
    return passwordCondition.test(password.value) ? 'is-valid' : 'is-invalid';
  }
});

const login = function () {
  store.login(userId.value, password.value);
};


</script>

<style scoped>
.form-floating {
  width: 100%;
}

.input-form {
  width: 100%;
}

.is-valid {
  color: blue;
}

.is-invalid {
  color: red;
}

.form-text {
  font-size: 0.8rem;
}

.btn-box {
  display: flex;
  justify-content: space-around;
}



.my-button{
  align-content: center;
  background-color:#000000;
  color:#fff;
  border:none;
  position:relative;
  height:4vh;
  font-size:1em;
  padding:0 2em;
  cursor:pointer;
  transition:800ms ease all;
  outline:none;
}
.my-button:hover{
  background:#fff;
  color:#000000;
}
.my-button:before, .my-button:after{
  content:'';
  position:absolute;
  top:0;
  right:0;
  height:2px;
  width:0;
  background: #3a3a3a;
  transition:400ms ease all;
}

.my-button:after{
  right:inherit;
  top:inherit;
  left:0;
  bottom:0;
}

.my-button:hover:before, .my-button:hover:after{
  width:100%;
  transition:800ms ease all;
}


</style>
