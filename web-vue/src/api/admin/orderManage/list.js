import service from '@/util/request.js'

// 全部订单
export function allList (data) {
  return service({
    url: '/api/admin/order/list',
    method: 'post',
    data: data
  })
}
