<template>
    <div class="product-registration container mt-5">
        <h2 class="text-center mb-4">상품 수정</h2>
        <form @submit.prevent="submitForm" class="needs-validation" novalidate>
            <div class="form-group row mb-3">
                <label for="productName" class="col-sm-3 col-form-label">제품명:</label>
                <div class="col-sm-9">
                    <input type="text" id="productName" v-model="editedProduct.productName" class="form-control" required />
                </div>
            </div>
            <div class="form-group row mb-3">
                <label for="category" class="col-sm-3 col-form-label">카테고리:</label>
                <div class="col-sm-9">
                    <select id="category" v-model="editedProduct.categoryId" class="form-select" required>
                        <option disabled value="">카테고리를 선택하세요</option>
                        <option v-for="category in store.category" :key="category.id" :value="category.id">
                            {{ category.id }} - {{ category.name }}
                        </option>
                    </select>
                </div>
            </div>
            <div class="form-group row mb-3">
                <label for="price" class="col-sm-3 col-form-label">가격:</label>
                <div class="col-sm-9">
                    <input type="number" id="price" v-model="editedProduct.productPrice" class="form-control" required />
                </div>
            </div>
            <div class="form-group row mb-3">
                <label for="stock" class="col-sm-3 col-form-label">재고:</label>
                <div class="col-sm-9">
                    <input type="number" id="stock" v-model="editedProduct.productStock" class="form-control" required />
                </div>
            </div>
            <div class="form-group row mb-3">
                <label for="description" class="col-sm-3 col-form-label">제품 설명:</label>
                <div class="col-sm-9">
                    <textarea id="description" v-model="editedProduct.productInfo" rows="5" class="form-control" required></textarea>
                </div>
            </div>
            <div class="form-group row mb-4">
                <label for="file" class="col-sm-3 col-form-label">파일:</label>
                <div class="col-sm-9">
                    <input type="file" id="file" @change="handleFileUpload" class="form-control" />
                </div>
            </div>
            <div class="btn-box">
                <button class="regist-btn" @click="store.hideEditModal">닫기</button> 
                <button type="submit" class="regist-btn">상품 수정</button>
            </div>
        </form>
    </div>
</template>

<script setup>
import { ref,  onMounted } from "vue";
import { useAdminStore } from "@/stores/admin";

const store = useAdminStore();

onMounted(async () => {
  const p = store.selectedProduct;
  console.log(store.selectedProduct)
  
  editedProduct.value = {
    productName: p.productName,
    productId: p.productId,
    categoryId: p.categoryId,
    productPrice: p.productPrice,
    productStock: p.productStock,
    productInfo: p.productInfo,
    image: p.image,
  };

});

const editedProduct = ref({
  productName: '',
  productId: '',
  categoryId: '',
  productPrice: '',
  productStock: '',
  productInfo: '',
  image: store.selectedProduct.image,
});

const handleFileUpload = (event) => {
    const file = event.target.files[0];
    editedProduct.value.image = file ? file.name : 'https://fiverr-res.cloudinary.com/images/q_auto,f_auto/gigs/263015180/original/59b151ebc48c01bf14852a50bdb003b97a713f60/choose-words-no-one-is-going-to-forget.jpg';
};

const submitForm = () => {
    console.log(editedProduct.value)
    store.updateProduct(editedProduct.value)
    alert('상품이 성공적으로 수정되었습니다.');
    store.hideEditModal();
};
</script>

<style scoped>

.regist-btn {
    text-decoration: none;
    width: 50%;
    margin: 1rem;
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
.product-registration {
    background: #f9f9f9;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(153, 153, 153, 0.1);
}

.needs-validation .form-control:invalid {
    border-color: #dc3545;
}

.needs-validation .form-control:valid {
    border-color: #989898;
}
</style>
