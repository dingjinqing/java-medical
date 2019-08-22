import service from '@/util/request.js'

// 平台分类-根据父ID获取下级分类
export function selectPlatformClassification (parentId) {
  return service({
    url: `/api/admin/decorate/cate/child?parentId=${parentId}`,
    method: 'get'
  })
}
// 选择平台分类
export function cateList () {
  return service({
    url: `/api/admin/decorate/cate/list`,
    method: 'get'
  })
}
// 商品新增，发送请求分为有sku和无sku两个示例
export function goodsList (data) {
  return service({
    url: `/api/admin/goods/add`,
    method: 'post',
    data
  })
}
// 全部商品查询
export function getGoodsList (data) {
  return service({
    url: `/api/admin/goods/list`,
    method: 'post',
    data
  })
}
