import service from '@/util/request.js'

// 优惠券礼包活动列表
export function couponPackageList (data) {
  return service({
    url: '/api/admin/market/couponpack/list',
    method: 'post',
    data: data
  })
}

// 添加优惠券礼包活动
export function addCouponPackage (data) {
  return service({
    url: `/api/admin/market/couponpack/add`,
    method: 'post',
    data: data
  })
}

// 更新优惠券礼包活动
export function updateCouponPackage (data) {
  return service({
    url: `/api/admin/market/couponpack/update`,
    method: 'post',
    data: data
  })
}

// 删除优惠券礼包活动
export function deleteCouponPackage (data) {
  return service({
    url: `/api/admin/market/couponpack/del`,
    method: 'post',
    data: data
  })
}
