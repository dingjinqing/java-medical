import service from '@/util/request.js'

// 普通优惠券列表
export function couponList (data) {
  return service({
    url: '/api/admin/coupon/list',
    method: 'post',
    data: data
  })
}

// 停用
export function pauseCoupon (data) {
  return service({
    url: `/api/admin/coupon/pause?couponId=${data}`,
    method: 'get',
    data: data
  })
}

// 删除
export function deleteCoupon (data) {
  return service({
    url: `/api/admin/coupon/delete?couponId=${data}`,
    method: 'get',
    data: data
  })
}

// 添加保存优惠券
export function saveCoupon (data) {
  return service({
    url: '/api/admin/coupon/add',
    method: 'post',
    data: data
  })
}

// 优惠券领取明细
export function couponGetDetail (data) {
  return service({
    url: '/api/admin/coupon/get/detail',
    method: 'post',
    data: data
  })
}

// 编辑获取单条记录信息
export function updateCoupon (data) {
  return service({
    url: `/api/admin/coupon/update/info?couponId=${data}`,
    method: 'get',
    data: data
  })
}

// 优惠券详情
export function coupondetail (data) {
  return service({
    url: `/api/admin/coupon/update/info?couponId=${data}`,
    method: 'get'
  })
}
