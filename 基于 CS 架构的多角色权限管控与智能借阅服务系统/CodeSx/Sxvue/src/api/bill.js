import request from '@/utils/request'

// 获取账单列表
export function getBillList() {
  return request({
    url: '/bill/list',
    method: 'get'
  })
}

// 分页查询账单列表
export function getBillListByPage(params) {
  return request({
    url: '/bill/page',
    method: 'get',
    params
  })
}

// 添加账单
export function addBill(data) {
  return request({
    url: '/bill/add',
    method: 'post',
    data
  })
}

// 更新账单
export function updateBill(data) {
  return request({
    url: '/bill/update',
    method: 'put',
    data
  })
}

// 删除账单
export function deleteBill(id) {
  return request({
    url: `/bill/delete/${id}`,
    method: 'delete'
  })
}

// 获取单个账单
export function getBillById(id) {
  return request({
    url: `/bill/${id}`,
    method: 'get'
  })
}

export const providerApi = {
  // 获取供应商列表
  getProviderList() {
    return request({
      url: '/provider/list',
      method: 'get'
    })
  },

  // 添加供应商
  addProvider(data) {
    return request({
      url: '/provider/add',
      method: 'post',
      data
    })
  },

  // 更新供应商
  updateProvider(data) {
    return request({
      url: '/provider/update',
      method: 'put',
      data
    })
  },

  // 删除供应商
  deleteProvider(id) {
    return request({
      url: `/provider/delete/${id}`,
      method: 'delete'
    })
  },

  // 获取单个供应商
  getProviderById(id) {
    return request({
      url: `/provider/${id}`,
      method: 'get'
    })
  }
} 