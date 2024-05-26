<template>
    <div class="nav-container">
        <RouterLink class="category" :to="'/product'">All</RouterLink>
        <RouterLink class="category" v-for="item in store.category" :key="item.id" :to="`/product/${item.id}`">
            <div> {{ item.name }} </div>
        </RouterLink>
    </div>
</template>

<script setup>

import { onMounted } from 'vue'
import { useProductStore } from '@/stores/product';

const store = useProductStore()


onMounted(() => {
    store.category;
})

</script>

<style scoped>
.nav-container {
    height: 60px;
    border: solid 1px;
    border-top: 0;
    border-right: 0;
    border-left: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0 20px;
    margin: 0, 100rem;
    /* 좌우 패딩 */
}

.category:hover {
    font-weight: 1000;
    font-size: x-large;
}

.category {
    padding: 10px;
    text-decoration: none;
    color: inherit;
    margin: 0 2rem;
}
</style>
