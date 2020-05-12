import service from '@/util/request.js'

// 加价购分页条件查询
export function getList (data) {
  return service({
    url: '/api/admin/market/increasepurchase/selectbypage',
    method: 'post',
    data: data
  })
}
// 添加加价购活动
export function add (data) {
  return service({
    url: '/api/admin/market/increasepurchase/addincreasepurchase',
    method: 'post',
    data: data
  })
}
// 更新加价购活动
export function update (data) {
  return service({
    url: '/api/admin/market/increasepurchase/updateincreasepurchase',
    method: 'post',
    data: data
  })
}
// 获取加价购活动详细信息
export function getDetail (data) {
  return service({
    url: '/api/admin/market/increasepurchase/getpurchasedetail',
    method: 'post',
    data: data
  })
}
// 停用/启用/删除加价购活动
export function changeActivity (data) {
  return service({
    url: `/api/admin/market/increasepurchase/changethestatus`,
    method: 'post',
    data: data
  })
}
// 修改活动优先级
export function updatePriority (data) {
  return service({
    url: `/api/admin/market/increasepurchase/updatepriority`,
    method: 'post',
    data: data
  })
}
// 分享,获取小程序二维码
export function share (data) {
  return service({
    url: '/api/admin/market/increasepurchase/share',
    method: 'post',
    data: data
  })
}
// 查看换购订单列表
export function orderList (data) {
  return service({
    url: `/api/admin/market/increasepurchase/getredemptionorderlist`,
    method: 'post',
    data: data
  })
}
// 查看换购明细
export function detailList (data) {
  return service({
    url: `/api/admin/market/increasepurchase/getredemptiondetail`,
    method: 'post',
    data: data
  })
}
// 换购订单列表导出
export function orderExport (data) {
  return service({
    url: `/api/admin/market/increasepurchase/exportorderlist`,
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
// 换购明细导出
export function detailExport (data) {
  return service({
    url: `/api/admin/market/increasepurchase/exportorderdetail`,
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
