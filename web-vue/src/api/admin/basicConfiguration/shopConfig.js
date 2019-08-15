import service from '@/util/request.js'

//  我已有小程序，一键授权
export function grantAuthorizationRequest () {
  return service({
    url: '/api/admin/start/auth',
    method: 'get'
  })
}

//  我已有小程序，一键授权
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
