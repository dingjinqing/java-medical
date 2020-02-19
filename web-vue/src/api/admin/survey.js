import service from '@/util/request.js'

// 数据总接口
export function getAllOverview (data) {
  return service({
    url: '/api/admin/malloverview/allOverview',
    method: 'post',
    data: data
  })
}

// 店铺分享
export function shopShareRequest () {
  return service({
    url: '/api/admin/malloverview/shareShop',
    method: 'get'
  })
}

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
    url: '/api/admin/malloverview/getAnnouncementList',
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

// 店铺信息
export function shopInfoRequest () {
  return service({
    url: '/api/admin/account/shop/oneInfo',
    method: 'get'
  })
}

// 获取所有门店
export function getAllStore (data) {
  return service({
    url: '/api/admin/store/all/get',
    method: 'post',
    data: data
  })
}
