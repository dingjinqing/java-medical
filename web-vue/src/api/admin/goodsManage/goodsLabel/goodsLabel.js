import service from '@/util/request.js'

// 分页查询商品标签
export function getGoodsLabelList (data) {
  return service({
    url: '/api/admin/goods/label/list',
    method: 'post',
    data: data
  })
}
// 商品标签删除
export function deleteGoodsLabel (labelId) {
  return service({
    url: `/api/admin/goods/label/delete/${labelId}`,
    method: 'post'
  })
}
// 商品标签修改
export function updateGoodsLabel (data) {
  return service({
    url: `/api/admin/goods/label/update`,
    method: 'post',
    data: data
  })
}
