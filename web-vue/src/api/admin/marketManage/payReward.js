import service from '@/util/request.js'

// 分享有礼列表页查询
export function payRewardList (data) {
  return service({
    url: '/api/admin/market/payreward/list',
    method: 'post',
    data: data
  })
}
