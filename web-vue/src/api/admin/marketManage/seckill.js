import service from '@/util/request.js'

// 秒杀列表
export function seckillList (data) {
  return service({
    url: '/api/admin/market/seckill/list',
    method: 'post',
    data: data
  })
}

// 秒杀活动弹窗
export function dialogSeckillList (data) {
  return service({
    url: '/api/admin/decorate/seckill/list',
    method: 'post',
    data: data
  })
}

// 添加保存秒杀活动
export function addSeckillList (data) {
  return service({
    url: '/api/admin/market/seckill/add',
    method: 'post',
    data: data
  })
}

// 获取编辑详情
export function getSeckillList (data) {
  return service({
    url: '/api/admin/market/seckill/get',
    method: 'post',
    data: data
  })
}

// 删除秒杀活动
export function deleteSeckillList (data) {
  return service({
    url: '/api/admin/market/seckill/del',
    method: 'post',
    data: data
  })
}

// 分享秒杀活动
export function shareSeckillList (data) {
  return service({
    url: `/api/admin/market/seckill/share?id=${data}`,
    method: 'get',
    data: data
  })
}

// 更新秒杀活动
export function updateSeckillList (data) {
  return service({
    url: '/api/admin/market/seckill/update',
    method: 'post',
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
