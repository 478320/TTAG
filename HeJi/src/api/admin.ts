import { service } from '@/utils/request'
import type { ResType } from './types'

// 获取用户的农户的申请 
interface RequestType {
  data: { 
    id: string
    username: string
    email: string | null
    fullName: string | null
    address: string | null
    picture: string
    role: string
  }[] | null;
  errorMsg: null | string;
  msg: null | string;
  success: boolean;
  total: number | null;
}
export function GetRequest(current: number): Promise<RequestType> {
  return service.request({
    method: 'get',
    url: '/user/admin/request',
    params: {
      current
    }
  })
}

// 允许成为农户
export function PermitFarmer(userId: string): Promise<ResType> {
  return service.request({
    method: 'put',
    url: `/user/admin/permission/${userId}`
  })
}

// 拒绝成为农户
export function RejectFarmer(userId: string): Promise<ResType> {
  return service.request({
    method: 'put',
    url: `/user/admin/reject/${userId}`
  })
}

// 查询产品上架申请
interface ProductRequestType {
  data: { 
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
  }[] | null;
  errorMsg: null | string;
  msg: null | string;
  success: boolean;
  total: number | null;
}
export function GetProductRequest(current: number): Promise<ProductRequestType> {
  return service.request({
    method: 'get',
    url: '/products/admin/request',
    params: {
      current
    }
  })
}

// 允许产品上线
export function PermitProduct(id: string): Promise<ResType> {
  return service.request({
    method: 'put',
    url: `/products/admin/permission/${id}`
  })
}

// 拒绝产品上线
export function RejectProduct(id: string): Promise<ResType> {
  return service.request({
    method: 'put',
    url: `/products/admin/reject/${id}`
  })
}