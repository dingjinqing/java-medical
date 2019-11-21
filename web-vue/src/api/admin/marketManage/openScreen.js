import service from '@/util/request.js'

export function getOpenScreenList (data) {
  return service({
    url: '/api/admin/market/activity_reward/list',
    method: 'post',
    data: data
  })
}
