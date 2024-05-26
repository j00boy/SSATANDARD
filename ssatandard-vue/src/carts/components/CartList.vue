<template>
  <div class="cart">

    <div class="cart-left">
      <div class="cart-table">
        <button @click="deleteAll" class="delete-all-btn" v-if="carts.length > 0">전체삭제</button>

        <table class="table" v-if="carts.length > 0">
          <tr>
            <th>물건 담은 시간</th>
            <th>물건 이미지</th>
            <th>물건 이름</th>
            <th>물건 가격</th>
            <th>물건 갯수</th>
            <th>총 가격</th>
          </tr>
          <tr v-for="cart in carts" :key="cart.cartId" class="cartContent">
            <td>{{ cart.regDate }}</td>
            <td><img :src="cart.image"></td>
            <td>{{ cart.productName }}</td>
            <td>₩ {{ cart.productPrice.toLocaleString() }}</td>
            <td>
              <button class="quantity-btn" @click="updateCartAmount(cart.cartId, cart.amount - 1)"
                :disabled="cart.amount <= 1">-</button>
              {{ cart.amount }}
              <button class="quantity-btn" @click="updateCartAmount(cart.cartId, cart.amount + 1)">+</button>
            </td>
            <td>₩ {{ getTotalPrice(cart).toLocaleString() }}</td>
            <div class="delete-one-btn-box">
              <button class="delete-one-btn" @click="deleteCart(cart.cartId)">x 삭제</button>
            </div>
          </tr>
        </table>
        <!-- 사진 넣을거면 여기 -->
        <div v-else style="object-fit: fill;">
          <img style="width: 100%; margin-left: 5%" src="@\img\pic.png" />
        </div>

        <div v-if="carts.length > 0" style="height: 20rem; color: white">
          nothing
        </div>
      </div>
    </div>
    <div class="cart-right">
      <div class="check-box" v-if="carts.length > 0">
        <div class="title">
          <div style="font-size: 160%; font-weight: bolder;">
            결제상세
          </div>
          <div style="color: darkblue; padding-top: 2%; font-weight: bolder;">
            {{ (calculateTotalPrice() + calculateShippingFee()).toLocaleString() }}원
          </div>
        </div>
        <div class="checkout-text">
          <div class="checkout-text-detail">
            <div style="font-size: larger; font-weight: bolder;">
              전체 상품 가격
            </div>
            <div>
              {{ calculateTotalPrice().toLocaleString() }}원
            </div>
          </div>
          <div class="checkout-text-detail">
            <div style="font-size: larger; font-weight: bolder;">
              배송료
            </div>
            <div>
              {{ calculateShippingFee().toLocaleString() }}원
            </div>
          </div>
          <div style="font-size: 13px;">
            10만원 미만 구매 시 배송료 5,000원이 부과됩니다.
          </div>
        </div>
      </div>
      <div v-else class="empty-box">
        장바구니가 비어있습니다.
      </div>

      <div class="point-box" v-if="carts.length > 0">
        <div class="title">
          <div style="font-size: 160%; font-weight: bolder;">포인트 혜택</div>
          <div style="color: darkblue; padding-top: 2%; font-weight: bolder;">최대 : {{ ((calculateTotalPrice() +
            calculateShippingFee()) / 25).toLocaleString() }}원</div>
        </div>
        <div>
          <div class="point-detail">


            <div class="checkout-text-detail">
              <div style="font-size: large; font-weight: bold;">
                구매적립
              </div>
              <div>
                총 {{ (((calculateTotalPrice() + calculateShippingFee()) / 25) / 10 * 9).toLocaleString() }}원
              </div>
            </div>
            <div>
            </div>
            <div class="checkout-text-detail">
              <div style="font-size: large; font-weight: bold;">
                리뷰적립
              </div>
              <div>
                최대 {{ (((calculateTotalPrice() + calculateShippingFee()) / 25) - (((calculateTotalPrice() +
                  calculateShippingFee()) / 25) / 10 * 9)).toLocaleString() }}원
              </div>
            </div>
            <div style="font-size: smaller; text-align: center;">
              동일상품의 상품/한달리뷰 적립은 각 1회로 제한
            </div>
          </div>
        </div>

      </div>

      <div style="font-size: small; text-align: center; margin: 1rem 0;" v-if="carts.length > 0">
        주문 내용을 확인하였으며, 정보 제공 등에 동의합니다.
      </div>

      <div class="checkout-btn" v-if="carts.length > 0">
        <button @click="checkout()" class="my-button">결제하기</button>
      </div>
      <div class="adv">
        <a href="https://new-m.pay.naver.com/paymoney/bankbook/pc?from=hf0001" style="text-align: center;"><img
            src="@\img\card.png" /></a>
      </div>

    </div>


  </div>
</template>

<script setup>
import { useCartStore } from '@/stores/cart';
import { ref, onMounted } from 'vue';

const store = useCartStore();
const carts = ref([]);

onMounted(async () => {
  await fetchCartsByUserId(sessionStorage.getItem('userId'));
})

const fetchCartsByUserId = async (userId) => {
  await store.fetchCartsByUserId(userId);
  carts.value = store.carts;
  console.log(carts.value)
}

