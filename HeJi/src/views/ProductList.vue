<template>
  <div 
    class="container" 
    v-infinite-scroll="load" 
    :infinite-scroll-distance="10" 
    :infinite-scroll-immediate="false"
  >
    <el-space
      wrap
      :size="74"
    >
      <ProductCard
        v-for="p in productsStore.products"
        :key="p.id"
        :data="p"
        show-icon
        :me-product="false"
        @click="showProductDetail(p.id)"
      />
    </el-space>
    <el-empty v-if="!productsStore.products.length" description="暂无数据" />
  </div>
</template>

<script setup lang="ts">
  import ProductCard from '@/components/ProductCard.vue'
  import { useRouter } from 'vue-router'
  import { onMounted, onUnmounted, ref } from 'vue'
  import { useProductsStore } from '@/stores/products'
  import { ProductsList, SearchProduct } from '@/api/product'
  import emitter from '@/utils/emitter'
  // pinia
  const productsStore = useProductsStore()
  // 生命周期钩子
  onMounted(() => {
    productsStore.clearProducts()
    // 绑定事件
    emitter.on('search', (content) => {
      if (content !== '') {
        isSearch.value = true
        searchContent.value = content as string
        productsStore.clearProducts()
        SearchProduct(content as string, productsStore.current)
        .then (
          data => {
            if (data.success) {
              if (data.total !== 0) {
                productsStore.setProducts(data.data)
                productsStore.plusCurrent()
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
      } else {
        location.reload()
      }
    })
    // 挂载的时候发请求
    if (!productsStore.products.length) {
      ProductsList(productsStore.current)
      .then(
        data => {
          if (data.success) {
            productsStore.setProducts(data.data)
            productsStore.plusCurrent()
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
  onUnmounted(() => {
    emitter.off('search')
    productsStore.clearProducts()
  })
  // 路由
  const router = useRouter()
  // 数据
  const isSearch = ref(false)
  const searchContent = ref('')
  // 函数
  function showProductDetail(id: string) {
    router.push({
      path: `/products/detail/${id}`
    })
  }
  function load() {
    if (isSearch.value && searchContent.value !== '') {
      SearchProduct(searchContent.value, productsStore.current)
      .then(
        data => {
          if (data.success) {
            if (data.total !== 0) {
              productsStore.pushProducts(data.data)
              productsStore.plusCurrent()
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
    } else {
      ProductsList(productsStore.current)
      .then(
        data => {
          if (data.success) {
            if (data.total !== 0) {
              productsStore.pushProducts(data.data)
              productsStore.plusCurrent()
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
</script>

<style scoped lang="scss">
  .container {
    padding: 40px 100px;
  }
</style>