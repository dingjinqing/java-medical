/* 商品品牌接口 */
import service from '@/util/request.js'

// 品牌下拉框
export function goodsBrandListApi () {
  return service({
    url: `/api/admin/goods/brand/name/list`,
    method: 'post'
  })
}
// 品牌分页查询接口
export function goodsBrandPageListApi (data) {
  return service({
    url: `/api/admin/goods/brand/list`,
    method: 'post',
    data: data
  })
}
// 品牌分类下拉列表
export function goodsBrandClassifyListApi () {
  return service({
    url: `/api/admin/goods/brand/classify/name/list`,
    method: 'get'
  })
}
