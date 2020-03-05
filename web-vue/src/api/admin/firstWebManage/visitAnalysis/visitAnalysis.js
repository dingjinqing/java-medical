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
// 表格导出
export function excelExport (data) {
  return service({
    url: '/api/admin/summary/visit/export',
    method: 'post',
    data: data,
    responseType: 'blob'
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

// 来源分析折线图
export function sourceAnalysis (data) {
  return service({
    url: '/api/admin/summary/source/distribution',
    method: 'post',
    data: data
  })
}

// 来源下拉
export function sourceSelect () {
  return service({
    url: '/api/admin/summary/source/selectoption',
    method: 'get'
  })
}
