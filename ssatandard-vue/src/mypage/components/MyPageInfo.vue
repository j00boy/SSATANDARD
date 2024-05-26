<template>

  <div id="invoice-POS">
    <center id="top">
      <div class="teko">SSATANDARD</div>
      <div class="info">
        <h1 class="teko">Total Pay</h1>
      </div>
    </center>

    <div id="mid">
      <div class="info">
        <h2>Contact Info</h2>
        <p>
        <div>Address : {{ store.loginUser.address }}</div>
        <div>Phone : {{ store.loginUser.phoneNumber }}</div>
        <div>regDate : {{ store.loginUser.regDate }}</div>
        </p>
      </div>
    </div>
    <div id="bot">
      <div id="table">
        <table>
          <tr class="tabletitle">
            <td class="item">
              <h2>Item</h2>
            </td>
            <td class="Hours">
              <h2>Qty</h2>
            </td>
            <td class="Rate">
              <h2>Sub Total</h2>
            </td>
            <td class="Rate">
              <h2>Date</h2>
            </td>
          </tr>

          <tr class="service" v-for="item in store.orderList" :key="item.id">
            <td class="tableitem">
              <p class="itemtext">{{ item.productName }}</p>
            </td>
            <td class="tableitem">
              <p class="itemtext">{{ item.amount }}</p>
            </td>
            <td class="tableitem">
              <p class="itemtext">₩ {{ item.totalPrice.toLocaleString() }}</p>
            </td>
            <td class="tableitem">
              <p class="itemtext">₩ {{ item.regDate }}</p>
            </td>
          </tr>

          <tr class="tabletitle">
            <td></td>
            <td class="Rate">
              <h2>tax</h2>
            </td>
            <td class="payment">
              <h2>-</h2>
            </td>
            <td></td>
          </tr>

          <tr class="tabletitle">
            <td></td>
            <td class="Rate">
              <h2>Total</h2>
            </td>
            <td class="payment">
              <h1>{{ totalSales }}</h1>
            </td>
            <td></td>
          </tr>

        </table>
      </div>
      <div id="legalcopy">
        <p class="legal"><strong>Thank you for your purchase!</strong>  SSATANDARD is always striving to support your
          fitness and health.</p>
      </div>

    </div>
  </div>

</template>

<script setup>

import { useUserStore } from '@/stores/user';
import { computed, onMounted } from 'vue';

onMounted(async () => {
  await store.getOrderList();
})

const store = useUserStore()

const totalSales = computed(() => {
  return store.orderList.reduce((sum, order) => sum + order.totalPrice, 0).toLocaleString();
});

const receipt = document.querySelector('.receipt')
const button = document.querySelector('button')

const toggleReceipt = () => {
  receipt.classList.toggle('active')
  if (receipt.classList.contains('active')) {
    button.innerHTML = 'Less info'
  } else {
    button.innerHTML = 'More info'
  }
}

button.addEventListener('click', toggleReceipt)

</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Teko:wght@300..700&display=swap');

.teko {
  font-family: "Teko";
  font-size: xx-large;
  font-optical-sizing: auto;
  font-weight: 300;
  font-style: normal;
}

#invoice-POS {
  box-shadow: 0 0 1in -0.25in rgba(0, 0, 0, 0.5);
  padding: 2mm;
  margin: 0 auto;
  width: 100%;
  background: #FFF;
}

#invoice-POS::selection {
  background: #f31544;
  color: #FFF;
}

#invoice-POS::-moz-selection {
  background: #f31544;
  color: #FFF;
}

#invoice-POS h1 {
  font-size: 1.5em;
  color: #222;
}

#invoice-POS h2 {
  font-size: .9em;
}

#invoice-POS h3 {
  font-size: 1.2em;
  font-weight: 300;
  line-height: 2em;
}

#invoice-POS p {
  font-size: .7em;
  color: #666;
  line-height: 1.2em;
}

#top,
#mid,
#bot {
  border-bottom: 1px solid #EEE;
}

#top {
  min-height: 100px;
}

#mid {
  min-height: 80px;
}

#bot {
  min-height: 50px;
}

#top .logo {
  height: 60px;
  width: 60px;
  opacity: 50%;
  background: url(https://i.imgur.com/CkmfovE.jpg) no-repeat;
  background-size: 60px 60px;
}

.clientlogo {
  float: left;
  height: 60px;
  width: 60px;
  background: url(http://michaeltruong.ca/images/client.jpg) no-repeat;
  background-size: 60px 60px;
  border-radius: 50px;
}

.info {
  display: block;
  margin-left: 0;
}

.title {
  float: right;
}

.title p {
  text-align: right;
}

table {
  width: 100%;
  border-collapse: collapse;
}

td {
  padding: 0.6em;
}

.tabletitle {
  font-size: .7em;
  background: #EEE;
}

.service {
  border-bottom: 2px solid #EEE;
}

.item {
  width: 30mm;
}

.itemtext {
  font-size: 0.7em;
}

#legalcopy {
  margin-top: 5mm;
}
</style>
