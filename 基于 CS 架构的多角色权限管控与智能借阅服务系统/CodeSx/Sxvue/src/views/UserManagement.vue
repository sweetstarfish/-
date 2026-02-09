<template>
  <div class="user-management">
    <div class="page-header">
      <h2>用户管理</h2>
      <el-button type="primary" @click="showAddDialog = true">
        <Plus />
        添加用户
      </el-button>
    </div>

    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="搜索关键词">
          <el-input
            v-model="searchForm.keyword"
            placeholder="请输入用户编码、名称、手机号或地址"
            clearable
            @keyup.enter="handleSearch"
            style="width: 300px"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 用户列表 -->
    <el-card class="user-list-card">
      <template #header>
        <div class="card-header">
          <span>用户列表</span>
          <span class="total-count">共 {{ pagination.total }} 条记录</span>
        </div>
      </template>

      <el-table :data="userList" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="userCode" label="用户编码" width="120" />
        <el-table-column prop="userName" label="用户名称" width="120" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="address" label="地址" />
        <el-table-column prop="gender" label="性别" width="80">
          <template #default="scope">
            {{ scope.row.gender === 1 ? '女' : '男' }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '启用' : '冻结' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="userRole" label="角色" width="100">
          <template #default="scope">
            {{ getRoleName(scope.row.userRole) }}
          </template>
        </el-table-column>
        <el-table-column prop="creationDate" label="创建时间" width="160">
          <template #default="scope">
            {{ formatDate(scope.row.creationDate) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button
              v-if="scope.row.status === 1"
              type="warning"
              size="small"
              @click="handleFreeze(scope.row)"
            >
              冻结
            </el-button>
            <el-button
              v-else
              type="success"
              size="small"
              @click="handleEnable(scope.row)"
            >
              启用
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页组件 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="pagination.pageNum"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 添加用户对话框 -->
    <el-dialog
      v-model="showAddDialog"
      title="添加用户"
      width="500px"
      :close-on-click-modal="false"
    >
      <el-form
        ref="addFormRef"
        :model="addForm"
        :rules="addFormRules"
        label-width="100px"
      >
        <el-form-item label="用户编码" prop="userCode">
          <el-input v-model="addForm.userCode" placeholder="请输入用户编码" />
        </el-form-item>
        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="addForm.userName" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="密码" prop="userPassword">
          <el-input
            v-model="addForm.userPassword"
            type="password"
            placeholder="请输入密码"
            show-password
          />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="addForm.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="addForm.address" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="addForm.gender">
            <el-radio :value="1">男</el-radio>
            <el-radio :value="2">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="角色" prop="userRole">
          <el-select v-model="addForm.userRole" placeholder="请选择角色">
            <el-option
              v-for="role in roleList"
              :key="role.id"
              :label="role.roleName"
              :value="role.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAddDialog = false">取消</el-button>
          <el-button type="primary" @click="handleAddUser" :loading="addLoading">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { getUserListByPage, addUser, updateUserStatus, getRoleList } from '@/api/user.js'

const loading = ref(false)
const addLoading = ref(false)
const showAddDialog = ref(false)
const userList = ref([])
const roleList = ref([])

// 分页参数
const pagination = reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0
})

// 搜索表单
const searchForm = reactive({
  keyword: ''
})

const addFormRef = ref()
const addForm = reactive({
  userCode: '',
  userName: '',
  userPassword: '',
  phone: '',
  address: '',
  gender: 1,
  userRole: null
})

const addFormRules = {
  userCode: [
    { required: true, message: '请输入用户编码', trigger: 'blur' },
    { min: 3, max: 15, message: '用户编码长度在3到15个字符', trigger: 'blur' }
  ],
  userName: [
    { required: true, message: '请输入用户名称', trigger: 'blur' },
    { min: 2, max: 15, message: '用户名称长度在2到15个字符', trigger: 'blur' }
  ],
  userPassword: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  address: [
    { required: true, message: '请输入地址', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  userRole: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ]
}

// 获取用户列表
const fetchUserList = async () => {
  loading.value = true
  try {
    const params = {
      pageNum: pagination.pageNum,
      pageSize: pagination.pageSize,
      keyword: searchForm.keyword || undefined
    }
    
    const response = await getUserListByPage(params)
    if (response.success) {
      userList.value = response.data.records
      pagination.total = response.data.total
    } else {
      ElMessage.error(response.message || '获取用户列表失败')
    }
  } catch (error) {
    console.error('获取用户列表失败:', error)
    ElMessage.error('获取用户列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  pagination.pageNum = 1
  fetchUserList()
}

// 重置搜索
const handleReset = () => {
  searchForm.keyword = ''
  pagination.pageNum = 1
  fetchUserList()
}

// 分页大小改变
const handleSizeChange = (size) => {
  pagination.pageSize = size
  pagination.pageNum = 1
  fetchUserList()
}

// 当前页改变
const handleCurrentChange = (page) => {
  pagination.pageNum = page
  fetchUserList()
}

// 获取角色列表
const fetchRoleList = async () => {
  try {
    const response = await getRoleList()
    if (response.success) {
      roleList.value = response.data
    }
  } catch (error) {
    console.error('获取角色列表失败:', error)
  }
}

// 添加用户
const handleAddUser = async () => {
  if (!addFormRef.value) return

  await addFormRef.value.validate(async (valid) => {
    if (valid) {
      addLoading.value = true
      try {
        const response = await addUser(addForm)
        if (response.success) {
          ElMessage.success('用户添加成功')
          showAddDialog.value = false
          resetAddForm()
          fetchUserList()
        } else {
          ElMessage.error(response.message || '用户添加失败')
        }
      } catch (error) {
        console.error('添加用户失败:', error)
        ElMessage.error('添加用户失败')
      } finally {
        addLoading.value = false
      }
    }
  })
}

// 冻结用户
const handleFreeze = async (user) => {
  try {
    await ElMessageBox.confirm(
      `确定要冻结用户"${user.userName}"吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await updateUserStatus(user.id, 2)
    if (response.success) {
      ElMessage.success('用户冻结成功')
      fetchUserList()
    } else {
      ElMessage.error(response.message || '用户冻结失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('冻结用户失败:', error)
      ElMessage.error('冻结用户失败')
    }
  }
}

// 启用用户
const handleEnable = async (user) => {
  try {
    await ElMessageBox.confirm(
      `确定要启用用户"${user.userName}"吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await updateUserStatus(user.id, 1)
    if (response.success) {
      ElMessage.success('用户启用成功')
      fetchUserList()
    } else {
      ElMessage.error(response.message || '用户启用失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('启用用户失败:', error)
      ElMessage.error('启用用户失败')
    }
  }
}

// 重置添加表单
const resetAddForm = () => {
  addForm.userCode = ''
  addForm.userName = ''
  addForm.userPassword = ''
  addForm.phone = ''
  addForm.address = ''
  addForm.gender = 1
  addForm.userRole = null
}

// 获取角色名称
const getRoleName = (roleId) => {
  const role = roleList.value.find(r => r.id === roleId)
  return role ? role.roleName : '未知'
}

// 格式化日期
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleString('zh-CN')
}

onMounted(() => {
  fetchUserList()
  fetchRoleList()
})
</script>

<style scoped>
.user-management {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  color: #333;
}

.search-card {
  margin-bottom: 20px;
}

.search-form {
  margin: 0;
}

.user-list-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.total-count {
  font-size: 14px;
  color: #666;
  font-weight: normal;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .user-management {
    padding: 10px;
  }
  
  .page-header {
    flex-direction: column;
    gap: 10px;
    align-items: flex-start;
  }
  
  .search-form {
    flex-direction: column;
  }
  
  .search-form .el-form-item {
    margin-right: 0;
  }
}
</style>
 