/****
访问分析相应接口
 ****/

import service from '@/util/request.js'

// 折线图综合查询
export function amountAnalysis (data) {
  return service({
    url: '/api/admin/summary/visit/amount',
    method: 'post',
    data: data
  })
}

// 访问分布
export function distributionAnalysis (data) {
  return service({
    url: '/api/admin/summary/visit/distribution',
    method: 'post',
    data: data
  })
}

// 留存统计
export function retainAnalysis (data) {
  return service({
    url: '/api/admin/summary/visit/retain',
    method: 'post',
    data: data
  })
}

// 访问页面
export function pageAnalysis (data) {
  return service({
    url: '/api/admin/summary/visit/page',
    method: 'post',
    data: data
  })
}
