<template>
  <div>
    <h1 class="mb-4" style="text-align: center">내 주문내역</h1>
    <section class="tables">
      <div class="ali-center">
        <div class="table">
          <div class="table-container ali-center">
            <table class="table table-hover text-center">
              <thead>
                <tr>
                  <th class="fs-5" width="20%">주문일자</th>
                  <th class="fs-5" width="7.5%">주문번호</th>
                  <th class="fs-5">이미지</th>
                  <th class="fs-5" width="20%">상품명</th>
                  <th class="fs-5" width="15%">가격</th>
                  <th class="fs-5" width="7.5%">수량</th>
                  <th class="fs-5">총 결제금액</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="order in currentPageOrderList" :key="order.orderId">
                  <td class="text-center">{{ order.regDate }}</td>
                  <td>{{ order.orderId }}</td>
                  <td class="table-image"><img :src="order.image" /></td>
                  <td>{{ order.productName }}</td>
                  <td>₩ {{ order.productPrice.toLocaleString() }}</td>
                  <td>{{ order.amount }}</td>
                  <td>₩ {{ order.totalPrice.toLocaleString() }}</td>
                </tr>
              </tbody>
            </table>
            <nav aria-label="Page navigation">
              <ul class="pagination d-flex justify-content-center">
                <li class="page-item">
                  <a class="page-link" @click.prevent="currentPage--" :class="{ disabled: currentPage <= 1 }"
                    href="#">&lt;</a>
                </li>
                <li class="page-item" :class="{ active: currentPage === page }" v-for="page in pageCount" :key="page">
                  <a class="page-link" href="#" @click.prevent="clickPage(page)">{{
                    page
                    }}</a>
                </li>
                <li class="page-item">
                  <a class="page-link" @click.prevent="currentPage++" :class="{ disabled: currentPage >= pageCount }"
                    href="#">&gt;</a>
                </li>
              </ul>
            </nav>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { useUserStore } from "@/stores/user";
import { ref, computed, onMounted } from "vue";

const store = useUserStore();

const orderList = ref(store.orderList);

onMounted(() => {
  orderList.value = store.getOrderList();
  console.log(orderList.value);
});

const perPage = 5;
const currentPage = ref(1);

const pageCount = computed(() => {
  return Math.ceil(store.orderList.length / perPage);
});

const clickPage = function (page) {
  currentPage.value = page;
};

const currentPageOrderList = computed(() => {
  return store.orderList.slice(
    (currentPage.value - 1) * perPage,
    currentPage.value * perPage
  );
});
</script>

<style scoped>

.text-center {
  line-height: 9;
}

.table-image {
  width: 130px;
  height: 130px;
}

body {
  background-color: #fff;
  color: #000;
}

h2 {
  color: #000;
}

.table {
  width: 100%;
  max-width: 100%;
  margin-bottom: 1rem;
  background-color: #fff;
}

.table th,
.table td {
  padding: 0.75rem;
  vertical-align: top;
  border-top: 1px solid #dee2e6;
}

.table thead th {
  vertical-align: bottom;
  border-bottom: 2px solid #dee2e6;
}

.table-hover tbody tr:hover {
  background-color: rgba(0, 0, 0, 0.075);
}

.table-image img {
  width: 150px;
  height: 150px;
  object-fit: cover;
  border: 1px solid #dee2e6;
  padding: 5px;
}

.pagination {
  display: flex;
  padding-left: 0;
  list-style: none;
  border-radius: 0.25rem;
}

.page-item {
  margin: 0 0.25rem;
}

.page-link {
  position: relative;
  display: block;
  padding: 0.5rem 0.75rem;
  margin-left: -1px;
  line-height: 1.25;
  color: #000;
  background-color: #fff;
  border: 1px solid #dee2e6;
}

.page-link:hover {
  color: #fff;
  background-color: #000;
  border-color: #000;
}

.page-item.disabled .page-link {
  color: #6c757d;
  pointer-events: none;
  background-color: #fff;
  border-color: #dee2e6;
}

.page-item.active .page-link {
  z-index: 1;
  color: #fff;
  background-color: #000;
  border-color: #000;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .table thead {
    display: none;
  }

  .table,
  .table tbody,
  .table tr,
  .table td {
    display: block;
    width: 100%;
  }

  .table tr {
    margin-bottom: 1rem;
  }

  .table td {
    text-align: right;
    padding-left: 50%;
    position: relative;
  }

  .table td::before {
    content: attr(data-label);
    position: absolute;
    left: 0;
    width: 50%;
    padding-left: 0.75rem;
    font-weight: bold;
    text-align: left;
  }
}

</style>
