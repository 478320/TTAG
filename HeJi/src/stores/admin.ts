import { defineStore } from 'pinia'
import { ref } from 'vue'

interface checkFarmer { 
  id: string
  username: string
  email: string | null
  fullName: string | null
  address: string | null
  picture: string
  role: string
}

interface checkProduct { 
  email: string
  username: string
  picture: string
  products: {
    id: string
    name: string
    price: number
    stock: number
    description: string
    farmerId: string
    image: string
    shipAddress: string
    service: string
    score: number
    status: string
  }
}

export const useAdminStore = defineStore('admin', () => {
  // 数据
  const checkFarmers = ref<checkFarmer[]>([])
  const checkProducts = ref<checkProduct[]>([])
  // 函数
  
  // 设置申请农户
  function setCheckFarmers(val: checkFarmer[]) {
    checkFarmers.value = val
  }
  // 新添农户
  function pushCheckFarmers(val: checkFarmer[]) {
    val.forEach(f => {
      checkFarmers.value.push(f)
    })
  }
  // 删除农户
  function deleteCheckFarmer(id: string) {
    checkFarmers.value = checkFarmers.value.filter(f => f.id !== id)
  }
  // 清空数据
  function clearCheckFarmer() {
    checkFarmers.value = []
  }

  // 设置申请商品
  function setCheckProducts(val: checkProduct[]) {
    checkProducts.value = val
  }
  // 添加申请商品
  function pushCheckProducts(val: checkProduct[]) {
    val.forEach(p => {
      checkProducts.value.push(p)
    })
  }
  // 删除商品
  function deleteCheckProduct(id: string) {
    checkProducts.value = checkProducts.value.filter(p => p.products.id !== id)
  }
  // 清空数据
  function clearCheckProduct() {
    checkProducts.value = []
  }


  return {
    checkFarmers,
    setCheckFarmers,
    pushCheckFarmers,
    deleteCheckFarmer,
    clearCheckFarmer,
    checkProducts,
    setCheckProducts,
    pushCheckProducts,
    deleteCheckProduct,
    clearCheckProduct
  }
}, {
  persist: true
})