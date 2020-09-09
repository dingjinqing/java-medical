import service from '@/util/request.js'

// 获取医师返利配置
export function getWithDrawConfig (data) {
  return service({
    url: '/api/admin/rebate/get',
    method: 'post',
    data: data
  })
}
// 设置返利信息
export function setWithDrawConfig (data) {
  return service({
    url: '/api/admin/rebate/set',
    method: 'post',
    data: data
  })
}
// 处方返利统计
export function getPrescriptionRebate (data) {
  return service({
    url: '/api/admin/doctor/rebate/prescription/list',
    method: 'post',
    data: data
  })
}
// 咨询返利统计
export function getInquiryOrderRebate (data) {
  return service({
    url: '/api/admin/doctor/rebate/inquiryOrder/list',
    method: 'post',
    data: data
  })
}
// 返利审核列表
export function getDoctorWithdrawList (data) {
  return service({
    url: '/api/admin/doctor/withdraw/list',
    method: 'post',
    data: data
  })
}
// 提现状态操作
export function changeWithdrawStatus (data) {
  return service({
    url: '/api/admin/doctor/withdraw/audit',
    method: 'post',
    data: data
  })
}
// 获取返利详情接口
export function getWithdrawDetail (data) {
  return service({
    url: `/api/admin/doctor/withdraw/detail?id=${data}`,
    method: 'get'
  })
}
// 添加备注信息
export function addDesc (data) {
  return service({
    url: `/api/admin/doctor/withdraw/desc/add`,
    method: 'post',
    data
  })
}
// 处方返利信息导出
export function exportPrescriptionRebate (data) {
  return service({
    url: `/api/admin/doctor/rebate/prescription/export`,
    method: 'post',
    data
  })
}
// 咨询订单返利信息导出
export function exporTinquiryOrderRebate (data) {
  return service({
    url: `/api/admin/doctor/rebate/inquiryOrder/export`,
    method: 'post',
    data
  })
}
