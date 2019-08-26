import service from '@/util/request.js'

// 分享有礼活动首页展示列表
export function getList (data) {
  return service({
    url: '/api/admin/market/sharereward/selectbypage',
    method: 'post',
    data: data
  })
}
// 添加分享有礼活动
export function addShareReward (data) {
  return service({
    url: '/api/admin/market/sharereward/addsharereward',
    method: 'post',
    data: data
  })
}
// 获取分享有礼活动详情
export function getShareRewardInfo (data) {
  return service({
    url: '/api/admin/market/sharereward/getsharerewardinfo/65',
    method: 'get',
    data: data
  })
}
// 更新分享有礼活动
export function updateShareReward (data) {
  return service({
    url: '/api/admin/market/sharereward/updatesharereward',
    method: 'post',
    data: data
  })
}
// 删除-停用-启用分享有礼活动
export function changeActivity (data) {
  return service({
    url: `/api/admin/market/sharereward/changeactivity`,
    method: 'post',
    data: data
  })
}
// 获取分享有礼活动领取明细
export function getReceiveDetail (data) {
  return service({
    url: '/api/admin/market/sharereward/sharereceivedetail',
    method: 'post',
    data: data
  })
}
// 更新每日用户可分享次数上限参数
export function updateDailyLimit (data) {
  return service({
    url: `/api/admin/market/sharereward/updatedailyshareaward/${data}`,
    method: 'get',
    data: data
  })
}
