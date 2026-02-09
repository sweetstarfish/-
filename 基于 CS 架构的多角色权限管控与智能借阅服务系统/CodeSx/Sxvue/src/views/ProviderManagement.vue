<template>
  <div class="provider-management">
    <div class="page-header">
      <h2>供应商管理</h2>
      <el-button type="primary" @click="showAddDialog = true">
        <Plus />
        添加供应商
      </el-button>
    </div>

    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="搜索关键词">
          <el-input
            v-model="searchForm.keyword"
            placeholder="请输入供应商编码、名称、联系人、电话或地址"
            clearable
            @keyup.enter="handleSearch"
            style="width: 350px"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 供应商列表 -->
    <el-card class="provider-list-card">
      <template #header>
        <div class="card-header">
          <span>供应商列表</span>
          <span class="total-count">共 {{ pagination.total }} 条记录</span>
        </div>
      </template>

      <el-table :data="providerList" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="proCode" label="供应商编码" width="150" />
        <el-table-column prop="proName" label="供应商名称" width="200" />
        <el-table-column prop="proDesc" label="供应商描述" />
        <el-table-column prop="proContact" label="联系人" width="120" />
        <el-table-column prop="proPhone" label="联系电话" width="130" />
        <el-table-column prop="proAddress" label="地址" />
        <el-table-column prop="proFax" label="传真" width="120" />
        <el-table-column prop="creationDate" label="创建时间" width="160">
          <template #default="scope">
            {{ formatDate(scope.row.creationDate) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="handleEdit(scope.row)"
            >
              编辑
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="handleDelete(scope.row)"
            >
              删除
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

    <!-- 添加/编辑供应商对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="isEdit ? '编辑供应商' : '添加供应商'"
      width="600px"
      :close-on-click-modal="false"
    >
      <el-form
        ref="providerFormRef"
        :model="providerForm"
        :rules="providerFormRules"
        label-width="100px"
      >
        <el-form-item label="供应商编码" prop="proCode">
          <el-input v-model="providerForm.proCode" placeholder="请输入供应商编码" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="proName">
          <el-input v-model="providerForm.proName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="供应商描述" prop="proDesc">
          <el-input
            v-model="providerForm.proDesc"
            type="textarea"
            placeholder="请输入供应商描述"
            :rows="3"
          />
        </el-form-item>
        <el-form-item label="联系人" prop="proContact">
          <el-input v-model="providerForm.proContact" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="proPhone">
          <el-input v-model="providerForm.proPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="地址" prop="proAddress">
          <el-input v-model="providerForm.proAddress" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="传真" prop="proFax">
          <el-input v-model="providerForm.proFax" placeholder="请输入传真号码" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAddDialog = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit" :loading="submitLoading">
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
import { getProviderListByPage, addProvider, updateProvider, deleteProvider } from '@/api/provider.js'

const loading = ref(false)
const submitLoading = ref(false)
const showAddDialog = ref(false)
const isEdit = ref(false)
const providerList = ref([])

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

const providerFormRef = ref()
const providerForm = reactive({
  id: null,
  proCode: '',
  proName: '',
  proDesc: '',
  proContact: '',
  proPhone: '',
  proAddress: '',
  proFax: ''
})

const providerFormRules = {
  proCode: [
    { required: true, message: '请输入供应商编码', trigger: 'blur' }
  ],
  proName: [
    { required: true, message: '请输入供应商名称', trigger: 'blur' }
  ],
  proDesc: [
    { required: true, message: '请输入供应商描述', trigger: 'blur' }
  ],
  proContact: [
    { required: true, message: '请输入联系人', trigger: 'blur' }
  ],
  proPhone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  proAddress: [
    { required: true, message: '请输入地址', trigger: 'blur' }
  ]
}

// 获取供应商列表
const fetchProviderList = async () => {
  loading.value = true
  try {
    const params = {
      pageNum: pagination.pageNum,
      pageSize: pagination.pageSize,
      keyword: searchForm.keyword || undefined
    }
    
    const response = await getProviderListByPage(params)
    if (response.success) {
      providerList.value = response.data.records
      pagination.total = response.data.total
    } else {
      ElMessage.error(response.message || '获取供应商列表失败')
    }
  } catch (error) {
    console.error('获取供应商列表失败:', error)
    ElMessage.error('获取供应商列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  pagination.pageNum = 1
  fetchProviderList()
}

// 重置搜索
const handleReset = () => {
  searchForm.keyword = ''
  pagination.pageNum = 1
  fetchProviderList()
}

// 分页大小改变
const handleSizeChange = (size) => {
  pagination.pageSize = size
  pagination.pageNum = 1
  fetchProviderList()
}

// 当前页改变
const handleCurrentChange = (page) => {
  pagination.pageNum = page
  fetchProviderList()
}

// 编辑供应商
const handleEdit = (provider) => {
  isEdit.value = true
  Object.assign(providerForm, provider)
  showAddDialog.value = true
}

// 删除供应商
const handleDelete = async (provider) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除供应商"${provider.proName}"吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await deleteProvider(provider.id)
    if (response.success) {
      ElMessage.success('供应商删除成功')
      fetchProviderList()
    } else {
      ElMessage.error(response.message || '供应商删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除供应商失败:', error)
      ElMessage.error('删除供应商失败')
    }
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!providerFormRef.value) return

  await providerFormRef.value.validate(async (valid) => {
    if (valid) {
      submitLoading.value = true
      try {
        const response = isEdit.value 
          ? await updateProvider(providerForm)
          : await addProvider(providerForm)
        
        if (response.success) {
          ElMessage.success(isEdit.value ? '供应商更新成功' : '供应商添加成功')
          showAddDialog.value = false
          resetProviderForm()
          fetchProviderList()
        } else {
          ElMessage.error(response.message || (isEdit.value ? '供应商更新失败' : '供应商添加失败'))
        }
      } catch (error) {
        console.error(isEdit.value ? '更新供应商失败:' : '添加供应商失败:', error)
        ElMessage.error(isEdit.value ? '更新供应商失败' : '添加供应商失败')
      } finally {
        submitLoading.value = false
      }
    }
  })
}

// 重置供应商表单
const resetProviderForm = () => {
  providerForm.id = null
  providerForm.proCode = ''
  providerForm.proName = ''
  providerForm.proDesc = ''
  providerForm.proContact = ''
  providerForm.proPhone = ''
  providerForm.proAddress = ''
  providerForm.proFax = ''
  isEdit.value = false
}

// 格式化日期
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleString('zh-CN')
}

onMounted(() => {
  fetchProviderList()
})
</script>

<style scoped>
.provider-management {
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

.provider-list-card {
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
  .provider-management {
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