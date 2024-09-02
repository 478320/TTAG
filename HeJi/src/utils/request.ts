// 导入 axios
import axios from 'axios'

// 创建服务对象
export const service = axios.create({
  baseURL: 'http://47.121.128.102:8081'
})

// 设置请求拦截器
service.interceptors.request.use(
  config => {
    const token = JSON.parse(localStorage.getItem('auth') || '{}').token
    if (token) {
      config.headers['Authorization'] = token
    }
    return config
  },
  err => Promise.reject(err)
)

// 设置响应拦截器
service.interceptors.response.use(
  response => {
    return response.data
  },
  err => Promise.reject(err)
)