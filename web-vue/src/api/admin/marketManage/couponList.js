import service from '@/util/request.js'

// 普通优惠券列表
export function couponList (data) {
  return service({
    url: '/api/admin/coupon/list',
    method: 'post',
    data: data
  })
}
