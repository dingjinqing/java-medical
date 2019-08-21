import service from '@/util/request.js'

// 查询团购列表
export function groupBuyList (data) {
  return service({
    url: '/api/admin/market/groupbuy/list',
    method: 'post',
    data: data
  })
}
