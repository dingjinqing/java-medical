import service from '@/util/request.js'

// 开屏有礼列表
export function getOpenScreenList (data) {
  return service({
    url: '/api/admin/market/coopen/list',
    method: 'post',
    data: data
  })
}

// 添加开屏有礼
export function addOpenScreen (data) {
  return service({
    url: '/api/admin/market/coopen/add',
    method: 'post',
    data: data
  })
}

// 幸运大抽奖下拉
export function selectPayRewardApi (data) {
  return service({
    url: '/api/admin/market/lottery/usablelist',
    method: 'post'
  })
}

// 开屏有礼详情
export function queryOpenScreen (data) {
  return service({
    url: '/api/admin/market/coopen/detail',
    method: 'post',
    data: data
  })
}

// 更新开屏有礼
export function updateOpenScreen (data) {
  return service({
    url: '/api/admin/market/coopen/update',
    method: 'post',
    data: data
  })
}

// 开屏有礼活动明细
export function detailOpenScreen (data) {
  return service({
    url: '/api/admin/market/coopen/issuelist',
    method: 'post',
    data: data
  })
}

// 停用活动
export function disableOpenScreenApi (data) {
  return service({
    url: '/api/admin/market/coopen/disable',
    method: 'post',
    data: data
  })
}

// 启用活动
export function enableOpenScreenApi (data) {
  return service({
    url: '/api/admin/market/coopen/enable',
    method: 'post',
    data: data
  })
}

// 删除活动
export function deleteOpenScreenApi (data) {
  return service({
    url: '/api/admin/market/coopen/delete',
    method: 'post',
    data: data
  })
}
