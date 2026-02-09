<template>
  <div class="dashboard">
    <!-- 统计卡片区域 -->
    <div class="stats-section">
      <div class="stats-grid">
        <div class="stat-card">
          <div class="stat-icon today">
            <el-icon><Flag /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-title">今日账单数</div>
            <div class="stat-value">{{ stats.todayBills }} 笔</div>
          </div>
        </div>

        <div class="stat-card">
          <div class="stat-icon yesterday">
            <el-icon><Flag /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-title">昨日账单数</div>
            <div class="stat-value">{{ stats.yesterdayBills }} 笔</div>
          </div>
        </div>

        <div class="stat-card">
          <div class="stat-icon week">
            <el-icon><Flag /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-title">本周账单数</div>
            <div class="stat-value">{{ stats.weekBills }} 笔</div>
          </div>
        </div>

        <div class="stat-card">
          <div class="stat-icon month">
            <el-icon><Flag /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-title">本月账单数</div>
            <div class="stat-value">{{ stats.monthBills }} 笔</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 图表区域 -->
    <div class="charts-section">
      <div class="charts-grid">
        <!-- 最近7天账单数量趋势 -->
        <div class="chart-card">
          <div class="chart-header">
            <h3>最近7天账单数量趋势</h3>
          </div>
          <div class="chart-container">
            <div ref="quantityChartRef" class="chart"></div>
          </div>
        </div>

        <!-- 最近7天账单金额趋势 -->
        <div class="chart-card">
          <div class="chart-header">
            <h3>最近7天账单金额趋势</h3>
          </div>
          <div class="chart-container">
            <div ref="amountChartRef" class="chart"></div>
          </div>
        </div>
      </div>
    </div>

    <!-- 快速操作区域 -->
    <div class="quick-actions">
      <div class="actions-grid">
        <div class="action-card" @click="navigateTo('/bill')">
          <div class="action-icon">
            <el-icon><Document /></el-icon>
          </div>
          <div class="action-text">账单管理</div>
        </div>

        <div class="action-card" @click="navigateTo('/provider')">
          <div class="action-icon">
            <el-icon><Shop /></el-icon>
          </div>
          <div class="action-text">供应商管理</div>
        </div>

        <div class="action-card" @click="navigateTo('/user')">
          <div class="action-icon">
            <el-icon><User /></el-icon>
          </div>
          <div class="action-text">用户管理</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Flag, Document, Shop, User } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import { getDashboardStats, getQuantityTrend, getAmountTrend } from '@/api/dashboard.js'

const router = useRouter()

// 统计数据
const stats = reactive({
  todayBills: 0,
  yesterdayBills: 0,
  weekBills: 0,
  monthBills: 0
})

// 图表引用
const quantityChartRef = ref(null)
const amountChartRef = ref(null)

// 导航函数
const navigateTo = (path) => {
  router.push(path)
}

// 初始化数量趋势图表
const initQuantityChart = (data) => {
  if (!quantityChartRef.value) return

  const chart = echarts.init(quantityChartRef.value)
  
  const option = {
    tooltip: {
      trigger: 'axis',
      formatter: '{b}: {c} 笔'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: data.dates || ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      axisLine: {
        lineStyle: {
          color: '#ddd'
        }
      },
      axisLabel: {
        color: '#666'
      }
    },
    yAxis: {
      type: 'value',
      min: 0,
      max: 300,
      interval: 50,
      axisLine: {
        lineStyle: {
          color: '#ddd'
        }
      },
      axisLabel: {
        color: '#666'
      }
    },
    series: [
      {
        name: '账单数量',
        type: 'line',
        smooth: true,
        data: data.quantities || [150, 230, 180, 160, 130, 200, 260],
        itemStyle: {
          color: '#409EFF'
        },
        areaStyle: {
          color: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              { offset: 0, color: 'rgba(64, 158, 255, 0.3)' },
              { offset: 1, color: 'rgba(64, 158, 255, 0.1)' }
            ]
          }
        }
      }
    ]
  }
  
  chart.setOption(option)
  
  // 响应式调整
  window.addEventListener('resize', () => {
    chart.resize()
  })
}

