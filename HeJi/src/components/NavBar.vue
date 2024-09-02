<template>
  <div>
    <el-menu
      mode="horizontal"
      :ellipsis="false"
      router
      :default-active="activeIndex"
    >
      <el-menu-item index="/products" class="logo">
        <img src="../assets/logo.svg" alt="logo">
        <img src="../assets/projectName.svg" alt="projectName">
      </el-menu-item>
      <el-menu-item index="/products">产品大厅</el-menu-item>
      <el-menu-item index="/order">待付订单</el-menu-item>
      <el-menu-item index="/order-other">其他订单</el-menu-item>
      <el-menu-item index="/me">我的信息</el-menu-item>
      <el-menu-item index="/me-products">我的产品</el-menu-item>
      <!-- <div class="flex-grow"></div> -->
      <el-menu-item index="/products" class="flex-grow about-user">
        <el-input
          :suffix-icon="SearchIcon"
          placeholder="搜索感兴趣的产品"
          v-model="searchContent"
          @keyup.enter="searchProduct"
        />
      </el-menu-item>
      <el-menu-item v-if="authStore.token" class="about-user">
        <el-button v-if="userStore.user.role !== 'farmer'" size="large" class="btn" @click="beFarmer">成为农户</el-button>
      </el-menu-item>
      <el-menu-item v-if="authStore.token" class="about-user">
        <el-avatar :src="userStore.user.picture"></el-avatar>
      </el-menu-item>
      <el-menu-item v-if="authStore.token" class="about-user">
        <el-button size="large" class="btn" @click="handleLogout">登出</el-button>
      </el-menu-item>
      <el-menu-item class="about-user">
        <el-button v-if="!authStore.token" size="large" class="btn" @click="handleLogin">登录</el-button>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script setup lang="ts">
  import { ref, nextTick, onUnmounted } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import SearchIcon from '@/components/SearchIcon.vue'
  import { useUserStore } from '@/stores/user'
  import { useAuthStore } from '@/stores/auth'
  import { BeFarmer, Logout } from '@/api/user'
  import emitter from '@/utils/emitter'
  // 
  nextTick(() => {
    emitter.on('click-search', () => {
      searchProduct()
    })
  })
  onUnmounted(() => {
    emitter.off('click-search')
  })
  // pinia
  const userStore = useUserStore()
  const authStore = useAuthStore()
  // 路由
  const route = useRoute()
  const router = useRouter()
  //  数据
  const activeIndex = ref(`/${route.path.split('/')[1]}`)
  const searchContent = ref('')
  // 函数
  function searchProduct() {
    // router.push({
    //   path: '/products'
    // })
    emitter.emit('search', searchContent.value)
  }
  function beFarmer() {
    if (userStore.user.role === 'request') {
      ElMessage({
        message: '请耐心等待通知，勿重复发请求！',
        type: 'warning',
        grouping: true
      })
      return
    }
    if (!userStore.user.email) {
      ElMessage({
        message: '您的邮箱为空，请前往 我的信息 页面设置！',
        type: 'warning',
        grouping: true
      })
      return
    }
    BeFarmer({email: userStore.user.email})
    .then(
      data => {
        if (data.success) {
          ElMessage({
            message: data.msg as string,
            type: 'success',
            grouping: true
          })
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
  function handleLogout() {
    Logout()
    .then(
      data => {
        if (data.success) {
          userStore.clearUser()
          authStore.clearToken()
          ElMessage({
            message: data.msg as string,
            type: 'success',
            grouping: true
          })
          router.replace({
            path: '/login'
          })
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
  function handleLogin() {
    router.push({
      path: '/login'
    })
  }
</script>

<style scoped lang="scss">
  .flex-grow {
    flex-grow: 1;
  }
  .el-menu {
    // box-shadow: 0px 3px 4px rgba(0, 0, 0, 0.25);
    .el-menu-item {
      color: rgba(0, 0, 0, 0.5);
      font-weight: 400;
      font-size: 24px;
      &:hover {
        background: linear-gradient(180deg, #FFFFFF 0%, #FFFBD6 100%);
        color: #000000;
      }
      --el-menu-active-color: #F5D35A;
      &.is-active {
        background: linear-gradient(180deg, #FFFFFF 0%, #FFFBD6 100%);
      }
      .el-input {
        width: 335px;
        height: 41px;
        // border: 3px solid #F5D35A;
        --el-input-focus-border-color: #F5D35A;
      }
    }
    .about-user,
    .logo {
      &:hover {
        background: none;
      }
      &.is-active {
        background: none;
      }
      border-bottom: none;
    }
    .about-user {
      background-color: transparent !important;
      cursor: auto;
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
</style>