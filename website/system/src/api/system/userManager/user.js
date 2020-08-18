import request from '@/util/request'

// 问题反馈列表接口
export function loadUserListApi (data) {
  return request({
    url: '/api/system/user/page/list',
    method: 'post',
    data: data
  })
}
