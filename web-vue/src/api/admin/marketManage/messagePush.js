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

// 获取会员卡下拉弹窗
export const allCardApi = () =>
  service({
    url: '/api/admin/member/card/all/get',
    method: 'post'
  })
// 获取所有标签名称
export const allTagApi = () =>
  service({
    url: '/api/admin/tag/all/get',
    method: 'post'
  })
