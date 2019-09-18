import service from '@/util/request.js'

// 会员卡订单列表
export function getMemberCardOrderList (data) {
  return service({
    url: '/api/admin/order/member_card/list',
    method: 'post',
    data: data
  })
}

// 会员卡订单手动退款
export function refundMemberCardOrder (data) {
  return service({
    url: '/api/admin/order/member_card/refund',
    method: 'post',
    data: data
  })
}

// 优惠券礼包订单列表
export function getCouponPackageOrderList (data) {
  return service({
    url: '/api/admin/order/couponpack/list',
    method: 'post',
    data: data
  })
}

// 优惠券礼包订单手动退款
export function refundCouponPackageOrder (data) {
  return service({
    url: '/api/admin/order/couponpack/refund',
    method: 'post',
    data: data
  })
}
