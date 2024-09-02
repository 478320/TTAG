<template>
  <div>
    <el-container>
      <el-header>
        <NavBar />
      </el-header>
      <el-main>
        <el-scrollbar>
          <RouterView />
        </el-scrollbar>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
  import NavBar from '@/components/NavBar.vue'
</script>

<style scoped lang="scss">
  .el-container {
    height: 100vh;
    .el-header,
    .el-main {
      padding: 0;
    }
  }
</style>