import service from '@/util/request.js'

// 积分兑换活动分页列表
export function integralExchangeList (data) {
  return service({
    url: '/api/admin/market/integral/convert/list',
    method: 'post',
    data: data
  })
}
// 返回指定商品规格详情
export function goodsSpecDetail (data) {
  return service({
    url: '/api/admin/market/integral/convert/product',
    method: 'post',
    data: data
  })
}

// 添加积分兑换活动
export function addIntegral (data) {
  return service({
    url: '/api/admin/market/integral/convert/add',
    method: 'post',
    data: data
  })
}
