<template >
  <aside class="sidebar">
    <div class="profile">
      <div class="profile-box">
        <img :src="loginUser.image">
      </div>
      <p class="profile-name">{{ loginUser.userName }}</p>
    </div>
    <nav>
      <div class="nav-bar">
        <RouterLink class="nav-item p-4 nav-item-detail" :to="{ name: 'mypageHome' }" exact-active-class="active">홈</RouterLink>
        <RouterLink class="nav-item p-4 nav-item-detail mb-5" :to="{ name: 'mypageOrder' }" exact-active-class="active">내 주문내역</RouterLink>
        <a class="nav-item p-4 nav-item-detail at-first" @click="store.showEditModal" exact-active-class="active">내 정보 수정</a>
        <a class="nav-item p-4 nav-item-detail" @click="store.showWithdrawModal" exact-active-class="active">탈퇴하기</a>
      </div>
    </nav>
  </aside>
  <MyPageEditModal v-if="store.isEditModalVisible"/>
  <MyPageWithdrawModal v-if="store.isWithdrawModalVisible"/>
</template>

<script setup>
import MyPageEditModal from './MyPageEditModal.vue';
import MyPageWithdrawModal from './MyPageWithdrawModal.vue';

import { useUserStore } from '@/stores/user';
import { computed, onMounted } from 'vue';

const store = useUserStore();

const loginUser = computed(() => store.loginUser);

onMounted(() => {
  store.getLoginUser();
});
</script>

<style scoped>

img {
  width: 100%;
  height: 100%;
}

.sidebar {
  height: 100%;
  border: solid 1px;
  border-top: 0;
  border-bottom: 0;
  background-color: white;
  color: black;
  box-sizing: border-box;
}

.profile {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  margin-bottom: 4rem;
}

.profile-box {
  width:250px;
  height:250px;
  border-radius: 50%;
  overflow:hidden;
  margin: 1.5rem;
}

.profile-name {
  margin-top: 0.8rem;
  font-size: xx-large;
}

.nav-bar {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.nav-item {
  width: 100%;
  text-align: center;
  cursor: pointer; /* Add cursor pointer to indicate it's clickable */
}

.nav-item:hover {
  color: white;
  background-color: black;
}

.nav-item-detail {
  text-decoration: none;
  color: rgb(0, 0, 0);
  font-size: 1.5rem;
}

.active {
  background-color: rgb(0, 0, 0);
  color: rgb(255, 255, 255);
}
</style>