const deleteCart = async (cartId) => {
  await store.deleteCart(cartId);
  await fetchCartsByUserId(sessionStorage.getItem('userId'));  // 삭제 후 장바구니 목록 갱신
}

const updateCartAmount = async (cartId, amount) => {
  if (amount < 1) return;

  const cart = carts.value.find(c => c.cartId === cartId);
  await store.searchByProductId(cart.productId);

  if (amount > store.product.productStock) {
    alert(`<${store.product.productName}>의 재고가 부족합니다. 재고 수량:${store.product.productStock} 개`);
    return;
  }

  await store.updateCartAmount(cartId, amount);
  await fetchCartsByUserId(sessionStorage.getItem('userId'));
};

const deleteAll = async (userId) => {
  await store.deleteCartsByUserId(sessionStorage.getItem('userId'));
  await fetchCartsByUserId(sessionStorage.getItem('userId'));

}

const getTotalPrice = (cart) => {
  return cart.productPrice * cart.amount;
}

const calculateTotalPrice = () => {
  return carts.value.reduce((total, cart) => total + getTotalPrice(cart), 0);
}

const calculateShippingFee = () => {
  const totalPrice = calculateTotalPrice();

  if (totalPrice >= 100000) {
    return 0;
  } else {
    return 5000;
  }
}

// @@@@@@@ 결제하기
const checkout = async () => {
  for (let i = 0; i < store.carts.length; i++) {
    console.log("카트 조회 : ", store.carts[i]);
    await store.searchByProductId(carts.value[i].productId);
    console.log(store.product)
    if (store.carts[i].amount > store.product.productStock) {
      alert(`<${store.product.productName}>의 재고가 부족합니다. 재고 수량: ${store.product.productStock} 개`);
      return;
    }

  }
  if (confirm('결제하시겠습니까?')) {
    await store.addToOrder(sessionStorage.getItem('userId'));
    await store.fetchCartsByUserId(sessionStorage.getItem('userId'));
  }else{
    alert('결제가 취소되었습니다.');
    return;
  }

}

</script>

<style scoped>
.adv {
  margin-top: 5%;
  display: flex;
  justify-content: center;
}

.check-box {
  display: flex;
  flex-direction: column;
}

.point-box {
  display: flex;
  flex-direction: column;
}


.empty-box {
  text-align: center;
  align-content: center;
  font-size: larger;
  font-weight: bolder;
  height: 10rem;
}

.checkout-text-detail {
  display: flex;
  justify-content: space-between;
  margin-bottom: 3%;
}


.point-detail {
  border-color: white;
  background-color: rgb(243, 243, 243);
  padding: 5%;
  display: flex;
  flex-direction: column;
}

.title {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-bottom: 3%;

}

.checkout-text {
  border-style: solid;
  border-color: white;
  padding: 5%;
  width: 98%;
  text-align: center;
  margin-bottom: 2%;
  background-color: rgb(243, 243, 243);
  color: rgb(0, 0, 0);
}

.checkout-button {
  display: inline-block;
  margin-left: 10px;
  font-size: medium;
}

.checkout-btn {
  display: flex;
  justify-content: center;
  height: 5%;
}

.my-button {
  align-content: center;
  background: #000000;
  color: #fff;
  border: none;
  position: relative;
  width: 50%;
  height: 100%;
  font-size: 1em;
  cursor: pointer;
  transition: 800ms ease all;
  outline: none;
}

.my-button:hover {
  background: #fff;
  color: #000000;
}

.my-button:before,
.my-button:after {
  content: '';
  position: absolute;
  top: 0;
  right: 0;
  height: 2px;
  width: 0;
  background: #3a3a3a;
  transition: 400ms ease all;
}

.my-button:after {
  right: inherit;
  top: inherit;
  left: 0;
  bottom: 0;
}

.my-button:hover:before,
.my-button:hover:after {
  width: 100%;
  transition: 800ms ease all;
}


img {
  width: 80%;
}

.cart-table {
  width: 100%;
}

td,
th {
  border-width: 1px;
  text-align: center;
}

.quantity-btn {
  border: none;
}

.delete-one-btn-box {
  display: flex;
  justify-content: center;
  font-size: smaller;
}

.table {
  table-layout: fixed;
  width: 100%;
}

.cart {
  padding: 2rem 10%;
  display: flex;
  flex-direction: row;
}

.delete-one-btn {
  border-style: solid;
  border-color: #8f8f8f;
  border-width: 1px;
  background-color: #ffffff;
  color: rgb(0, 0, 0);
  margin-left: 10%;
  margin-top: 24%;
  margin-right: 40%;
}

.delete-all-btn {
  margin-bottom: 2%;
  background-color: white;
  border-width: 1px;
}

.cart-left {
  display: flex;
  flex-direction: row;
  width: 60%;
}

.cart-right {
  width: 20%;
  display: flex;
  flex-direction: column;
  position: fixed;
  padding: 2%;
  right: 20%;
}

h4 {
  margin-bottom: 0;
}

.cart-top {
  margin-bottom: 1%;
}
</style>