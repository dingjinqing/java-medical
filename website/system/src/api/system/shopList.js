import service from '@/util/request.js'

// 店铺列表查询
export function shopSearchRequest (data) {
  return service({
    url: '/api/system/shop/list',
    method: 'post',
    data: data
  })
}

// 添加店铺
export function newShopRequest (data) {
  return service({
    url: '/api/system/shop/add',
    method: 'post',
    data: data
  })
}

// 校验手机号是否重复
export function checkMobileRequest (data) {
  return service({
    url: '/api/system/shop/check/mobile',
    method: 'post',
    data: data
  })
}

// 更改是否禁用
export function upEnableRequest (data) {
  return service({
    url: '/api/system/shop/upEnable',
    method: 'post',
    data: data
  })
}

// 更改是否隐藏底部导航
export function upBottomRequest (data) {
  return service({
    url: '/api/system/shop/upBottom',
    method: 'post',
    data: data
  })
}
// 单个店铺查询
export function searchAccountByOneRequest (data) {
  return service({
    url: `/api/system/shop/editList/${data}`,
    method: 'get'
  })
}

// 编辑店铺
export function editShopRequest (data) {
  return service({
    url: '/api/system/shop/edit',
    method: 'post',
    data: data
  })
}

// 续费店铺
export function renewShopRequest (data) {
  return service({
    url: '/api/system/shop/renew',
    method: 'post',
    data: data
  })
}

// 店铺续费列表
export function renewShopListRequest (data) {
  return service({
    url: '/api/system/shop/renew/queryList',
    method: 'post',
    data: data
  })
}

// 查询所有可用店铺列表
export function loadAllShopInfoApi () {
  return service({
    url: '/api/system/shop/getList',
    method: 'get'
  })
}
