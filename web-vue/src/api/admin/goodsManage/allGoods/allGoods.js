import service from '@/util/request.js'

// 全部商品查询
export function getGoodsList (data) {
  return service({
    url: `/api/admin/goods/list`,
    method: 'post',
    data: data
  })
}
// 全部商品查询
export function getGoodsProductList (data) {
  return service({
    url: `/api/admin/goods/product/list`,
    method: 'post',
    data: data
  })
}
// 根据商品ID集合查询对应商品信息
export function getGoodsInfosByGoodIds (data) {
  return service({
    url: `/api/admin/goods/info/list`,
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
// 商品规格价格和数量批量修改
export function batchOperateSpecPrdPriceNumber (data) {
  return service({
    url: `/api/admin/goodsPrd/updatePriceNumber`,
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
    data: data
  })
}

// 所选条件过滤出的数据条数
export function getExportTotalRows (data) {
  return service({
    url: `/api/admin/goods/export/rows`,
    method: 'post',
    data: data
  })
}

// 商品导出
export function goodsExport (data) {
  return service({
    url: `/api/admin/goods/export`,
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
