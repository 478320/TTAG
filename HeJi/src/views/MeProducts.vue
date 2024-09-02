<template>
  <div>
    <el-container>
      <el-header>
        <NavBar />
      </el-header>
      <el-main>
        <el-scrollbar>
          <div
            class="container"
            v-if="userStore.user.role === 'farmer'"
            v-infinite-scroll="load" 
            :infinite-scroll-distance="10" 
            :infinite-scroll-immediate="false"  
          >
            <div class="btn">
              <el-button size="large" @click="publishProduct">发布我的产品</el-button>
            </div>
            <el-space
              wrap
              :size="74"
            >
              <ProductCard
                v-for="p in productsStore.productsMe"
                :key="p.id"
                :data="p"
                :show-icon="false"
                me-product
              />
            </el-space>
          </div>
          <el-empty description="您还不是农户！请先申请为农户" v-if="userStore.user.role !== 'farmer'" />
        </el-scrollbar>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
  import NavBar from '@/components/NavBar.vue'
  import ProductCard from '@/components/ProductCard.vue'
  import { useRouter } from 'vue-router'
  import { useUserStore } from '@/stores/user'
  import { useProductsStore } from '@/stores/products'
  import { ProductsMe } from '@/api/product'
  import { onMounted, onUnmounted, ref } from 'vue';
  import emitter from '@/utils/emitter'
  // 路由
  const router = useRouter()
  // pinia
  const userStore = useUserStore()
  const productsStore = useProductsStore()
  // 数据
  const current = ref(1)
  // 函数
  function publishProduct() {
    router.push({
      path: '/me-products/publish'
    })
  }
  // 
  onMounted(() => {
    emitter.on('load', () => {
      load()
    })
    if (userStore.user.role === 'farmer') {
      // 发请求拿数据
      ProductsMe(current.value++)
      .then(
        data => {
          if (data.success) {
            productsStore.setProductsMe(data.data)
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
  })
  function load() {
    if (userStore.user.role === 'farmer') {
      // 发请求拿数据
      ProductsMe(current.value++)
      .then(
        data => {
          if (data.success) {
            if (data.total !== 0) {
              productsStore.pushProductsMe(data.data)
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
  }
  onUnmounted(() => {
    emitter.off('load')
    productsStore.clearProductsMe()
  })
</script>

<style scoped lang="scss">
  .el-container {
    height: 100vh;
    .el-header,
    .el-main {
      padding: 0;
      .container {
        padding: 40px 100px;
        background: linear-gradient(180deg, rgba(255, 247, 206, 0.33) 0%, rgba(247, 247, 247, 0.55) 100%);
        .btn {
          text-align: center;
          box-sizing: border-box;
          padding: 80px;
          padding-top: 40px;
          .el-button {
            width: 258px;
            height: 63px;
            color: white;
            background: linear-gradient(45deg, #FAE28B 0%, #9EE19C 100%);
            font-weight: 700;
            font-size: 32px;
            border-radius: 8px;
            &:active {
              border-color: gray;
            }
          }
        }
      }
    }
  }
</style>