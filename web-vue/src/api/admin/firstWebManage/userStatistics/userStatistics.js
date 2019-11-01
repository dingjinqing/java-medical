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
