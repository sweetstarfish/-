import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import BillManagement from '../views/BillManagement.vue'
import ProviderManagement from '../views/ProviderManagement.vue'
import UserManagement from '../views/UserManagement.vue'
import ProfileView from '../views/ProfileView.vue'
import LoginView from '../views/LoginView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: LoginView,
      meta: { requiresAuth: false }
    },
    {
      path: '/',
      name: 'home',
      component: HomeView,
      meta: { requiresAuth: true }
    },
    {
      path: '/bill',
      name: 'bill',
      component: BillManagement,
      meta: { requiresAuth: true }
    },
    {
      path: '/provider',
      name: 'provider',
      component: ProviderManagement,
      meta: { requiresAuth: true }
    },
    {
      path: '/user',
      name: 'user',
      component: UserManagement,
      meta: { requiresAuth: true, requiresAdmin: true }
    },
    {
      path: '/profile',
      name: 'profile',
      component: ProfileView,
      meta: { requiresAuth: true }
    }
  ]
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const requiresAuth = to.meta.requiresAuth !== false
  const requiresAdmin = to.meta.requiresAdmin === true

  if (requiresAuth && !token) {
    // 需要登录但没有token，跳转到登录页
    next('/login')
  } else if (to.path === '/login' && token) {
    // 已登录用户访问登录页，跳转到首页
    next('/')
  } else if (requiresAdmin) {
    // 检查是否为管理员
    const userStr = localStorage.getItem('user')
    if (userStr) {
      const user = JSON.parse(userStr)
      if (user.userCode === 'admin') {
        next()
      } else {
        // 非管理员用户，跳转到首页
        next('/')
      }
    } else {
      next('/')
    }
  } else {
    next()
  }
})

export default router
