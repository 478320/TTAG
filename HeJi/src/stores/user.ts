import { defineStore } from 'pinia'
import { ref } from 'vue'

// 定义类型
interface UserType {
  id?: string,
  username?: string,
  fullName?: string,
  address?: string | null,
  role?: "user" | "admin" | "farmer" | "request" ,
  picture?: string | null,
  email?: string | null,
}

export const useUserStore = defineStore('user', () => {
  // 数据
  const user = ref<UserType>({})
  // 函数
  // 设置user
  function setUser(data: UserType) {
    user.value = data
  }
  // 清空user
  function clearUser() {
    user.value = {}
  }
  // 更改邮箱
  function updateEmail(email: string) {
    user.value.email = email
  }
  // 更改地址
  function updateAddress(address: string) {
    user.value.address = address
  }
  // 更改头像
  function updataAvatar(url: string) {
    user.value.picture = url
  }

  return {
    user,
    setUser,
    clearUser,
    updateEmail,
    updateAddress,
    updataAvatar
  }
}, {
  persist: true
})