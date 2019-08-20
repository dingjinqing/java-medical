import service from '@/util/request.js'

// 页面装修-页面装修列表
export function shopDecorateList (data) {
  return service({
    url: '/api/admin/decorate/list',
    method: 'post',
    data
  })
}
