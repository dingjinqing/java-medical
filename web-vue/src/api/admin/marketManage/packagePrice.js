import service from '@/util/request.js'
// 打包一口价分页查询
export function packagePriceList (data) {
  return service({
    url: '/api/admin/market/packsale/list',
    method: 'post',
    data: data
  })
}
