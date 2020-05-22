import service from '@/util/request.js'

// 查询已绑定过的服务号
export function serviceAuthListRequest (data) {
  return service({
    url: '/api/admin/public/service/auth/list',
    method: 'post',
    data: data
  })
}

// 公众号绑定
export function authorizationRequest () {
  return service({
    url: '/api/admin/official/account/authorization',
    method: 'get'
  })
}

// 查询单个服务号详情
export function oneListRequest (data) {
  return service({
    url: '/api/admin/public/service/auth/oneList',
    method: 'post',
    data: data
  })
}

// 提现配置
export function payManageRequest (data) {
  return service({
    url: '/api/admin/public/service/auth/payManage',
    method: 'post',
    data: data
  })
}

// 账户设置的查询接口
export function queryShopRequest (riid) {
  return service({
    url: '/api/admin/account/manage/query',
    method: 'post',
    data: riid
  })
}
