import service from '@/util/request.js'

// 查询定向发券列表
export function couponGiftList (data) {
  return service({
    url: '/api/admin/coupon/give/list',
    method: 'post',
    data: data
  })
}
