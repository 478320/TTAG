<template>
  <div>
    <el-menu
      mode="horizontal"
      :ellipsis="false"
      router
      :default-active="activeIndex"
    >
      <el-menu-item index="/admin/farmer" class="logo">
        <img src="../assets/logo.svg" alt="logo">
        <img src="../assets/projectName.svg" alt="projectName">
      </el-menu-item>
      <el-menu-item index="/admin/farmer">农户审核</el-menu-item>
      <el-menu-item index="/admin/products">产品审核</el-menu-item>
      <div class="flex-grow"></div>
      <el-menu-item class="about-user">
        <el-avatar :src="userStore.user.picture" />
      </el-menu-item>
      <el-menu-item class="about-user">
        <el-button size="large" class="btn" @click="handleLogout">登出</el-button>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script setup lang="ts">
  import { onMounted, ref } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { useUserStore } from '@/stores/user'
  import { useAuthStore } from '@/stores/auth'
  import { Logout } from '@/api/user'
  // 路由
  const route = useRoute()
  const router = useRouter()
  // pinia
  const userStore = useUserStore()
  const authStore = useAuthStore()
  //  数据
  const activeIndex = ref(route.path)
  // 函数
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
  onMounted(() => {
    if (!userStore.user) {
      router.replace({
        path: '/'
      })
    }
    if (userStore.user.role !== 'admin') {
      router.replace({
        path: '/'
      })
    }
  })
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