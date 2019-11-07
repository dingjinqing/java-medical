import service from '@/util/request.js'

// 全部商品推荐查询
export function getRecommendList (data) {
  return service({
    url: `/api/admin/goods/recommend/list`,
    method: 'post',
    data: data
  })
}
// 添加商品推荐
export function addRecommend (data) {
  return service({
    url: `/api/admin/goods/recommend/add`,
    method: 'post',
    data: data
  })
}
// 删除商品推荐
export function delRecommend (data) {
  return service({
    url: `/api/admin/goods/recommend/delete/${data}`,
    method: 'post'
  })
}
// 更新商品推荐
export function updateRecommend (data) {
  return service({
    url: `/api/admin/goods/recommend/update`,
    method: 'post',
    data: data
  })
}
// 查找指定商品推荐
export function getRecommendInfo (data) {
  return service({
    url: `/api/admin/goods/recommend/select/${data}`,
    method: 'get'
  })
}
