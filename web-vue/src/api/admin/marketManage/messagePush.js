/**
 * 营销管理/留存复购/消息推送
 */
import service from '@/util/request'

// 消息推送列表页分页查询
export const messageTemplateListApi = data =>
  service({
    url: '/api/admin/market/message/template/list',
    method: 'post',
    data
  })
