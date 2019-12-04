import service from '@/util/request.js'

// 满包邮列表
export function getShippingList (data) {
  return service({
    url: '/api/admin/market/free/shipping/list',
    method: 'post',
    data: data
  })
}

// 添加保存
export function addShipping (data) {
  return service({
    url: '/api/admin/market/free/shipping/add',
    method: 'post',
    data: data
  })
}

// 获取编辑详情
export function getDetail (data) {
  return service({
    url: '/api/admin/market/free/shipping/get',
    method: 'post',
    data: data
  })
}

// 编辑保存
export function updateShipping (data) {
  return service({
    url: '/api/admin/market/free/shipping/update',
    method: 'post',
    data: data
  })
}

// 删除
export function deleteShipping (data) {
  return service({
    url: '/api/admin/market/free/shipping/delete',
    method: 'post',
    data: data
  })
}

// 更改状态
export function changeShipping (data) {
  return service({
    url: '/api/admin/market/free/shipping/status/change',
    method: 'post',
    data: data
  })
}

// 分享
export function shareShipping (data) {
  return service({
    url: '/api/admin/market/free/shipping/share',
    method: 'post',
    data: data
  })
}
