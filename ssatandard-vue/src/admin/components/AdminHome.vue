<template>
  <main class="main-content">
    <header>
      <h1>관리자 페이지</h1>
    </header>
    <section class="stats">
      <div class="stats-border">
        <div class="stat-box">
          <div class="text-highlight">{{ products.length.toLocaleString() }} 건</div>
          <div>총 상품 수</div>
        </div>
        <div class="stat-box">
          <div class="text-highlight">{{ users.length }} 건</div>
          <div>총 고객 수</div>
        </div>
        <div class="stat-box">
          <div class="text-highlight">{{ orders.length.toLocaleString() }} 건</div>
          <div>총 판매 수</div>
        </div>
        <div class="stat-box">
          <div class="text-highlight">₩ {{ totalSales }}</div>
          <div>총 매출</div>
        </div>
      </div>
    </section>
    <div class="btn-box">
      <button @click="store.showRegistModal" class="regist-btn">상품 등록</button>
    </div>
    <section class="tables">
      <div class="ali-center">
        <div class="table mt-3">
          <h2 class="mt-5 pb-2 table-text">상품 목록</h2>
          <div class="table-container ali-center">
            <table>
              <thead>
                <tr>
                  <th class="table-text" width="17.5%">등록일</th>
                  <th class="table-text" width="7.5%">상품번호</th>
                  <th class="table-text" width="10%">이미지</th>
                  <th class="table-text" width="30%">상품명</th>
                  <th class="table-text" width="20%">가격</th>
                  <th class="table-text" width="7.5%">재고</th>
                  <th class="table-text" width="7.5%">판매량</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="product in currentPageProducts" :key="product.id">
                  <td class="table-text">{{ product.regDate }}</td>
                  <td class="table-text">{{ product.productId }}</td>
                  <td class="table-text"><img :src="product.image"></td>
                  <td class="table-text">{{ product.productName }}</td>
                  <td class="table-text">₩ {{ product.productPrice.toLocaleString() }}</td>
                  <td class="table-text">{{ product.productStock }}</td>
                  <td class="table-text">{{ product.productSold }}</td>
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
    <section class="tables">
      <div class="ali-center">
        <div class="table mt-3">
          <h2 class="mt-5 pb-2 table-text">주문 목록</h2>
          <div class="table-container ali-center">
            <table>
              <thead>
                <tr>
                  <th class="table-text" width="7.5%">주문번호</th>
                  <th class="table-text" width="17.5%">주문일자</th>
                  <th class="table-text" width="15%">주문자</th>
                  <th class="table-text" width="30%">상품명</th>
                  <th class="table-text" width="10%">가격</th>
                  <th class="table-text" width="7.5%">수량</th>
                  <th class="table-text" width="12.5%">총 가격</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="order in currentPageOrders" :key="order.id">
                  <td class="table-text">{{ order.orderId }}</td>
                  <td class="table-text">{{ order.regDate }}</td>
                  <td class="table-text">{{ order.userId }}</td>
                  <td class="table-text">{{ order.productName }}</td>
                  <td class="table-text">₩ {{ order.productPrice.toLocaleString() }}</td>
                  <td class="table-text">{{ order.amount }}</td>
                  <td class="table-text">₩ {{ order.totalPrice.toLocaleString() }}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <nav aria-label="Page navigation">
            <ul class="pagination d-flex justify-content-center">
              <li class="page-item" :class="{ disabled: currentOrderPage <= 1 }">
                <a class="page-link" @click.prevent="changeOrderPage(currentOrderPage - 1)" href="#">&lt;</a>
              </li>
              <li class="page-item" :class="{ disabled: currentOrderPage >= orderPageCount }">
                <a class="page-link" @click.prevent="changeOrderPage(currentOrderPage + 1)" href="#">&gt;</a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </section>
  </main>

  <AdminRegist v-if="store.isRegistModalVisible" />
  
</template>

<script setup>
import AdminRegist from '@/admin/components/AdminRegist.vue'
import { onMounted, computed, ref } from 'vue';
import { useAdminStore } from '@/stores/admin';

const store = useAdminStore();

onMounted(() => {
  store.getAllProducts();
  store.getAllOrders();
  store.searchUser();
});

const products = computed(() => store.products);
const orders = computed(() => store.orders);
const users = computed(() => store.users.filter(user => user.isAdmin == '0'));  // 전체 유저에서 관리자 아이디를 제외한 고객 수

const perPage = 5;
const currentPage = ref(1);
const currentOrderPage = ref(1);

const totalSales = computed(() => {
  return store.orders.reduce((sum, order) => sum + order.totalPrice, 0).toLocaleString();
});

const pageCount = computed(() => {
  return Math.ceil(store.products.length / perPage);
});

const orderPageCount = computed(() => {
  return Math.ceil(store.orders.length / perPage);
});

const changePage = (page) => {
  if (page >= 1 && page <= pageCount.value) {
    currentPage.value = page;
    store.getAllProducts();
  }
};

const changeOrderPage = (page) => {
  if (page >= 1 && page <= orderPageCount.value) {
    currentOrderPage.value = page;
    store.getAllOrders();
  }
};

const currentPageProducts = computed(() => {
  const start = (currentPage.value - 1) * perPage;
  const end = currentPage.value * perPage;
  return store.products.slice(start, end);
});

const currentPageOrders = computed(() => {
  const start = (currentOrderPage.value - 1) * perPage;
  const end = currentOrderPage.value * perPage;
  return store.orders.slice(start, end);
});
</script>

<style scoped>
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
  width: 120px;
  height: 120px;
}

img {
  width: 100px;
  height: 100px;
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

.stats-border {
  display: flex;
  justify-content: center;
  width: 80%;
}

.stats {
  display: flex;
  justify-content: space-around;
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
