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

// 更新支付有礼活动
export function addPayRewardUpdate (data) {
  return service({
    url: '/api/admin/market/payaward/update',
    method: 'post',
    data: data
  })
}

// 删除支付有礼活动
export function delPayRewardAct (data) {
  return service({
    url: '/api/admin/market/payaward/delete',
    method: 'post',
    data: data
  })
}

// 支付有礼活动停用启用
export function actSwitch (data) {
  return service({
    url: '/api/admin/market/payaward/change/status',
    method: 'post',
    data: data
  })
}

// 支付有礼明细列表
export function payRewardDetails (data) {
  return service({
    url: '/api/admin/market/payaward/record/list',
    method: 'post',
    data: data
  })
}
