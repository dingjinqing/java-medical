import service from '@/util/request.js'

// 销售报表
export function getSalesReportList (data) {
  return service({
    url: '/api/admin/report/sales/medical',
    method: 'post',
    data: data
  })
}

// 导出
export function getSalesReportExport (data) {
  return service({
    url: '/api/admin/report/sales/medical/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
