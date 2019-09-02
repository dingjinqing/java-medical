import service from '@/util/request.js'

// 商品分类查询
export function getSortList (data) {
  return service({
    url: `/api/admin/goods/sort/list`,
    method: 'post',
    data: data
  })
}
// 商品分类删除
export function deleteSort (data) {
  return service({
    url: `/api/admin/goods/sort/delete`,
    method: 'post',
    data: data
  })
}
