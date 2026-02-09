<script setup>
import { computed, ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  ShoppingCart,
  Shop,
  Document,
  User,
  Plus,
  List,
  DataBoard,
  ArrowDown,
  Lock,
  SwitchButton
} from '@element-plus/icons-vue'
import { authApi } from '@/api/auth.js'

const route = useRoute()
const router = useRouter()

// 用户状态
const currentUser = ref(null)

// 计算当前激活的菜单项
const activeMenu = computed(() => {
  const path = route.path
  if (path === '/') return '/'
  if (path.startsWith('/provider')) return '/provider'
  if (path.startsWith('/bill')) return '/bill'
  if (path.startsWith('/user')) return '/user'
  return path
})

// 判断是否为管理员
const isAdmin = computed(() => {
  return currentUser.value?.userCode === 'admin'
})

// 判断是否为经理
const isManager = computed(() => {
  return currentUser.value?.userRole === 2
})

// 判断是否为普通员工
const isEmployee = computed(() => {
  return currentUser.value?.userRole === 3
})

// 判断是否可以访问供应商管理
const canAccessProvider = computed(() => {
  return isAdmin.value || isManager.value || isEmployee.value
})

// 判断是否可以访问账单管理
const canAccessBill = computed(() => {
  return isAdmin.value || isManager.value || isEmployee.value
})

// 判断是否可以访问用户管理
const canAccessUser = computed(() => {
  return isAdmin.value
})

// 加载用户信息
const loadUserInfo = () => {
  const userStr = localStorage.getItem('user')
  if (userStr) {
    try {
      currentUser.value = JSON.parse(userStr)
    } catch (error) {
      console.error('解析用户信息失败:', error)
      currentUser.value = null
    }
  }
}

// 退出登录
const handleLogout = async () => {
  try {
    await ElMessageBox.confirm(
      '确定要退出登录吗？',
      '确认退出',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    try {
      await authApi.logout()
    } catch (error) {
      console.error('退出登录API调用失败:', error)
    }

    // 清除本地存储
    localStorage.removeItem('token')
    localStorage.removeItem('user')

    ElMessage.success('退出成功')

    // 跳转到登录页
    router.push('/login')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('退出失败')
    }
  }
}

// 修改密码
const handleChangePassword = () => {
  router.push('/profile')
}

// 个人信息
const handleProfile = () => {
  router.push('/profile')
}

// 处理下拉菜单命令
const handleCommand = (command) => {
  switch (command) {
    case 'logout':
      handleLogout()
      break
    case 'password':
      handleChangePassword()
      break
    case 'profile':
      handleProfile()
      break
  }
}

// 获取用户头像
const getUserAvatar = () => {
  if (currentUser.value?.headUrl) {
    return currentUser.value.headUrl
  }
  
  // 根据用户角色提供不同的默认头像
  const userCode = currentUser.value?.userCode
  const userRole = currentUser.value?.userRole
  
  if (userCode === 'admin') {
    return 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
  }
  
  // 根据角色提供不同的头像
  switch (userRole) {
    case 1:
      return 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' // 管理员头像
    case 2:
      return 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9cpng.png' // 经理头像
    case 3:
      return 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png' // 员工头像
    default:
      return 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png' // 默认头像
  }
}

// 获取用户角色
const getUserRole = () => {
  if (!currentUser.value) return '未登录'
  
  const userCode = currentUser.value.userCode
  const userRole = currentUser.value.userRole
  
  if (userCode === 'admin') {
    return '系统管理员'
  }
  
  // 根据userRole字段判断角色
  switch (userRole) {
    case 1:
      return '系统管理员'
    case 2:
      return '经理'
    case 3:
      return '普通员工'
    default:
      return '普通用户'
  }
}

// 获取用户显示名称
const getUserDisplayName = () => {
  if (!currentUser.value) return '用户'
  
  const userName = currentUser.value.userName
  const userCode = currentUser.value.userCode
  
  // 如果是admin用户，显示"系统管理员"
  if (userCode === 'admin') {
    return '系统管理员'
  }
  
  return userName || '用户'
}

