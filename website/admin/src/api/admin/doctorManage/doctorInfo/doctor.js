import service from '@/util/request.js'

// 医师新增
export function addDoctor (data) {
  return service({
    url: '/api/admin/doctor/add',
    method: 'post',
    data: data
  })
}

// 职称列表
export function getDoctorTitle (data) {
  return service({
    url: '/api/admin/doctor/title/select/list',
    method: 'get',
    data: data
  })
}

// 科室列表
export function getBelongParts (data) {
  return service({
    url: '/api/admin/doctor/department/tree/list',
    method: 'get',
    data: data
  })
}

// 医生列表
export function doctorList (data) {
  return service({
    url: '/api/admin/doctor/list',
    method: 'post',
    data: data
  })
}
