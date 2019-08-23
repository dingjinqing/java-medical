import service from '@/util/request.js'

// 分页查询商品标签
export function getLabelList (data) {
  return service({
    url: '/api/admin/goods/label/list',
    method: 'post',
    data: data
  })
}
