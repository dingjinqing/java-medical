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
