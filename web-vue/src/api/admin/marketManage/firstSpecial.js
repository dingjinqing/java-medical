import service from '@/util/request.js'

// 首单特惠活动列表
export function firstSpecialList (data) {
  return service({
    url: '/api/admin/market/firstspecial/list',
    method: 'post',
    data: data
  })
}

// 获取首单特惠用户仅可购买活动商品的数量
export function getFirstSpecialLimitGoods () {
  return service({
    url: '/api/admin/market/firstspecial/limit/goods/get',
    method: 'get'
  })
}

// 设置首单特惠用户仅可购买活动商品的数量
export function setFirstSpecialLimitGoods (firstLimitGoods) {
  return service({
    url: `/api/admin/market/firstspecial/limit/goods/set?firstLimitGoods=${firstLimitGoods}`,
    method: 'get'
  })
}

// 添加 首单特惠活动
export function addFirstSpecial (data) {
  return service({
    url: '/api/admin/market/firstspecial/add',
    method: 'post',
    data: data
  })
}

// 取单个首单特惠活动信息
export function getFirstSpecialById (data) {
  return service({
    url: '/api/admin/market/firstspecial/get',
    method: 'post',
    data: data
  })
}

// 更新 首单特惠活动
export function updateFirstSpecial (data) {
  return service({
    url: '/api/admin/market/firstspecial/update',
    method: 'post',
    data: data
  })
}

// 删除 首单特惠活动
export function delFirstSpecial (data) {
  return service({
    url: '/api/admin/market/firstspecial/del',
    method: 'post',
    data: data
  })
}

// 首单特惠订单列表
export function getFirstSpecialOrderList (data) {
  return service({
    url: '/api/admin/market/firstspecial/order',
    method: 'post',
    data: data
  })
}

// 活动订单 取将要导出的行数
export function getExportTotalRows (data) {
  return service({
    url: '/api/admin/market/firstspecial/order/export/rows',
    method: 'post',
    data: data
  })
}

// 订单导出
export function orderListExport (data) {
  return service({
    url: '/api/admin/market/firstspecial/order/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
