<template>
  <div class="wrapper">
    <el-card>
      <template #header>
        <div class="order-description">
          <span class="create-time">{{ props.data.createAt }}</span>
          <span class="order-id">订单号： {{ props.data.id }}</span>
        </div>
        <div class="cancle-icon">
          <img src="../assets/delete.svg" @click="deleteOrder" alt="">
        </div>
      </template>
      <img :src="props.data.image" alt="">
      <div class="about">
        <div class="name-price">
          <div class="name">{{ props.data.name }}</div>
          <div class="price">{{ props.data.price }} 元/斤</div>
        </div>
        <!-- <el-rate v-model="rating" size="large" disabled /> -->
      </div>
      <div class="des">
        <div class="content">
          ￥{{ props.data.totalPrice }}
        </div>
        <!-- <div class="address">
          发货地址：烟台市
        </div> -->
      </div>
      <div class="flex-grow"></div>
      <div class="btn-wrapper">
        <div class="status" :style="props.data.status === 'completedd' ? 'color: rgba(0, 0, 0, 0.25);' : ''">{{ statusText }}</div>
        <el-button v-if="props.data.status !== 'cancel'" class="btn" size="large" @click="handleClick">{{ btnText }}</el-button>
      </div>
    </el-card>
    <PayDialog v-model="dialogPayVisiable" :data="payData" />
    <PaidDialog v-model="dialogPaidVisiable" :status="props.data.status" :data="paidData" />
  </div>
</template>

<script setup lang="ts">
  import PayDialog from './PayDialog.vue'
  import PaidDialog from './PaidDialog.vue'
  import { computed, onMounted, reactive, ref } from 'vue'
  import { useOrderStore } from '@/stores/order'
  import { GetLogistics, DeleteOrder } from '@/api/order'
  // pinia
  const orderStore = useOrderStore()
  // 这里的 dialogVisiable 应该是对应商品的一个属性？
  const dialogPayVisiable = ref(false)
  const dialogPaidVisiable = ref(false)
  // 函数
  function handleClick() {
    if (props.data.status === 'pending') {
      dialogPayVisiable.value = true
    } else {
      GetLogistics(props.data.id)
      .then(
        data => {
          if (data.success) {
            paidData.orderId = data.data.logistics.orderId
            paidData.name = data.data.products.name
            paidData.description = data.data.products.description
            paidData.price = data.data.products.price
            paidData.shipAddress = data.data.products.shipAddress
            paidData.totalPrice = data.data.totalPrice
            paidData.image = data.data.products.image
            dialogPaidVisiable.value = true
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
  function deleteOrder() {
    DeleteOrder(props.data.id)
    .then(
      data => {
        if (data.success) {
          orderStore.deleteOtherOrder(props.data.id)
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
  // 数据
  interface OrderType {
    id: string,
    totalPrice: number,
    status: "pending" | "completed" | "paid" | "cancel",
    num: number,
    name: string,
    image: string,
    createAt: string,
    itemId: string,
    price: number
  }
  const props = defineProps<{
    data: OrderType
  }>()
  const btnText = computed(() => {
    if (props.data.status === 'pending') {
      return '成交'
    } else if (props.data.status === 'completed') {
      return '评价'
    } else if (props.data.status === 'paid') {
      return '查看'
    }
  })
  const statusText = computed(() => {
    if (props.data.status === 'pending') {
      return '待支付'
    } else if (props.data.status === 'completed') {
      return '订单已完成'
    } else if (props.data.status === 'paid') {
      return '订单进行中'
    } else if (props.data.status === 'cancel') {
      return '订单已取消'
    }
  })
  const payData = reactive({
    id: props.data.id,
    name: props.data.name,
    image: props.data.image,
    totalPrice: props.data.totalPrice,
    num: props.data.num,
    price: props.data.price
  })
  const paidData = reactive<{
    orderId: string
    name: string
    description: string
    price: number
    shipAddress: string
    totalPrice: number,
    image: string
  }>({})
</script>

<style scoped lang="scss">
  .wrapper {
    margin: 40px 0;
    .el-card {
      width: 80%;
      margin: 0 auto;
      border-radius: 10px;
      :deep(.el-card__header) {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 10px;
        background-color: #F5D35A;
      }
      .el-card__header {
        .order-description {
          font-weight: 700;
          font-size: 16px;
          color: #FFFFFF;
          .create-time {
            margin-right: 40px;
          }
        }
      }
      :deep(.el-card__body) {
        display: flex;
      }
      img {
        width: 15%;
      }
      .about {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        margin-left: 20px;
        .name-price {
          .name {
            font-weight: 700;
            font-size: 24px;
            line-height: 32px;
            color: #F5D35A;
          }
          .price {
            font-weight: 700;
            font-size: 16px;
            line-height: 21px;
            color: rgba(0, 0, 0, 0.7);
          }
        }
      }
      .des {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        margin-left: 40px;
        .content {
          font-weight: 400;
          font-size: 24px;
        }
        // .address {
        //   font-weight: 400;
        //   font-size: 14px;
        //   line-height: 40px;
        //   color: rgba(0, 0, 0, 0.4);
        // }
      }
      .flex-grow {
        flex-grow: 1;
      }
      .btn-wrapper {
        display: flex;
        flex-direction: column;
        justify-content: flex-end;
        .status {
          text-align: center;
          font-weight: 400;
          font-size: 24px;
          margin-bottom: 20px;
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
      .cancle-icon {
        img {
          width: 28px;
          height: 28px;
          cursor: pointer;
        }
      }
    }
    :deep(.el-dialog) {
      padding: 0;
      border-radius: 16px;
    }
    
  }
  
</style>