// 初始化金额趋势图表
const initAmountChart = (data) => {
  if (!amountChartRef.value) return

  const chart = echarts.init(amountChartRef.value)
  
  const option = {
    tooltip: {
      trigger: 'axis',
      formatter: '{b}: ¥{c}'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: data.dates || ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      axisLine: {
        lineStyle: {
          color: '#ddd'
        }
      },
      axisLabel: {
        color: '#666'
      }
    },
    yAxis: {
      type: 'value',
      min: 0,
      max: 200,
      interval: 50,
      axisLine: {
        lineStyle: {
          color: '#ddd'
        }
      },
      axisLabel: {
        color: '#666',
        formatter: '¥{value}'
      }
    },
    series: [
      {
        name: '账单金额',
        type: 'bar',
        data: data.amounts || [120, 195, 150, 80, 70, 110, 130],
        itemStyle: {
          color: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              { offset: 0, color: '#409EFF' },
              { offset: 1, color: '#67C23A' }
            ]
          }
        }
      }
    ]
  }
  
  chart.setOption(option)
  
  // 响应式调整
  window.addEventListener('resize', () => {
    chart.resize()
  })
}

// 获取统计数据
const fetchStats = async () => {
  try {
    const response = await getDashboardStats()
    if (response.success) {
      Object.assign(stats, response.data)
    }
  } catch (error) {
    console.error('获取统计数据失败:', error)
  }
}

// 获取趋势数据
const fetchTrends = async () => {
  try {
    // 获取数量趋势
    const quantityResponse = await getQuantityTrend()
    if (quantityResponse.success) {
      initQuantityChart(quantityResponse.data)
    }
    
    // 获取金额趋势
    const amountResponse = await getAmountTrend()
    if (amountResponse.success) {
      initAmountChart(amountResponse.data)
    }
  } catch (error) {
    console.error('获取趋势数据失败:', error)
    // 使用默认数据
    initQuantityChart({})
    initAmountChart({})
  }
}

onMounted(() => {
  fetchStats()
  
  // 延迟初始化图表，确保DOM已渲染
  setTimeout(() => {
    fetchTrends()
  }, 100)
})
</script>

<style scoped>
.dashboard {
  padding: 24px;
  max-width: 1400px;
  margin: 0 auto;
}

.stats-section {
  margin-bottom: 32px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
}

.stat-card {
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  border: 1px solid #f0f0f0;
  display: flex;
  align-items: center;
  gap: 16px;
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.12);
}

.stat-icon {
  width: 50px;
  height: 50px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  color: white;
  flex-shrink: 0;
}

.stat-icon.today {
  background: linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%);
}

.stat-icon.yesterday {
  background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%);
}

.stat-icon.week {
  background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
}

.stat-icon.month {
  background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%);
}

.stat-content {
  flex: 1;
}

.stat-title {
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 24px;
  font-weight: 600;
  color: #333;
}

.charts-section {
  margin-bottom: 32px;
}

.charts-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(500px, 1fr));
  gap: 24px;
}

.chart-card {
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  border: 1px solid #f0f0f0;
}

.chart-header {
  margin-bottom: 20px;
}

.chart-header h3 {
  margin: 0;
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.chart-container {
  position: relative;
}

.chart {
  width: 100%;
  height: 300px;
}

.quick-actions {
  margin-bottom: 24px;
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

.action-card {
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  border: 1px solid #f0f0f0;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.action-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.12);
}

.action-icon {
  width: 50px;
  height: 50px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  color: white;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  margin: 0 auto 16px auto;
}

.action-text {
  font-size: 16px;
  font-weight: 500;
  color: #333;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .dashboard {
    padding: 16px;
  }
  
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
  }
  
  .charts-grid {
    grid-template-columns: 1fr;
    gap: 16px;
  }
  
  .chart {
    height: 250px;
  }
  
  .actions-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
  }
}

@media (max-width: 480px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }
  
  .actions-grid {
    grid-template-columns: 1fr;
  }
  
  .stat-card {
    padding: 20px;
  }
  
  .chart-card {
    padding: 20px;
  }
}
</style> 