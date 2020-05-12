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
// 增加瓜分积分活动
export function createGroupIntegration (data) {
  return service({
    url: '/api/admin/market/integration/add',
    method: 'post',
    data: data
  })
}
// 分享活动
export function shareActivity (data) {
  return service({
    url: '/api/admin/market/integration/getqrcode/' + data,
    method: 'post'
  })
}
// 编辑活动
export function editGroupIntegration (data) {
  return service({
    url: '/api/admin/market/integration/update',
    method: 'post',
    data: data
  })
}

// 查询活动内容
export function selectGroupIntegration (id) {
  return service({
    url: `/api/admin/market/integration/select/${id}`,
    method: 'get'
  })
}
// 查询参与活动的用户列表
export function detailGroupIntegration (data) {
  return service({
    url: '/api/admin/market/integration/detail',
    method: 'post',
    data: data
  })
}
// 查询组团瓜分积分 成团明细
export function successGroupIntegration (data) {
  return service({
    url: '/api/admin/market/integration/success',
    method: 'post',
    data: data
  })
}

// 查询组团瓜分积分 效果数据
export function getAnalysisRequest (data) {
  return service({
    url: '/api/admin/market/integration/getAnalysis',
    method: 'post',
    data: data
  })
}
