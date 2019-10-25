import service from '@/util/request.js'

// 页面分类分页查询
export function getPageclassificationData (data) {
  return service({
    url: '/api/admin/applets/pageclassification/getListByPage',
    method: 'post',
    data
  })
}

// 获取页面分类下拉列表
export function getPageClassificationList () {
  // TODO: 需要后台接口
}

// 页面分类添加
export function addPageclassification (data) {
  return service({
    url: '/api/admin/applets/pageclassification/add',
    method: 'post',
    data
  })
}
// 跟新
export function updateCategoryName (data) {
  return service({
    url: '/api/admin/applets/pageclassification/updateCategoryName',
    method: 'post',
    data
  })
}
// 删除
export function deleteCategoryById (data) {
  return service({
    url: '/api/admin/applets/pageclassification/deleteCategoryById',
    method: 'post',
    data
  })
}
