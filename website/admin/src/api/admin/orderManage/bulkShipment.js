import service from '@/util/request.js'

export function getBatchListApi (data) {
  return service({
    url: '/api/admin/order/ship/batch/list',
    data,
    method: 'post'
  })
}

export function downloadFailOrder (data) {
  return service({
    url: '/api/admin/order/ship/batch/fail/download/' + data.id,
    method: 'post',
    responseType: 'blob'
  })
}

export function uplaodBulkShipmentFileApi (data) {
  return service({
    url: '/api/admin/order/ship/batch',
    method: 'post',
    data
  })
}
