<template>
  <div class="modal" v-if="store.isEditModalVisible">
    <div class="modal-content">
      <MyPageEditForm />
    </div>
  </div>
</template>

<script setup>
import MyPageEditForm from '@/mypage/components/MyPageEditForm.vue';
import { useUserStore } from '@/stores/user';

const store = useUserStore()

</script>

<style scoped>
.modal {
  display: block;
  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
  background-color: #fefefe;
  padding: 20px;
  border: 1px solid #888;
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: auto;
  /* 자동으로 내용에 맞춰 조절 */
  max-width: 600px;
  /* 최대 너비 설정 */
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>