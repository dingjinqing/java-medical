import service from '@/util/request.js'

// 普通优惠券选择弹窗数据接口
export function getCouponSelectComponentData (data) {
  return service({
    url: '/api/admin/coupon/give/pop',
    method: 'post',
    data: data
  })
}
