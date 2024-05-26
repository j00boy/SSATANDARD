<template>
  <div class="modal" v-if="store.isSignUpModalVisible">
    <div class="modal-content">
      <SignUpForm />
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import SignUpForm from './SignUpForm.vue';

const store = useUserStore();

</script>

<style scoped>
.modal {
  display: block;
  position: fixed;
  z-index: 1;
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
  width: 100%;
  max-width: 600px;
}

span {
  margin-left: 3%;
  font-size: larger;
  font-weight: bolder;
}
</style>