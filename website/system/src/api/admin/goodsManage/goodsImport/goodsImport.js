import service from '@/util/request.js'

export function goodsListApi (data) {
  return service({
    url: '/api/admin/goods/vpu/excel/operate/list',
    method: 'post',
    data
  })
}

export function downloadFailExportApi (id) {
  return service({
    url: '/api/admin/goods/vpu/excel/download/fail/data/' + id,
    method: 'get',
    responseType: 'blob'
  })
}

export function downloadModuleApi () {
  return service({
    url: '/api/admin/goods/vpu/excel/download/module',
    method: 'get',
    responseType: 'blob'
  })
}

export function uploadGoodsApi (data) {
  return service({
    url: '/api/admin/goods/vpu/excel/import',
    method: 'post',
    data
  })
}
