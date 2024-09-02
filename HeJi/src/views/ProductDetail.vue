<template>
  <div class="detail-container" v-infinite-scroll="load" :infinite-scroll-distance="10" :infinite-scroll-immediate="false">
    <div class="top">
      <img :src="productsStore.product?.image" alt="productImg" class="left-img">
      <div class="right-description">
        <div class="title">
          <span class="name">{{ productsStore.product?.name }}</span>
          <el-rate
            v-model="score"
            disabled
          ></el-rate>
        </div>
        <div class="description">
          <ul>
            <li>
              <span class="des-title">产品简介</span>
              <span class="des">{{ productsStore.product?.description }}</span>
            </li>
            <li>
              <span class="des-title">提供服务</span>
              <span class="des">{{ productsStore.product?.service === null ? '暂无' : productsStore.product?.service}}</span>
            </li>
            <li>
              <span class="des-title">发货地址</span>
              <span class="des">{{ productsStore.product?.shipAddress }}</span>
            </li>
            <li>
              <span class="des-title">价格明细</span>
              <span class="des">{{ productsStore.product?.price }}</span>
            </li>
            <li>
              <span class="des-title">剩余库存</span>
              <span class="des">{{ productsStore.product?.stock }}</span>
            </li>
          </ul>
        </div>
        <div class="footer-btn">
          <el-input-number 
            :controls="false"
            v-model="stockNum"
            :disabled="!authStore.token"
          /> <span style="margin-right: 10px;">斤</span>
          <el-button class="btn" size="large" @click="newOrder" >加入购物清单</el-button>
        </div>
      </div>
    </div>
    <div class="mid">
      <div class="title">产品展示</div>
      <div class="pictures">
        <el-carousel
          :autoplay="false"
          arrow="always"
          type="card"
          height="auto"
        >
          <el-carousel-item style="height: 400px;">
            <img :src="productsStore.product?.image" alt="img">
          </el-carousel-item>
        </el-carousel>
      </div>
    </div>
    <div class="footer">
      <div class="title">买家评价</div>
      <div class="comment">
        <CommentCard
          v-for="c in productsStore.comments"
          :key="c.review.id"
          :data="c"
        />
        <el-empty  v-if="!productsStore.comments.length" description="暂无评价" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import CommentCard from '@/components/CommentCard.vue'
  import { computed, onMounted, onUnmounted, ref } from 'vue'
  import { ProductDetail, GetComments } from '@/api/product'
  import { CreateOrder } from '@/api/order'
  import { useProductsStore } from '@/stores/products'
  import { useAuthStore } from '@/stores/auth'
  import { useRoute } from 'vue-router'
  // pinia
  const productsStore = useProductsStore()
  const authStore = useAuthStore()
  // 路由
  const route = useRoute()
  // 数据
  const score = computed(() => {
    productsStore.product?.score
  })
  const current = ref(1)
  const stockNum = ref<number | null>()
  onMounted(() => {
    // 发请求拿数据
    ProductDetail(route.params.id as string)
    .then(
      data => {
        if (data.success) {
          productsStore.setProduct(data.data)
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
    // 发请求拿评论
    GetComments(route.params.id as string, current.value++)
    .then(
      data => {
        if (data.success) {
          productsStore.setComments(data.data)
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
    productsStore.clearProductDetail()
  })
  function load() {
    GetComments(route.params.id as string, current.value++)
    .then(
      data => {
        if (data.success) {
          if (data.total !== 0) {
            productsStore.pushComments(data.data)
          } else {
            ElMessage({
              message: '评论到底辣',
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
  function newOrder() {
    if (!authStore.token) {
      ElMessage({
        message: '您还未登录！',
        type: 'warning',
        grouping: true
      })
      return
    }
    if (typeof stockNum === undefined || stockNum.value === 0) {
      ElMessage({
        message: '请输入正确的质量!',
        type: 'warning',
        grouping: true
      })
    } else {
      CreateOrder(route.params.id as string, stockNum.value as number)
      .then (
        data => {
          if (data.success) {
            ElMessage({
              message: data.msg,
              type: 'success',
              grouping: true
            })
            stockNum.value = null
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
  .detail-container {
    width: 1397px;
    margin: 0 auto;
    box-shadow: 1px 1px 4px 2px rgba(0, 0, 0, 0.25);
    .top {
      box-sizing: border-box;
      padding: 40px;
      width: 1397px;
      height: 473px;  
      background-image: url('../assets/detailBkg.png'); 
      background-size: cover; 
      background-position: center; 
      background-repeat: no-repeat; 
      display: flex;
      align-items: center;
      .left-img {
        width: 364px;
        height: 364px;
        margin-right: 50px;
      }
      .right-description {
        height: 364px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        .title {
          display: flex;
          align-items: center;
          .name {
            font-weight: 700;
            font-size: 48px;
            line-height: 63px;
            color: #F5D35A;
          }
          .el-rate {
            transform: scale(2);
            margin-left: 100px;
          }
        }
        .description {
          ul {
            list-style: none;
            margin: 0;
            padding: 0;
            li {
              display: flex;
              margin: 20px 0;
              align-items: baseline;
              .des-title {
                width: 100px;
                font-weight: 700;
                font-size: 16px;
                line-height: 21px;
                color: rgba(0, 0, 0, 0.5);
                margin-right: 20px;
              }
              .des {
                width: calc(100% - 100px);
                font-weight: 400;
                font-size: 14px;
                line-height: 18px;
                color: #000000;
              }
            }
          }
        }
        .footer-btn {
          .el-input-number {
            width: 50px;
            margin-right: 10px;
          }
          .btn {
            color: white;
            background: linear-gradient(45deg, #FAE28B 0%, #9EE19C 100%);
            font-weight: 600;
            font-size: 20px;
            &:active {
              border-color: gray;
            }
          }
        }
      }
    }
    .mid {
      box-sizing: border-box;
      padding: 40px;
      background: linear-gradient(180deg, rgba(255, 247, 206, 0.33) 0%, rgba(255, 255, 255, 0.55) 100%);
      backdrop-filter: blur(14.6px);
      .title {
        width: 160px;
        height: 46px;
        background: linear-gradient(45deg, #FAE28B 0%, #9EE19C 100%);
        font-weight: 700;
        font-size: 24px;
        line-height: 46px;
        color: #FFFFFF;
        text-align: center;
        border-radius: 8px;
      }
      .pictures {
        margin-top: 60px;
        .el-carousel {
          margin: 0 auto;
          width: 100%;
        }
        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
      }
    }
    .footer {
      box-sizing: border-box;
      padding: 40px;
      background: linear-gradient(180deg, rgba(255, 247, 206, 0.33) 0%, rgba(255, 255, 255, 0.55) 100%);
      .title {
        width: 160px;
        height: 46px;
        background: linear-gradient(45deg, #FAE28B 0%, #9EE19C 100%);
        font-weight: 700;
        font-size: 24px;
        line-height: 46px;
        color: #FFFFFF;
        text-align: center;
        border-radius: 8px;
      }
    }
  }
  :deep(.el-input-number .el-input__wrapper.is-focus) {
    box-shadow: 0 0 0 1px #F5D35A inset !important;
  }
</style>