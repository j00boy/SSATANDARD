<template>
  <div class="container" @keyup.enter="confirmWithdraw">
    <h2>회원 탈퇴</h2>
    <div class="form-floating mb-3 mt-3">
      <input type="text" class="form-control input-form" :class="userIdValid" id="userId" placeholder="아이디"
        v-model="userInfo.userId" />
      <label for="userId">아이디</label>
    </div>
    <div class="form-floating mb-3">
      <input type="password" class="form-control input-form" :class="passwordValid" id="password" placeholder="비밀번호"
        v-model="userInfo.password" />
      <label for="password">비밀번호</label>
    </div>
    <div class="form-floating mb-3">
      <input type="password" class="form-control input-form" :class="passwordConfirmValid" id="passwordConfirm"
        placeholder="비밀번호 재확인" v-model="userInfo.passwordConfirm" />
      <label for="passwordConfirm">비밀번호 확인</label>
    </div>
    <div class="form-floating mb-3">
      <input type="text" class="form-control input-form" :class="phoneNumberValid" id="phoneNumber" placeholder="휴대폰"
        v-model="userInfo.phoneNumber" />
      <label for="phoneNumber">핸드폰</label>
      <small class="form-text text-muted">형식: XXX-XXXX-XXXX</small>
    </div>
    <div class="btn-box">
      <button class="btn my-button" @click="store.hideWithdrawModal">닫기</button>
      <button class="btn my-button" @click="withdraw">탈퇴하기</button>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/user';

const store = useUserStore();

const loginInfo = ref({
  userId: '',
  password: '',
  passwordConfirm: '',
  phoneNumber: '',
});

const userInfo = ref({
  userId: '',
  password: '',
  passwordConfirm: '',
  phoneNumber: '',
});

onMounted(async () => {
  await store.getLoginUser();
  const loginUser = store.loginUser;

  loginInfo.value = {
    userId: loginUser.userId,
    password: loginUser.password,
    passwordConfirm: loginUser.password, // 수정
    phoneNumber: loginUser.phoneNumber,
  };

});

const userIdCondition = /^[a-zA-Z0-9]{4,16}$/;
const passwordCondition = /^[a-zA-Z0-9!@#$%^&*()_+]{8,20}$/;
const passwordConfirmCondition = /^[a-zA-Z0-9!@#$%^&*()_+]{8,20}$/;
const phoneNumberCondition = /^\d{3}-\d{3,4}-\d{4}$/;

const userIdValid = computed(() => {
  if (userInfo.value.userId == '') {
    return ''
  } else {
    const isValid = userIdCondition.test(userInfo.value.userId);
    const isSame = userInfo.value.userId === loginInfo.value.userId;
    return isValid && isSame ? 'is-valid' : 'is-invalid';
  }
});

const passwordValid = computed(() => {
  if (userInfo.value.password == '') {
    return ''
  } else {
    const isValid = passwordCondition.test(userInfo.value.password);
    const isSame = userInfo.value.password === loginInfo.value.password;
    return isValid && isSame ? 'is-valid' : 'is-invalid';
  }
});

const passwordConfirmValid = computed(() => {
  if (userInfo.value.passwordConfirm == '') {
    return ''
  } else {
    return passwordConfirmCondition.test(userInfo.value.passwordConfirm) && (userInfo.value.passwordConfirm == userInfo.value.password) ? 'is-valid' : 'is-invalid';
  }
});

const phoneNumberValid = computed(() => {
  if (userInfo.value.phoneNumber == '') {
    return ''
  } else {
    const isSame = userInfo.value.phoneNumber === loginInfo.value.phoneNumber;
    return phoneNumberCondition.test(userInfo.value.phoneNumber) && isSame ? 'is-valid' : 'is-invalid'
  }
});

const withdraw = function () {
  if (
    !userIdCondition.test(userInfo.value.userId) ||
    !passwordCondition.test(userInfo.value.password) ||
    !phoneNumberCondition.test(userInfo.value.phoneNumber)
  ) {
    alert('입력 조건을 만족하지 않습니다.');
    return;
  }

  for (const key in userInfo.value) {
    if (!userInfo.value[key]) {
      alert('모든 필드를 입력해주세요.');
      return;
    }
  }

  if (confirmWithdraw()) {
    store.withdraw(userInfo.value.userId);
  } else {
    alert("취소되었습니다.")
  }
};

const confirmWithdraw = function () {
  return confirm("탈퇴하시겠습니까?");
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
