<template>
  <div>
    <el-dialog
      v-model="dialogVisiable"
      :show-close="false"
      @close="handleClose"
    >
      <template #header="{ close }">
        <div class="header">
          <img src="../assets/cancle.svg" alt="cancle" @click="close">
        </div>
      </template>
      <div class="content">
        <div class="description">
          <img :src="props.data.image" alt="img">
          <div class="des-content">
            <div class="top">
              <div class="name">{{ props.data.name }}</div>
              <div class="price">{{ props.data.price }}元/斤</div>
            </div>
            <!-- <div class="address">
              烟台市
            </div> -->
          </div>
        </div>
        <div class="form">
          <el-form
           v-model="formData"
           label-position="left"
           label-width="auto"
          >
            <!-- <el-form-item label="购买份额">
              <el-input class="first-input" /> 斤
            </el-form-item> -->
            <!-- <el-form-item label="发货地址">
              烟台市
            </el-form-item> -->
            <el-form-item label="收货地址">
              <el-input v-model="formData.address" />
            </el-form-item>
            <el-form-item label="密码">
              <el-input type="password" v-model="formData.password" />
            </el-form-item>
          </el-form>
        </div>
      </div>
      <template #footer>
        <div class="footer">
          <div class="container">
            <span class="total-w">共 {{ props.data.num }} 斤</span>
            合计：<span class="total-m">￥{{ props.data.totalPrice }}</span>
            <el-button class="btn" size="large" @click="payOrder">付款</el-button>
          </div>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
  import { reactive, defineModel, defineProps, onMounted } from 'vue'
  import { useUserStore } from '@/stores/user'
  import { useOrderStore } from '@/stores/order'
  import { PayOrder } from '@/api/order'
  // pinia
  const userStore = useUserStore()
  const orderStore = useOrderStore()
  // 数据
  const dialogVisiable = defineModel()
  const props = defineProps<{
    data: {
      id: string,
      name: string,
      image: string,
      totalPrice: number,
      num: number,
      price: number
    }
  }>()
  const formData = reactive({
    address: userStore.user.address,
    password: ''
  })
  // 函数
  function handleClose() {
    formData.password = ''
  }
  function payOrder() {
    PayOrder(props.data.id, formData.password)
    .then(
      data => {
        if (data.success) {
          orderStore.deletePendingOrder(props.data.id)
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
    .content {
      box-sizing: border-box;
      padding: 20px;
      .description {
        display: flex;
        justify-content: center;
        img {
          width: 200px;
          margin-right: 20px;
        }
        .des-content {
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
          // .address {
          //   font-weight: 400;
          //   font-size: 14px;
          //   color: rgba(0, 0, 0, 0.4);
          // }
        }
      }
      .form {
        width: 50%;
        margin: 0 auto;
        .el-form {
          margin-top: 30px;
          .el-form-item {
            font-weight: 400;
            font-size: 16px;
            line-height: 21px;
            .el-input {
              --el-input-focus-border-color: #F5D35A;
            }
            // .first-input {
            //   width: 40px;
            //   margin-right: 5px;
            // }
          }
        }
      }
    }
    .footer {
      height: 63px;
      background: #F5D35A;
      border-radius: 16px;
      display: flex;
      align-items: center;
      justify-content: flex-end;
      .container {
        display: flex;
        align-items: center;
        .total-w {
          font-weight: 400;
          font-size: 16px;
          color: rgba(0, 0, 0, 0.5);
          margin-right: 20px;
        }
        .total-m {
          font-weight: 700;
          font-size: 32px;
          color: #FFFFFF;
        }
        .btn {
        color: white;
        background: linear-gradient(180deg, #FAE28B 0%, #9EE19C 0.01%);
        font-weight: 600;
        font-size: 20px;
        &:active {
          border-color: gray;
        }
        margin: 0 40px;
      }
      }
    }
  }
</style>