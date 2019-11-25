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
    url: '/api/admin/decorate/lottery/list',
    method: 'get'
  })
}
