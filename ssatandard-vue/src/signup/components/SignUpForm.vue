<template>
  <div class="container" @keyup.enter="signup">
    <div class="form-floating mb-3">
      <input type="text" class="form-control input-form" :class="userIdValid" id="userId" placeholder="아이디"
        v-model="userInfo.userId">
      <label for="userId">아이디</label>
      <small class="form-text text-muted">영문 대소문자와 숫자 4-16자만 가능</small>
    </div>
    <div class="form-floating mb-3">
      <input type="password" class="form-control input-form" :class="passwordValid" id="password" placeholder="비밀번호"
        v-model="userInfo.password">
      <label for="password">비밀번호</label>
      <small class="form-text text-muted">8-20자, 영문 대소문자, 숫자, 특수문자(!@#$%^&*()_+)만 가능</small>
    </div>
    <div class="form-floating mb-3">
      <input type="text" class="form-control input-form" :class="userNameValid" id="userName" placeholder="이름"
        v-model="userInfo.userName">
      <label for="userName">이름</label>
      <small class="form-text text-muted">한글만 입력 가능</small>
    </div>
    <div class="form-floating mb-3">
      <input type="text" class="form-control input-form" :class="phoneNumberValid" id="phoneNumber" placeholder="휴대폰"
        v-model="userInfo.phoneNumber">
      <label for="phoneNumber">핸드폰</label>
      <small class="form-text text-muted">형식: XXX-XXXX-XXXX</small>
    </div>
    <div class="form-floating mb-3">
      <div>
        <div class="d-flex">
          <input type="text" class="form-control input-form" v-model="addressFromAPI.roadAddress" placeholder="주소"
            readonly />
          <button class="btn btn-outline-dark address-btn" id="postcode" @click="openPostcode">검색</button>
        </div>
        <input type="text" class="form-control input-form" v-model="addressFromAPI.detailAddress" placeholder="상세주소" />
      </div>
    </div>
    <div class="btn-box">
      <button class="btn my-button" @click="store.hideSignUpModal">닫기</button>
      <button class="btn my-button" @click="signup">회원가입</button>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from "vue";
import { useUserStore } from "@/stores/user";

const addressFromAPI = ref({
  roadAddress: '',
  detailAddress: '',
})

const openPostcode = function () {
  new window.daum.Postcode({
    oncomplete: (data) => {
      addressFromAPI.value.roadAddress = data.roadAddress
    }
  }).open();
}

const store = useUserStore()

const userInfo = ref({
  userId: "",
  password: "",
  userName: "",
  phoneNumber: "",
  address: computed(() => addressFromAPI.value.roadAddress + " ".concat(addressFromAPI.value.detailAddress))
})

const userIdCondition = /^[a-zA-Z0-9]{4,16}$/;
const passwordCondition = /^[a-zA-Z0-9!@#$%^&*()_+]{8,20}$/;
const userNameCondition = /^[가-힣]{2,10}$/;
const phoneNumberCondition = /^\d{3}-\d{3,4}-\d{4}$/;

// is-valid => bootstrap
const userIdValid = computed(() => {
  if (userInfo.value.userId == '') {
    return ''
  } else {
    return userIdCondition.test(userInfo.value.userId) ? 'is-valid' : 'is-invalid'
  }
})

const passwordValid = computed(() => {
  if (userInfo.value.password == '') {
    return ''
  } else {
    return passwordCondition.test(userInfo.value.password) ? 'is-valid' : 'is-invalid'
  }
})

const userNameValid = computed(() => {
  if (userInfo.value.userName.length == '') {
    return ''
  } else {
    return userNameCondition.test(userInfo.value.userName) ? 'is-valid' : 'is-invalid'
  }
})

const phoneNumberValid = computed(() => {
  if (userInfo.value.phoneNumber == '') {
    return ''
  } else {
    return phoneNumberCondition.test(userInfo.value.phoneNumber) ? 'is-valid' : 'is-invalid'
  }
})

const signup = function () {
  if (!userIdCondition.test(userInfo.value.userId) || !passwordCondition.test(userInfo.value.password) || !userNameCondition.test(userInfo.value.userName) || !phoneNumberCondition.test(userInfo.value.phoneNumber)) {
    alert("입력 조건을 만족하지 않습니다.")
    return;
  }

  for (const key in userInfo.value) {
    if (!userInfo.value[key]) {
      alert("입력을 완료해주세요.")
      return;
    }
  }

  // 모든 조건을 충족하면,
  store.signup(userInfo.value);
  store.hideSignUpModal();
}

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


.my-button {
  align-content: center;
  background-color: #000000;
  color: #fff;
  border: none;
  position: relative;
  height: 4vh;
  font-size: 1em;
  padding: 0 2em;
  cursor: pointer;
  transition: 800ms ease all;
  outline: none;
}

.my-button:hover {
  background: #fff;
  color: #000000;
}

.my-button:before,
.my-button:after {
  content: '';
  position: absolute;
  top: 0;
  right: 0;
  height: 2px;
  width: 0;
  background: #3a3a3a;
  transition: 400ms ease all;
}

.my-button:after {
  right: inherit;
  top: inherit;
  left: 0;
  bottom: 0;
}

.my-button:hover:before,
.my-button:hover:after {
  width: 100%;
  transition: 800ms ease all;
}

.address-btn {
  width: 15%;
}
</style>
