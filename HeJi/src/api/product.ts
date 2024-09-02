import { service } from '@/utils/request'
import type { ResType } from './types'

// 查看主页推荐产品
export function ProductsList(current: number): Promise<ResType> {
  return service.request({
    method: 'get',
    url: '/products/list',
    params: {
      current
    }
  })
}

// 搜索查询多个产品
export function SearchProduct(context: string, current: number): Promise<ResType> {
  return service.request({
    method: 'get',
    url: '/products/vague',
    params: {
      context,
      current
    }
  })
}

// 查询货物具体信息
export function ProductDetail(id: string): Promise<ResType> {
  return service.request({
    method: 'get',
    url: `/products/detail/${id}`
  })
}

// 查询货物评论
export function GetComments(productId: string, current: number): Promise<ResType> {
  return service.request({
    method: 'get',
    url: `/review/product/paging/${productId}`,
    params: {
      current
    }
  })
}

// 评论商品
interface ReviewType {
  orderId: string,
  rating: number,
  comment: string
}
export function ReviewProduct(data: ReviewType): Promise<ResType> {
  return service.request({
    method: 'post',
    url: '/review/add/product',
    data
  })
}

// 农户

// 查询我的产品
export function ProductsMe(current: number): Promise<ResType> {
  return service.request({
    method: 'get',
    url: '/products/me',
    params: {
      current
    }
  })
}

// 上架货物
interface ProductType {
  name: string,
  price: number,
  stock: number,
  description: string,
  image: string,
  shipAddress: string,
  service: string
}
export function PostProduct(data: ProductType): Promise<ResType> {
  return service.request({
    method: 'post',
    url: '/products',
    data
  })
}

// 更改货物库存
export function ChangeStock(id: string, num: number): Promise<ResType> {
  return service.request({
    method: 'put',
    url: `/products/stock/${id}`,
    params: {
      num
    }
  })
}

// 下架货物
export function DeleteProduct(id: string): Promise<ResType> {
  return service.request({
    method: 'delete',
    url: `/products/${id}`
  })
}
