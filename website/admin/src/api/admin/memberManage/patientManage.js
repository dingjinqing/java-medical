import service from '@/util/request.js'

// 患者列表
export function getPatientList (data) {
  return service({
    url: '/api/admin/patient/list',
    method: 'post',
    data: data
  })
}
// 患者列表
export function getPatientMessage (patientId) {
  return service({
    url: `/api/admin/patient/info/${patientId}`,
    method: 'get'
  })
}
// 处方列表
export function getPrescriptionList (data) {
  return service({
    url: `/api/admin/prescription/list`,
    method: 'post',
    data: data
  })
}
// 处方详情
export function getPrescriptionMessage (data) {
  return service({
    url: `/api/admin/prescription/details`,
    method: 'post',
    data: data
  })
}
