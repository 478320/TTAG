import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAuthStore = defineStore('auth', () => {
  // 数据
  const token = ref('')
  //  函数
  // 设置token
  function setToken(val: string) {
    token.value = val
  }
  // 删除token
  function clearToken() {
    token.value = ''
  }


  return {
    token,
    setToken,
    clearToken
  }
}, {
  persist: true
})