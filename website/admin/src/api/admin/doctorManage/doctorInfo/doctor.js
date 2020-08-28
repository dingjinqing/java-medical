import service from '@/util/request.js'

// 医师新增
export function addDoctor (data) {
  return service({
    url: '/api/admin/doctors/add',
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
    url: '/api/admin/doctors/list',
    method: 'post',
    data: data
  })
}

// 编辑医师
export function getDoctor (id) {
  return service({
    url: `/api/admin/doctors/info/${id}`,
    method: 'get'
  })
}

// 更新医师
export function updateDoctor (data) {
  return service({
    url: '/api/admin/doctors/update',
    method: 'post',
    data: data
  })
}

// 停用，启用
export function enableDoctor (data) {
  return service({
    url: '/api/admin/doctors/enable',
    method: 'post',
    data: data
  })
}

// 职称查询
export function getProfessionTitle (data) {
  return service({
    url: '/api/admin/doctor/title/list',
    method: 'post',
    data
  })
}

// 解除绑定
export function unBundling (data) {
  return service({
    url: '/api/admin/doctor/unbundling',
    method: 'post',
    data
  })
}

// 切换问诊状态
export function updateConsultation (data) {
  return service({
    url: '/api/admin/doctor/consultation',
    method: 'post',
    data
  })
}

// 同步系统医师
export function fetchDoctor (data) {
  return service({
    url: '/api/admin/doctors/fetch',
    method: 'post',
    data
  })
}

// 同步系统职称
export function fetchDoctorTitle (data) {
  return service({
    url: '/api/admin/doctor/title/fetch',
    method: 'post',
    data
  })
}
