<template>
  <div>
    <el-dialog 
      v-model="dialogVisiable"
      :show-close="false"
    >
      <template #header="{ close }">
        <div class="header">
          <img src="../assets/cancle.svg" alt="cancle" @click="close">
        </div>
      </template>
      <div class="description">
        <div class="left">
          <img :src="props.data.image" alt="img">
          <div class="des-content">
            <div class="top">
              <div class="name">{{ props.data.name }}</div>
              <div class="price">{{ props.data.price }}元/斤</div>
            </div>
            <div class="address">
              {{ props.data.shipAddress }}
            </div>
          </div>
        </div>
        <div class="dess-totalm">
          <div class="dess">{{ props.data.description }}</div>
          <div class="total-m">成交金额 ￥{{ props.data.totalPrice }}</div>
        </div>
      </div>
      <div class="info">
        <div v-if="props.status === 'completed'" class="container">
          <el-rate v-model="rating" />
          <el-input 
            type="textarea" 
            placeholder="输入您对产品的评价" 
            autosize 
            v-model="comment"
          />
          <el-button class="btn" size="large" @click="submitReview" >提交</el-button>
        </div>
        <div v-if="props.status === 'paid'" class="container">
          <img src="../assets/address.svg" alt="">
          <div class="footer">
            <span>订单号：{{props.data.orderId}}</span>
            &nbsp
            <span>运单号：正在生成</span>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
  import { defineModel, defineProps, ref } from 'vue'
  import { ReviewProduct } from '@/api/product'
  const dialogVisiable = defineModel()
  const props = defineProps<{
    status: string
    data: {
    orderId: string
    name: string
    description: string
    price: number
    shipAddress: string
    totalPrice: number,
    image: string
  }
  }>()
  const rating = ref<number>()
  const comment = ref('')
  function submitReview() {
    const reviewData = {
      orderId: props.data.orderId,
      rating: rating.value,
      comment: comment.value
    }
    ReviewProduct(reviewData)
    .then(
      data => {
        if (data.success) {
          ElMessage({
            message: data.msg,
            type: 'success',
            grouping: true
          })
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
  .el-dialog {
    .header {
      display: flex;
      justify-content: flex-end;
      box-sizing: border-box;
      padding: 10px;
      img {
        cursor: pointer;
      }
    }
    .description {
      display: flex;
      justify-content: space-between;
      .left {
        display: flex;
      }
      img {
        width: 200px;
        margin-right: 20px;
      }
      .des-content {
        width: 200px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        .top {
          .name {
            font-weight: 700;
            font-size: 24px;
            color: #F5D35A;
          }
          .price {
            font-weight: 700;
            font-size: 16px;
            color: rgba(0, 0, 0, 0.7);
          }
        }
        .address {
          font-weight: 400;
          font-size: 14px;
          color: rgba(0, 0, 0, 0.4);
        }
      }
      .dess-totalm {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        .dess {
          font-weight: 400;
          font-size: 14px;
        }
        .total-m {
          font-weight: 400;
          font-size: 14px;
          color: rgba(0, 0, 0, 0.4);
          text-align: end;
        }
      }
    }
    .info {
      .container {
        width: 80%;
        margin: 0 auto;
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-top: 40px;
        .el-rate {
          transform: scale(2);
          margin-bottom: 20px;
        }
        .el-textarea {
          --el-input-focus-border-color: #F5D35A;
        }
        .btn {
          color: white;
          background: linear-gradient(45deg, #FAE28B 0%, #9EE19C 100%);
          font-weight: 600;
          font-size: 20px;
          margin-top: 20px;
          &:active {
            border-color: gray;
          }
        }
      }
    }
  }
  :deep(.el-dialog__header) {
    padding: 0;
  }
  :deep(.el-dialog__body) {
    padding: 30px;
    padding-top: 0;
  }
  :deep(.el-textarea__inner) {
    min-height: 200px !important;
  }
</style>