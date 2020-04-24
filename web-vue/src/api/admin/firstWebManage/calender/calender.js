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
    url: `/api/admin/calendar/list/year=${year}`,
    method: 'get'
  })
}
