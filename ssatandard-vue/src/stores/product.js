import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

axios.defaults.withCredentials = true;

const REST_PRODUCT_API = `http://localhost:8080/product`;

export const useProductStore = defineStore('product', () => {

    const category = ref([
        { id: 1, name: 'LifeStyle' },
        { id: 2, name: 'Training & Gym'},
        { id: 3, name: 'Contrology' },
        { id: 4, name: 'Pilates' },
        { id: 5, name: 'Climbing' },
        { id: 6, name: 'Running' },
        { id: 7, name: 'Swimming'},
    ])

    const latestProducts = ref([])
    const topRatedProducts = ref([])
    
    const product = ref([]);
    const productDetail = ref('');

    // 메인 화면에서 카테고리를 선택했을 때 카테고라 id에 맞는 product들을 가져오기 위한 method, 가장 main 화면
    const getProductsByCategoryId = (categoryId) => {
        axios.get(`${REST_PRODUCT_API}/${categoryId}`)
            .then((response) => {
                product.value = response.data;
            })
    }


    const getCartInfo = function (productId) {
        axios.get(`${REST_PRODUCT_API}/detail/${productId}`)
            .then((response) => {
                productDetail.value = response.data
            })
    }

    // 즉시 구매하기 위한 method
    const directOrder = (productId, quantity) => {
        axios.post(`${REST_PRODUCT_API}/detail/${productId}/direct`, {
            amount: quantity,
        }, {
            headers: {
                // 여기서 중요한 점은 데이터를 넘길 때 json 이라 변환 필요
                'Content-Type': 'application/json',
            },
        })
        .then((response) => {
            console.log(response)
        })
        .catch((error) => {
            console.error('There was an error adding the product to the order:', error.response ? error.response.data : error.message);
        })
    }

    // productDetail 모달에서 장바구니에 담기 위한 method
    const addToCart = (productId, quantity) => {
        axios.post(`${REST_PRODUCT_API}/detail/${productId}`, {
            amount: quantity,
        }, {
            headers: {
                'Content-Type': 'application/json',
            },
        })
        .then((response) => {
            console.log(response)
        })
        .catch((error) => {
            console.error('There was an error adding the product to the cart:', error.response ? error.response.data : error.message);
        })
    };

    // 검색 method, 정렬 method 구현
    const searchProductList = (searchCondition) => {
        axios.get(REST_PRODUCT_API, {
            params: searchCondition,
        })
        .then((response) => {
            product.value = response.data;    
            calculateLatestProducts();
            calculateTopProductsBySales();
        })
    };

    const calculateTopProductsBySales = () => {
        topRatedProducts.value = [...product.value]
            .sort((a, b) => b.productSold - a.productSold)
            .slice(0, 4);
      };
      
      const calculateLatestProducts = () => {
        latestProducts.value = [...product.value]
            .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
            .slice(0, 4);
      };

    return {
        category,
        product,
        getProductsByCategoryId,
        addToCart,
        searchProductList,
        getCartInfo,
        productDetail,
        calculateLatestProducts,
        calculateTopProductsBySales,
        topRatedProducts,
        latestProducts,
        directOrder,
    };
});
