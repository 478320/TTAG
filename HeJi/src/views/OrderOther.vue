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
              v-for="o in orderStore.otherOrders"
              :key="o.id"
              :data="o"
            />
          </div>
          <el-empty v-if="!orderStore.otherOrders.length" description="暂无订单，快去购买吧！" />
        </el-scrollbar>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
  import NavBar from '@/components/NavBar.vue'
  import OrderCard from '@/components/OrderCard.vue'
  import { useOrderStore } from '@/stores/order'
  import { GetOtherOrders } from '@/api/order'
  import { onMounted, onUnmounted, ref } from 'vue'
  // pinia
  const orderStore = useOrderStore()
  // 数据
  const current = ref(0)
  onMounted(() => {
    GetOtherOrders(current.value++)
    .then(
      data => {
        if (data.success) {
          if (data.total !== 0) {
            orderStore.setOtherOrders(data.data!)
          } else {
            ElMessage({
              message: '数据到头辣！',
              type: 'warning',
              grouping: true
            })
          }
        }
      },
      err => {
        throw err
      }
    )
  })
  onUnmounted(() => {
    orderStore.clearOtherOrders()
  })
  function load() {
    GetOtherOrders(current.value++)
    .then(
      data => {
        if (data.success) {
          orderStore.pushOtherOrders(data.data!)
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