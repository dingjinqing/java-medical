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

// 获取医师列表
export function getDoctorList (data) {
  return service({
    url: '/api/admin/doctors/select/list',
    method: 'post',
    data: data
  })
}

// 获取医师统计总数
export function getDoctorTotal (data) {
  return service({
    url: '/api/admin/inquiry/order/statistics/total',
    method: 'post',
    data: data
  })
}
