import request from '@/utils/request'

// 获取仪表板统计数据
export function getDashboardStats() {
  return request({
    url: '/dashboard/stats',
    method: 'get'
  })
}

// 获取最近7天账单数量趋势
export function getQuantityTrend() {
  return request({
    url: '/dashboard/trend/quantity',
    method: 'get'
  })
}

// 获取最近7天账单金额趋势
export function getAmountTrend() {
  return request({
    url: '/dashboard/trend/amount',
    method: 'get'
  })
} 