<template>
  <div>
    <el-container>
      <el-header>
        <NavBar />
      </el-header>
      <el-main>
        <el-scrollbar>
          <div
            v-infinite-scroll="load"
            :infinite-scroll-distance="50"
            :infinite-scroll-immediate="false"
          >
            <OrderCard
              v-for="po in orderStore.pendingOrders"
              :key="po.id"
              :data="po"
            />
          </div>
          <el-empty v-if="!orderStore.pendingOrders.length" description="暂无订单，快去购买吧！" />
        </el-scrollbar>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
  import NavBar from '@/components/NavBar.vue'
  import OrderCard from '@/components/OrderCard.vue'
  import { onMounted, onUnmounted, ref } from 'vue'
  import { useOrderStore } from '@/stores/order'
  import { GetPendingOrders } from '@/api/order'
  // pinia
  const orderStore = useOrderStore()
  // 数据
  const current = ref(0)
  // 挂载的时候发请求拿数据
  onMounted(() => {
    GetPendingOrders(current.value++)
    .then(
      data => {
        if (data.success) {
          if (data.total !== 0) {
            orderStore.setPendingOrders(data.data!)
          } else {
            ElMessage({
              message: '数据到头辣！',
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
  })
  onUnmounted(() => {
    orderStore.clearPendingOrders()
  })
  function load() {
    GetPendingOrders(current.value++)
    .then(
      data => {
        if (data.success) {
          orderStore.pushPendingOrders(data.data!)
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
</script>

<style scoped lang="scss">
  .el-container {
    height: 100vh;
    .el-header,
    .el-main {
      padding: 0;
    }
  }
</style>