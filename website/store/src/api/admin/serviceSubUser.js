import service from '@/util/request.js'

// 查询子账户
export function subUserListRequest (data) {
  return service({
    url: '/api/admin/account/user/list',
    method: 'post',
    data: data
  })
}

// 添加子账户
export function authorizationRequest (data) {
  return service({
    url: '/api/admin/account/user/add',
    method: 'post',
    data: data
  })
}

// 删除子账户
export function oneListRequest (data) {
  return service({
    url: `/api/admin/account/user/del/${data}`,
    method: 'get'
  })
}

// 编辑子账户
export function payManageRequest (data) {
  return service({
    url: '/api/admin/account/user/edit',
    method: 'post',
    data: data
  })
}
