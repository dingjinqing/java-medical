import service from '@/util/request.js'

// 销售报表
export function getAdvistoryReportList (data) {
  return service({
    url: '/api/admin/inquiry/order/statistics',
    method: 'post',
    data: data
  })
}

// 导出
export function getReportExport (data) {
  return service({
    url: '/api/admin/inquiry/order/statistics/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
