import { defineStore } from 'pinia'
import { ref } from 'vue'

interface OrderType {
  id: string,
  totalPrice: number,
  status: "pending" | "completed" | "paid" | "cancel",
  num: number,
  price: number,
  name: string,
  image: string,
  createAt: string,
  itemId: string
}

export const useOrderStore = defineStore('order', () => {
  // 数据
  const pendingOrders = ref<OrderType[]>([])
  const otherOrders = ref<OrderType[]>([])
  // 函数
  // 设置预支付订单
  function setPendingOrders(val: OrderType[]) {
    pendingOrders.value = val
  }
  // 添入新的预支付订单
  function pushPendingOrders(val: OrderType[]) {
    val.forEach(po => {
      pendingOrders.value.push(po)
    })
  }
  // 删除预支付订单
  function deletePendingOrder(id: string) {
   pendingOrders.value = pendingOrders.value.filter(po => po.id !== id)
  }
  // 清空预支付订单
  function clearPendingOrders() {
    pendingOrders.value = []
  }
  // 设置其他订单
  function setOtherOrders(val: OrderType[]) {
    otherOrders.value = val
  }
  // 添入新的其他订单
  function pushOtherOrders(val: OrderType[]) {
    val.forEach(oo => {
      otherOrders.value.push(oo)
    })
  }
  // 删除其他订单
  function deleteOtherOrder(id: string) {
    otherOrders.value = otherOrders.value.filter(oo => oo.id !== id)
  }
  // 清空其他订单
  function clearOtherOrders() {
    otherOrders.value = []
  }

  return {
    pendingOrders,
    setPendingOrders,
    pushPendingOrders,
    deletePendingOrder,
    clearPendingOrders,
    otherOrders,
    setOtherOrders,
    pushOtherOrders,
    deleteOtherOrder,
    clearOtherOrders
  }

}, {
  persist: true
})