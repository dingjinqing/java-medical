import service from '@/util/request.js'

// 页面装修-页面装修列表
export function shopDecorateList (data) {
  return service({
    url: '/api/admin/decorate/list',
    method: 'post',
    data: data
  })
}

// 页面装修编辑保存接口
export function saveDecorationPage (data) {
  return service({
    url: '/api/admin/decorate/page/add',
    method: 'post',
    data: data
  })
}

// 商品列表模块接口
export function queryDataList (data) {
  return service({
    url: '/api/admin/goods/mp/list',
    method: 'post',
    data: data
  })
}

// 页面编辑保存
export function editSave (data) {
  return service({
    url: '/api/admin/decorate/page/save',
    method: 'post',
    data: data
  })
}
