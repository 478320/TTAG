import { service } from '@/utils/request'
import type { ResType } from './types'


// 注册
interface RegData {
  username: string;
  password: string;
  phoneNumber: string;
}
export function Register(data: RegData): Promise<ResType> {
  return service.request({
    method: 'post',
    url: '/user/register',
    data
  })
}

// 登录
interface LoginData {
  username: string;
  password: string;
}
export function Login(data: LoginData): Promise<ResType> {
  return service.request({
    method: 'post',
    url: '/user/login',
    data
  })
}

// 获取我的信息
interface UserType {
  data: {
    id: string,
    username: string,
    fullName: string,
    address: string | null,
    role: "user" | "admin" ,
    picture: string | null,
    email: string | null,
  } | null;
  errorMsg: null | string;
  msg: null | string;
  success: boolean;
  total: number | null;
}
export function GetMe(): Promise<UserType> {
  return service.request({
    method: 'get',
    url: '/user/me'
  })
}

// 发起农户申请
interface EmailData {
  email: string
}
export function BeFarmer(data: EmailData): Promise<ResType> {
  return service.request({
    method: 'put',
    url: '/user/be/farmer',
    data
  })
}

// 登出
export function Logout(): Promise<ResType> {
  return service.request({
    method: 'delete',
    url: '/user/logout'
  })
}

// 用户设置邮箱
export function SetEmail(email: string): Promise<ResType> {
  return service.request({
    method: 'put',
    url: '/user/email',
    data: {
      email
    }
  })
}

// 用户设置收货地址
export function SetAddress(address: string): Promise<ResType> {
  return service.request({
    method: 'put',
    url: '/user/address',
    data: {
      address
    }
  })
}

// 更改头像
export function ChangeAvatar(picture: string): Promise<ResType> {
  return service.request({
    method: 'put',
    url: '/user/head',
    data: {
      picture
    }
  })
}