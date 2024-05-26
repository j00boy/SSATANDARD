<template>
  <div class="fontst"> 
    <template v-if="isAdmin">
      <Header/>
      <RouterView/>
    </template>
    <template v-else>
      <Header/>
      <HeaderNav/>
      <RouterView/>
      <Footer/>
    </template>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import Header from '@/common/components/Header.vue';
import HeaderNav from '@/common/components/HeaderNav.vue';
import Footer from '@/common/components/Footer.vue';

const isAdmin = ref(false);

onMounted(() => {
  isAdmin.value = sessionStorage.getItem('isAdmin') === '1';
});
</script>

<style scoped>

@font-face {
  font-family: 'Pretendard-Regular';
  src: url('https://fastly.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
  font-weight: 400;
  font-style: normal;
}

.fontst {
  font-family: 'Pretendard-Regular';
}

.my-button{
  align-content: center;
  background:#000000;
  color:#fff;
  border:none;
  position:relative;
  height:4vh;
  font-size:1.6em;
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
