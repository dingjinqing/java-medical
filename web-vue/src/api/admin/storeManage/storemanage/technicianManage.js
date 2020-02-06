import service from '@/util/request.js'

/**
 * 技师管理
 */
// 技师列表
export function getTechnicianList (data) {
  return service({
    url: '/api/admin/store/services/technician/list',
    method: 'post',
    data: data
  })
}

// 技师分组下拉
export function getTechnicianGroup (data) {
  return service({
    url: '/api/admin/store/services/technician/group/all/' + data.storeId,
    method: 'get'
  })
}

// 获取服务列表
export function getServiceList (data) {
  return service({
    url: '/api/admin/store/service/list',
    method: 'post',
    data: data
  })
}

// 添加技师
export function addTechnicianApi (data) {
  return service({
    url: '/api/admin/store/services/technician/add',
    method: 'post',
    data: data
  })
}

// 读取技师信息
export function getTechnician (data) {
  return service({
    url: '/api/admin/store/services/technician/select/' + data.id,
    method: 'get'
  })
}

// 更新技师信息
export function updateTechnicianApi (data) {
  return service({
    url: '/api/admin/store/services/technician/update',
    method: 'post',
    data: data
  })
}

// 技师分组列表
export function getTechnicianGroupList (data) {
  return service({
    url: '/api/admin/store/services/technician/group/list',
    method: 'post',
    data: data
  })
}

// 添加技师分组
export function addTechnicianGroup (data) {
  return service({
    url: '/api/admin/store/services/technician/group/add',
    method: 'post',
    data: data
  })
}

// 更新技师分组
export function updateTechnicianGroup (data) {
  return service({
    url: '/api/admin/store/services/technician/group/update',
    method: 'post',
    data: data
  })
}

// 删除技师分组
export function deleteTechnicianGroup (data) {
  return service({
    url: '/api/admin/store/services/technician/group/delete/' + data.id,
    method: 'post'
  })
}
