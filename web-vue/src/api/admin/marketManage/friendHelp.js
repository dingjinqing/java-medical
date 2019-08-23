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
