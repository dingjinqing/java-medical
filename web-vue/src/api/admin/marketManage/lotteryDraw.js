import service from '@/util/request.js'

// 拼团抽奖列表
export function lotteryDrawList (data) {
  return service({
    url: '/api/admin/group_draw/list',
    method: 'post',
    data: data
  })
}

// 添加拼团抽奖
export function addLotteryDraw (data) {
  return service({
    url: '/api/admin/group_draw/add',
    method: 'post',
    data: data
  })
}

// 获取编辑详情
export function getLotteryDetail (data) {
  return service({
    url: '/api/admin/order/group_draw/detail/{id}',
    method: 'post',
    data: data
  })
}

// 更新拼团抽奖
export function updateLotteryDraw (data) {
  return service({
    url: '/api/admin/group_draw/update/{id}',
    method: 'post',
    data: data
  })
}

// 删除拼团抽奖
export function deleteLotteryDraw (data) {
  return service({
    url: '/api/admin/group_draw/delete/{id}',
    method: 'post',
    data: data
  })
}

// 停用启用拼团抽奖
export function updateStatus (data) {
  return service({
    url: '',
    method: 'post',
    data: data
  })
}

// 分享拼团抽奖
export function shareLotteryDraw (data) {
  return service({
    url: '',
    method: 'get',
    data: data
  })
}

// 查询秒杀订单列表
export function orderSeckillList (data) {
  return service({
    url: '/api/admin/market/seckill/order',
    method: 'post',
    data: data
  })
}

// 查询用户明细列表
export function detailSeckillList (data) {
  return service({
    url: '/api/admin/market/seckill/source',
    method: 'post',
    data: data
  })
}

// 查询秒杀用户列表
export function userSeckillList (data) {
  return service({
    url: '/api/admin/market/seckill/detail',
    method: 'post',
    data: data
  })
}

// 查询活动数据
export function effactSeckillList (data) {
  return service({
    url: '/api/admin/market/seckill/analysis',
    method: 'post',
    data: data
  })
}
