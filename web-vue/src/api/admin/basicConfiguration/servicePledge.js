/**
 * admin>基础配置>服务承诺
 * @ Author 梁晨
 */
import service from '@/util/request.js'

// 服务承诺列表
export function pledgeList () {
  return service({
    url: '/api/admin/config/pledge/list',
    method: 'get'
  })
}
