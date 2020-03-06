import service from '@/util/request.js'

// 表单装修保存接口
export function formDecorationAdd (data) {
  return service({
    url: '/api/admin/formstatistics/addforminfo',
    method: 'post',
    data: data
  })
}

// 分页查询表单信息
export function formListQuery (data) {
  return service({
    url: '/api/admin/formstatistics/selectforminfo',
    method: 'post',
    data: data
  })
}
// 编辑表单请求接口
export function editFormListQuery (data) {
  return service({
    url: '/api/admin/formstatistics/getformdetailinfo',
    method: 'post',
    data: data
  })
}

// 表单的发布、删除和关闭接口
export function delCloseListQuery (data) {
  return service({
    url: '/api/admin/formstatistics/changeFormStatus',
    method: 'post',
    data: data
  })
}

// 表单复制
export function copyFormQuery (data) {
  return service({
    url: '/api/admin/formstatistics/copyForm',
    method: 'post',
    data: data
  })
}

// 表单更新接口
export function upDataFormQuery (data) {
  return service({
    url: '/api/admin/formstatistics/updateforminfo',
    method: 'post',
    data: data
  })
}

// 分享表单
export function shareFormQuery (data) {
  return service({
    url: '/api/admin/formstatistics/shareForm',
    method: 'post',
    data: data
  })
}
// 反馈列表接口
export function feedBackListQuery (data) {
  return service({
    url: '/api/admin/formstatistics/feedBackList',
    method: 'post',
    data: data
  })
}

// 表单列表反馈导出
export function exportFeedBackListQuery (data) {
  return service({
    url: '/api/admin/formstatistics/export2Excel',
    method: 'post',
    data: data
  })
}

// 表单列表反馈信息详情
export function feedBackListDetailQuery (data) {
  return service({
    url: '/api/admin/formstatistics/feedBackDetail',
    method: 'post',
    data: data
  })
}

// 表单列表反馈统计接口
export function feedbackStatisticsQuery (data) {
  return service({
    url: '/api/admin/formstatistics/feedBackStatistics',
    method: 'post',
    data: data
  })
}
