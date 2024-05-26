<template>
  <div class="admin-dashboard">
    <AdminSidebar />
    <div class="content">
      <RouterView />
    </div>
  </div>
</template>

<script setup>
import AdminSidebar from '@/admin/components/AdminSidebar.vue';
</script>

<style scoped>
.admin-dashboard {
  display: flex;
}

.content {
  margin-left: 18%;
  padding: 1rem;
  flex-grow: 1;
}
</style>
