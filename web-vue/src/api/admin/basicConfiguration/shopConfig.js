import service from '@/util/request.js'

//  我已有小程序，一键授权
export function grantAuthorizationRequest () {
  return service({
    url: '/api/admin/start/auth',
    method: 'get'
  })
}
