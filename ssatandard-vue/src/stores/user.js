import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

axios.defaults.withCredentials = true;

const REST_MAIN_API = `http://localhost:8080`
const REST_USER_API = 'http://localhost:8080/mypage'

export const useUserStore = defineStore('user', () => {

  // 로그인용 모달
  const isLoginModalVisible = ref(false);

  const showLoginModal = async() => {
    isLoginModalVisible.value = true;
  };

  const hideLoginModal = async() => {
    isLoginModalVisible.value = false;
  };

  // 회원가입 모달
  const isSignUpModalVisible = ref(false);

  const showSignUpModal = async() => {
    isSignUpModalVisible.value = true;
  };

  const hideSignUpModal = async() => {
    isSignUpModalVisible.value = false;
  };

  // 마이페이지에서 회원 정보수정 위한 모달
  const isEditModalVisible = ref(false);

  const showEditModal = async() => {
    isEditModalVisible.value = true;
  };

  const hideEditModal = async() => {
    isEditModalVisible.value = false;
  };

  // 마이페이지 회원 탈퇴 모달
  const isWithdrawModalVisible = ref(false);

  const showWithdrawModal = async() => {
    isWithdrawModalVisible.value = true;
  };

  const hideWithdrawModal = async() => {
    isWithdrawModalVisible.value = false;
  };


  const receipt = ref(true);


  // 로그인 method -> session에 저장해두는 방식
  const login = function (id, password) {
    axios.post(`${REST_MAIN_API}/login`, {
      userId: id,
      password: password
    })
      .then((response) => {
        sessionStorage.setItem('userId', response.data.userId)
        sessionStorage.setItem('isAdmin', response.data.isAdmin)

        // 여기서 관리자인지 그냥 유저인지 필터
        if(response.data.isAdmin == '1') {
          location.href="/admin"
        } else {
          location.href=""
        }
        // location.replace({name: 'main'})
        alert("성공적으로 로그인되었습니다.");
      })
      .catch((error) => {
        console.log(error)
        alert("아이디 또는 비밀번호가 일치하지 않습니다");
      });
  }
  
  // 로그아웃 method
  const logout = function () {
    axios.get(`${REST_MAIN_API}/logout`)
      .then((response) => {
        sessionStorage.removeItem('userId')
        sessionStorage.removeItem('isAdmin')

        location.href=""
        alert("로그아웃 되었습니다.")
      })
  }

  // 회원가입 메서드
  const signup = function (userInfo) {
    axios.post(`${REST_MAIN_API}/signup`, userInfo)
    .then((response) => {
      alert("SSATANDARD의 회원이 되신 것을 환영합니다.")
    })
  }

  // 주문내역 가져오기 구현
  const orderList = ref([]) // List로 받아와야 하니까

  const getOrderList = function () {
    axios.get(`${REST_USER_API}/orders`)
    .then((response) => {
      orderList.value = response.data
    })
  }

  // 로그인 후 회원정보를 수정할 때 기존의 데이터를 가져올 method
  const loginUser = ref({
    userId: '',
    userName: '',
    phoneNumber: '',
    address: '',
    image: '',
    regDate: ''
  })

  const getLoginUser = function() {
    axios.get(`${REST_USER_API}/edit`)
    .then((response) => {
      loginUser.value = response.data
    })
  }


  const getMyInfo = function() {
    axios.get(`${REST_USER_API}/myInfo`)
    .then((response) => {
      loginUser.value = response.data
    })
  }

  const update = function(userInfo) {
    console.log(userInfo)
    axios.put(`${REST_USER_API}/edit`, userInfo)
    .then((response) => {
      console.log(response.data)
      location.href="/mypage"
      alert("회원정보가 수정되었습니다.")
    })
  }

  // 회원 탈퇴용 method
  const withdraw = function(userId) {
    axios.post(`${REST_USER_API}/withdraw`, { userId: userId }, {
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then((response) => {
      sessionStorage.removeItem('userId')
      sessionStorage.removeItem('isAdmin')
      location.href=""
      alert("탈퇴가 성공적으로 완료되었습니다.")
    })
  }

  return {
    login,
    logout,
    signup,
    getOrderList,
    orderList,
    getLoginUser,
    loginUser,
    update,
    withdraw,
    getMyInfo,
    showLoginModal,
    showSignUpModal,
    showEditModal,
    showWithdrawModal,
    hideLoginModal,
    hideSignUpModal,
    hideEditModal,
    hideWithdrawModal,
    isLoginModalVisible,
    isSignUpModalVisible,
    isEditModalVisible,
    isWithdrawModalVisible,
    receipt,
  }
},
  {
    persist: true
  }
)