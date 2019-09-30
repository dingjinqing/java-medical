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
// 商品分类新增
export function addGoodsSort (data) {
  return service({
    url: `/api/admin/goods/sort/add`,
    method: 'post',
    data: data
  })
}
// 商品分类修改
export function updateGoodsSort (data) {
  return service({
    url: `/api/admin/goods/sort/update`,
    method: 'post',
    data: data
  })
}
// 商品分类详情查询
export function getGoodsSort (sortId) {
  return service({
    url: `/api/admin/goods/sort/get/${sortId}`,
    method: 'get'
  })
}
