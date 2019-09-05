/* 商品品牌接口 */
import service from '@/util/request.js'

// 品牌下拉框
export function goodsBrandListApi () {
  return service({
    url: `/api/admin/goods/brand/name/list`,
    method: 'get'
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
