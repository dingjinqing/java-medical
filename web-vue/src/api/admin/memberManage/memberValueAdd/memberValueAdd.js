import service from '@/util/request.js'

// 会员卡购买交易数据
export function getMemberSardPurchase (data) {
  return service({
    url: '/api/admin/order/member_card/analysis',
    method: 'post',
    data: data
  })
}
// 优惠券礼包交易数据
export function getCouponpackPurchase (data) {
  return service({
    url: '/api/admin/order/couponpack/analysis',
    method: 'post',
    data: data
  })
}

// 会员卡续费明细列表
export function getRenewalDetails (data) {
  return service({
    url: '/api/admin/user/card/renew/order',
    method: 'post',
    data: data
  })
}
