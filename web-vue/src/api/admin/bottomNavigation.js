import service from '@/util/request.js'

// 底部导航查询
export function bottomGetRequest () {
  return service({
    url: '/api/admin/user/center/config/get',
    method: 'get'
  })
}

// 底部导航修改
export function bottomUpdateRequest (data) {
  return service({
    url: '/api/admin/user/center/config/update',
    method: 'post',
    data: data
  })
}
