import request from '@/util/request'

// 问题反馈列表接口
export function problemFeedbackList (data) {
  return request({
    url: '/api/system/index/feedback/list',
    method: 'post',
    data: data
  })
}

// 问题反馈详情接口
export function problemFeedbackDetail (params) {
  return request({
    url: `/api/system/index/feedback/detail`,
    method: 'Post',
    data: params
  })
}
