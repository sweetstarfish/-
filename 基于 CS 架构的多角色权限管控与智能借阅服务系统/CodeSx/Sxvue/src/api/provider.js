import request from '@/utils/request'

// 获取供应商列表
export function getProviderList() {
  return request({
    url: '/provider/list',
    method: 'get'
  })
}

// 分页查询供应商列表
export function getProviderListByPage(params) {
  return request({
    url: '/provider/page',
    method: 'get',
    params
  })
}

// 添加供应商
export function addProvider(data) {
  return request({
    url: '/provider/add',
    method: 'post',
    data
  })
}

// 更新供应商
export function updateProvider(data) {
  return request({
    url: '/provider/update',
    method: 'put',
    data
  })
}

// 删除供应商
export function deleteProvider(id) {
  return request({
    url: `/provider/delete/${id}`,
    method: 'delete'
  })
}

// 获取单个供应商
export function getProviderById(id) {
  return request({
    url: `/provider/${id}`,
    method: 'get'
  })
} 