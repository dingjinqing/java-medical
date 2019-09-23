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
