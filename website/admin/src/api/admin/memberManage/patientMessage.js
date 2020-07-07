import service from '@/util/request.js'

// 获取
export function getPatientPrescriptionData (data) {
  return service({
    url: '/api/admin/prescription/list',
    method: 'post',
    data: data
  })
}
