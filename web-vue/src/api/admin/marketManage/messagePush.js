/**
 * 营销管理/留存复购/消息推送
 * @ Author 杨万里
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
// 获取模版内容
export const contentListApi = data =>
  service({
    url: `/api/admin/market/message/template/content/list`,
    method: 'post',
    data
  })

// 添加模板
export const contentAddApi = data =>
  service({
    url: `/api/admin/market/message/template/content/add`,
    method: 'post',
    data
  })
// 获取发送人群列表
export const getUserArrayApi = data =>
  service({
    url: `/api/admin/market/message/template/getUserArray`,
    method: 'post',
    data
  })
// 获取发送人群
export const getUserNumberApi = data =>
  service({
    url: `/api/admin/market/message/template/getUserNumbers`,
    method: 'post',
    data
  })

// 推送统计

export const analysisApi = data =>
  service({
    url: `/api/admin/market/message/template/analysis`,
    method: 'post',
    data
  })
// 删除消息推送
export const templateDeleteApi = data =>
  service({
    url: `/api/admin/market/message/template/delete`,
    method: 'post',
    data
  })
// 发送记录
export const recordListApi = data =>
  service({
    url: `/api/admin/market/message/template/record/list`,
    method: 'post',
    data
  })
// 添加消息推送
export const addMessageApi = data =>
  service({
    url: `/api/admin/market/message/template/addMessage`,
    method: 'post',
    data
  })
// 查看消息模板
export const getDetailApi = data =>
  service({
    url: `/api/admin/market/message/template/getDetail`,
    method: 'post',
    data
  })

// id组查询会员卡
export const getUserIdsData = data =>
  service({
    url: `/api/admin/member/cards/list/get`,
    method: 'post',
    data
  })
// id组查询标签
export const getIdsLabelData = data =>
  service({
    url: `/api/admin/tags/get`,
    method: 'post',
    data
  })
