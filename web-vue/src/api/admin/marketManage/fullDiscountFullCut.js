import service from '@/util/request.js'

export function fullCutTableDataSearch () {
  return service({
    url: ''
  })
}

export function addFullCutActivityApi (data) {
  return service({
    url: '/api/admin/market/fullcut/add',
    method: 'post',
    data: data
  })
}
