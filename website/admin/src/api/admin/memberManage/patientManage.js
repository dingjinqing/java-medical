import service from '@/util/request.js'

// 获取
export function getPatientList (data) {
  return service({
    url: '/api/admin/patient/list',
    method: 'post',
    data: data
  })
}
