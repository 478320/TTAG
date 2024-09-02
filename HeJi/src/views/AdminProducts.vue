<template>
  <div class="container"
    v-infinite-scroll="load" 
    :infinite-scroll-distance="10" 
    :infinite-scroll-immediate="false"  
    v-if="adminStore.checkProducts.length"
  >
    <el-space
      wrap
      :size="74"
    >
    <AdminProductCard
      v-for="p in adminStore.checkProducts"
      :key="p.products.id"
      :data="p.products"
      :farmer-data="{
        username: p.username,
        picture: p.picture,
        email: p.email
      }"
      :load="load"
    />
    </el-space>
  </div>
  <el-empty v-if="!adminStore.checkProducts.length" description="暂无申请" />
</template>

<script setup lang="ts">
  import AdminProductCard from '@/components/AdminProductCard.vue'
  import { useAdminStore } from "@/stores/admin"
  import { GetProductRequest } from '@/api/admin'
  import { onMounted, onUnmounted, ref } from 'vue';
  // pinia 
  const adminStore = useAdminStore()
  // 数据
  const current = ref(0)
  onMounted(() => {
    GetProductRequest(current.value++)
    .then(
      data => {
        if (data.success) {
          adminStore.setCheckProducts(data.data)
        } else {
          ElMessage({
            message: data.errorMsg,
            type: 'error',
            grouping: true
          })
        }
      },
      err => {
        throw err
      }
    )
  })
  function load() {
    GetProductRequest(current.value++)
    .then(
      data => {
        if (data.success) {
          if (data.total !== 0) {
            adminStore.pushCheckProducts(data.data)
          } else {
            ElMessage({
              message: '数据到底辣！',
              type: 'warning',
              grouping: true
            })
          }
        } else {
          ElMessage({
            message: data.errorMsg,
            type: 'error',
            grouping: true
          })
        }
      },
      err => {
        throw err
      }
    )
  }
  onUnmounted(() => {
    adminStore.clearCheckProduct()
  })
</script>

<style scoped lang="scss">
  .container {
    padding: 40px 100px;
  }
</style>