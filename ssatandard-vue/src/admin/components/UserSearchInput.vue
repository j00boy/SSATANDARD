<template>
    <div class="search-container" @keyup.enter="searchUser">
        <div class="row justify-content-center align-items-center">
            <div class="col-auto">
                <select name="key-option" id="key-option" class="pl" v-model="searchInfo.key">
                    <option value="userName">이름</option>
                    <option value="userId">아이디</option>
                    <option value="address">주소</option>
                </select>
            </div>
            <div class="col-auto">
                <input type="text" class="form-control" v-model="searchInfo.word" placeholder="검색어를 입력하세요" />
            </div>
            <div class="col-auto">
                <button class="btn btn-dark" @click="searchUser">검색</button>
            </div>
        </div>
    </div>

</template>

<script setup>
import { ref } from "vue";
import { useAdminStore } from "@/stores/admin";
import { useRouter } from 'vue-router';

const router = useRouter();
const store = useAdminStore();

const searchInfo = ref({
    key: "userName",
    word: "",
});

const searchUser = async () => {
    await store.searchUser(searchInfo.value);
    router.push({ 
        path: "/admin/users",
        query: {
            key: searchInfo.value.key,
            word: searchInfo.value.word,
        }
    });
};

</script>

<style scoped>

.pl {
    border: 1px solid #c4c4c4;
    border-radius: 0.5rem;
    padding: 12px;
    font-weight: 400;
    font-size: 0.9rem;
    line-height: 16px;
  }
  
  .pl:focus {
    border: 0.5px solid #c8c8c8;
    border-radius: 10px;
    outline: 2px solid #7a7a7a;
  }
.search-container {
    margin-top: 20px;
}

.form-control {
    width: 300px; /* 원하는 너비로 조정 */
}

.btn-outline-warning {
    margin-left: 10px;
}
</style>
