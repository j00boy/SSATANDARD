import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import router from '@/router';

export const useAdminStore = defineStore('admin', () => {

  const REST_USER_API = `http://localhost:8080/mypage`
  const REST_ADMIN_API = `http://localhost:8080/admin`;

  const orders = ref([]);
  const products = ref([]);
  const users = ref([]);
  const selectedProduct = ref('')

  const loginUser = ref({
    userId: '',
    userName: '',
    phoneNumber: '',
    address: '',
    image: '',
    regDate: ''
  })

  // 상품 등록 모달 method
  const isRegistModalVisible = ref(false)

  const showRegistModal = async() => {
    isRegistModalVisible.value = true;
  }

  const hideRegistModal = async() => {
    await searchProductList();
    await getAllProducts();
    isRegistModalVisible.value = false;
  }

  // 상품 수정 method -> 재고 관리나 제품 설명 수정할 때 사용하기 위해
  const isEditModalVisible = ref(false)

  const showEditModal = async(product) => {
    isEditModalVisible.value = true;
    selectedProduct.value = product;
  }

  const hideEditModal = async() => {
    isEditModalVisible.value = false;
    searchProductList();
  }

  //
  const getLoginUser = function() {
    axios.get(`${REST_USER_API}/edit`)
    .then((response) => {
      loginUser.value = response.data
    })
  }

  // 제품 관리 페이지에서 활용하기 위한 method 
  const getAllProducts = function () {
    axios.get(`${REST_ADMIN_API}/products`)
      .then((response) => {
        products.value = response.data;
      })
  };

  // 주문 관리를 위한 method
  const getAllOrders = function () {
      axios.get(`${REST_ADMIN_API}/orders`)
      .then((response) => {
        orders.value = response.data;  
      })
  };

  // 제품 정렬을 위한 method -> 재고가 적은 순도 가능
  const searchProductList = (searchCondition) => {
      axios.get(`${REST_ADMIN_API}/products`, {
        params: searchCondition,
      })
      .then((response) => {
        products.value = response.data;  
      })
      .catch((error) => {
        console.error("상품 목록 조회 실패", error);  
      })
  };

  // 주문 정렬을 위한 method
  const searchOrderList = (searchCondition) => {
    axios.get(`${REST_ADMIN_API}/orders`, {
      params: searchCondition,
    })
    .then((response) => {
      orders.value = response.data;
    })
    .catch((error) => {
      console.error("주문 목록 조회 실패", error);
    })
  };

  // 유저 검색을 위한 method
  const searchUser = (searchCondition) => {
    axios.get(`${REST_ADMIN_API}/users`, {
      params: searchCondition,
    })
    .then((response) => {
      users.value = response.data;
    })
    .catch((error) => {
      console.error("회원 목록 조회 실패", error);
    })
  };

  // 제품 등록을 위한 method
  const submitForm = function (product) {
    axios.post(`${REST_ADMIN_API}/regist`, product)
      .then((response) => {
        console.log('상품 등록 성공:', response.data);
        alert("상품이 성공적으로 등록되었습니다.")
      })
  };

  // 제품 수정을 위한 method
  const updateProduct = function (editedProduct) {
    axios.put(`${REST_ADMIN_API}/products/edit`, editedProduct)
      .then((response) => {
        console.log(response.data)
      })
  }

  const category = ref([
    { id: 1, name: 'LifeStyle' },
    { id: 2, name: 'Training & Gym' },
    { id: 3, name: 'Contrology' },
    { id: 4, name: 'Pilates' },
    { id: 5, name: 'Climbing' },
    { id: 6, name: 'Running' },
    { id: 7, name: 'Swimming' },
  ])

  // 제품 삭제를 위한 method
  const deleteProduct = function (productId) {
    if (confirm('삭제하시겠습니까?')) {
      axios.delete(`${REST_ADMIN_API}/products`, {
        params: { productId: productId }
      })
        .then((response) => {
          console.log(response.data);
          alert('상품이 성공적으로 삭제되었습니다.');
          searchProductList();
        })
    } else {
      alert('삭제가 취소되었습니다.');
    }
  };

  return {
    products,
    getAllProducts,
    orders,
    getAllOrders,
    submitForm,
    users,
    searchProductList,
    searchOrderList,
    searchUser,
    updateProduct,
    category,
    deleteProduct,
    loginUser,
    getLoginUser,
    isRegistModalVisible,
    showRegistModal,
    hideRegistModal,
    isEditModalVisible,
    showEditModal,
    hideEditModal,
    selectedProduct,

  };
});
