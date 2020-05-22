import service from '@/util/request.js'

// 分页查询商品标签
export function getGoodsLabelPageList (data) {
  return service({
    url: '/api/admin/goods/label/page/list',
    method: 'post',
    data: data
  })
}

// 商品标签添加
export function addGoodsLabel (data) {
  return service({
    url: '/api/admin/goods/label/add',
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
// 商品标签查询
export function getGoodsLabel (labelId) {
  return service({
    url: `/api/admin/goods/label/select/${labelId}`,
    method: 'get'
  })
}
// 判断商品标签名称是否不重复可以使用
export function isGoodsLabelNameOk (data) {
  let url = ''
  if (data.isUpdate) {
    url = `/api/admin/goods/label/name/exist/${data.goodsLabelName}/1/${data.id}`
  } else {
    url = `/api/admin/goods/label/name/exist/${data.goodsLabelName}/0/-1`
  }
  return service({
    url: url,
    method: 'get'
  })
}
