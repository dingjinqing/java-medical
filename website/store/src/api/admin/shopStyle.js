import service from '@/util/request.js'

// 店铺风格查询
export function shopStyleGetRequest () {
  return service({
    url: '/api/admin/decorate/style/get',
    method: 'get'
  })
}

// 店铺风格修改
export function shopStyleModifyRequest (riid) {
  return service({
    url: '/api/admin/decorate/style/update',
    method: 'post',
    data: riid
  })
}
