import request from '@/util/request'

// system 版本列表查询
export function versionListRequest (data) {
  return request({
    url: '/api/system/version/list',
    method: 'post',
    data: data
  })
}

// 默认的版本权限
export function versionDefRequest () {
  return request({
    url: `/api/system/version/getList`,
    method: 'get'
  })
}

// 获取单个版本的详情
export function getOneVersionRequest (data) {
  return request({
    url: `/api/system/version/getOne/${data}`,
    method: 'get'
  })
}

// 编辑店铺版本权限
export function editVersionRequest (data) {
  return request({
    url: `/api/system/version/edit`,
    method: 'post',
    data: data
  })
}

// 展示店铺的版本
export function showShopVersionRequest (data) {
  return request({
    url: `/api/system/version/show`,
    method: 'post',
    data: data
  })
}
