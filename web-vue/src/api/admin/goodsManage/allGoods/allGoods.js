import service from '@/util/request.js'

// 全部商品查询
export function getGoodsList (data) {
  return service({
    url: `/api/admin/goods/list`,
    method: 'post',
    data: data
  })
}
// 初始化全部商品顶部下拉框数据
export function getAllGoodsInitValue (data) {
  return service({
    url: `/api/admin/goods/page/init`,
    method: 'get'
  })
}
