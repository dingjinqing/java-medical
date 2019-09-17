import service from '@/util/request.js'

// 会员卡订单列表
export function getMemberCardOrderList (data) {
  return service({
    url: '/api/admin/order/member_card/list',
    method: 'post',
    data: data
  })
}
