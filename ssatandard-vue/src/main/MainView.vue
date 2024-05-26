<template>
  <div class="main-view">
    <Carousel />
    <Recommend />
  </div>
</template>

<script setup>
import Carousel from '@/main/components/Carousel.vue';
import Recommend from '@/main/components/Recommend.vue';
</script>

<style scoped>
.main-view {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
