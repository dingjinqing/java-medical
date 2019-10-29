import service from '@/util/request.js'

// 店铺权限进来的查询
export function getRoleGroupRequest (data) {
  return service({
    url: '/api/admin/config/role/query',
    method: 'post',
    data: data
  })
}
// 保存权限组
export function setRoleRequest (data) {
  return service({
    url: '/api/admin/config/role/add',
    method: 'post',
    data: data
  })
}

// 更新权限组
export function editRoleRequest (data) {
  return service({
    url: '/api/admin/config/role/edit',
    method: 'post',
    data: data
  })
}

// 删除账户权限
export function delRoleRequest (data) {
  return service({
    url: '/api/admin/config/role/del',
    method: 'post',
    data: data
  })
}

// 绑定解绑
export function bindUnBindOfficialRequest (data) {
  return service({
    url: '/api/admin/survey/official/bind',
    method: 'post',
    data: data
  })
}

// 查询权限组
export function groupListRequest (data) {
  return service({
    url: '/api/admin/config/role/group/list',
    method: 'post',
    data: data
  })
}

// 查询默认权限组
export function defRoleListRequest () {
  return service({
    url: '/api/admin/config/role/group/getList',
    method: 'get'
  })
}

// 添加权限组
export function addRoleListRequest (data) {
  return service({
    url: '/api/admin/config/role/group/add',
    method: 'post',
    data: data
  })
}

// 删除权限组
export function delGroupRoleRequest (data) {
  return service({
    url: '/api/admin/config/role/group/del',
    method: 'post',
    data: data
  })
}

// 编辑前查询权限组
export function editViewRoleRequest (data) {
  return service({
    url: '/api/admin/config/role/group/editView',
    method: 'post',
    data: data
  })
}

// 编辑权限组
export function editUpdateRoleRequest (data) {
  return service({
    url: '/api/admin/config/role/group/editUpdate',
    method: 'post',
    data: data
  })
}
