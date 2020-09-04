import service from '@/util/request.js'

// 获取门店订单列表
export function getOrderList (data) {
  return service({
    url: '/api/store/order/list',
    method: 'post',
    data
  })
}
// 标星
export function star (data) {
  return service({
    url: 'api/store/order/star',
    method: 'post',
    data
  })
}
// 标星
export function info (data) {
  return service({
    url: '/api/store/order/get',
    method: 'post',
    data
  })
}
// 查询售后订单中订单信息简略
export function getOrderBrief (data) {
  return service({
    url: '/api/store/order/simple',
    method: 'post',
    data: data
  })
}
// 退款订单详情
export function returnInfo (data) {
  return service({
    url: '/api/store/order/return/get',
    method: 'post',
    data: data
  })
}
// 退款订单详情操作
export function handleReturnInfo (data) {
  return service({
    url: '/api/store/order/refund',
    method: 'post',
    data: data
  })
}
// 查询商家默认收获地址
export function getDefaultAddress (data) {
  return service({
    url: '/api/store/config/trade/getdefaultaddress',
    method: 'post',
    data: data
  })
}