// 获取用户权限描述
const getUserPermissions = () => {
  if (!currentUser.value) return ''
  
  const permissions = []
  
  if (canAccessProvider.value) {
    permissions.push('供应商管理')
  }
  if (canAccessBill.value) {
    permissions.push('账单管理')
  }
  if (canAccessUser.value) {
    permissions.push('用户管理')
  }
  
  return permissions.length > 0 ? permissions.join('、') : '仅查看'
}

onMounted(() => {
  loadUserInfo()
})

// 监听路由变化，重新加载用户信息
watch(() => route.path, () => {
  loadUserInfo()
})
</script>

<template>
  <div id="app">
    <!-- 登录页面不显示导航栏和侧边栏 -->
    <router-view v-if="route.path === '/login'" />
    
    <!-- 其他页面显示完整布局 -->
    <el-container v-else class="app-container">
      <!-- 顶部导航栏 -->
      <el-header class="app-header">
        <div class="header-left">
          <div class="logo-container">
            <ShoppingCart class="header-logo" />
            <div class="logo-glow"></div>
          </div>
          <h1 class="header-title">超市管理系统</h1>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-dropdown">
              <div class="user-avatar-container">
                <el-avatar
                  :size="40"
                  :src="getUserAvatar()"
                  class="user-avatar"
                >
                  <el-icon><User /></el-icon>
                </el-avatar>
                <div class="user-info">
                  <div class="user-name">{{ getUserDisplayName() }}</div>
                  <div class="user-role">{{ getUserRole() }}</div>
                </div>
                <el-icon class="dropdown-arrow"><ArrowDown /></el-icon>
              </div>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">
                  <el-icon><User /></el-icon>
                  个人信息
                </el-dropdown-item>
                <el-dropdown-item command="password">
                  <el-icon><Lock /></el-icon>
                  修改密码
                </el-dropdown-item>
                <el-dropdown-item divided command="logout">
                  <el-icon><SwitchButton /></el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-container>
        <!-- 侧边栏 -->
        <el-aside class="app-sidebar" width="240px">
          <el-menu
            :default-active="activeMenu"
            class="sidebar-menu"
            background-color="transparent"
            text-color="rgba(255, 255, 255, 0.7)"
            active-text-color="#ffffff"
            router
          >
            <!-- 首页 -->
            <el-menu-item index="/">
              <el-icon><DataBoard /></el-icon>
              <span>首页</span>
            </el-menu-item>

            <!-- 供应商管理 -->
            <el-sub-menu index="provider" v-if="canAccessProvider">
              <template #title>
                <el-icon><Shop /></el-icon>
                <span>供应商管理</span>
              </template>
              <el-menu-item index="/provider">
                <el-icon><List /></el-icon>
                <span>供应商列表</span>
              </el-menu-item>
            </el-sub-menu>

            <!-- 账单管理 -->
            <el-sub-menu index="bill" v-if="canAccessBill">
              <template #title>
                <el-icon><Document /></el-icon>
                <span>账单管理</span>
              </template>
              <el-menu-item index="/bill">
                <el-icon><List /></el-icon>
                <span>账单列表</span>
              </el-menu-item>
            </el-sub-menu>

            <!-- 用户管理 -->
            <el-sub-menu index="user" v-if="canAccessUser">
              <template #title>
                <el-icon><User /></el-icon>
                <span>用户管理</span>
              </template>
              <el-menu-item index="/user">
                <el-icon><List /></el-icon>
                <span>用户列表</span>
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-aside>

        <!-- 主内容区域 -->
        <el-main class="app-main">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style>
/* 全局样式重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 移除默认的focus样式 */
*:focus {
  outline: none !important;
}

.el-avatar:focus {
  outline: none !important;
}

.el-dropdown:focus {
  outline: none !important;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', 'Helvetica Neue', Helvetica, Arial, sans-serif;
  background-color: #f8f9fa;
  color: #333;
  line-height: 1.6;
}

#app {
  height: 100vh;
}

/* 应用容器 */
.app-container {
  height: 100vh;
}

