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
