<template>
  <main class="main-content">
    <header>
      <h1>상품 관리</h1>
    </header>
    <div class="btn-box">
      <button @click="store.showRegistModal" class="regist-btn">상품 등록</button>
    </div>
    <div class="ali-center">
      <div class="w-90">
        <div class="mt-4 mb-4 d-flex justify-content-between align-items-center">
          <div class="fs-3">등록된 상품: {{ store.products.length.toLocaleString() }} 개</div>
          <select name="sort-btns" id="sort-btns" class="pl" @change="handleSortChange">
            <option value="createdAt_DESC">신상품순</option>
            <option value="createdAt_ASC">오래된 상품순</option>
            <option value="productPrice_DESC">높은 가격순</option>
            <option value="productPrice_ASC">낮은 가격순</option>
            <option value="productSold_DESC">판매량 많은 순</option>
            <option value="productSold_ASC">판매량 낮은 순</option>
            <option value="productStock_DESC">재고 많은 순</option>
            <option value="productStock_ASC">재고 적은 순</option>
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
                  <th class="table-text" width="15%">등록일</th>
                  <th class="table-text" width="7.5%">상품번호</th>
                  <th class="table-text" width="10%">이미지</th>
                  <th class="table-text" width="25%">상품명</th>
                  <th class="table-text" width="15%">가격</th>
                  <th class="table-text" width="7.5%">재고</th>
                  <th class="table-text" width="7.5%">판매량</th>
                  <th class="table-text" width="5%"></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="product in currentPageProducts" :key="product.id">
                  <td class="table-text">{{ product.regDate }}</td>
                  <td class="table-text">{{ product.productId }}</td>
                  <td class="table-img"><img :src="product.image" /></td>
                  <td class="table-text">{{ product.productName }}</td>
                  <td class="table-text">₩ {{ product.productPrice.toLocaleString() }}</td>
                  <td class="table-text">{{ product.productStock }}</td>
                  <td class="table-text">{{ product.productSold }}</td>
                  <td>
                    <button class="custom-btn btn-1" @click="store.showEditModal(product)">수정</button><br>
                    <button class="custom-btn btn-2" @click="deleteProduct(product.productId)">삭제</button>
                  </td>
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
  <AdminRegist v-if="store.isRegistModalVisible" />
  <AdminProductEditModal v-if="store.isEditModalVisible" />
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute } from 'vue-router';
import { useAdminStore } from '@/stores/admin';

import AdminRegist from '@/admin/components/AdminRegist.vue';
import AdminProductEditModal from '@/admin/components/AdminProductEditModal.vue';

const store = useAdminStore();
const route = useRoute();

onMounted(() => {
  loadProducts();
});

const perPage = 8;
const currentPage = ref(1);

const pageCount = computed(() => {
  return Math.ceil(store.products.length / perPage);
});

const changePage = (page) => {
  if (page >= 1 && page <= pageCount.value) {
    currentPage.value = page;
  }
};

const currentPageProducts = computed(() => {
  const start = (currentPage.value - 1) * perPage;
  const end = currentPage.value * perPage;
  return store.products.slice(start, end);
});

const searchInfo = ref({
  key: 'productName',
  word: route.query.word || '',
  orderBy: 'none',
  orderByDir: 'DESC', // 기본: 내림차순
});

// 정렬 변경 시 다시 load해오기 -> 페이지는 다시 1로
const setOrderBy = (orderBy, orderByDir) => {
  searchInfo.value.orderBy = orderBy;
  searchInfo.value.orderByDir = orderByDir;
  currentPage.value = 1; // 현재 페이지를 1로 설정
  loadProducts();
};

const handleSortChange = (event) => {
  const [orderBy, orderByDir] = event.target.value.split('_');
  setOrderBy(orderBy, orderByDir);
};

const loadProducts = async () => {
  const params = {
    categoryId: route.params.id || null,
    key: searchInfo.value.key,
    word: searchInfo.value.word,
    orderBy: searchInfo.value.orderBy,
    orderByDir: searchInfo.value.orderByDir,
  };
  await store.searchProductList(params);
};

// 모달 관련 변수
const categories = ref([]); // categories 초기화


onMounted(async () => {
  await loadProducts();
  categories.value = store.category; // categories 로드
});

const deleteProduct = (productId) => {
  console.log(productId)
  store.deleteProduct(productId);
  loadProducts();
}

</script>

<style scoped>

.custom-btn {
  width: 100%;
  height: 3rem;
  color: #fff;
  border-radius: 5px;
  font-weight: 500;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
   box-shadow:inset 2px 2px 2px 0px rgba(255,255,255,.5),
   7px 7px 20px 0px rgba(0,0,0,.1),
   4px 4px 5px 0px rgba(0,0,0,.1);
  outline: none;
}

.btn-1 {
  background: rgb(0, 5, 70);
  border: none;
}
.btn-1:hover {
   background: rgb(0,3,255);
}

.btn-2 {
  background: rgb(128, 0, 34);
  border: none;
}
.btn-2:hover {
   background: rgb(170, 1, 46);
}

.w-90 {
  width: 90%;
}
.ali-center {
  display: flex;
  justify-content: center;
}
.pl {
  width: 15%;
  height: 3rem;
  border: 1px solid #C4C4C4;
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
  width: 150px;
  height: 150px;
}

img {
  width: 150px;
  height: 150px;
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
  width: 90%;
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
  color: #000000;
  text-decoration: none;
}

.page-link.disabled {
  pointer-events: none;
  color: #6c757d;
}

.page-item.active .page-link {
  background-color: white;
  color: #000000;
  border-color: white;
}



</style>
