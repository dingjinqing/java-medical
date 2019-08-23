import service from '@/util/request.js'
// 组团瓜分积分
export function groupIntegrationList (data) {
  return service({
    url: '/api/admin/market/integration/list',
    method: 'post',
    data: data
  })
}
// 改变瓜分积分活动状态（禁用0/启用1）
export function changeGroupIntegrationStatus (data) {
  return service({
    url: '/api/admin/market/integration/change/status',
    method: 'post',
    data: data
  })
}
// 删除瓜分积分活动
export function delGroupIntegration (data) {
  return service({
    url: '/api/admin/market/integration/delete/' + data,
    method: 'post',
    data: {}
  })
}
