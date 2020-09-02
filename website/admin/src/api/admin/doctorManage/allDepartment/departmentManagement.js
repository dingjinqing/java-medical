import service from '@/util/request.js'

// 科室分页查询
export function getDepartmentList (data) {
  return service({
    url: `/api/admin/doctor/department/list`,
    method: 'post',
    data: data
  })
}
// 科室批量查询
export function getBatchDepartmentList (id) {
  return service({
    url: `/api/admin/doctor/department/child/list/${id}`,
    method: 'get'
  })
}

// 科室删除
export function deleteDepartment (id) {
  return service({
    url: `/api/admin/doctor/department/delete/${id}`,
    method: 'get'
  })
}
// 科室新增
export function addDepartment (data) {
  return service({
    url: `/api/admin/doctor/department/add`,
    method: 'post',
    data: data
  })
}

// 科室修改
export function updateDepartment (data) {
  return service({
    url: `/api/admin/doctor/department/update`,
    method: 'post',
    data: data
  })
}
// 科室详情查询
export function getDepartment (id) {
  return service({
    url: `/api/admin/doctor/department/info/${id}`,
    method: 'get'
  })
}
