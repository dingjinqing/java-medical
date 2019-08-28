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
