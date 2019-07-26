import service from '@/util/request.js'

// 商品分页查询
export function brandAllGetRequest (data) {
  return service({
    url: '/api/admin/goods/brand/list',
    method: 'post',
    data: data
  })
}

// 添加品牌
export function brandAddGetRequest (data) {
  return service({
    url: '/api/admin/goods/brand/add',
    method: 'post',
    data: data
  })
}

// 商品品牌删除
export function brandDeleteGetRequest (data) {
  return service({
    url: '/api/admin/goods/brand/delete',
    method: 'post',
    data: data
  })
}

// 商品品牌修改
export function brandUpdateGetRequest (data) {
  return service({
    url: '/api/admin/goods/brand/update',
    method: 'post',
    data: data
  })
}

// 根据ID查询品牌
export function brandSelectGetRequest (data) {
  return service({
    url: '/api/admin/goods/brand/select',
    method: 'post',
    data: data
  })
}

// 品牌分类下拉框数据获取
export function classificationSelectRequest () {
  return service({
    url: '/api/admin/goods/brand/classify/name/list',
    method: 'get'
  })
}

// 修改品牌分类
export function modifyGrandRequest (data) {
  return service({
    url: '/api/admin/goods/brand/classify/update',
    method: 'post',
    data: data
  })
}

export function test (data) {
  return service({
    url: '/api/admin/market/bargain/record/list/export',
    method: 'post',
    data: data
  })
}
