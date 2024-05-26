<template>
  <aside class="sidebar">
    <div class="profile">
      <div class="profile-box">
        <img :src="loginUser.image">
      </div>
      <p class="profile-name">{{ loginUser.userName }}</p>
    </div>
    <nav>
      <div class="nav-bar">
        <RouterLink class="nav-item p-4 nav-item-detail" :to="{ name: 'adminHome' }" exact-active-class="active">홈</RouterLink>
        <RouterLink class="nav-item p-4 nav-item-detail" :to="{ name: 'adminProduct' }" exact-active-class="active">상품관리</RouterLink>
        <RouterLink class="nav-item p-4 nav-item-detail" :to="{ name: 'adminOrder' }" exact-active-class="active">주문관리</RouterLink>
        <RouterLink class="nav-item p-4 nav-item-detail" :to="{ name: 'adminUser' }" exact-active-class="active">회원관리</RouterLink>
      </div>
    </nav>
  </aside>
</template>

<script setup>
import { useAdminStore } from '@/stores/admin';
import { computed, onMounted } from 'vue';

const store = useAdminStore();

const loginUser = computed(() => store.loginUser);

onMounted(() => {
  store.getLoginUser();
});
</script>

<style scoped>
img {
  width: 100%;
  border-radius: 50%;
  padding: 1rem;
}

.sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: 18%;
  height: 100%;
  background-color: black;
  color: #fff;
  box-sizing: border-box;
}

.profile {
  text-align: center;
  margin-bottom: 4rem;
}

.profile-box {
  border-radius: 100%;
  overflow:visible;
  margin: 1rem;
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
  color: black;
  background-color: white;
}

.nav-item-detail {
  text-decoration: none;
  color: white;
  font-size: 1.3rem;
}

.active {
  background-color: white;
  color: black;
}
</style>
