import service from '@/util/request.js'

// 标签列表
export function getAllTagListByName (name) {
  return service({
    url: '/api/admin/tag/list',
    method: 'post',
    data: name
  })
}

// 新建保存
export function addTag (data) {
  return service({
    url: '/api/admin/tag/add',
    method: 'post',
    data
  })
}

// 编辑详情
export function getTagDetail (data) {
  return service({
    url: '/api/admin/tag/all/get',
    method: 'post',
    data
  })
}

// 编辑保存
export function updateTag (data) {
  return service({
    url: '/api/admin/tag/update',
    method: 'post',
    data
  })
}

// 删除
export function deleteTag (data) {
  return service({
    url: '/api/admin/tag/delete',
    method: 'post',
    data
  })
}
