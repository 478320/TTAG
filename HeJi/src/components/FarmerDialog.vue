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
          <el-avatar size="large" :src="props.data.picture" />
          <span class="username">{{ props.data.username }}</span>
        </div>
        <div class="user-data">
          <div class="item">
            <span class="title">
              用户名
            </span>
            <span class="data">
              {{ props.data.username }}
            </span>
          </div>
          <div class="item">
            <span class="title">
              地址
            </span>
            <span class="data">
              {{ props.data.address === null ? '暂无' : props.data.address }}
            </span>
          </div>
          <div class="item">
            <span class="title">
              邮箱
            </span>
            <span class="data">
              {{ props.data.email }}
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
  import { useAdminStore } from '@/stores/admin'
  import { PermitFarmer, RejectFarmer } from '@/api/admin'
  // 数据
  const adminStore = useAdminStore()
  const dialogVisiable = defineModel()
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
  function permit() {
    PermitFarmer(props.data.id)
    .then(
      data => {
        if (data.success) {
          adminStore.deleteCheckFarmer(props.data.id)
          if (adminStore.checkFarmers.length < 12) {
            props.load()
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
      }
    )
  }
  function reject() {
    RejectFarmer(props.data.id)
    .then(
      data => {
        if (data.success) {
          adminStore.deleteCheckFarmer(props.data.id)
          if (adminStore.checkFarmers.length < 12) {
            props.load()
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
          margin: 40px 0;
          .title {
            width: 100px;  
            color: #F5D35A;
          }
          .data {
            width: 70%;
            padding: 10px;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
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
  }
</style>