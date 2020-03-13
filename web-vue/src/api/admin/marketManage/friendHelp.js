import service from '@/util/request.js'

// 查询好友助力列表
export function friendHelpList (data) {
  return service({
    url: '/api/admin/market/promote/list',
    method: 'post',
    data: data
  })
}
// 删除好友助力活动
export function deleteActive (data) {
  return service({
    url: '/api/admin/market/promote/delete',
    method: 'post',
    data: data
  })
}
// 启用或停用好友助力活动
export function switchAct (data) {
  return service({
    url: '/api/admin/market/promote/switch',
    method: 'post',
    data: data
  })
}
// 添加好友助力活动
export function addActive (data) {
  return service({
    url: '/api/admin/market/promote/add',
    method: 'post',
    data: data
  })
}
// 好友助力领取明细
export function receiveDetails (data) {
  return service({
    url: '/api/admin/market/promote/receive',
    method: 'post',
    data: data
  })
}
// 好友助力发起明细
export function launchDetails (data) {
  return service({
    url: '/api/admin/market/promote/launch',
    method: 'post',
    data: data
  })
}
// 好友助力参与明细
export function participateDetails (data) {
  return service({
    url: '/api/admin/market/promote/participate',
    method: 'post',
    data: data
  })
}
// 查询单个助力活动信息
export function selectOneInfo (data) {
  return service({
    url: '/api/admin/market/promote/select',
    method: 'post',
    data: data
  })
}
// 编辑好友助力活动信息
export function updateInfo (data) {
  return service({
    url: '/api/admin/market/promote/update',
    method: 'post',
    data: data
  })
}
// 根据prdId得到商品详情
export function getGoodsInfo (data) {
  return service({
    url: '/api/admin/market/promote/goodsInfo',
    method: 'post',
    data: data
  })
}
