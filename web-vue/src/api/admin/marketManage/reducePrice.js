import service from '@/util/request.js'

// 限时降价活动列表
export function reducePriceList (data) {
  return service({
    url: '/api/admin/market/reduceprice/list',
    method: 'post',
    data: data
  })
}

// 添加限时降价活动
export function addReducePrice (data) {
  return service({
    url: '/api/admin/market/reduceprice/add',
    method: 'post',
    data: data
  })
}

// 取单个限时降价活动信息
export function getReducePriceById (data) {
  return service({
    url: '/api/admin/market/reduceprice/get',
    method: 'post',
    data: data
  })
}

// 更新限时降价活动
export function updateReducePrice (data) {
  return service({
    url: `/api/admin/market/reduceprice/update`,
    method: 'post',
    data: data
  })
}

// 删除限时降价活动
export function deleteReducePrice (data) {
  return service({
    url: '/api/admin/market/reduceprice/del',
    method: 'post',
    data: data
  })
}

// 停用限时降价活动
// export function deleteReducePrice(data) {
//   return service({
//     url: '/api/admin/market/reduceprice/del',
//     method: 'post',
//     data: data
//   })
// }

// 限时降价订单列表
export function getReducePriceOrderList (data) {
  return service({
    url: '/api/admin/market/reduceprice/order',
    method: 'post',
    data: data
  })
}
