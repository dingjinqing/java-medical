import service from '@/util/request.js'

// 门店列表
export function storeList (data) {
  return service({
    url: '/api/admin/store/list',
    method: 'post',
    data: data
  })
}

// 全部门店分组
export function allStoreGroup () {
  return service({
    url: '/api/admin/store/group/all',
    method: 'get'
  })
}
