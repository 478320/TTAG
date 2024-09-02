<template>
  <div>
    <el-container>
      <el-header>
        <NavBar />
      </el-header>
      <el-main>
        <div class="wrapper" v-if="authStore.token">
          <div class="top">
            <div class="about-me">
              <el-upload
                action="http://47.121.128.102:8081/upload/add"
                :headers="{
                  Authorization: authStore.token
                }"
                method="post"
                :on-success="handleAvatarSuccess"
                :on-error="handleAvatarError"
                :show-file-list="false"
              >
                <el-avatar :src="userStore.user.picture">
                </el-avatar>
              </el-upload>
              <span class="name">
                {{ userStore.user.username }}
              </span>
            </div>
            <div class="tags">
              <div v-if="userStore.user.role === 'farmer'" class="tag">
                <img src="../assets/gfrz.svg" alt="">
                <span>官方认证农户</span>
              </div>
            </div>
          </div>
          <div class="bottom">
            <el-card>
              <!-- <div class="item">
                <span class="title">用户名</span>
                <el-input class="value" disabled></el-input>
                <img src="../assets/edit.svg" alt="">
              </div>
              <div class="item">
                <span class="title">实名信息</span>
                <el-input class="value"></el-input>
                <img src="../assets/edit.svg" alt="">
              </div> -->
              <div class="item">
                <span class="title">邮箱</span>
                <el-input class="value" :disabled="!isEmailEdit" v-model="newEmail"></el-input>
                <img v-show="!isEmailEdit" @click="handleEditEmail" src="../assets/edit.svg" alt="">
                <el-button v-show="isEmailEdit" @click="handleEditEmail" type="warning" class="btn" size="small">确认修改</el-button>
              </div>
              <div class="item">
                <span class="title">地址</span>
                <el-input class="value" :disabled="!isAddressEdit" v-model="newAddress"></el-input>
                <img v-show="!isAddressEdit" @click="handleEditAddress" src="../assets/edit.svg" alt="">
                <el-button v-show="isAddressEdit" @click="handleEditAddress" type="warning" class="btn" size="small">确认修改</el-button>
              </div>
            </el-card>
          </div>
        </div>
        <el-empty v-if="!authStore.token"  description="您还未登录！" />
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
  import NavBar from '@/components/NavBar.vue'
  import { ref } from 'vue'
  import { useUserStore } from '@/stores/user'
  import { useAuthStore } from '@/stores/auth'
  import { SetEmail, SetAddress, ChangeAvatar } from '@/api/user'
  import type { UploadProps } from 'element-plus'
  // pinia
  const userStore = useUserStore()
  const authStore = useAuthStore()
  // 数据
  const isEmailEdit = ref(false)
  const isAddressEdit = ref(false)
  const newEmail = ref(userStore.user.email)
  const newAddress = ref(userStore.user.address)
  // 函数
  const handleAvatarSuccess: UploadProps['onSuccess'] = (response) => {
    ChangeAvatar(response.data)
    .then(
      data => {
        if (data.success) {
          userStore.updataAvatar(response.data)
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
  const handleAvatarError: UploadProps['onError'] = (err) => {
    ElMessage({
      message: '图片上传失败，请重试!',
      type: 'error',
      grouping: true
    })
  }
  function handleEditEmail() {
    if (isEmailEdit.value) {
      SetEmail(newEmail.value as string)
      .then(
        data => {
          console.log(data)
          if (data.success) {
            userStore.updateEmail(newEmail.value)
            ElMessage({
              message: data.msg,
              type: 'success',
              grouping: true
            })
          } else {
            newEmail.value = userStore.user.email
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
    isEmailEdit.value = !isEmailEdit.value
  }
  function handleEditAddress() {
    if (isAddressEdit.value) {
      SetAddress(newAddress.value as string)
      .then(
        data => {
          console.log(data)
          if (data.success) {
            userStore.updateAddress(newAddress.value)
            ElMessage({
              message: data.msg,
              type: 'success',
              grouping: true
            })
          } else {
            newAddress.value = userStore.user.address
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
    isAddressEdit.value = !isAddressEdit.value
  }
</script>

<style scoped lang="scss">
  .el-container {
    height: 100vh;
    .el-header,
    .el-main {
      padding: 0;
      .wrapper {
        width: 1409px;
        height: 100%;
        margin: 0 auto;
        display: flex;
        flex-direction: column;
        .top {
          height: 320px;
          background-image: url('../assets/meBkg.png');
          background-size: cover; 
          background-position: center; 
          background-repeat: no-repeat; 
          box-sizing: border-box;
          padding: 40px 100px;
          display: flex;
          flex-direction: column;
          justify-content: space-around;
          .about-me {
            display: flex;
            align-items: end;
            .el-avatar {
              width: 163px;
              height: 163px;
              position: relative;
              &:hover::after {
                content: url('../assets/editAvatar.svg');
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(255, 248, 223, 0.37);
                backdrop-filter: blur(4px);
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                cursor: pointer;
              }
            }
            .name {
              font-weight: 700;
              font-size: 32px;
              margin-left: 30px;
            }
          }
          .tags {
            .tag {
              width: 177px;
              background: #CFB454;
              border-radius: 8px;
              font-weight: 400;
              font-size: 20px;
              color: #FFFFFF;
              display: flex;
              justify-content: space-evenly;
              align-items: center;
              padding: 5px;
            }
          }
        }
      }
      .bottom {
        flex-grow: 1;
        background: linear-gradient(180deg, rgba(255, 247, 206, 0.198) 0%, rgba(255, 255, 255, 0.55) 100%);
        backdrop-filter: blur(14.6px);
        display: flex;
        align-items: center;
        .el-card {
          width: 90%;
          margin: 0 auto;
          border-radius: 40px;
          padding: 60px;
          box-sizing: border-box;
        }
        .item {
          display: flex;
          align-items: flex-end;
          border-bottom: 2px solid rgba(120, 120, 120, 0.43);
          padding: 20px;
          .title {
            width: 100px;
            font-weight: 400;
            font-size: 24px;
            flex-grow: 0.2;
          }
          .value {
            font-weight: 290;
            font-size: 20px;
            color: rgba(0, 0, 0, 0.5);
            flex-grow: 0.8;
          }
          .el-input {
            --el-input-width: 0;
            display: inline;
            :deep(.el-input__wrapper) {
              box-shadow: none;
              width: 50%;
            }
          }
          .el-input.is-disabled {
            cursor: auto;
          }
          img {
            cursor: pointer;
          }
        }
      }
    }
  }
</style>