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

// 会员卡充值明细列表
export function memberpCarRecharge (data) {
  return service({
    url: '/api/admin/user/card/charge/order',
    method: 'post',
    data: data
  })
}

// 会员卡续费交易数据
export function getRenewPurchase (data) {
  return service({
    url: '/api/admin/user/card/renew/analysis',
    method: 'post',
    data: data
  })
}

// 会员卡续费交易数据
export function getRechargePurchase (data) {
  return service({
    url: '/api/admin/user/card/charge/analysis',
    method: 'post',
    data: data
  })
}

// 会员卡购买列表导出条数查询
export function queryPurchaseExportNum (data) {
  return service({
    url: '/api/admin/order/member_card/export/rows',
    method: 'post',
    data: data
  })
}
// 会员卡购买列表导出
export function purchaseExport (data) {
  return service({
    url: '/api/admin/order/member_card/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}

// 会员卡续费记录列表导出
export function renewExport (data) {
  return service({
    url: '/api/admin/user/card/renew/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}

// 会员卡充值记录列表导出
export function chargeExport (data) {
  return service({
    url: '/api/admin/user/card/charge/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
