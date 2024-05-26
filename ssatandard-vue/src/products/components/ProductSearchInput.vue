<template>
    <div class="search-container" @keyup.enter="searchProductList">
        <div class="row justify-content-center align-items-center">
            <div class="col-auto" v-if="isSearchVisible">
                <input type="text" class="form-control" v-model="searchInfo.word" placeholder="Search.." />
            </div>
            <div class="col-auto btn-search">
                <button @click="toggleSearch">
                    <h4><i :class="isSearchVisible ? 'fas fa-search' : 'fas fa-search'"></i></h4>
                    
                </button>
            </div>
        </div>
    </div>
</template>




<script setup>
import { ref } from "vue";
import { useProductStore } from "@/stores/product";
import { useRouter } from 'vue-router';

const router = useRouter();
const store = useProductStore();

const searchInfo = ref({
    key: "productName",
    word: "",
});

const isSearchVisible = ref(false); // 검색창 표시 여부를 제어하는 상태

const toggleSearch = () => {
    if (isSearchVisible.value && searchInfo.value.word) {
        searchProductList();
    } else {
        isSearchVisible.value = true; // 검색창 표시
    }
};

const searchProductList = () => {
    if (searchInfo.value.word) {
        router.push({
            path: '/product',
            query: {
                key: searchInfo.value.key,
                word: searchInfo.value.word,
            }
        });
        store.searchProductList(searchInfo.value);
    }
};
</script>


<style scoped>
.col-auto {
    padding: 1px;
    margin-bottom: 7px;
}

.btn-search {
    margin-top: 5px;
}

.search-container {
    display: flex;
    align-items: center;
}

.fas.fa-search {
    cursor: pointer;
}

button {
    border: none;
    background-color: white;
    
}
</style>

