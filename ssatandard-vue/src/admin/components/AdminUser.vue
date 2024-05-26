<template>
  <main class="main-content">
    <header>
      <h1>회원관리</h1>
    </header>
    <div>
      <UserSearchInput />
    </div>
    <div class="ali-center">
      <div class="w-90">
        <div class="mb-4 d-flex justify-content-between align-items-center">
          <div class="fs-3">총 회원: {{ store.users.length.toLocaleString() }} 명</div>
          <select name="sort-btns" id="sort-btns" class="pl" @change="handleSortChange">
            <option value="userId_ASC">아이디 a</option>
            <option value="userId_DESC">아이디 z</option>
            <option value="createdAt_ASC">가입 오래된 순</option>
            <option value="createdAt_DESC">가입 최신순</option>
            <option value="userName_ASC">이름순 ㄱ</option>
            <option value="userName_DESC">이름순 ㅎ</option>
            <option value="totalOrderPrice_DESC">구매금액 큰 순</option>
            <option value="totalOrderPrice_ASC">구매금액 작은 순</option>
          </select>
        </div>
      </div>
    </div>
    <section class="tables">
      <div class="ali-center">
        <div class="table">
          <div class="table-container ali-center">
            <table>
              <thead>
                <tr>
                  <th class="table-text" width="17.5%">가입일자</th>
                  <th class="table-text">이미지</th>
                  <th class="table-text" width="10%">아이디</th>
                  <th class="table-text" width="10%">성함</th>
                  <th class="table-text" width="15%">핸드폰 번호</th>
                  <th class="table-text" width="20%">주소</th>
                  <th class="table-text" width="">총 주문 금액</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in user" :key="item.id">
                  <td>{{ item.regDate }}</td>
                  <td class="table-img"><img :src="item.image"></td>
                  <td>{{ item.userId }}</td>
                  <td>{{ item.userName }}</td>
                  <td>{{ item.phoneNumber }}</td>
                  <td>{{ item.address }}</td>
                  <td>₩ {{ item.totalOrderPrice.toLocaleString() }}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <nav aria-label="Page navigation">
            <ul class="pagination d-flex justify-content-center">
              <li class="page-item" :class="{ disabled: currentPage <= 1 }">
                <a class="page-link" @click.prevent="changePage(currentPage - 1)" href="#">&lt;</a>
              </li>
              <li class="page-item" :class="{ disabled: currentPage >= pageCount }">
                <a class="page-link" @click.prevent="changePage(currentPage + 1)" href="#">&gt;</a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
import { onMounted, computed, ref, watch } from 'vue';
import { useAdminStore } from '@/stores/admin';
import UserSearchInput from './UserSearchInput.vue';
import { useRouter, useRoute } from 'vue-router';

const route = useRoute();
const store = useAdminStore();
const currentPage = ref(1);
const perPage = 5;

const user = computed(() => {
  const start = (currentPage.value - 1) * perPage;
  const end = currentPage.value * perPage;
  return store.users.slice(start, end);
});

const pageCount = computed(() => {
  return Math.ceil(store.users.length / perPage);
});

const changePage = (page) => {
  if (page >= 1 && page <= pageCount.value) {
    currentPage.value = page;
  }
};

onMounted(() => {
  store.searchUser();
  store.getAllOrders();
});

const searchInfo = ref({
    key: "none",
    word: route.query.word || "",
    orderBy: "none",
    orderByDir: "DESC",  // 기본: 내림차순
});

// 정렬 변경 시 다시 load해오기 -> 페이지는 다시 1로
const setOrderBy = (orderBy, orderByDir) => {
  searchInfo.value.orderBy = orderBy;
  searchInfo.value.orderByDir = orderByDir;
  currentPage.value = 1; // 현재 페이지를 1로 설정
  loadUsers();
}

const handleSortChange = (event) => {
  const [orderBy, orderByDir] = event.target.value.split('_');
  setOrderBy(orderBy, orderByDir);
}

const loadUsers = async () => {
  const params = {
    categoryId: route.params.id || null,
    key: searchInfo.value.key,
    word: searchInfo.value.word,
    orderBy: searchInfo.value.orderBy,
    orderByDir: searchInfo.value.orderByDir,
  };
    await store.searchUser(params);
};


watch(
  () => store.users,
  () => {
    currentPage.value = 1;
  }
);
</script>

<style scoped>

.w-90 {
  width: 90%;
}

.pl {
  width: 10%;
  height: 3rem;
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

.table-text {
  text-align: center;
}

.ali-center {
  display: flex;
  justify-content: center;
}

aside {
  width: 20%;
}

.table-img {
  width: 180px;
  height: 180px;
}

img {
  width: 180px;
  height: 180px;
  object-fit: cover;
}

.main-content {
  flex: 1;
  padding: 20px;
}

header {
  text-align: center;
  margin-bottom: 20px;
}

.stats {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.stat-box {
  flex: 1;
  width: 50%;
  text-align: center;
  padding-top: 20px;
  padding-bottom: 20px;
  margin: 0 10px;
  border-radius: 10px;
  background-color: #f2f2f2;
  box-shadow: 3px 3px 10px;
}

.regist-btn {
  display: flex;
  text-decoration: none;
  width: 98%;
  font-weight: 10;
  justify-content: center;
  border: solid 1px black;
  color: white;
  background-color: black;
  height: 4rem;
  align-items: center;
  border-radius: 10px;
  box-shadow: 3px 3px 10px;
}

.regist-btn:hover {
  transition-delay: 0.3s;
  transition: 0.3s;
  background-color: white;
  color: black;
}

.btn-box {
  display: flex;
  justify-content: center;
}

.table {
  margin-bottom: 20px;
  width: 100%;
}

.table-container {
  min-height: 300px;
  overflow-y: auto;
}

.text-right {
  text-align: right;
}

table {
  width: 90%;
  border-collapse: collapse;
  text-align: center;
}

table,
th,
td {
  border: 1px solid #ddd;
}

th,
td {
  padding: 8px;
}

th {
  background-color: #f4f4f4;
}

.fixed-width {
  width: 150px;
}

.text-highlight {
  font-size: larger;
  font-weight: 1000;
  color: #202020;
}

.pagination {
  list-style-type: none;
  padding: 0;
  display: flex;
  justify-content: center;
}

.page-item {
  margin: 0 5px;
}

.page-link {
  display: block;
  padding: 0.5rem 0.75rem;
  background-color: #fff;
  border: 1px solid #ddd;
  color: black;
  text-decoration: none;
}

.page-link.disabled {
  pointer-events: none;
  color: #6c757d;
}

.page-item.active .page-link {
  background-color: white;
  color: black;
  border-color: white;
}
</style>
