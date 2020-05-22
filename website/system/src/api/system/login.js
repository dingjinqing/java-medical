import request from '@/util/request'

// system 登录接口
export function loginRequest (riid) {
  return request({
    url: '/api/system/login',
    method: 'post',
    data: riid
  })
}

// system 退出接口
export function logOut (params) {
  return request({
    url: `/api/system/logout`,
    method: 'get',
    data: params
  })
}
