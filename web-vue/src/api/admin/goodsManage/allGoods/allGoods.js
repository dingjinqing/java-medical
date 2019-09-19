import service from '@/util/request.js'

// 全部商品查询
export function getGoodsList (data) {
  return service({
    url: `/api/admin/goods/list`,
    method: 'post',
    data: data
  })
}
// 商品删除
export function deleteGoods (data) {
  return service({
    url: `/api/admin/goods/delete`,
    method: 'post',
    data: data
  })
}
// 商品批量处理
export function batchOperateGoods (data) {
  return service({
    url: `/api/admin/goods/batch`,
    method: 'post',
    data: data
  })
}

// 根据商品id修改对应的标签
export function updateLabelByGoodsId (data) {
  return service({
    url: `/api/admin/label/couple/updateByGoodsId`,
    method: 'post',
    data: data
  })
}

// 初始化全部商品顶部下拉框数据
export function getAllGoodsInitValue (data) {
  return service({
    url: `/api/admin/goods/page/init`,
    method: 'post',
    data
  })
}
