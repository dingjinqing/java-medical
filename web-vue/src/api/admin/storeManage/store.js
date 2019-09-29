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

// 门店分组列表
export function storeGroupList (data) {
  return service({
    url: '/api/admin/store/group/list',
    method: 'post',
    data: data
  })
}

// 门店删除
export function delStore (data) {
  return service({
    url: '/api/admin/store/del',
    method: 'post',
    data: data
  })
}

// 门店更新
export function updateStore (data) {
  return service({
    url: '/api/admin/store/update',
    method: 'post',
    data: data
  })
}

// 批量更新门店信息
export function batchUpdateStore (data) {
  return service({
    url: '/api/admin/store/batchupdate',
    method: 'post',
    data: data
  })
}
