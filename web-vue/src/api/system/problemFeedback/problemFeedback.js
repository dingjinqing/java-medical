import request from '@/util/request'

// 问题反馈列表接口
export function problemFeedbackList (data) {
  return request({
    url: '/api/system/index/feedback/list',
    method: 'post',
    data: data
  })
}

// system 退出接口
export function logOut (params) {
  return request({
    url: `/api/system/logout`,
    method: 'get',
    data: params
  })
}
