import service from '@/util/request.js'

// 砍价活动列表
export function bargainList (data) {
  return service({
    url: '/api/admin/market/bargain/list',
    method: 'post',
    data: data
  })
}

// 添加砍价活动
export function addBargain (data) {
  return service({
    url: `/api/admin/market/bargain/add`,
    method: 'post',
    data: data
  })
}

// 更新砍价活动
export function updateBargain (data) {
  return service({
    url: `/api/admin/market/bargain/update`,
    method: 'post',
    data: data
  })
}

// 删除砍价活动
export function deleteBargain (data) {
  return service({
    url: `/api/admin/market/bargain/del`,
    method: 'post',
    data: data
  })
}

// 取砍价取单日可帮助砍价的次数
export function getDailyCutTimes () {
  return service({
    url: `/api/admin/market/bargain/cut/times/get`,
    method: 'get'
  })
}

// 设置砍价取单日可帮助砍价的次数
export function setDailyCutTimes (dailyCutTimes) {
  return service({
    url: `/api/admin/market/bargain/cut/times/set?dailyCutTimes=${dailyCutTimes}`,
    method: 'get'
  })
}

// 取活动分享二维码
export function getBargainShareCode (id) {
  return service({
    url: `/api/admin/market/bargain/share?id=${id}`,
    method: 'get'
  })
}

// 取单个砍价活动信息
export function getBargainByIsd (data) {
  return service({
    url: `/api/admin/market/bargain/get`,
    method: 'post',
    data: data
  })
}

// 砍价订单列表
export function getBargainOrderList (data) {
  return service({
    url: '/api/admin/market/bargain/order',
    method: 'post',
    data: data
  })
}

// 发起砍价的用户列表
export function getRecordPageList (data) {
  return service({
    url: '/api/admin/market/bargain/record/list',
    method: 'post',
    data: data
  })
}

// 导出发起砍价的用户列表
export function exportBargainRecordList (data) {
  return service({
    url: '/api/admin/market/bargain/record/list/export',
    method: 'post',
    data: data
  })
}

// 帮忙砍价的用户列表
export function getBargainUserPageList (data) {
  return service({
    url: '/api/admin/market/bargain/record/detail',
    method: 'post',
    data: data
  })
}

// 导出帮忙砍价的用户列表
export function exportBargainUserList (data) {
  return service({
    url: '/api/admin/market/bargain/user/list/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}

// 砍价效果分析
export function getRecordAnalysisData (data) {
  return service({
    url: '/api/admin/market/bargain/analysis',
    method: 'post',
    data: data
  })
}

// 砍价拉新用明细
export function getBargainSourceUserList (data) {
  return service({
    url: '/api/admin/market/bargain/source',
    method: 'post',
    data: data
  })
}

// 弹窗选择砍价活动
export function getDialogBargainList (data) {
  return service({
    url: '/api/admin/decorate/bargain/list',
    method: 'post',
    data: data
  })
}

// 砍价用户-导出数据
export function exportBargainUserData (data) {
  return service({
    url: '/api/admin/market/bargain/record/list/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
