import { service } from '@/utils/request'
import type { ResType } from './types'

// 查询订单物流信息

// 新增预订单
export function CreateOrder(id: string, num: number): Promise<ResType> {
  return service.request({
    method: 'post',
    url: `/order/${id}`,
    params: {
      num
    }
  })
}

// 查看所有我的待支付订单
interface PendingOrderType {
  data: {
    id: string,
    totalPrice: number,
    status: "pending",
    num: number,
    name: string,
    image: string,
    createAt: string,
    itemId: string
  }[] | null;
  errorMsg: null | string;
  msg: null | string;
  success: boolean;
  total: number | null;
}
export function GetPendingOrders(current: number): Promise<PendingOrderType> {
  return service.request({
    method: 'get',
    url: '/order/me/pending',
    params: {
      current
    }
  })
}

// 支付预订单
export function PayOrder(id: string, pw: string): Promise<ResType> {
  return service.request({
    method: 'put',
    url: `/order/${id}`,
    params: {
      pw
    }
  })
}

// 查看所有我的其他订单
interface OtherOrderType {
  data: {
    id: string,
    totalPrice: number,
    status: "completed" | "paid" | "cancel",
    num: number,
    price: number,
    name: string,
    image: string,
    creatAt: string,
    itemId: string
  }[] | null;
  errorMsg: null | string;
  msg: null | string;
  success: boolean;
  total: number | null;
}
export function GetOtherOrders(current: number): Promise<OtherOrderType> {
  return service.request({
    method: 'get',
    url: '/order/me/other',
    params: {
      current
    }
  })
}

// 查询订单物流信息
export function GetLogistics(id: string): Promise<ResType> {
  return service.request({
    method: 'get',
    url: `/logistics/${id}`
  })
}

// 查询某个订单的具体信息
export function OrderDetail(id: string): Promise<ResType> {
  return service.request({
    method: 'get',
    url: `/order/${id}`
  })
}

// 删除订单
export function DeleteOrder(id: string): Promise<ResType> {
  return service.request({
    method: 'delete',
    url: `/order/${id}`
  })
}
