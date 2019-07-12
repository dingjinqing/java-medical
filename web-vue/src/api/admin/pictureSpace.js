import service from '@/util/request.js'

// 获取tree数据
export function getTreeListRequest () {
  return service({
    url: '/api/admin/image/category/list',
    method: 'get'
  })
}

// 图片分组增加接口
export function groupAddRequest (riid) {
  return service({
    url: '/api/admin/image/category/add',
    method: 'post',
    data: riid
  })
}

// 图片分组删除接口
export function groupDelRequest (riid) {
  return service({
    url: '/api/admin/image/category/delete',
    method: 'post',
    data: riid
  })
}
