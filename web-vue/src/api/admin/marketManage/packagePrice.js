import service from '@/util/request.js'
// 打包一口价分页查询
export function packagePriceList (data) {
  return service({
    url: '/api/admin/market/packsale/list',
    method: 'post',
    data: data
  })
}
// 打包一口价分享
export function shareActivity (id) {
  return service({
    url: `/api/admin/market/packsale/qrcode/${id}`,
    method: 'post'
  })
}
// 启用活动
export function enableActivity (id) {
  return service({
    url: `/api/admin/market/packsale/enable/${id}`,
    method: 'post'
  })
}
// 停用活动
export function disableActivity (id) {
  return service({
    url: `/api/admin/market/packsale/disable/${id}`,
    method: 'post'
  })
}
// 删除打包一口价活动
export function deleteActivity (id) {
  return service({
    url: `/api/admin/market/packsale/delete/${id}`,
    method: 'post'
  })
}
// 分页查询打包一口价活动参与明细
export function activityDetail (data) {
  return service({
    url: `/api/admin/market/packsale/detail`,
    method: 'post',
    data: data
  })
}
// 打包一口价 - 活动订单查询
export function activityOrder (data) {
  return service({
    url: `/api/admin/market/packsale/order`,
    method: 'post',
    data: data
  })
}
// 打包一口价 - 新增活动
export function addActivity (data) {
  return service({
    url: '/api/admin/market/packsale/add',
    method: 'post',
    data: data
  })
}
