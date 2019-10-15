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
