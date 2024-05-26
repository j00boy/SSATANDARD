import { createRouter, createWebHistory } from 'vue-router';
import AdminView from '@/admin/AdminView.vue';
import ProductList from '@/products/components/ProductList.vue';
import Carousel from '@/main/components/Carousel.vue';
import MainView from '@/main/MainView.vue';
import LoginModal from '@/login/components/LoginModal.vue';
import MyPageView from '@/mypage/MyPageView.vue';
import MyPageHome from '@/mypage/components/MyPageHome.vue';
import MyPageOrder from '@/mypage/components/MyPageOrder.vue'
import MyPageEditModal from '@/mypage/components/MyPageEditModal.vue';
import MyPageWithdrawModal from '@/mypage/components/MyPageWithdrawModal.vue';
import SignUpModal from '@/signup/components/SignUpModal.vue';
import CartView from '@/carts/CartView.vue';
import CartList from '@/carts/components/CartList.vue';
import MyPageInfo from '@/mypage/components/MyPageInfo.vue';
import AdminHome from '@/admin/components/AdminHome.vue';
import AdminOrder from '@/admin/components/AdminOrder.vue'
import AdminRegist from '@/admin/components/AdminRegist.vue';
import AdminUser from '@/admin/components/AdminUser.vue';
import AdminProductList from '@/admin/components/AdminProductList.vue';
import AdminProductEdit from '@/admin/components/AdminProductEditModal.vue'
import ProductsView from '@/products/ProductsView.vue';
import ProductDetail from '@/products/components/ProductDetail.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/product',
      name: 'category',
      component: ProductsView,
      props: true,
      children: [
        {
          path: '',
          name: 'allProducts',
          component: ProductList,
        },
        {
          path: ':id',
          name: 'productList',
          component: ProductList,
        },
        {
          path: 'detail/:id',
          name: 'productDetail',
          component: ProductDetail,
        }
      ]
    },
    {
      path: '/',
      name: 'main',
      component: MainView,
      children: [{
        path: '',
        name: 'carousel',
        component: Carousel,
      }]
    },
    {
      path: '/cart',
      name: 'cart',
      component: CartView,
      children: [{
        path: '',
        name: 'cartList',
        component: CartList,
      }]
    },
    {
      path: '/admin',
      name: 'admin',
      component: AdminView,
      children: [
        {
          path: '',
          name: 'adminHome',
          component: AdminHome,
        },
        {
          path: 'orders',
          name: 'adminOrder',
          component: AdminOrder,
        },
        {
          path: 'products',
          name: 'adminProduct',
          component: AdminProductList,
        },
        {
          path: 'users',
          name: 'adminUser',
          component: AdminUser,
        },
        {
          path: 'regist',
          name: 'adminRegist',
          component: AdminRegist,
        },
        {
          path: 'edit/:id',
          name: 'adminProductEdit',
          component: AdminProductEdit,
        },
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: LoginModal,
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignUpModal,
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MyPageView,
      children: [
        {
          path: '', // 'mypage' 경로에 바로 접속했을 때 MyPageHome이 로드되어야 함
          name: 'mypageHome',
          component: MyPageHome
        },
        {
          path: 'myinfo',
          name: 'mypageInfo',
          component: MyPageInfo
        },
        {
          path: 'order',
          name: 'mypageOrder',
          component: MyPageOrder,
        },
        {
          path: 'edit',
          name: 'mypageEdit',
          component: MyPageEditModal,
        },

        {
          path: 'withdraw',
          name: 'mypageWithdraw',
          component: MyPageWithdrawModal,
        },
      ]
    },

  ]
});

const isLoggedIn = sessionStorage.getItem('userId') !== null;  // 로그인 상태 체크
const isAdmin = sessionStorage.getItem('isAdmin') === '1';     // 관리자 체크 (문자열 '1'로 비교)

router.beforeEach((to, from, next) => {

  // 로그인 페이지 또는 회원가입 페이지 접근 시 로그인이 되어 있으면 메인 페이지로 리다이렉션
  if ((to.name === 'login' || to.name === 'signup') && isLoggedIn) {
    alert("이미 로그인이 되어있습니다.");
    next({ name: 'main' });
  } 
  // 인증이 필요한 페이지 접근 시
  else if (to.matched.some(record => record.path.includes('/mypage') || record.path.includes('/cart')) && !isLoggedIn) {
    alert("로그인이 필요한 서비스입니다.");
    next({ name: 'main' });
  } 
  // 관리자 권한이 필요한 페이지 접근 시
  else if (to.matched.some(record => record.path.includes('/admin')) && !isAdmin) {
    alert("접근 권한이 없습니다.");
    next({ name: 'main' });
  } 
  // 인증이나 권한이 모두 통과된 경우
  else {
    next();
  }
});


export default router;
