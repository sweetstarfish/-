<template>
  <div class="bill-management">
    <div class="page-header">
      <h2>账单管理</h2>
      <el-button type="primary" @click="showAddDialog = true">
        <Plus />
        添加账单
      </el-button>
    </div>

    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="搜索关键词">
          <el-input
            v-model="searchForm.keyword"
            placeholder="请输入账单编码、商品名称、描述或供应商名称"
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

    <!-- 账单列表 -->
    <el-card class="bill-list-card">
      <template #header>
        <div class="card-header">
          <span>账单列表</span>
          <span class="total-count">共 {{ pagination.total }} 条记录</span>
        </div>
      </template>

      <el-table :data="billList" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="billCode" label="账单编码" width="150" />
        <el-table-column prop="productName" label="商品名称" width="150" />
        <el-table-column prop="productDesc" label="商品描述" />
        <el-table-column prop="productUnit" label="商品单位" width="100" />
        <el-table-column prop="productCount" label="商品数量" width="100" />
        <el-table-column prop="totalPrice" label="总金额" width="120">
          <template #default="scope">
            ¥{{ scope.row.totalPrice }}
          </template>
        </el-table-column>
        <el-table-column prop="isPayment" label="支付状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.isPayment === 2 ? 'success' : 'warning'">
              {{ scope.row.isPayment === 2 ? '已支付' : '未支付' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="providerName" label="供应商" width="150" />
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

    <!-- 添加/编辑账单对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="isEdit ? '编辑账单' : '添加账单'"
      width="600px"
      :close-on-click-modal="false"
    >
      <el-form
        ref="billFormRef"
        :model="billForm"
        :rules="billFormRules"
        label-width="100px"
      >
        <el-form-item label="账单编码" prop="billCode">
          <el-input v-model="billForm.billCode" placeholder="请输入账单编码" />
        </el-form-item>
        <el-form-item label="商品名称" prop="productName">
          <el-input v-model="billForm.productName" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="商品描述" prop="productDesc">
          <el-input
            v-model="billForm.productDesc"
            type="textarea"
            placeholder="请输入商品描述"
            :rows="3"
          />
        </el-form-item>
        <el-form-item label="商品单位" prop="productUnit">
          <el-input v-model="billForm.productUnit" placeholder="请输入商品单位" />
        </el-form-item>
        <el-form-item label="商品数量" prop="productCount">
          <el-input-number
            v-model="billForm.productCount"
            :min="1"
            placeholder="请输入商品数量"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="总金额" prop="totalPrice">
          <el-input-number
            v-model="billForm.totalPrice"
            :min="0"
            :precision="2"
            placeholder="请输入总金额"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="支付状态" prop="isPayment">
          <el-radio-group v-model="billForm.isPayment">
            <el-radio :value="1">未支付</el-radio>
            <el-radio :value="2">已支付</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="供应商" prop="providerId">
          <el-select v-model="billForm.providerId" placeholder="请选择供应商" style="width: 100%">
            <el-option
              v-for="provider in providerList"
              :key="provider.id"
              :label="provider.proName"
              :value="provider.id"
            />
          </el-select>
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
import { getBillListByPage, addBill, updateBill, deleteBill } from '@/api/bill.js'
import { getProviderList } from '@/api/provider.js'

const loading = ref(false)
const submitLoading = ref(false)
const showAddDialog = ref(false)
const isEdit = ref(false)
const billList = ref([])
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

const billFormRef = ref()
const billForm = reactive({
  id: null,
  billCode: '',
  productName: '',
  productDesc: '',
  productUnit: '',
  productCount: 1,
  totalPrice: 0,
  isPayment: 1,
  providerId: null,
  createdBy: 1,
  modifyBy: 1
})

const billFormRules = {
  billCode: [
    { required: true, message: '请输入账单编码', trigger: 'blur' }
  ],
  productName: [
    { required: true, message: '请输入商品名称', trigger: 'blur' }
  ],
  productDesc: [
    { required: true, message: '请输入商品描述', trigger: 'blur' }
  ],
  productUnit: [
    { required: true, message: '请输入商品单位', trigger: 'blur' }
  ],
  productCount: [
    { required: true, message: '请输入商品数量', trigger: 'blur' }
  ],
  totalPrice: [
    { required: true, message: '请输入总金额', trigger: 'blur' }
  ],
  isPayment: [
    { required: true, message: '请选择支付状态', trigger: 'change' }
  ],
  providerId: [
    { required: true, message: '请选择供应商', trigger: 'change' }
  ]
}

// 获取账单列表
const fetchBillList = async () => {
  loading.value = true
  try {
    const params = {
      pageNum: pagination.pageNum,
      pageSize: pagination.pageSize,
      keyword: searchForm.keyword || undefined
    }
    
    const response = await getBillListByPage(params)
    if (response.success) {
      billList.value = response.data.records
      pagination.total = response.data.total
    } else {
      ElMessage.error(response.message || '获取账单列表失败')
    }
  } catch (error) {
    console.error('获取账单列表失败:', error)
    ElMessage.error('获取账单列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  pagination.pageNum = 1
  fetchBillList()
}

// 重置搜索
const handleReset = () => {
  searchForm.keyword = ''
  pagination.pageNum = 1
  fetchBillList()
}

// 分页大小改变
const handleSizeChange = (size) => {
  pagination.pageSize = size
  pagination.pageNum = 1
  fetchBillList()
}

// 当前页改变
const handleCurrentChange = (page) => {
  pagination.pageNum = page
  fetchBillList()
}

// 获取供应商列表
const fetchProviderList = async () => {
  try {
    console.log('开始获取供应商列表...')
    const response = await getProviderList()
    console.log('供应商列表响应:', response)
    
    if (response.success) {
      providerList.value = response.data
      console.log('供应商列表设置成功:', providerList.value)
    } else {
      console.error('获取供应商列表失败:', response.message)
      ElMessage.error(response.message || '获取供应商列表失败')
    }
  } catch (error) {
    console.error('获取供应商列表异常:', error)
    ElMessage.error('获取供应商列表失败')
  }
}

// 编辑账单
const handleEdit = (bill) => {
  isEdit.value = true
  // 确保包含所有必要字段
  billForm.id = bill.id
  billForm.billCode = bill.billCode || ''
  billForm.productName = bill.productName || ''
  billForm.productDesc = bill.productDesc || ''
  billForm.productUnit = bill.productUnit || ''
  billForm.productCount = bill.productCount || 1
  billForm.totalPrice = bill.totalPrice || 0
  billForm.isPayment = bill.isPayment || 1
  billForm.providerId = bill.providerId || null
  billForm.createdBy = bill.createdBy || 1
  billForm.modifyBy = bill.modifyBy || 1
  showAddDialog.value = true
}

// 删除账单
const handleDelete = async (bill) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除账单"${bill.billCode}"吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await deleteBill(bill.id)
    if (response.success) {
      ElMessage.success('账单删除成功')
      fetchBillList()
    } else {
      ElMessage.error(response.message || '账单删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除账单失败:', error)
      ElMessage.error('删除账单失败')
    }
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!billFormRef.value) return

  await billFormRef.value.validate(async (valid) => {
    if (valid) {
      submitLoading.value = true
      try {
        const response = isEdit.value 
          ? await updateBill(billForm)
          : await addBill(billForm)
        
        if (response.success) {
          ElMessage.success(isEdit.value ? '账单更新成功' : '账单添加成功')
          showAddDialog.value = false
          resetBillForm()
          fetchBillList()
        } else {
          ElMessage.error(response.message || (isEdit.value ? '账单更新失败' : '账单添加失败'))
        }
      } catch (error) {
        console.error(isEdit.value ? '更新账单失败:' : '添加账单失败:', error)
        ElMessage.error(isEdit.value ? '更新账单失败' : '添加账单失败')
      } finally {
        submitLoading.value = false
      }
    }
  })
}

// 重置账单表单
const resetBillForm = () => {
  billForm.id = null
  billForm.billCode = ''
  billForm.productName = ''
  billForm.productDesc = ''
  billForm.productUnit = ''
  billForm.productCount = 1
  billForm.totalPrice = 0
  billForm.isPayment = 1
  billForm.providerId = null
  billForm.createdBy = 1
  billForm.modifyBy = 1
  isEdit.value = false
}

// 格式化日期
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleString('zh-CN')
}

onMounted(() => {
  fetchBillList()
  fetchProviderList()
})
</script>

<style scoped>
.bill-management {
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

.bill-list-card {
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
  .bill-management {
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
 
 