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

// 编辑医师
export function getDoctor (id) {
  return service({
    url: `/api/admin/doctor/info/${id}`,
    method: 'get'
  })
}

// 更新医师，停用，启用
export function updateDoctor (data) {
  return service({
    url: '/api/admin/doctor/update',
    method: 'post',
    data: data
  })
}
