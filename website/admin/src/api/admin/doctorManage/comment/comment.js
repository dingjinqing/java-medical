import service from '@/util/request.js'

// 获取医师列表
export function getCommentList (data) {
  return service({
    url: '/api/admin/doctor/comment/list',
    method: 'post',
    data: data
  })
}

// 是否自动审核
export function ifAuditAuto (data) {
  return service({
    url: '/api/admin/doctor/comment/audit/auto',
    method: 'post',
    data: data
  })
}

// 获取是否自动审核
export function getAuditAuto (data) {
  return service({
    url: '/api/admin/doctor/comment/audit/auto/get',
    method: 'post',
    data: data
  })
}

// 医师评论置顶
export function commentUntop (data) {
  return service({
    url: '/api/admin/doctor/comment/untop',
    method: 'post',
    data: data
  })
}

// 删除评价
export function deleteComment (data) {
  return service({
    url: '/api/admin/doctor/comment/delete',
    method: 'post',
    data: data
  })
}
// 删除医师回复
export function deleteDoctorComment (data) {
  return service({
    url: '/api/admin/doctor/comment/reply/delete',
    method: 'post',
    data: data
  })
}

// 审核评论
export function aduitComment (data) {
  return service({
    url: '/api/admin/doctor/comment/audit',
    method: 'post',
    data: data
  })
}
