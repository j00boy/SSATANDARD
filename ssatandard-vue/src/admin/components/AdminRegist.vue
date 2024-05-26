<template>
  <div class="modal" v-if="store.isRegistModalVisible">
    <div class="modal-content">
      <div class="product-registration">
        <h2>상품 등록</h2>
        <form @submit.prevent="submitForm">
          <div class="form-group">
            <div class="horizontal">
              <label for="productName">제품명:</label>
              <input type="text" id="productName" v-model="product.productName" required />
            </div>
            <div class="horizontal">
              <label for="category">카테고리:</label>
              <select id="category" v-model="product.categoryId" required>
                <option disabled value="">카테고리를 선택하세요</option>
                <option v-for="category in store.category" :key="category.id" :value="category.id">
                  {{ category.id }} - {{ category.name }}
                </option>
              </select>
            </div>
          </div>
          <div class="horizontal">
            <label for="price">가격:</label>
            <input type="number" id="price" v-model="product.productPrice" required />
          </div>
          <div class="horizontal">
            <label for="stock">재고:</label>
            <input type="number" id="stock" v-model="product.productStock" required />
          </div>
          <div class="form-group">
            <label for="description">제품 설명:</label>
          </div>
          <textarea id="description" v-model="product.productInfo" rows="5" cols="58" required></textarea>
          <div class="form-group">
            <label for="file">파일:</label>
            <input type="file" id="file" @change="handleFileUpload" />
          </div>
          <div class="btn-box">
            <button class="regist-btn" @click="store.hideRegistModal">닫기</button> 
            <button type="submit" class="regist-btn">상품 등록</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAdminStore } from '@/stores/admin';
import { ref } from 'vue';

const store = useAdminStore();

const product = ref({
  productName: '',
  categoryId: '',
  productPrice: 0,
  productStock: 0,
  image: 'https://fiverr-res.cloudinary.com/images/q_auto,f_auto/gigs/263015180/original/59b151ebc48c01bf14852a50bdb003b97a713f60/choose-words-no-one-is-going-to-forget.jpg',
  productInfo: ''
});

const file = ref(null);

const handleFileUpload = (event) => {
  file.value = event.target.files[0];
  product.value.image = file.value.name;
};

const submitForm = () => {
  store.submitForm(product.value);
  store.hideRegistModal();
};
</script>

<style scoped>

.modal {
  display: block;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
  background-color: #fefefe;
  padding: 20px;
  border: 1px solid #888;
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
  max-width: 600px;
}

.product-registration {
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid white;
  border-radius: 8px;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
}

.horizontal {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.horizontal label {
  flex: 0 0 100px;
  margin-right: 10px;
}

input[type="text"],
input[type="number"],
select,
textarea {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

textarea {
  resize: vertical;
}

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

</style>
