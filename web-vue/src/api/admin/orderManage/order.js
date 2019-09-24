import service from '@/util/request.js'

// 全部订单
export function list (data) {
  return service({
    url: '/api/admin/order/list',
    method: 'post',
    data: data
  })
}
// 订单详情
export function info (data) {
  return service({
    url: '/api/admin/order/get',
    method: 'post',
    data: data
  })
}
// 卖家备注
export function notes (data) {
  return service({
    url: '/api/admin/order/sellerRemark',
    method: 'post',
    data: data
  })
}
// 标星
export function star (data) {
  return service({
    url: '/api/admin/order/star',
    method: 'post',
    data: data
  })
}
// 发货查询
export function deliveryInfo (data) {
  return service({
    url: '/api/admin/order/shipGoods',
    method: 'post',
    data: data
  })
}
// 发货
export function delivery (data) {
  return service({
    url: '/api/admin/order/ship',
    method: 'post',
    data: data
  })
}
// 买单订单
export function store (data) {
  return service({
    url: '/api/admin/order/store/list',
    method: 'post',
    data: data
  })
}
// 买单订单详情
export function storeInfo (data) {
  return service({
    url: '/api/admin/order/store/get',
    method: 'post',
    data: data
  })
}
// 退款订单详情
export function returnInfo (data) {
  return service({
    url: '/api/admin/order/return/get',
    method: 'post',
    data: data
  })
}
