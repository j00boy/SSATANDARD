<template>
  <div class="container" @keyup.enter="update">
    <h2>회원정보 수정</h2>
    <div class="form-floating mb-3 mt-3">
      <input type="text" class="form-control input-form" id="userId" v-model="store.loginUser.userId" disabled />
      <label for="userId">아이디</label>
    </div>
    <div class="form-floating mb-3">
      <input type="password" class="form-control input-form" :class="passwordValid" id="password" placeholder="비밀번호"
        v-model="userInfo.password" />
      <label for="password">비밀번호</label>
      <small class="form-text text-muted">8-20자, 영문 대소문자, 숫자, 특수문자(!@#$%^&*()_+)만 가능</small>
    </div>
    <div class="form-floating mb-3">
      <input type="text" class="form-control input-form" id="userName" v-model="store.loginUser.userName" disabled />
      <label for="userName">이름</label>
      <small class="form-text text-muted">한글만 입력 가능</small>
    </div>
    <div class="form-floating mb-3">
      <input type="text" class="form-control input-form" :class="phoneNumberValid" id="phoneNumber" placeholder="휴대폰"
        v-model="userInfo.phoneNumber" />
      <label for="phoneNumber">핸드폰</label>
      <small class="form-text text-muted">형식: XXX-XXXX-XXXX</small>
    </div>
    <div class="form-floating mb-3">
      <div>
        <div class="d-flex">
          <input type="text" class="form-control input-form" v-model="addressFromAPI.roadAddress" placeholder="주소" readonly />
          <button class="btn btn-outline-dark address-btn" id="postcode" @click="openPostcode">검색</button><br/>
        </div>
        <input type="text" class="form-control input-form" v-model="addressFromAPI.detailAddress" placeholder="상세주소" />
      </div>
    </div>
    <div class="btn-box">
      <button class="btn my-button" @click="store.hideEditModal">닫기</button>
      <button class="btn my-button" @click="update">정보 수정</button>
    </div>
  </div>
</template>

<script setup>
import { watch, computed, ref, onMounted } from "vue";
import { useUserStore } from "@/stores/user";

const store = useUserStore();

const addressFromAPI = ref({
  roadAddress: '',
  detailAddress: '',
})

const userInfo = ref({
  userId: store.loginUser.userId,
  password: "",
  userName: store.loginUser.userName,
  phoneNumber: "",
  address: '',
});

watch([addressFromAPI], ([newAddress]) => {
  userInfo.value.address = newAddress.roadAddress + ' ' + newAddress.detailAddress;
})


const openPostcode = function() {
  new window.daum.Postcode({
    oncomplete: (data) => {
      addressFromAPI.value.roadAddress = data.roadAddress
    }
  }).open();
}

onMounted(async () => {
  await store.getLoginUser();
  const loginUser = store.loginUser;
  
  userInfo.value = {
    userId: loginUser.userId,
    password: "",
    userName: loginUser.userName,
    phoneNumber: "",
    address: "",
  };

});

const passwordCondition = /^[a-zA-Z0-9!@#$%^&*()_+]{8,20}$/;
const phoneNumberCondition = /^\d{3}-\d{3,4}-\d{4}$/;

const passwordValid = computed(() => {
  if(userInfo.value.password === '') {
    return ''
  } else {
    return passwordCondition.test(userInfo.value.password) ? 'is-valid' : 'is-invalid'
  }
})

const phoneNumberValid = computed(() => {
  if(userInfo.value.phoneNumber == '') {
    return ''
  } else {
    return phoneNumberCondition.test(userInfo.value.phoneNumber) ? 'is-valid' : 'is-invalid'
  }
})

const update = function () {
  // 자꾸 주소 변경이 한번에 안먹어서 여기서 임의로 한번에 처리
  userInfo.value.address = addressFromAPI.value.roadAddress + " " + addressFromAPI.value.detailAddress;
  if (
    !passwordCondition.test(userInfo.value.password) ||
    !phoneNumberCondition.test(userInfo.value.phoneNumber)
  ) {
    alert("입력 조건을 만족하지 않습니다.");
    return;
  }

  if(userInfo.value.address == '') {
    alert('모든 필드를 입력해주세요.')
    return;
  }

  store.update(userInfo.value);
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
