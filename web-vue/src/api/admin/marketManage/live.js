import service from '@/util/request.js'

// 直播列表
export function getLiveList (data) {
  return service({
    url: '/api/admin/market/live/list',
    method: 'post',
    data: data
  })
}

// 单个直播活动商品信息
export function getGoodsList (data) {
  return service({
    url: `/api/admin/market/live/goodList/${data}`,
    method: 'get'
  })
}
