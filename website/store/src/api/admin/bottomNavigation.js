import service from '@/util/request.js'

// 底部导航查询
export function bottomGetRequest () {
  return service({
    url: '/api/admin/bottom/get',
    method: 'get'
  })
}

// 底部导航修改
export function bottomUpdateRequest (data) {
  return service({
    url: '/api/admin/bottom/update',
    method: 'post',
    data: data
  })
}
