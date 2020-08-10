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
export function getAllPrescriptionList (data) {
  return service({
    url: `/api/admin/prescription/list`,
    method: 'post',
    data: data
  })
}
// 患者详情-处方列表
export function getPrescriptionList (data) {
  return service({
    url: `/api/admin/prescription/patient/list`,
    method: 'post',
    data: data
  })
}
// 处方详情
export function getPrescriptionMessage (code) {
  return service({
    url: `/api/admin/prescription/get/${code}`,
    method: 'get'
  })
}
