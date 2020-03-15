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

// 查询指定积分兑换活动
export function integralDetail (data) {
  return service({
    url: '/api/admin/market/integral/convert/select',
    method: 'post',
    data: data
  })
}

// 修改积分兑换活动信息
export function integralUpdate (data) {
  return service({
    url: '/api/admin/market/integral/convert/update',
    method: 'post',
    data: data
  })
}

// 停用或启用积分兑换活动
export function integralDiscontinueUse (data) {
  return service({
    url: '/api/admin/market/integral/convert/switch',
    method: 'post',
    data: data
  })
}

// 积分活动删除
export function integralDel (data) {
  return service({
    url: '/api/admin/market/integral/convert/delete',
    method: 'post',
    data: data
  })
}

// 查看积分兑换订单
export function integralOrder (data) {
  return service({
    url: '/api/admin/market/integral/convert/order',
    method: 'post',
    data: data
  })
}

// 积分兑换用户列表
export function integralUserList (data) {
  return service({
    url: '/api/admin/market/integral/convert/user',
    method: 'post',
    data: data
  })
}
