import service from '@/util/request.js'

// 商家账号信息
export function accountInfoRequest () {
  return service({
    url: '/api/system/shop/account/edit',
    method: 'post'
  })
}

// 添加店铺时验证手机号是否已经存在
export function confirmMobileRequest (data) {
  return service({
    url: '/api/system/shop/check/mobile',
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

// 添加店铺
export function addShopRequest (data) {
  return service({
    url: '/api/system/shop/add',
    method: 'post'
  })
}
