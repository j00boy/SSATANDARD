<template>
    <div class="container mt-3">
        <h2 class="mb-3">내 정보</h2>
        <div>
            
            <div class="user-info card p-4 rounded shadow mb-5">
                <div class="d-flex align-items-center">
                    <div class="user-image me-4 border border-secondary rounded p-1 shadow-sm">
                        <img :src="store.loginUser.image">
                    </div>
                    <div class="user-details">
                        <p class="fs-4 mb-1 ms-4"><strong>아이디 :</strong> {{ store.loginUser.userId }}</p>
                        <p class="fs-4 mb-1 ms-4"><strong>이름 :</strong> {{ store.loginUser.userName }}</p>
                        <p class="fs-4 mb-1 ms-4"><strong>핸드폰 :</strong> {{ store.loginUser.phoneNumber }}</p>
                        <p class="fs-4 mb-1 ms-4"><strong>주소 :</strong> {{ store.loginUser.address }}</p>
                        <p class="fs-4 mb-0 ms-4"><strong>가입일자 :</strong> {{ store.loginUser.regDate }}</p>
                    </div>
                </div>
            </div>
            <div>
                <a href="#" @click="store.receipt = !store.receipt" data-text="Receipt">Button</a>
                <MyPageInfo v-if="store.receipt"/>
            </div>
        </div>
    </div>
    
</template>

<script setup>
import MyPageInfo from '@/mypage/components/MyPageInfo.vue';

import { ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/user';

const store = useUserStore();

const userInfo = ref({
    userId: '',
    userName: '',
    phoneNumber: '',
    address: '',
    image: '',
    regDate: '',
});

onMounted(async () => {
    await store.getMyInfo();

    userInfo.value = {
        userId: store.loginUser.userId,
        userName: store.loginUser.userName,
        phoneNumber: store.loginUser.phoneNumber,
        address: store.loginUser.address,
        image: store.loginUser.image,
        regDate: store.loginUser.regDate
    };
});
</script>

<style scoped>
.container {
    margin: 0 auto;
}

h2 {
    font-size: 2.5rem;
    text-align: center;
    margin-bottom: 1.5rem;
}

.user-info {
    width: 100%;
    background-color: #ffffff;
    border: 1px solid #dee2e6;
    border-left: 5px solid #000000;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.user-image {
    width: 30%;
    overflow: hidden;
    display: flex;
    align-items: center;
    justify-content: center;
}

.user-image img {
    width: 100%;
    height: auto;
}

.user-details {
    font-size: 1.2rem;
}

.user-details p {
    margin: 1.7rem 0;
}

body{
  margin: 0;
  padding: 0;
}

a {
  position: relative;
  transform: translate(-50%,-50%);
  width: 160px;
  height: 50px;
  padding-left: 3rem;
  font-size: 2.5rem;
  text-align: center;
  font-family: "teko";
  text-decoration: none;
  text-transform: uppercase;
  background: #ccc;
}
a:before{
  content: attr(data-text);
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  text-align: center;
  background: #000000;
  color: #fff;
  transition: 0.5s;
  transform-origin: bottom;
  transform: translatey(-100%) rotatex(90deg);
}
a:hover:before
{
  transform: translatey(0) rotatex(0deg);
}

a:after{
  content: attr(data-text);
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  text-align: center;
  background: #000000;
  color: #fff;
  transition: 0.5s;
  transform-origin: top;
  transform: translatey(0) rotatex(0deg);
}
a:hover:after
{
  transform: translatey(100%) rotatex(90deg);
}

</style>