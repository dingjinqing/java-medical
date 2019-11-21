import service from '@/util/request.js'

// 开屏有礼列表
export function getOpenScreenList (data) {
  return service({
    url: '/api/admin/market/activity_reward/list',
    method: 'post',
    data: data
  })
}

// 添加开屏有礼
export function addOpenScreen (data) {
  return service({
    url: '/api/admin/market/activity_reward/add',
    method: 'post',
    data: data
  })
}
