import service from '@/util/request.js'

// 门店商品分页查询
export function getGoodsPageListApi (data) {
  return service({
    url: '/api/store/goods/list',
    data,
    method: 'post'
  })
}
