/****
 资产管理
 ****/

import service from '@/util/request.js'

// 现金资产管理
export function cashManage (data) {
  return service({
    url: '/api/admin/assetmanagement/revenueprofile',
    method: 'post',
    data: data
  })
}

// 积分资产管理
export function scoreManage (data) {
  return service({
    url: '/api/admin/assetmanagement/revenueprofile',
    method: 'post',
    data: data
  })
}

// 现金资产明细
export function cashDetail (data) {
  return service({
    url: '/api/admin/assetmanagement/assetManageDetail',
    method: 'post',
    data: data
  })
}

// 积分资产明细
export function scoreDetail (data) {
  return service({
    url: '/api/admin/assetmanagement/assetManageDetail',
    method: 'post',
    data: data
  })
}

// excel导出
export function export2Excel (data) {
  return service({
    url: '/api/admin/assetmanagement/export2Excel',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
