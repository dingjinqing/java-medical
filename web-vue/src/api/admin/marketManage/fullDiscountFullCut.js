import service from '@/util/request.js'

export function fullCutTableDataSearchApi (data) {
  return service({
    url: '/api/admin/market/fullcut/list',
    method: 'post',
    data: data
  })
}

export function addFullCutActivityApi (data) {
  return service({
    url: '/api/admin/market/fullcut/add',
    method: 'post',
    data: data
  })
}
