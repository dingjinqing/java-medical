import service from '@/util/request.js'

// 查询团购列表
export function groupBuyList (data) {
  return service({
    url: '/api/admin/market/groupbuy/list',
    method: 'post',
    data: data
  })
}

// 添加团购活动
export function getGroupBuyDetail (data) {
  return service({
    url: '/api/admin/market/groupbuy/detail',
    method: 'post',
    data: data
  })
}

// 添加团购活动
export function addGroupBuyActivity (data) {
  return service({
    url: '/api/admin/market/groupbuy/add',
    method: 'post',
    data: data
  })
}

// 跟新团购活动
export function updateGroupBuy (data) {
  return service({
    url: '/api/admin/market/groupbuy/update',
    method: 'post',
    data: data
  })
}
// 改变活动状态
export function changeStatusActivity (data) {
  return service({
    url: '/api/admin/market/groupbuy/change/status',
    method: 'post',
    data: data
  })
}
// 删除活动
export function deleteGroupBuyActivity (data) {
  return service({
    url: '/api/admin/market/groupbuy/delete',
    method: 'post',
    data: data
  })
}
// 详情明细列表
export function detailGroupBuy (data) {
  return service({
    url: '/api/admin/market/groupbuy/detail/list',
    method: 'post',
    data: data
  })
}
// 详情明细列表
export function groupBuyOrderList (data) {
  return service({
    url: '/api/admin/market/groupbuy/order/list',
    method: 'post',
    data: data
  })
}
// 退款失败订单
export function refundFailOrderList (data) {
  return service({
    url: '/api/admin/order/list',
    method: 'post',
    data: data
  })
}
// 活动效果数据
export function groupBuyAnalysis (data) {
  return service({
    url: '/api/admin/market/groupbuy/analysis',
    method: 'post',
    data: data
  })
}
// 活动新用户明细
export function newUserList (data) {
  return service({
    url: '/api/admin/market/groupbuy/user/list',
    method: 'post',
    data: data
  })
}

// 分享
export function shareActivity (data) {
  return service({
    url: '/api/admin/market/groupbuy/share',
    method: 'post',
    data: data
  })
}
