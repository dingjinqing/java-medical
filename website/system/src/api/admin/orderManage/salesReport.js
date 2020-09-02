import service from '@/util/request.js'

// 销售报表
export function getSalesReportList (data) {
  return service({
    url: '/api/system/report/sales/order',
    method: 'post',
    data: data
  })
}

// 导出
export function getSalesReportExport (data) {
  return service({
    url: '/api/system/report/sales/order/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}

// 导出
export function getShopList (data) {
  return service({
    url: '/api/system/shop/getList',
    method: 'get'
  })
}
