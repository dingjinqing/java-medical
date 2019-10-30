import service from '@/util/request.js'

// 绑定解绑
export function bindRequest (data) {
  return service({
    url: '/api/admin/survey/official/bind',
    method: 'post',
    data: data
  })
}

// 待办事项
export function toDoItemRequest (data) {
  return service({
    url: '/api/admin/malloverview/toDoItem',
    method: 'post',
    data: data
  })
}

// 数据展示
export function dataRequest (data) {
  return service({
    url: '/api/admin/malloverview/datademonstration',
    method: 'post',
    data: data
  })
}

// 店铺助手
export function shopAssistantRequest (data) {
  return service({
    url: '/api/admin/malloverview/shopAssistant',
    method: 'post',
    data: data
  })
}

// 公告查询
export function noticeListRequest (data) {
  return service({
    url: '/api/official/article/list',
    method: 'post',
    data: data
  })
}

// 公告详情
export function noticeDetailRequest (data) {
  return service({
    url: '/api/official/article/get',
    method: 'post',
    data: data
  })
}
