<template>
  <div>
    <el-card>
      <div class="info">
        <el-avatar size="large" :src="props.data.picture" />
        <span class="username">{{ props.data.username }}</span>
      </div>
      <div class="btn-wrapper">
        <el-button class="btn" size="large" @click="showFarmerInfo">查看</el-button>
      </div>
    </el-card>
    <FarmerDialog v-model="dialogVisiable" :data="props.data" :load="load" />
  </div>
</template>

<script setup lang="ts">
  import FarmerDialog from '@/components/FarmerDialog.vue'
  import { ref } from 'vue'
  // 数据
  const dialogVisiable = ref(false)
  const props = defineProps<{
    data: { 
      id: string
      username: string
      email: string | null
      fullName: string | null
      address: string | null
      picture: string
      role: string
    },
    load: () => void
  }>()
  // 函数
  function showFarmerInfo() {
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
    .info {
      box-sizing: border-box;
      padding: 10px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      .el-avatar {
        width: 100px;
        height: 100px;
      }
      .username {
        font-weight: 700;
        // font-size: 24px;
      }
    }
    .btn-wrapper {
      box-sizing: border-box;
      padding: 10px;
      padding-bottom: 0;
      text-align: end;
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