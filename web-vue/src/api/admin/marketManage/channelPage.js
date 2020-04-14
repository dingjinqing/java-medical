import service from '@/util/request.js'

// 砍价活动列表
export function channelList (data) {
  return service({
    url: '/api/admin/market/channel/list',
    method: 'post',
    data: data
  })
}
