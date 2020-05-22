import service from '@/util/request.js'

// 门店管理-服务管理
// 获取所有服务分类
export function getAllServiceCats (data) {
  return service({
    url: '/api/admin/store/service/category/all',
    method: 'post',
    data: data
  })
}
// 获取所有服务
export function getAllService (data) {
  return service({
    url: '/api/admin/store/service/all',
    method: 'post',
    data: data
  })
}

// 获取服务列表
export function getServiceList (data) {
  return service({
    url: '/api/admin/store/service/list',
    method: 'post',
    data: data
  })
}

// 添加服务
export function addService (data) {
  return service({
    url: '/api/admin/store/service/add',
    method: 'post',
    data: data
  })
}

// 编辑服务
export function editService (data) {
  return service({
    url: '/api/admin/store/service/get/' + data.serviceId,
    method: 'get'
  })
}
// 更新服务
export function updateService (data) {
  return service({
    url: '/api/admin/store/service/update',
    method: 'post',
    data: data
  })
}
// 删除服务
export function deleteService (data) {
  return service({
    url: '/api/admin/store/service/del',
    method: 'post',
    data: data
  })
}
// 上架服务
export function onService (data) {
  return service({
    url: '/api/admin/store/service/batch/on',
    method: 'post',
    data: data
  })
}
// 下架服务
export function offService (data) {
  return service({
    url: '/api/admin/store/service/batch/off',
    method: 'post',
    data: data
  })
}
// 分享服务
export function shareService (data) {
  return service({
    url: '/api/admin/store/service/share/' + data.serviceId,
    method: 'get'
  })
}

// 获取服务分类列表
export function getServiceCatsList (data) {
  return service({
    url: '/api/admin/store/service/category/list',
    method: 'post',
    data: data
  })
}

// 添加服务分类
export function addServiceCat (data) {
  return service({
    url: '/api/admin/store/service/category/add',
    method: 'post',
    data: data
  })
}

// 更新服务分类
export function updateServiceCat (data) {
  return service({
    url: '/api/admin/store/service/category/update',
    method: 'post',
    data: data
  })
}

// 删除服务分类
export function deleteServiceCat (data) {
  return service({
    url: '/api/admin/store/service/category/del',
    method: 'post',
    data: data
  })
}

// 获取门店服务配置
export function getServiceConfig () {
  return service({
    url: '/api/admin/store/config/get',
    method: 'get'
  })
}

// 更新门店服务配置
export function updateServiceConfig (data) {
  return service({
    url: '/api/admin/store/config/update',
    method: 'post',
    data: data
  })
}
