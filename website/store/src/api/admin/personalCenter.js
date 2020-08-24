import service from '@/util/request.js'

// 个人中心信息查询
export function personalGetRequest () {
  return service({
    url: '/api/admin/user/center/config/get',
    method: 'get'
  })
}

// 个人中心信息保存
export function personalSaveRequest (data) {
  return service({
    url: '/api/admin/user/center/config/update',
    method: 'post',
    data: data
  })
}
