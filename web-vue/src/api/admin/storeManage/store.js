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

// 门店新增
export function addStore (data) {
  return service({
    url: '/api/admin/store/add',
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

// 获取门店信息
export function getStore (data) {
  return service({
    url: '/api/admin/store/get',
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

// 门店分组新增
export function addStoreGroup (data) {
  return service({
    url: '/api/admin/store/group/add',
    method: 'post',
    data: data
  })
}

// 门店分组删除
export function delStoreGroup (data) {
  return service({
    url: '/api/admin/store/group/del',
    method: 'post',
    data: data
  })
}

// 门店分组编辑
export function updateStoreGroup (data) {
  return service({
    url: '/api/admin/store/group/update',
    method: 'post',
    data: data
  })
}

// 门店分享
export function shareStore (data) {
  return service({
    url: '/api/admin/store/share/' + data,
    method: 'get',
    data: data
  })
}
