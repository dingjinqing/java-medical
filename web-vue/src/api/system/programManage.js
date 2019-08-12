import request from '@/util/request'

//  同步微信开放平台小程序模板
export function synchronizationRequest () {
  return request({
    url: '/api/system/mp/version/syn',
    method: 'post'
  })
}

//  小程序版本管理 分页
export function smallProManRequest (data) {
  return request({
    url: '/api/system/mp/version/list',
    method: 'post',
    data: data
  })
}
//  设置为当前使用版本接口
export function setNowVersionRequest (data) {
  return request({
    url: `/api/system/mp/version/set/${data}`,
    method: 'get'
  })
}

// 当前包版本设置
export function setCurrentVersionRequest (data) {
  return request({
    url: '/api/system/mp/package/version',
    method: 'post',
    data: data
  })
}

// 小程序授权列表
export function authListRequest (data) {
  return request({
    url: '/api/system/mp/auth/list',
    method: 'post',
    data: data
  })
}

// 获取小程序版本下拉列表
export function SpinnerListRequest () {
  return request({
    url: '/api/system/mp/version/user/version/list',
    method: 'get'
  })
}

// 小程序版本统计
export function versionStatRequest (data) {
  return request({
    url: '/api/system/mp/version/stat',
    method: 'post',
    data: data
  })
}

// 小程序操作日志
export function operateLogRequest (data) {
  return request({
    url: '/api/system/mp/operate/log/list',
    method: 'post',
    data: data
  })
}

// 小程序授权信息
export function authInformationRequest (data) {
  return request({
    url: `/api/system/mp/get/${data}`,
    method: 'get'
  })
}
