<template>
  <div>
    <el-card>
      <div class="top">
        <img :src="props.data.image" alt="img">
        <div class="right">
          <div class="right-top">
            <span class="name">{{ props.data.name }}</span>
            <!-- <div class="right-top-middle"> -->
              <!-- <span class="price">{{ props.data.price }} 元/斤 </span> -->
              <!-- <img  v-if="props.showIcon" class="gwc-svg" src="../assets/gwc.svg" alt="gwc"> -->
            <!-- </div> -->
          </div>
          <div class="right-bottom">
            <!-- <el-rate
              v-model="props.data.score"
              size="large"
              disabled
            /> -->
            <span class="price">{{ props.data.price }} 元/斤 </span>
          </div>
        </div>
      </div>
      <div class="description">
        {{ props.data.description }}
      </div>
      <div class="footer">
        <span class="address">{{ props.data.shipAddress }}</span>
        <el-button class="btn" @click="showDialog">查看</el-button>
      </div>
    </el-card>
    <AdminProductDialog :data="props.data" :farmer-data="props.farmerData" :load="props.load" v-model="dialogVisiable" />
  </div>
</template>

<script setup lang="ts">
  import AdminProductDialog from '@/components/AdminProductDialog.vue'
  import { defineProps, ref } from 'vue'
  // 定义类型
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
  // 数据
  const props = defineProps<{
    data: DataType,
    load: () => void,
    farmerData: {
      username: string
      picture: string
      email: string
    }
  }>()
  const dialogVisiable = ref(false)
  // 函数
  function showDialog() {
    dialogVisiable.value = true
  }
</script>

<style scoped lang="scss">
  .el-card {
    width: 320px;
    border-radius: 10px;
    transition: all .3s ease;
    &:hover {
      transform: translateY(-10px);
    }
    .top {
      display: flex;
      img {
        width: 50%;
      }
      .right {
        flex-grow: 1;
      }
      .right {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        .right-top {
          .name {
            display: block;
            font-weight: 700;
            font-size: 24px;
            line-height: 32px;
            color: #F5D35A;
            text-align: center;
          }
          // .right-top-middle {
          //   // text-align: center;
          //   margin-top: 3px;
          //   display: flex;
          //   align-items: center;
          //   justify-content: space-around;
          //   .price {
          //     font-weight: 700;
          //     font-size: 16px;
          //     line-height: 21px;
          //     color: rgba(0, 0, 0, 0.7);
          //   }
          //   .gwc-svg {
          //     width: 25px;
          //   }
          // }
        }
        .right-bottom {
          text-align: center;
          .price {
            font-weight: 700;
            font-size: 16px;
            line-height: 21px;
            color: rgba(0, 0, 0, 0.7);
          }
        }
      }
    }
    .description {
      font-weight: 400;
      font-size: 14px;
      line-height: 18px;
      margin-top: 20px;
      color: #000;
    }
    .footer {
      display: flex;
      align-items: center;
      justify-content: space-between;;
      margin-top: 20px;
      .address {
        font-weight: 400;
        font-size: 14px;
        line-height: 18px;
        color: rgba(0, 0, 0, 0.4);
      }
      .btn {
        color: white;
        background: linear-gradient(45deg, #FAE28B 0%, #9EE19C 100%);
        font-weight: 700;
        &:active {
          border-color: gray;
        }
      }
    }
  }
</style>