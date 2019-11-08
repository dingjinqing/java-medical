import service from '@/util/request.js'

// 用户画像的用户分布统计
export function getPortraitRequest (data) {
  return service({
    url: '/api/admin/summary/portrait/portrait',
    method: 'post',
    data: data
  })
}
