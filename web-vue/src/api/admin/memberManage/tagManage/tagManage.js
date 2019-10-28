import service from '@/util/request.js'

export function getAllTagListByName (name) {
  return service({
    url: '/api/admin/tag/list',
    method: 'post',
    data: name
  })
}

export function modifyTagName (data) {
  return service({
    url: '/api/admin/tag/update',
    method: 'post',
    data
  })
}

export function deleteTag (data) {
  return service({
    url: '/api/admin/tag/delete',
    method: 'post',
    data
  })
}

export function appendTag (data) {
  return service({
    url: '/api/admin/tag/add',
    method: 'post',
    data
  })
}
