import service from '@/util/request.js'

// 普通优惠券列表
export function seckillList (data) {
  return service({
    url: '/api/admin/market/seckill/list',
    method: 'post',
    data: data
  })
}
