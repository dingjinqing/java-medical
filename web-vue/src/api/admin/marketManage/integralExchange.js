import service from '@/util/request.js'

// 积分兑换活动分页列表
export function integralExchangeList (data) {
  return service({
    url: '/api/admin/market/integral/convert/list',
    method: 'post',
    data: data
  })
}
