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

// 图片分组重命名接口
export function renameRequest (riid) {
  return service({
    url: '/api/admin/image/category/rename',
    method: 'post',
    data: riid
  })
}

// 图片批量删除接口
export function imgsdeleteRequest (riid) {
  return service({
    url: '/api/admin/image/batch/delete',
    method: 'post',
    data: riid
  })
}

// 图片分页查询接口
export function queryImgsRequest (riid) {
  return service({
    url: '/api/admin/image/list',
    method: 'post',
    data: riid
  })
}

// 批量移动图片接口
export function moveImgsRequest (riid) {
  return service({
    url: '/api/admin/image/batch/move',
    method: 'post',
    data: riid
  })
}

// 上传图片接口
export function upmoreImgsRequest (riid) {
  return service({
    url: '/api/admin/image/uploadOneImgae',
    method: 'post',
    data: riid
  })
}

// 图片裁剪
export function picSpaceimgsCropperRequest (riid) {
  return service({
    url: '/api/admin/image/makeCrop',
    method: 'post',
    data: riid
  })
}
