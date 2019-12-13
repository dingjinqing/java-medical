import service from '@/util/request.js'

// 获取头像弹窗tree数据
export function getHeadTreeListRequest () {
  return service({
    url: '/api/system/account/image/category/list',
    method: 'get'
  })
}

// 图片分组增加接口
export function groupHeadAddRequest (riid) {
  return service({
    url: '/api/system/account/image/category/add',
    method: 'post',
    data: riid
  })
}

// 图片分组删除接口
export function groupHeadDelRequest (riid) {
  return service({
    url: '/api/system/account/image/category/delete',
    method: 'post',
    data: riid
  })
}

// 图片分组重命名接口
export function renameHeadRequest (riid) {
  return service({
    url: '/api/system/account/image/category/rename',
    method: 'post',
    data: riid
  })
}

// 图片批量删除接口
export function imgsHeaddeleteRequest (riid) {
  return service({
    url: '/api/system/account/image/batch/delete',
    method: 'post',
    data: riid
  })
}

// 图片分页查询接口
export function queryHeadImgsRequest (riid) {
  localStorage.setItem('contentType', 'application/json;charset=UTF-8')
  return service({
    url: '/api/system/account/image/list',
    method: 'post',
    data: riid
  })
}

// 批量移动图片接口
export function moveHeadImgsRequest (riid) {
  return service({
    url: '/api/system/account/image/batch/move',
    method: 'post',
    data: riid
  })
}

// 上传图片接口
export function upmoreHeadImgsRequest (riid) {
  localStorage.setItem('contentType', 'application/x-www-form-urlencoded;charset=UTF-8')
  return service({
    url: '/api/system/account/image/uploadOneImgae',
    method: 'post',
    data: riid
  })
}

// 图片裁剪
export function imgsCropperRequest (riid) {
  localStorage.setItem('contentType', 'application/json;charset=UTF-8')
  return service({
    url: '/api/system/account/image/makeCrop',
    method: 'post',
    data: riid
  })
}
