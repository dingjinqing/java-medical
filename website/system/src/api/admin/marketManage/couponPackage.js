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

// 取活动分享二维码
export function getCouponPackShareCode (id) {
  return service({
    url: `/api/admin/market/couponpack/share?id=${id}`,
    method: 'get'
  })
}

// 取单个优惠券礼包活动信息
export function getCouponPackById (data) {
  return service({
    url: `/api/admin/market/couponpack/get`,
    method: 'post',
    data: data
  })
}

// 优惠券礼包活动订单列表
export function getCouponPackOrderPageList (data) {
  return service({
    url: `/api/admin/market/couponpack/order`,
    method: 'post',
    data: data
  })
}

// 优惠券礼包活动订单列表-导出
export function exportCouponPackOrderList (data) {
  return service({
    url: `/api/admin/market/couponpack/order/export`,
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}

// 优惠券礼包活动领取详情列表
export function getCouponPackDetailPageList (data) {
  return service({
    url: `/api/admin/market/couponpack/detail`,
    method: 'post',
    data: data
  })
}
