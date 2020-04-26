import service from '@/util/request.js'

// 概览页显示的营销活动
export function getOverviewActivity (data) {
  return service({
    url: `/api/admin/calendar/limitlist`,
    method: 'get'
  })
}

// 营销日历列表
export function getCalendarList (year) {
  return service({
    url: `/api/admin/calendar/list/${year}`,
    method: 'get'
  })
}

// 删除事件
export function deltCalendarEvent (calendarId) {
  return service({
    url: `/api/admin/calendar/market/del/${calendarId}`,
    method: 'get'
  })
}

// 单个事件详情
export function eventDeatil (calendarId) {
  return service({
    url: `/api/admin/calendar/info/${calendarId}`,
    method: 'get'
  })
}

// 添加/编辑保存事件
export function saveEvent (data) {
  return service({
    url: '/api/admin/calendar/market/up',
    method: 'post',
    data: data
  })
}

// 查询营销活动所有可用活动
export function allMarketList (data) {
  return service({
    url: '/api/admin/calendar/market/list',
    method: 'post',
    data: data
  })
}
