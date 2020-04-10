import service from '@/util/request.js'

// 获取店铺基础信息
export function getBaseInfo () {
  return service({
    url: '/api/admin/config/shop/base/get',
    method: 'get'
  })
}

// 保存店铺基础信息
export function updateBasicInfoApi (data) {
  return service({
    url: '/api/admin/config/shop/base/update',
    data: data,
    method: 'post'
  })
}

// 店铺通用信息配置
export function getCommonInfo () {
  return service({
    url: '/api/admin/config/shop/common/get',
    method: 'get'
  })
}

// 店铺通用设置修改
export function updateCommonInfo (data) {
  return service({
    url: '/api/admin/config/shop/common/update',
    data: data,
    method: 'post'
  })
}

//  我已有小程序，一键授权
export function grantAuthorizationRequest () {
  return service({
    url: '/api/admin/start/auth',
    method: 'get'
  })
}

//  授权信息查询
export function queryAuthdritionRequest () {
  return service({
    url: '/api/admin/mp/get',
    method: 'get'
  })
}

//  好物圈查看
export function checkGoodThingRequest () {
  return service({
    url: '/api/admin/wxshopping/list',
    method: 'get'
  })
}

//  好物圈设置
export function setGoodThingRequest (data) {
  return service({
    url: '/api/admin/wxshopping/update',
    method: 'post',
    data: data
  })
}
//  小程序与公众号绑定
export function bindOfficial (data) {
  return service({
    url: `/api/admin/public/service/bind/official/${data}`,
    method: 'get'
  })
}

// 小程序授权信息页面按钮统一处理接口  上传代码并提交审核
export function publishSetRequest (data) {
  return service({
    url: '/api/admin/mp/publish',
    method: 'post',
    data: data
  })
}

// 获取小程序服务条款
export function getMiniProgramServices (data) {
  return service({
    url: `/api/wxapp/order/termsofservice?shopId=${data}`,
    method: 'get'
  })
}
