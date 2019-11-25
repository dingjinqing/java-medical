import service from '@/util/request.js'

// 编辑商家账号信息
export function getListRequest (data) {
  return service({
    url: '/api/system/shop/mp/list',
    method: 'post',
    data: data
  })
}
