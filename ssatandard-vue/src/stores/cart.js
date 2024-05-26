import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

axios.defaults.withCredentials = true;

const REST_CART_API = `http://localhost:8080/cart`;
const REST_PRODUCT_API = `http://localhost:8080/product`;

export const useCartStore = defineStore('cart', () => {

    const product = ref('');
    const carts = ref([]);

    // 해당 userId가 갖는 cart 정보를 불러오기 위한 method, parameter값으로 userId 넘기기
    const fetchCartsByUserId = (userId) => {
        return axios.get(`${REST_CART_API}`, {
            params: {
                userId: userId
            }
        })
            .then((response) => {
                carts.value = response.data;
                return response.data;
            })
    };

    // cart내에서 개별 삭제를 위한 method, cartId를 param값으로 넘겨 해당하는 cart만 삭제
    const deleteCart = async (cartId) => {
        try {
            if(confirm('삭제하시겠습니까?')) {
                const response = await axios.delete(`${REST_CART_API}`, {
                    params: { cartId }
                });
                alert('삭제가 완료되었습니다.')
                return response.data;
            } else {
                alert('삭제가 취소되었습니다.')
            }
        } catch (error) {
            console.error('Error deleting cart:', error.response ? error.response.data : error.message);
            throw error;
        }
    };

    // 선택한 cartId와 일치하는 cart의 amount 정보를 변경하는 amount를 변경하여 적용하는 method
    const updateCartAmount = async (cartId, amount) => {
        try {
            await axios.put(`${REST_CART_API}/${cartId}`, null, {
                params: { amount }  // amount를 params로 전달
            });
        } catch (error) {
            console.error('Error updating cart amount:', error.response ? error.response.data : error.message);
            throw error;
        }
    };

    // 현재 접속되어 있는 userId를 불러와서 해당하는 아이디가 일치하는 모든 cart삭제
    const deleteCartsByUserId = async (userId) => {
        try {
            if (confirm('전체 삭제하시겠습니까?')) {
                await axios.delete(`${REST_CART_API}/all`, {
                    params: { userId }
                });
                alert('전체 삭제가 완료되었습니다.')
            } else {
                alert('전체 삭제가 취소되었습니다.');
            }
        } catch (error) {
            console.error('Error updating cart amount:', error.response ? error.response.data : error.message);
            throw error;
        }
    };

    // 결제하기 위한 method -> 구현 방식은 cart 테이블에서 order테이블로의 데이터 전송 
    const addToOrder = async (userId) => {
        try {
            const response = await axios.post(`${REST_CART_API}/order`, {
                userId: userId
            });

            alert('결제가 완료되었습니다!');
            location.reload();

        } catch (error) {
            alert("담을 수 있는 수량을 초과하였습니다.")
            console.error('Error updating cart amount:', error.response ? error.response.data : error.message);
            throw error;
        }
    }

    // productId로 해당 product 정보 조회하기
    const searchByProductId = async (productId) => {
        try {
            const response = await axios.get(`${REST_PRODUCT_API}/detail/${productId}/info`);
            product.value = response.data;
        } catch (error) {
            console.error('Error fetching product by ID:', error);
        }
    }

    // productId와 session에 있는 userId를 통해서 그에 맞는 cart 가져오기 -> 단일 카트를 조회
    const getProductById = async (productId) => {
        try {
            const response = await axios.get(`${REST_PRODUCT_API}/detail/${productId}`);
            product.value = response.data;
        } catch (error) {
            console.error('Error fetching product by ID:', error);
        }
    };

    return {
        carts,
        fetchCartsByUserId,
        deleteCart,
        updateCartAmount,
        deleteCartsByUserId,
        addToOrder,
        getProductById,
        product,
        searchByProductId,
    };

});