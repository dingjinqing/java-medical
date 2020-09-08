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
    url: '/api/store/order/star',
    method: 'post',
    data
  })
}
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
// 设置订单备注
export function notes (data) {
  return service({
    url: '/api/store/order/sellerRemark',
    method: 'post',
    data: data
  })
}

// 关闭操作
export function close (data) {
  return service({
    url: '/api/store/order/close',
    method: 'post',
    data: data
  })
}
// 完成情操作
export function finish (data) {
  return service({
    url: '/api/store/order/finish',
    method: 'post',
    data: data
  })
}
// 核销操作
export function verify (data) {
  return service({
    url: '/api/store/order/checkVerifyCode',
    method: 'post',
    data: data
  })
}
// 发货查询
export function deliveryInfo (data) {
  return service({
    url: '/api/store/order/shipGoods',
    method: 'post',
    data: data
  })
}
// 发货
export function delivery (data) {
  return service({
    url: '/api/store/order/ship',
    method: 'post',
    data: data
  })
}
