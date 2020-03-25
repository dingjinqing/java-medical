import service from '@/util/request.js'

// 全部评价查询
export function getCommentList (data) {
  return service({
    url: `/api/admin/goods/comment/list`,
    method: 'post',
    data: data
  })
}
// 评价审核查询
export function getCommentCheckList (data) {
  return service({
    url: `/api/admin/goods/comment/checklist`,
    method: 'post',
    data: data
  })
}
// 删除商品评论
export function goodsCommentDelete (data) {
  return service({
    url: `/api/admin/goods/comment/delete`,
    method: 'post',
    data: data
  })
}
// 商品评论回复
export function CommentAnswer (data) {
  return service({
    url: `/api/admin/goods/comment/answer`,
    method: 'post',
    data: data
  })
}
// 评论通过审核
export function CommentPass (data) {
  return service({
    url: `/api/admin/goods/comment/passflag`,
    method: 'post',
    data: data
  })
}
// 评论拒绝审核
export function CommentRefuse (data) {
  return service({
    url: `/api/admin/goods/comment/refuseflag`,
    method: 'post',
    data: data
  })
}
// 修改审核要求
export function CommentCheckConfig (data) {
  return service({
    url: `/api/admin/goods/comment/checkconfig`,
    method: 'post',
    data: data
  })
}
// 修改隐藏未评价内容
export function CommentSwitchConfig (data) {
  return service({
    url: `/api/admin/goods/comment/switchconfig`,
    method: 'post',
    data: data
  })
}
// 评价商品列表
export function CommentGoodsList (data) {
  return service({
    url: `/api/admin/goods/comment/addlist`,
    method: 'post',
    data: data
  })
}
// 商品添加评论
export function goodsAddComment (data) {
  return service({
    url: `/api/admin/goods/comment/addcomm`,
    method: 'post',
    data: data
  })
}
// 删除评论回复
export function delAnswer (data) {
  return service({
    url: `/api/admin/goods/comment/delAnswer`,
    method: 'post',
    data: data
  })
}
// 获得评价审核配置
export function getCommentConfig () {
  return service({
    url: `/api/admin/goods/comment/getconfig`,
    method: 'get'
  })
}
// 获得评价开关配置
export function getCommentSwitch () {
  return service({
    url: `/api/admin/goods/comment/getswitch`,
    method: 'get'
  })
}
// 设置置顶
export function setTop (data) {
  return service({
    url: `/api/admin/goods/comment/setTop`,
    method: 'post',
    data: data
  })
}
// 取消置顶
export function cancelTop (data) {
  return service({
    url: `/api/admin/goods/comment/cancelTop`,
    method: 'post',
    data: data
  })
}
