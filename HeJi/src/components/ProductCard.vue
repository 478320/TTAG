<template>
  <div>
    <el-card>
      <div class="top">
        <img :src="props.data.image" alt="img">
        <div class="right">
          <div class="right-top">
            <span class="name">{{ props.data.name }}</span>
            <div class="right-top-middle">
              <span class="price">{{ props.data.price }} 元/斤 </span>
              <div class="gwc" v-if="props.showIcon">
                <el-input-number
                  @click.stop
                  :controls="false"
                  v-model="stockNum"
                  :disabled="!authStore.token"
                ></el-input-number>
                <img class="gwc-svg" src="../assets/gwc.svg" alt="gwc" @click.stop="newOrder">
              </div>
            </div>
          </div>
          <div class="right-bottom">
            <el-rate
              v-model="props.data.score"
              size="large"
              disabled
            />
          </div>
        </div>
      </div>
      <div class="description">
        {{ props.data.description }}
      </div>
      <div class="address">
        {{ props.data.shipAddress }}
      </div>
      <div v-if="props.meProduct" class="bottom">
        <div class="stock">
          库存
          <el-input-number
            @click.stop
            :controls="false"
            :disabled="isEdit"
            v-model="stockNum"
          ></el-input-number>
          <img v-show="isEdit" @click="editStock" src="../assets/edit.svg" alt="">
          <el-button v-show="!isEdit" @click="editStock" type="warning" class="btn" size="small">确认修改</el-button>
        </div>
        <div class="btns">
          <el-button type="warning">修改重申</el-button>
          <el-button type="danger" @click="deleteProduct">下架产品</el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
  import { defineProps, ref } from 'vue'
  import { useRoute } from 'vue-router'
  import { CreateOrder } from '@/api/order'
  import { ChangeStock, DeleteProduct } from '@/api/product'
  import { useProductsStore } from '@/stores/products'
  import { useAuthStore } from '@/stores/auth'
  import emitter from '@/utils/emitter'
  // pinia
  const productsStore = useProductsStore()
  const authStore = useAuthStore()
  // 路由
  const route = useRoute()
  // 定义类型
  interface DataType {
    id: string,
    name: string,
    price: number,
    description: string,
    image: string,
    score: number,
    shipAddress: string,
    stock: number,
    status: string
  }
  const props = defineProps<{
    data: DataType,
    showIcon: boolean,
    meProduct: boolean
  }>()
  // 数据
  const isEdit = ref(true)
  const stockNum = ref<number | null>(route.path === '/me-products' ? props.data.stock : null)
  // 函数
  function editStock() {
    if (!isEdit.value) {
      if (stockNum.value && stockNum.value !== props.data.stock) {
        ChangeStock(props.data.id, stockNum.value)
        .then(
          data => {
            if (data.success) {
              productsStore.changeStock(props.data.id, stockNum.value as number)
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
      } else {
        stockNum.value = props.data.stock
      }
    }
    isEdit.value = !isEdit.value
  }
  function deleteProduct() {
    DeleteProduct(props.data.id)
    .then(
      data => {
        if (data.success) {
          productsStore.deleteProduct(props.data.id)
          if (productsStore.productsMe.length < 8) {
            emitter.emit('load')
          }
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
  function newOrder() {
    if (!authStore.token) {
      ElMessage({
        message: '您还未登录！请先登录！',
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
      CreateOrder(props.data.id, stockNum.value as number)
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
  .el-card {
    width: 320px;
    border-radius: 10px;
    cursor: pointer;
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
            // font-size: 24px;
            line-height: 32px;
            color: #F5D35A;
            text-align: center;
          }
          .right-top-middle {
            // text-align: center;
            margin-top: 3px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: space-around;
            .price {
              font-weight: 700;
              font-size: 16px;
              line-height: 21px;
              color: rgba(0, 0, 0, 0.7);
            }
            .gwc {
              margin-top: 5px;
              width: 100%;
              display: flex;
              align-items: center;
              justify-content: space-around;
              .el-input-number {
                width: 70px;
                height: 23px;
                &::after {
                  content: '斤';
                  font-weight: 400;
                  font-size: 10px;
                  color: rgba(0, 0, 0, 0.5);
                  margin-left: 3px;
                }
              }
              .gwc-svg {
                width: 25px;
              }
            }
          }
          
        }
        .right-bottom {
          text-align: center;
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
    .address {
      font-weight: 400;
      font-size: 14px;
      line-height: 18px;
      color: rgba(0, 0, 0, 0.4);
      margin-top: 20px;
    }
    .bottom {
      .stock {
        font-weight: 400;
        font-size: 14px;
        line-height: 18px;
        color: rgba(0, 0, 0, 0.4);
        margin-top: 20px;
        display: flex;
        align-items: center;
        .el-input-number {
          width: 70px;
          height: 23px;
          margin-left: 10px;
          &::after {
            content: '斤';
            line-height: 23px;
            margin-left: 2px;
          }
        }
        .btn {
          margin-left: 10px;
        }
      }
      .btns {
        margin-top: 20px;
        display: flex;
        justify-content: space-between;
      }
    }
  }
  :deep(.el-input-number .el-input__wrapper.is-focus) {
    box-shadow: 0 0 0 1px #F5D35A inset !important;
  }
</style>