<template>
  <div class="modal">
    <div style="width: 60%; padding: 1rem;">
      <img style="width: 100%; margin-right: 5%;" v-if="product" :src="product.image" class="card-img-top" alt="...">
    </div>
    <div class="modalContent">
      <div class="close-btn">
        <button @click="closeModal" style="border: none;">X</button>
      </div>
      <div class="modalDetail">
        <hr>  
        <span v-if="product">제품명: {{ product.productName }}</span>
        <hr>
        <span v-if="product">제품설명: {{ product.productInfo }}</span>
        <hr>
        <span v-if="product">가격: {{ product.productPrice }}원</span>
        <hr>
      </div>
      <div class="quantityControl" v-if="product.productStock !== 0">
        <button @click="decreaseQuantity" style="border: none;">-</button>
        <span style="font-size: larger">{{ quantity }}</span>
        <button @click="increaseQuantity" style="border: none;">+</button>
        <button @click="toOrder()">즉시 구매</button>
        <button @click="addToCart()">장바구니에 담기</button>
      </div>
      <div class="quantityControl sold-out" v-if="product.productStock === 0">
        <h3>재고 없음</h3>
      </div>
    </div>

    <!-- 장바구니 확인 팝업 -->
    <div v-if="showPopup" class="popup">
      <div class="popupContent">
        <p>장바구니에 담겼습니다.</p>
        <button @click="closePopup">확인</button>
      </div>
    </div>

    <div v-if="showOrderPopup" class="popup">
      <div class="popupContent">
        <p>결제가 완료되었습니다.</p>
        <button @click="closeOrderPopup">확인</button>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, defineProps, watch, onMounted } from 'vue';
import { useProductStore } from '@/stores/product';

const props = defineProps(['product', 'modalVisible', 'closeModal']);

const quantity = ref(1);
const showPopup = ref(false);
const showOrderPopup = ref(false);

const store = useProductStore();

const toOrder = () => {
  if (sessionStorage.getItem('userId') == null) {
    alert('로그인이 필요한 서비스입니다.');
    props.closeModal();
    return;
  }

  if (quantity.value > props.product.productStock) {
    alert("담을 수 있는 수량을 초과하였습니다.");
  } else {
    if(confirm('바로 결제하시겠습니까?')) {
      store.directOrder(props.product.productId, quantity.value);
      showOrderPopup.value = true;
    } else {
      alert('취소되었습니다.')
      props.closeModal();
    }
  }
}

const addToCart = () => {
  if (sessionStorage.getItem('userId') == null) {
    alert('로그인이 필요한 서비스입니다.');
    props.closeModal();
    return;
  }

  const cart = store.productDetail;
  if (cart) {
    if (confirm("이미 장바구니에 있는 상품입니다. 수량을 수정하시겠습니까?")) {
      store.addToCart(props.product.productId, quantity.value);
      alert("수정사항이 반영되었습니다.");
      props.closeModal();
      return;
    } else {
      alert("취소되었습니다.");
      props.closeModal();
      return;
    }
  }
  if (quantity.value > props.product.productStock) {
    alert("담을 수 있는 수량을 초과하였습니다.");
  } else {
    if(confirm('장바구니에 담으시겠습니까?')) {
      store.addToCart(props.product.productId, quantity.value);
      showPopup.value = true;
      props.closeModal();
    } else {
      alert('취소되었습니다.');
      props.closeModal();
    }
  }
};

onMounted(() => {
  if (sessionStorage.getItem('userId') != null) {
    store.getCartInfo(props.product.productId);
  }
});

const closePopup = () => {
  showPopup.value = false;
  quantity.value = 1;
  props.closeModal(); // 모달을 닫음
};

const closeOrderPopup = () => {
  showOrderPopup.value = false;
  quantity.value = 1;
  props.closeModal(); // 모달을 닫음
};

const increaseQuantity = () => {
  if (quantity.value < props.product.productStock) {
    quantity.value++;
  }
};

const decreaseQuantity = () => {
  if (quantity.value > 1) {
    quantity.value--;
  }
};

const closeModal = (event) => {
  props.closeModal();
};

watch(() => props.product, () => {
  quantity.value = 1;
});

</script>

<style scoped>

.sold-out {
  color:red;
  font-weight: 1000;
}
.modal {
  position: fixed;
  top: 50%;
  left: 50%;
  width: 60rem;
  height: auto;
  background-color: rgb(255, 255, 255);
  display: flex;
  flex-direction: row;
  z-index: 1000;
  transform: translate(-50%, -50%);
  border-style: solid;
  border-width: 1px;
  padding: 15px;
}



.quantityControl {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10%;
}

.quantityControl button {
  margin: 0 5px;
}

.popup {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
}

.popupContent {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  text-align: center;
}

.popupContent button {
  margin-top: 10px;
}

.close-btn {
  height: 4.5%;
  display: flex;
  justify-content: flex-end;
}

img {
  width: 50%;
  margin-right: 1rem;
  border-style: solid;
  border-color: black;
  border-width: 1px;
}

h4 {
  width: fit-content;
}

button {
  border-style: solid;
  border-width: 0.5px;
  border-color: grey;
  background-color: white;
}

.modalContent {
  width: 40%;
  height: auto;
  background-color: rgb(255, 255, 255);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
</style>