/* 顶部导航栏 */
.app-header {
  background: linear-gradient(135deg, #4a90e2 0%, #357abd 100%);
  color: #ffffff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  box-shadow: 0 2px 8px rgba(74, 144, 226, 0.2);
  border-bottom: 1px solid #4a90e2;
  height: 64px;
  min-height: 64px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-shrink: 0;
}

.logo-container {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: linear-gradient(135deg, #ffffff 0%, #f8fbff 100%);
  box-shadow: 0 4px 12px rgba(255, 255, 255, 0.2);
  transition: all 0.3s ease;
  animation: logoPulse 2s ease-in-out infinite;
}

@keyframes logoPulse {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
}

.logo-container:hover {
  transform: translateY(-2px) scale(1.1);
  box-shadow: 0 6px 16px rgba(255, 255, 255, 0.3);
  animation: none;
}

.header-logo {
  font-size: 20px;
  color: #4a90e2;
  z-index: 2;
  position: relative;
}

.logo-glow {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(255, 255, 255, 0.2) 0%, transparent 70%);
  z-index: 1;
}

.header-title {
  font-size: 18px;
  font-weight: 600;
  color: #ffffff;
  margin: 0;
  white-space: nowrap;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-dropdown {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 8px 16px;
  border-radius: 12px;
  transition: all 0.3s ease;
  background: transparent;
  border: none;
}

.user-dropdown:hover {
  background: transparent;
  transform: translateY(-1px);
}

.user-avatar-container {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar {
  border: none;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease;
  outline: none !important;
}

.user-avatar:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.25);
  outline: none !important;
}

.user-avatar:focus {
  outline: none !important;
}

.user-info {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.user-name {
  font-size: 14px;
  font-weight: 600;
  color: #ffffff;
  line-height: 1.2;
  text-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
}

.user-role {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.95);
  line-height: 1.2;
  text-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
}

.dropdown-arrow {
  color: rgba(255, 255, 255, 0.9);
  font-size: 12px;
  transition: transform 0.3s ease;
}

.user-dropdown:hover .dropdown-arrow {
  transform: rotate(180deg);
}

/* 侧边栏 */
.app-sidebar {
  background: linear-gradient(180deg, #4a90e2 0%, #357abd 100%);
  box-shadow: 2px 0 8px rgba(74, 144, 226, 0.2);
  border-right: 1px solid #4a90e2;
}

.sidebar-menu {
  border: none;
  height: 100%;
  background: transparent !important;
}

.sidebar-menu .el-menu-item,
.sidebar-menu .el-sub-menu__title {
  height: 48px;
  line-height: 48px;
  font-size: 14px;
  border-radius: 0;
  color: rgba(255, 255, 255, 0.7) !important;
  background: transparent !important;
}

.sidebar-menu .el-menu-item:hover,
.sidebar-menu .el-sub-menu__title:hover {
  background-color: rgba(255, 255, 255, 0.08) !important;
  color: #ffffff !important;
}

.sidebar-menu .el-menu-item.is-active {
  background-color: rgba(255, 255, 255, 0.15) !important;
  color: #ffffff !important;
  border-right: 3px solid #ffffff;
}

.sidebar-menu .el-icon {
  margin-right: 12px;
  font-size: 16px;
  color: rgba(255, 255, 255, 0.7);
}

.sidebar-menu .el-menu-item:hover .el-icon,
.sidebar-menu .el-sub-menu__title:hover .el-icon,
.sidebar-menu .el-menu-item.is-active .el-icon {
  color: #ffffff !important;
}

/* 主内容区域 */
.app-main {
  background-color: #f8f9fa;
  padding: 24px;
  overflow-y: auto;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .app-sidebar {
    width: 200px !important;
  }

  .header-title {
    font-size: 16px;
  }

  .app-main {
    padding: 16px;
  }

  .app-header {
    padding: 0 16px;
  }

  .user-avatar-container {
    gap: 8px;
  }

  .user-info {
    display: none; /* 在移动端隐藏用户信息，只显示头像 */
  }

  .user-dropdown {
    padding: 6px 12px;
  }

  .user-avatar {
    width: 32px !important;
    height: 32px !important;
  }
}
</style>
