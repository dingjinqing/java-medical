import service from '@/util/request.js'

export function getThirdAuth (data) {
  return service({
    url: '/api/admin/config/third/auth/get',
    data,
    method: 'post'
  })
}
