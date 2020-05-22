/**
 * admin>基础配置>模板消息
 * @ Author 杨万里
 */
import service from '@/util/request.js'

// 模板消息更新
export const templateUpdateApi = data =>
  service({
    url: `/api/admin/config/message/template/update`,
    method: 'post',
    data
  })

// 模板消息查询
export function templateQueryApi () {
  return service({
    url: '/api/admin/config/message/template/query',
    method: 'get'
  })
}
