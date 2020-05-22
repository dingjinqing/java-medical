import service from '@/util/request.js'

// 编辑商家账号信息
export function editAccountRequest (data) {
  return service({
    url: '/api/system/shop/account/edit',
    method: 'post',
    data: data
  })
}

// 添加商家账户
export function addCoountRequest (data) {
  return service({
    url: '/api/system/shop/account/add',
    method: 'post',
    data: data
  })
}

// 商家账户列表查询
export function searchAccountRequest (data) {
  return service({
    url: '/api/system/shop/account/list',
    method: 'post',
    data: data
  })
}

// 单个商家账户列表查询
export function searchOneAccountRequest (data) {
  return service({
    url: '/api/system/shop/account/getOne',
    method: 'post',
    data: data
  })
}
