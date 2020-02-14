/****
 访问分析相应接口
 ****/

import service from '@/util/request.js'

// 折线图综合查询
export function realTime (data) {
  return service({
    url: '/api/admin/realtimeoverview/realTime',
    method: 'post',
    data: data
  })
}
// 核心指标
export function coreIndicator (data) {
  return service({
    url: '/api/admin/realtimeoverview/coreIndicator',
    method: 'post',
    data: data
  })
}
