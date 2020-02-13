/****
 商品统计
 ****/

import service from '@/util/request.js'

// 商品效果
export function producteffect (data) {
  return service({
    url: '/api/admin/commoditystatistics/producteffect',
    method: 'post',
    data: data
  })
}

// 商品概览
export function productoverview (data) {
  return service({
    url: '/api/admin/commoditystatistics/productoverview',
    method: 'post',
    data: data
  })
}

// 商品排行
export function cashDetail (data) {
  return service({
    url: '/api/admin/assetmanagement/assetManageDetail',
    method: 'post',
    data: data
  })
}

// 商品排行导出
export function scoreDetail (data) {
  return service({
    url: '/api/admin/assetmanagement/assetManageDetail',
    method: 'post',
    data: data
  })
}

// 商品效果导出
export function export2Excel (data) {
  return service({
    url: '/api/admin/assetmanagement/export2Excel',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
