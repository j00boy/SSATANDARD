<template>
  <div class="header">
    <div class="left-links">
      <a href="/" v-if="!isAdminUser">
        <h3><i class="bi bi-house-door-fill"></i></h3>
      </a>
      <span>
        <h6 class="ms-4" v-if="isLoggedIn"><strong>{{ userId }}</strong>  님, 안녕하세요.</h6>
      </span>
    </div>


    <div class="logo-center">
      <a href="/" v-if="!isAdminUser" class="teko">SSATANDARD</a>
    </div>


    <div class="right-links">
      <a href="#" v-if="!isLoggedIn" @click="store.showSignUpModal">
        <h3><i class="bi bi-person-fill-add"></i></h3>
      </a>
      <a href="#" v-if="!isLoggedIn" @click="store.showLoginModal">
        <h2><i class="bi bi-box-arrow-in-left"></i></h2>
      </a>


      <a href="/cart" v-if="isLoggedIn & !isAdminUser">
        <h3><i class="bi bi-cart2"></i></h3>
      </a>
      <a href="/mypage" v-if="isLoggedIn & !isAdminUser">
        <h3><i class="bi bi-person"></i></h3>
      </a>
      <a href="/" @click.prevent="logout" v-if="isLoggedIn">
        <h3><i class="bi bi-box-arrow-right"></i></h3>
      </a>
      <ProductSearchInput class="search-box" v-if="!isAdminUser" />
    </div>
  </div>

  <SignUpModal v-if="store.isSignUpModalVisible" />
  <LoginModal v-if="store.isLoginModalVisible" />
</template>

<script setup>
import { computed } from 'vue';
import { useUserStore } from '@/stores/user';
import SignUpModal from '@/signup/components/SignUpModal.vue'; // 모달 컴포넌트를 임포트
import LoginModal from '@/login/components/LoginModal.vue';
import ProductSearchInput from '@/products/components/ProductSearchInput.vue'

const store = useUserStore();
const isLoggedIn = computed(() => sessionStorage.getItem('userId') !== null);
const isAdminUser = computed(() => sessionStorage.getItem('isAdmin') == 1);

const userId = sessionStorage.getItem('userId')

const logout = () => {
  if (confirmLogout()) {
    store.logout();
  }
};

const confirmLogout = function () {
  return confirm("로그아웃 하시겠습니까?");
};

</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Teko:wght@300..700&display=swap');

.header {
  height: 70px;
  margin-top: 10px;
  padding-top: 0px;
  border: solid 1px;
  border-color: rgb(202, 202, 202);
  border-right: 0;
  border-left: 0;
  display: flex;
  align-items: center;
  padding: 0 20px;
  /* 좌우 패딩 */
}

.left-links,
.logo-center,
.right-links {
  flex: 1;
  /* 모든 영역이 같은 비율로 너비를 차지하게 설정 */
  display: flex;
  justify-content: center;
  align-items: center;
}

.left-links {
  justify-content: flex-start;
  margin-top: 4px;
}

.right-links {
  justify-content: flex-end;
  margin-top: 10px;
}

.nav-links a,
.nav-links .router-link,
.left-links a,
.left-links .router-link,
.right-links a,
.right-links .router-link {
  padding: 10px;
  text-decoration: none;
  font-weight: 1000;
  color: black;
  transition: color 0.3s;
}

.nav-links a:hover,
.nav-links .router-link:hover,
.left-links a:hover,
.left-links .router-link:hover,
.right-links a:hover,
.right-links .router-link:hover {
  color: rgb(0, 0, 0);
  font-size: large;
}

.logo-center a {
  margin-top: 10px;
  text-align: center;
  color: rgb(0, 0, 0);
  font-size: xx-large;
  font-weight: 1000;
  text-decoration: none;
  font-size: xxx-large;
}

.teko {
  font-family: "Teko", sans-serif;
  font-optical-sizing: auto;
  font-weight: 300;
  font-style: normal;
}

.search-box {
  margin-left: 1rem;
}
</style>
