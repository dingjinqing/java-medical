import service from '@/util/request.js'

// 获取视频分类tree接口
export function getVideoCategoryTreeRequest () {
  return service({
    url: '/api/admin/video/category/list',
    method: 'get'
  })
}

// 视频分组增加接口
export function addVideoCategoryRequest (riid) {
  return service({
    url: '/api/admin/video/category/add',
    method: 'post',
    data: riid
  })
}

// 视频分组删除接口
export function deleteVideoCategoryRequest (riid) {
  return service({
    url: '/api/admin/video/category/delete',
    method: 'post',
    data: riid
  })
}

// 视频分组重命名接口
export function renameVideoCategoryRequest (riid) {
  return service({
    url: '/api/admin/video/category/rename',
    method: 'post',
    data: riid
  })
}

// 批量删除视频接口
export function batchDeleteVideoRequest (riid) {
  return service({
    url: '/api/admin/video/batch/delete',
    method: 'post',
    data: riid
  })
}

// 查询视频接口
export function getVideoListRequest (riid) {
  localStorage.setItem('contentType', 'application/json;charset=UTF-8')
  return service({
    url: '/api/admin/video/list',
    method: 'post',
    data: riid
  })
}

// 批量移动视频接口
export function batchMoveVideoRequest (riid) {
  return service({
    url: '/api/admin/video/batch/move',
    method: 'post',
    data: riid
  })
}

// 上传视频接口
export function uploadVideoRequest (riid) {
  localStorage.setItem('contentType', 'application/x-www-form-urlencoded;charset=UTF-8')
}

// 查询视频空间已使用量
export function getUsedVideoSpace () {
  return service({
    url: '/api/admin/video/space/info',
    method: 'get'
  })
}
