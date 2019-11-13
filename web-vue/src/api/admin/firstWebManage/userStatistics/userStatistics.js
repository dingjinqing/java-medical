/****
 用户统计相应接口
 ****/

import service from '@/util/request.js'

// 客户概况及趋势
export function customerTrend (data) {
  return service({
    url: '/api/admin/overview/user/analysis/trend',
    method: 'post',
    data: data
  })
}

// 用户活跃
export function userActive (data) {
  return service({
    url: '/api/admin/overview/user/analysis/active',
    method: 'post',
    data: data
  })
}

// 会员统计
export function menberStatistics (data) {
  return service({
    url: '/api/admin/overview/user/analysis/vip',
    method: 'post',
    data: data
  })
}

// 用户成交分析
export function userAnalysis (data) {
  return service({
    url: '/api/admin/overview/user/analysis/order',
    method: 'post',
    data: data
  })
}

// 客户复购趋势
export function userReBuy (data) {
  return service({
    url: '/api/admin/overview/user/analysis/rebuy',
    method: 'post',
    data: data
  })
}
