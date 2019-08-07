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
