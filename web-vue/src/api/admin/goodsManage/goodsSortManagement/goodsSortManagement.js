import service from '@/util/request.js'

// 商品分类查询
export function getGoodsSortList (data) {
  return service({
    url: `/api/admin/goods/sort/list`,
    method: 'post',
    data: data
  })
}
// 商品分类删除
export function deleteGoodsSort (data) {
  return service({
    url: `/api/admin/goods/sort/delete`,
    method: 'post',
    data: data
  })
}
