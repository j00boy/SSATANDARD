<template>
  <div>
    <button @click="showModal">회원가입</button>
    <SignUpModal :isVisible="isModalVisible" :close="hideModal" />
  </div>
</template>

<script setup>
import SignUpModal from '@/signup/components/SignUpModal.vue';
import { ref } from 'vue';

const isModalVisible = ref(false);

const showModal = () => {
  isModalVisible.value = true;
};

const hideModal = () => {
  isModalVisible.value = false;
};
</script>
