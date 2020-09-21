import service from '@/util/request.js'

// 获取医师科室推荐配置
export function getRecommend (data) {
  return service({
    url: '/api/admin/config/shop/doctor/recommend/get',
    method: 'get'
  })
}

// 保存医师推荐配置
export function saveRecommend (data) {
  return service({
    url: '/api/admin/config/shop/doctor/recommend/update',
    method: 'post',
    data: data
  })
}
