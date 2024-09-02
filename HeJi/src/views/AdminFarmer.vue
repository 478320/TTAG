<template>
  <div 
    class="container"
    v-infinite-scroll="load" 
    :infinite-scroll-distance="10" 
    :infinite-scroll-immediate="false"  
    v-if="adminStore.checkFarmers.length"
  >
    <el-space
      wrap
      :size="74"
    >
      <FarmerCard
        v-for="f in adminStore.checkFarmers"
        :key="f.id"
        :data="f"
        :load="load"
      />
    </el-space>
  </div>
  <el-empty v-if="!adminStore.checkFarmers.length" description="暂无申请" />
</template>

<script setup lang="ts">
  import FarmerCard from '@/components/FarmerCard.vue'
  import { GetRequest } from '@/api/admin'
  import { useAdminStore } from '@/stores/admin'
  import { onMounted, onUnmounted, ref } from 'vue'
  const adminStore = useAdminStore()
  const current = ref(1)
  onMounted(() => {
    GetRequest(current.value++)
    .then(
      data => {
        if (data.success) {
          adminStore.setCheckFarmers(data.data)
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
    GetRequest(current.value++)
    .then(
      data => {
        if (data.success) {
          if (data.total !== 0) {
            adminStore.pushCheckFarmers(data.data)
          } else {
            ElMessage({
            message: "数据到底辣！",
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
    adminStore.clearCheckFarmer()
  })
</script>

<style scoped lang="scss">
  .container {
    padding: 40px 100px;
  }
</style>