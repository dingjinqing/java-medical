import service from '@/util/request.js'

// 运费模版分页查询
export function deliverTemplatelist (data) {
  return service({
    url: '/api/admin/goods/deliver/templatelist',
    method: 'post',
    data
  })
}
