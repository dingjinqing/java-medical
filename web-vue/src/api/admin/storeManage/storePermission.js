import service from '@/util/request.js'

// 获取门店账户列表
export function getAccountListApi (data) {
  return service({
    url: '/api/admin/store/account/list',
    data,
    method: 'post'
  })
}

// 添加门店账户
export function addAccountApi (data) {
  return service({
    url: '/api/admin/store/account/create',
    data,
    method: 'post'
  })
}

// 操作门店账户
export function editAccountApi (data) {
  return service({
    url: '/api/admin/store/account/manage',
    data,
    method: 'post'
  })
}

// 读取账户信息
export function getAccountApi (accountId) {
  return service({
    url: `/api/admin/store/account/getOne/${accountId}`,
    method: 'get'
  })
}

// 更新账户信息
export function updateAccountApi (data) {
  return service({
    url: '/api/admin/store/account/edit',
    data,
    method: 'post'
  })
}

// 获取门店权限列表和选中状态
export function getSettingApi () {
  return service({
    url: '/api/admin/store/account/getSetting',
    method: 'get'
  })
}
