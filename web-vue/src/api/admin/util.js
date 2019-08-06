import service from '@/util/request.js'

// 权限判断
export function judgeJurisdictionRequest (data) {
  return service({
    url: '/api/admin/checkMenu',
    method: 'post',
    data: data
  })
}
