import service from '@/util/request.js'

// 权限总查询
export function jurisdictionQueryRequest () {
  return service({
    url: '/api/admin/showMenu',
    method: 'post'
  })
}
