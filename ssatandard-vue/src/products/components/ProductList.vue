<template>
  <div class="mt-4">
    <div class="mb-4">
      <select name="sort-btns" id="sort-btns" @change="handleSortChange">
        <option value="createdAt_ASC">오래된 상품순</option>
        <option value="createdAt_DESC">신상품순</option>
        <option value="productPrice_DESC">높은 가격순</option>
        <option value="productPrice_ASC">낮은 가격순</option>
        <option value="productSold_DESC">판매량 많은 순</option>
        <option value="productSold_ASC">판매량 낮은 순</option>
      </select>
    </div>
    <div class="productList">
      <div class="row" v-for="(row, rowIndex) in paginatedProducts" :key="rowIndex">
        <div class="col-3 productItem" v-for="item in row" :key="item.id">
          <div class="card" style="width: 25rem;">
            <div class="image-container" @click="openModal(item)">
              <img :src="item.image" class="card-img-top" alt="...">
              <div class="overlay">
                <div class="text">상세보기</div>
              </div>
            </div>
            <ul class="list-group list-group-flush">
              <li class="list-group-item">{{ item.productName }}</li>
              <li v-if="item.productStock !== 0" class="list-group-item">₩ {{ item.productPrice.toLocaleString() }}</li>
              <li v-if="item.productStock === 0" class="list-group-item sold-out">Sold Out</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <nav aria-label="Page navigation">
      <ul class="pagination d-flex justify-content-center">
        <li class="page-item" :class="{ disabled: currentPage <= 1 }">
          <a class="page-link" @click.prevent="prevPage" href="#">&lt;</a>
        </li>
        <li class="page-item" :class="{ disabled: currentPage >= store.product.length / itemsPerPage }">
          <a class="page-link" @click.prevent="nextPage" href="#">&gt;</a>
        </li>
      </ul>
      
    </nav>
    <ProductDetail v-if="modalVisible" :product="selectedProduct" :modalVisible="modalVisible"
      :closeModal="closeModal" />
  </div>
</template>

<script setup>
import { ref, watch, onMounted, computed } from 'vue';
import { useProductStore } from '@/stores/product';
import { useRoute } from 'vue-router';
import ProductDetail from './ProductDetail.vue';

const store = useProductStore();
const route = useRoute();

const currentPage = ref(1);
const itemsPerPage = 12;

const searchInfo = ref({
  key: "productName",
  word: route.query.word || "",
  orderBy: "none",
  orderByDir: "DESC",  // 기본: 내림차순
});

// 정렬 변경 시 다시 load해오기 -> 페이지는 다시 1로
const setOrderBy = (orderBy, orderByDir) => {
  searchInfo.value.orderBy = orderBy;
  searchInfo.value.orderByDir = orderByDir;
  currentPage.value = 1; // 현재 페이지를 1로 설정
  loadProducts();
}

const handleSortChange = (event) => {
  const [orderBy, orderByDir] = event.target.value.split('_');
  setOrderBy(orderBy, orderByDir);
}

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

// 시작과 동시에 loadProducts 실행
onMounted(() => {
  loadProducts();
});

watch([() => route.query, () => route.params.id], () => {
  searchInfo.value.word = route.query.word || "";
  loadProducts();
});

const paginatedProducts = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  const slicedProducts = store.product.slice(start, end);
  const rows = [];
  for (let i = 0; i < slicedProducts.length; i += 4) {
    rows.push(slicedProducts.slice(i, i + 4));
  }
  return rows;
});

const totalPages = computed(() => {
  return Math.ceil(store.product.length / itemsPerPage);
});

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};

// 모달 관련 변수
const modalVisible = ref(false);
const selectedProduct = ref(null);

const openModal = (product) => {
  selectedProduct.value = product;
  modalVisible.value = true;
};

const closeModal = () => {
  modalVisible.value = false;
  loadProducts();
};

</script>

<style scoped>

.sold-out {
  color: red;
  font-weight: 1000;
}
.mb-4 {
  margin-left: 6%;
}


.productList .row {
  margin-left: 5%;
  margin-bottom: 3rem;
}

.pagination {
  margin-top: 1rem;
}

.image-container {
  position: relative;
  width: 100%;
  cursor: pointer;
}

.card-img-top {
  transition: opacity 0.5s ease;
}

.overlay {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100%;
  width: 100%;
  opacity: 0;
  transition: opacity 0.5s ease;
  background-color: rgba(133, 133, 133, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  color: rgb(255, 255, 255);
  font-size: 1.5rem;
}

.image-container:hover .card-img-top {
  opacity: 0.3;
}

.image-container:hover .overlay {
  opacity: 1;
}

.search-container {
  margin-top: 20px;
}

.form-control {
  width: 300px;
  /* 원하는 너비로 조정 */
}

.btn-outline-warning {
  margin-left: 10px;
}

.list-group {
  text-align: center;
}

.productItem {
  display: flex;
  justify-content: center;
  padding: 0;
  width: auto;
  margin: 0 1rem;
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
