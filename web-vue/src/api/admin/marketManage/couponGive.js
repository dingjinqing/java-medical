import service from '@/util/request.js'

// 普通优惠券选择弹窗数据接口
export function getCouponSelectComponentData (data) {
  return service({
    url: '/api/admin/coupon/give/pop',
    method: 'post',
    data: data
  })
}
// 查询定向发券列表
export function couponGiveList (data) {
  return service({
    url: '/api/admin/coupon/give/list',
    method: 'post',
    data: data
  })
}

// 定向发券领取明细
export function receiveDetails (data) {
  return service({
    url: '/api/admin/coupon/give/detail',
    method: 'post',
    data: data
  })
}

// 定向发券废除优惠券
export function stopCoupon (data) {
  return service({
    url: '/api/admin/coupon/give/delete',
    method: 'post',
    data: data
  })
}

// 添加定向发券活动
export function addActivity (data) {
  return service({
    url: '/api/admin/coupon/give/grant',
    method: 'post',
    data: data
  })
}
