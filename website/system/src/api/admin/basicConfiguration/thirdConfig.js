import service from '@/util/request.js'

export function getThirdAuth (data) {
  return service({
    url: '/api/admin/config/third/auth/get',
    data,
    method: 'post'
  })
}

// 授权
export function authorizeThird (data) {
  return service({
    url: '/api/admin/config/third/auth/enable',
    data,
    method: 'post'
  })
}

// 重置sessionKey
export function resetsessionkey (data) {
  return service({
    url: '/api/admin/config/third/auth/resetsessionkey',
    data,
    method: 'post'
  })
}

// 推送配置：自提订单核销后推送、同城配送订单已收货后推送
export function pushSetApi (data) {
  return service({
    url: '/api/admin/config/third/auth/push/set',
    data,
    method: 'post'
  })
}

// 保存appKey
export function saveAppKeyApi (data) {
  return service({
    url: '/api/admin/config/third/auth/appkey/save',
    data,
    method: 'post'
  })
}

// 切换Erp版本
export function switchVersionApi (data) {
  return service({
    url: '/api/admin/config/third/auth/erp/product',
    data,
    method: 'post'
  })
}

// 同步POS数据
export function syncPosApi (data) {
  return service({
    url: '/api/admin/config/third/auth/pos/sync',
    data,
    method: 'post'
  })
}
