<!-- 사진 회전 목마 광고판 -->
<template>
  <div id="carouselExampleIndicators" class="carousel slide">
  <div class="carousel-indicators">
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active visually-hidden" aria-current="true" aria-label="Slide 1"></button>
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" class="visually-hidden" aria-label="Slide 2"></button>
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" class="visually-hidden" aria-label="Slide 3"></button>
  </div>
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img src="@\img\work.jpg" class="d-block w-100" alt="...">
    </div>
    <div class="carousel-item">
      <img src="@\img\work2.jpg" class="d-block w-100" alt="...">
    </div>
    <div class="carousel-item">
      <img src="@\img\work3.jpg" class="d-block w-100" alt="...">
    </div>
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
</div>

</template>

<script setup>
</script>

<style scoped>

.carousel-inner {
  margin-top: 1rem;
}

.carousel-image {
  width: 100%;
  height: auto;
  /* object-fit: cover; 또는 contain */
}

.w-100 {
    width: 996px !important;
}

.carousel {
    width: 996px;
}

.button {
  z-index: tra;
}

</style>