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
      <div class="info-container">
        <div class="info">
          <el-avatar size="large" :src="props.farmerData.picture" />
          <span class="username">{{ props.farmerData.username }}</span>
        </div>
        <div class="user-data">
          <div class="item">
            <span class="title">
              产品名称
            </span>
            <span class="data">
              {{ props.data.name }}
            </span>
          </div>
          <div class="item">
            <span class="title">
              价格明细
            </span>
            <span class="data">
              {{ props.data.price }} 元/斤
            </span>
          </div>
          <div class="item">
            <span class="title">
              产品简介
            </span>
            <span class="data">
              {{ props.data.description }}
            </span>
          </div>
          <div class="item">
            <span class="title">
              提供服务
            </span>
            <span class="data">
              {{ !props.data.service ? '暂无' : props.data.service }}
            </span>
          </div>
          <div class="item">
            <span class="title">
              发货地址
            </span>
            <span class="data">
              {{ props.data.shipAddress }}
            </span>
          </div>
          <div class="item">
            <span class="title">
              产品展示
            </span>
            <span class="data">
              <el-carousel>
                <el-carousel-item style="height: 400px;">
                  <img :src="props.data.image" alt="img" >
                </el-carousel-item>
              </el-carousel>
            </span>
          </div>
        </div>
      </div>
      <template #footer>
        <div class="btns">
          <el-button size="large" class="yes-btn btn" @click="permit">通过</el-button>
          <el-button size="large" class="no-btn btn"  @click="reject">退回</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
  import { defineModel } from 'vue'
  import { PermitProduct, RejectProduct } from '@/api/admin'
  import { useAdminStore } from '@/stores/admin'
  // pinia
  const adminStore = useAdminStore()
  // 数据
  const dialogVisiable = defineModel()
  interface DataType {
    id: string
    name: string
    price: number
    stock: number
    description: string
    farmerId: string
    image: string
    shipAddress: string
    service: string
    score: number
    status: string
  }
  const props = defineProps<{
    data: DataType,
    load: () => void,
    farmerData: {
      username: string
      picture: string
      email: string
    }
  }>()
  // 函数
  function permit() {
    PermitProduct(props.data.id)
    .then(
      data => {
        if (data.success) {
          adminStore.deleteCheckProduct(props.data.id)
          if (adminStore.checkProducts.length < 12) {
            props.load()
          }
          ElMessage({
            message: data.msg,
            type: 'success'
          })
        } else {
          ElMessage({
            message: data.errorMsg,
            type: 'error'
          })
        }
      },
      err => {
        throw err
      }
    )
  }
  function reject() {
    RejectProduct(props.data.id)
    .then(
      data => {
        if (data.success) {
          adminStore.deleteCheckProduct(props.data.id)
          if (adminStore.checkProducts.length < 12) {
            props.load()
          }
          ElMessage({
            message: data.msg,
            type: 'success'
          })
        } else {
          ElMessage({
            message: data.errorMsg,
            type: 'error'
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
    .info-container {
      .info {
        display: flex;
        justify-content: center;
        align-items: end;
        .el-avatar {
          width: 100px;
          height: 100px;
        }
        .username {
          font-weight: 700;
          font-size: 32px;
        }
      }
      .user-data {
        .item {
          display: flex;
          justify-content: center;
          align-items: center;
          font-weight: 700;
          font-size: 20px;
          margin: 25px 0;
          .title {
            width: 100px;  
            color: #F5D35A;
          }
          .data {
            width: 70%;
            padding: 10px;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
            img {
              width: 100%;
              height: 100%;
              object-fit: cover;
            }
          }
        }
      }
    }
    .btns {
      display: flex;
      justify-content: center;
      .btn {
        color: white;
        font-weight: 700;
        font-size: 24px;
        &:active {
          border-color: gray;
        }
      }
      .yes-btn {
        background: linear-gradient(45deg, #FAE28B 0%, #9EE19C 100%);
      }
      .no-btn {
        background: linear-gradient(180deg, #FF8970 0%, #FF8B38 100%);
        margin-left: 60px;
      }
    }
  }
  :deep(.el-dialog) {
    border-radius: 16px;
    padding-bottom: 30px;
    margin-top: 5vh;
  }
</style>