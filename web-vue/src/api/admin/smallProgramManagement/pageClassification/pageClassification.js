import service from '@/util/request.js'

// 页面分类分页查询
export function getPageclassificationData (data) {
  return service({
    url: '/api/admin/applets/pageclassification/getListByPage',
    method: 'post',
    data
  })
}

// 页面分类添加
export function addPageclassification (data) {
  return service({
    url: '/api/admin/applets/pageclassification/add',
    method: 'post',
    data
  })
}
