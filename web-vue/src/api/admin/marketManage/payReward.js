/**
 * 支付有礼模块接口
 **/

import service from '@/util/request.js'

// 分享有礼列表
export function payRewardList (data) {
  return service({
    url: '/api/admin/market/payaward/list',
    method: 'post',
    data: data
  })
}

// 添加支付有礼活动
export function addPayRewardAct (data) {
  return service({
    url: '/api/admin/market/payaward/add',
    method: 'post',
    data: data
  })
}
