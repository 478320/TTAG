<template>
  <div>
    <el-container>
      <el-header>
        <NavBar />
      </el-header>
      <el-main>
        <div class="container">
          <div class="back-icon">
            <img src="../assets/back.svg" alt="back" @click="backToMeProducts">
          </div>
          <el-form v-model="formData">
            <el-form-item label="产品名称">
              <el-input size="large" v-model="formData.name" />
            </el-form-item>
            <el-form-item label="价格明细">
              <el-input-number :controls="false" size="large" v-model="formData.price" /> <span style="margin-left: 10px;">元/斤</span>
            </el-form-item>
            <el-form-item label="产品简介">
              <el-input size="large" v-model="formData.description" />
            </el-form-item>
            <el-form-item label="提供服务">
              <el-input size="large" v-model="formData.service" />
            </el-form-item>
            <el-form-item label="发货地址">
              <el-input size="large" v-model="formData.shipAddress" />
            </el-form-item>
            <el-form-item label="初始库存">
              <el-input-number :controls="false" size="large" v-model="formData.stock" /> <span style="margin-left: 10px;">斤</span>
            </el-form-item>
            <el-form-item label="产品展示">
              <el-upload
                drag
                multiple
                action="http://47.121.128.102:8081/upload/add"
                :headers="{
                  Authorization: authStore.token
                }"
                method="post"
                :on-success="handleAvatarSuccess"
                :on-error="handleAvatarError"
                :show-file-list="false"
              >
              <div class="el-upload__text">
                DROP IMAGE FILE HERE
              </div>
              <span style="color: #F5D35A;">or click to upload</span>
              </el-upload>
            </el-form-item>
            <el-form-item class="btn-wrapper">
              <el-button class="btn" size="large" @click="submitProduct">提交审核</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
  import NavBar from '@/components/NavBar.vue'
  import { reactive } from 'vue'
  import { useRouter } from 'vue-router'
  import { useAuthStore } from '@/stores/auth'
  import { PostProduct } from '@/api/product'
  import type { UploadProps } from 'element-plus'
  // 路由
  const router = useRouter()
  // pinia
  const authStore = useAuthStore()
  // 数据
  const formData = reactive({
    name: '',
    price: 0,
    description: '',
    image: '',
    shipAddress: '',
    service: '',
    stock: 0
  })
  // 函数
  function backToMeProducts() {
    router.push({
      path: '/me-products'
    })
  }
  const handleAvatarSuccess: UploadProps['onSuccess'] = (response) => {
    formData.image = response.data
    ElMessage({
      message: response.msg,
      type: 'success',
      grouping: true
    })
  }
  const handleAvatarError: UploadProps['onError'] = (err) => {
    ElMessage({
      message: '图片上传失败，请重试!',
      type: 'error',
      grouping: true
    })
  }
  function submitProduct() {
    PostProduct(formData)
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
  .el-container {
    height: 100vh;
    .el-header,
    .el-main {
      padding: 0;
      .container {
        background: linear-gradient(180deg, rgba(255, 247, 206, 0.198) 0%, rgba(255, 255, 255, 0.55) 100%);
        width: 90%;
        height: 100%;
        margin: 0 auto;
        box-sizing: border-box;
        padding: 20px;
        .back-icon {
          text-align: end;
          img {
            cursor: pointer;
          }
        }
        .el-form {
          width: 80%;
          margin: 0 auto;
          .el-form-item {
            margin-top: 30px;
            :deep(.el-form-item__label) {
              font-weight: 700;
              font-size: 20px;
              color: #F5D35A;
            }
            .el-input {
              // width: 335px;
              // height: 41px;
              --el-input-focus-border-color: #F5D35A;
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
            .el-upload {
              .el-upload__text {
                font-weight: 700;
                font-size: 32px;
                color: rgba(0, 0, 0, 0.28);
                padding: 45px 30px;
              }
            }
          }
          .btn-wrapper {
            :deep(.el-form-item__content) {
              justify-content: center;
            }
          }
        }
      }
    }
  }
  :deep(.el-upload-dragger:hover) {
    border-color: darken(#F5D35A, 20%);
  }
  :deep(.el-upload:focus .el-upload-dragger) {
    border-color: darken(#F5D35A, 20%);
  }
  :deep(.el-input-number .el-input__wrapper.is-focus) {
    box-shadow: 0 0 0 1px #F5D35A inset !important;
  }
</style>