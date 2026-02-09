import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:4399',
  timeout: 10000
})

// 请求拦截器
api.interceptors.request.use(
  config => {
    // 添加token到请求头
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
api.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    // 处理401未授权错误
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      window.location.href = '/login'
    }
    console.error('API Error:', error)
    return Promise.reject(error)
  }
)

export const authApi = {
  // 用户登录
  login(data) {
    return api.post('/auth/login', data)
  },

  // 用户退出
  logout() {
    return api.post('/auth/logout')
  },

  // 获取用户信息
  getProfile() {
    return api.get('/auth/profile')
  },

  // 验证token
  verifyToken() {
    return api.get('/auth/verify')
  }
}

export const userApi = {
  // 修改用户资料
  updateProfile(data) {
    return api.put('/user/profile', data)
  },

  // 修改密码
  updatePassword(data) {
    return api.put('/user/password', data)
  },

  // 获取用户信息
  getUserById(id) {
    return api.get(`/user/${id}`)
  }
} 