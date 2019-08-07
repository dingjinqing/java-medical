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
