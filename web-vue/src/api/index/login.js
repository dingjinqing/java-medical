import request from '@/util/request'

//  登录     -接口

// 登录
export function loginRequest (riid) {
  return request({
    url: '/api/admin/login',
    method: 'post',
    data: riid
  })
}

// 登出
export function loginRequestOut (riid) {
  return request({
    url: '/api/admin/logout',
    method: 'get',
    data: riid
  })
}
// 登出
export function isloginRequest () {
  return request({
    url: '/api/admin/login/isLogin',
    method: 'get'
  })
}
