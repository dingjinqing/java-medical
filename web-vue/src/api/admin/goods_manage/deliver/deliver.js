import service from '@/util/request.js'

// 运费模版分页查询
export function deliverTemplatelist (data) {
  return service({
    url: '/api/admin/goods/deliver/templatelist',
    method: 'post',
    data
  })
}
// 添加自定义运费模版
export function addTemplate (data) {
  return service({
    url: '/api/admin/goods/deliver/addtemplate',
    method: 'post',
    data
  })
}
// 删除指定运费模版
export function deliverDelete (data) {
  return service({
    url: '/api/admin/goods/deliver/delete',
    method: 'post',
    data
  })
}
