import service from '@/util/request.js'

// 商品分类分类查询
export function getSortList (data) {
  return service({
    url: '/api/admin/goods/sort/list',
    method: 'post',
    data
  })
}
// 商品分类新增
export function addSort (data) {
  return service({
    url: '/api/admin/goods/sort/add',
    method: 'post',
    data
  })
}
// 删除商品分类
export function deleteSort (data) {
  return service({
    url: '/api/admin/goods/sort/delete',
    method: 'post',
    data
  })
}
