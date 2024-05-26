<template>
  <div class="mt-3">
    <div class="user-info d-flex justify-content-between align-items-center border p-3 mb-3 rounded shadow-sm" style="flex-direction:column">
      <div class="flow-container" style="width:100%">
        <div class="flow-text">
          <div class="flow-wrap text-middle">
            건강한 삶을 추구하는 모든 분들께 SSATANDARD는 언제나 열려 있습니다.
            저희와 함께 더욱 즐겁고 효율적인 운동 생활을 만들어 보세요!
          </div>
          <div class="flow-wrap">
            건강한 삶을 추구하는 모든 분들께 SSATANDARD는 언제나 열려 있습니다.
            저희와 함께 더욱 즐겁고 효율적인 운동 생활을 만들어 보세요!
          </div>
          <div class="flow-wrap">
            건강한 삶을 추구하는 모든 분들께 SSATANDARD는 언제나 열려 있습니다.
            저희와 함께 더욱 즐겁고 효율적인 운동 생활을 만들어 보세요!
          </div>
          <div class="flow-wrap">
            건강한 삶을 추구하는 모든 분들께 SSATANDARD는 언제나 열려 있습니다.
            저희와 함께 더욱 즐겁고 효율적인 운동 생활을 만들어 보세요!
          </div>
        </div>
      </div>
    </div>
    <hr />
    <div class="display-container p-5">
      <div class="mypage-display p-4">
        <div class="mypage-sidebar">
          <MyPageSideBar/>
        </div>
        <div class="content">
          <RouterView />
        </div>
      </div>
      <hr>
    </div>
  </div>
</template>

<script setup>
import MyPageSideBar from './components/MyPageSideBar.vue';

</script>

<style scoped>

.mypage-sidebar {
  width: 20%;
}

.content {
  width: 75%;
  padding: 1rem;
  flex-grow: 1;
}

.mypage-display {
  display: flex;
}

.font-container {
  width: 80%;
}

.flow-text {
  display: flex;
  flex: 0 0 auto;
  white-space: nowrap;
  overflow: hidden;
  transition: 1s;
  font-size: 1.5rem;
  font-weight: bold;
  color: #9d9993;
}
.flow-text:hover {
  color: #000;
}
.flow-text:hover .flow-wrap {
  animation-play-state: paused;
  cursor: pointer;
}
.flow-wrap {
  animation: textLoop 20s linear infinite;
  padding-right: 1.5vw;
}

@keyframes textLoop {
  0% {
    -webkit-transform: translate3d(0, 0, 0);
    transform: translate3d(0, 0, 0);
  }
  100% {
    -webkit-transform: translate3d(-100%, 0, 0);
    transform: translate3d(-100%, 0, 0);
  }
}

.user-info {
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
}

hr {
  border-top: 1px solid #dee2e6;
}

@media (max-width: 576px) {
  .container {
    padding: 0 15px;
  }
  .user-info {
    flex-direction: column;
    text-align: center;
  }
  .user-info h2 {
    margin-bottom: 15px;
  }
}

.my-button{
  align-content: center;
  background:#000000;
  color:#fff;
  border:none;
  position:relative;
  height:4vh;
  font-size:1.6em;
  padding:0 2em;
  cursor:pointer;
  transition:800ms ease all;
  outline:none;
}
.my-button:hover{
  background:#fff;
  color:#000000;
}
.my-button:before, .my-button:after{
  content:'';
  position:absolute;
  top:0;
  right:0;
  height:2px;
  width:0;
  background: #3a3a3a;
  transition:400ms ease all;
}

.my-button:after{
  right:inherit;
  top:inherit;
  left:0;
  bottom:0;
}

.my-button:hover:before, .my-button:hover:after{
  width:100%;
  transition:800ms ease all;
}

</style>
