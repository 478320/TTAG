<template>
  <div class="card-wrapper">
    <div class="logo">
      <img src="../assets/logo.svg" alt="logo">
    </div>
    <el-card>
      <el-form v-model="formData" label-position="top">
        <el-form-item label="账号">
          <el-input v-model="formData.username" size="large"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="formData.password" size="large" type="password" @keyup.enter="clickBtn"></el-input>
        </el-form-item>
        <el-form-item v-if="route.path === '/reg'" label="手机号">
          <el-input v-model="formData.phoneNumber" size="large"></el-input>
        </el-form-item>
        <el-form-item class="btn">
          <el-button size="large" @click="clickBtn">
            {{ route.path === '/login' ? '登入' : '注册' }}
          </el-button>
        </el-form-item>
        <div class="footer">
          <span @click="handleRedirect">
            {{ route.path === '/login' ? '还未拥有账号？点击注册' : '已经拥有账号？点击登入' }}
          </span>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
  import { onMounted, reactive } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { Register, Login, GetMe } from '@/api/user'
  import { useAuthStore } from '@/stores/auth'
  import { useUserStore } from '@/stores/user'
  // 路由
  const route = useRoute()
  const router = useRouter()
  // pinia
  const authStore = useAuthStore()
  const userStore = useUserStore()
  // 数据
  const formData = reactive({
    username: '',
    password: '',
    phoneNumber: ''
  })
  // 函数
  function clickBtn() {
    // 注册
    if (route.path === '/reg') {
      Register({...formData})
      .then(
        data => {
          if (data.success) {
            // 注册成功
            ElMessage({
              message: data.msg as string,
              type: 'success',
              grouping: true
            })
            router.replace({
              path: '/login'
            })
          } else {
            // 注册失败
            ElMessage({
              message: data.errorMsg as string,
              type: 'error',
              grouping: true
            })
          }
        },
        err => {
          throw err
        }
      )
    } else if (route.path === '/login') {
      // 登录
      const {username, password} = formData
      Login({username, password})
      .then(
        data => {
          // 登录成功
          if (data.success) {
            // 设置token
            authStore.setToken(data.data!.token)
            // 获取用户信息
            GetMe()
            .then(
              data => {
                if (data.success) {
                  // 设置用户信息
                  userStore.setUser(data.data!)
                  ElMessage({
                    message: '登录成功',
                    type: 'success',
                    grouping: true
                  })
                  if (data.data?.role === 'admin') {
                    router.replace({
                      path: '/admin'
                    })
                  } else {
                    router.replace({
                      path: '/products'
                    })
                  }
                } else {
                  ElMessage({
                    message: '登录失败，请重试',
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
            ElMessage({
              message: data.errorMsg as string,
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
  function handleRedirect() {
    if (route.path === '/login') {
      router.replace({
        path: '/reg'
      })
    } else if (route.path === '/reg') {
      router.replace({
        path: '/login'
      })
    }
  }
  onMounted(() => {
    if (authStore.token) {
      if (userStore.user.role === 'admin') {
        router.replace({
          path: '/admin'
        })
      } else {
        router.replace({
          path: '/products'
        })
      }
    }
  })
</script>

<style scoped lang="scss">
  .card-wrapper {
    width: 100%;
    height: 100%;
    position: relative;
    .el-card {
      width: 50%;
      margin: 0 auto;
      margin-top: 10%;
      :deep(.el-card__body) {
        display: flex;
        justify-content: center;
        .el-form {
          width: 50%;
          .el-form-item {
            margin-top: 13%;
          }
          .el-form-item__label {
            font-weight: 700;
            font-size: 20px;
            line-height: 26px;
            color: #FFE27D;
          }
          .el-input {
            --el-input-focus-border-color: #FFE27D;
          }
          .el-button {
            width: 100px;
            height: 40px;
            // font-weight: 700;
            font-size: 17px;
            transform: scale(1);
            &:hover {
              color: darken(#FFE27D, 30%);
              --el-button-hover-border-color: lighten(#FFE27D, 10%);
              background-color: lighten(#FFE27D, 20%);
            }
            &:active {
              color: darken(#FFE27D, 30%);
            --el-button-active-border-color: lighten(#FFE27D, 10%);
              background-color: lighten(#FFE27D, 20%);
            }
          }
        }
      }
      .el-form {
        .btn {
          :deep(.el-form-item__content) {
            justify-content: center;
          }
        }
        .footer {
          text-align: center;
          font-weight: 400;
          font-size: 14px;
          line-height: 18px;
          color: rgba(0, 0, 0, 0.5);
          &:hover {
            color: darken(#FFE27D, 30%);
            cursor: pointer;
            user-select: none;
          }
          &:active {
            color: darken(#FFE27D, 30%);
          }
        }
      }
      
    }
    .logo {
      width: 120px;
      height: 120px;
      position: absolute;
      left: 50%;
      transform: translateX(-50%);
      top: 13%;
      background-color: white;
      border-radius: 50%;
      display: flex;
      justify-content: center;
      align-items: center;
      img {
        width: 70px;
        height: 70px;
      }
    }
  }
</style>