<template>
  <div class="recommend-container" style="text-align: center;">
    <h3>BEST Top 4</h3>
    <div class="recommend-list">
      <div class="recommend-item" v-for="item in store.topRatedProducts" :key="item.id">
        <div class="card" style="width: 18rem">
          <img :src="item.image" class="card-img-top" alt="...">
          <div class="card-body">
            <p class="card-text">{{ item.productName }}</p>
            <hr>
            <p class="card-text">₩ {{ item.productPrice.toLocaleString() }}</p>
            <hr>

            <p class="card-text">판매량 : {{ item.productSold }}</p>
          </div>
        </div>
      </div>
    </div>

    <h3>NEW</h3>
    <div class="recommend-list">
      <div class="recommend-item" v-for="item in store.latestProducts" :key="item.id">
        <div class="card" style="width: 18rem;">
          <img :src="item.image" class="card-img-top" alt="...">
          <div class="card-body">
            <p class="card-text">{{ item.productName }}</p>
            <hr>
            <p class="card-text">₩ {{ item.productPrice.toLocaleString() }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useProductStore } from '@/stores/product';
import { onMounted } from 'vue';

const store = useProductStore();

onMounted(async() => {
  await store.searchProductList();
})

</script>


<style scoped>
.recommend-container {
  margin-top: 2rem;
}

.recommend-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.recommend-item {
  margin: 1rem;
}

h3 {
  margin-left: 1.2%;
  font-weight: bolder;
}
</style>
