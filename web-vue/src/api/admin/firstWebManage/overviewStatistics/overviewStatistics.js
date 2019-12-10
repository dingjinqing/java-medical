import service from '@/util/request.js'

//  昨日统计
export function yesterdayAnalysis () {
  return service({
    url: '/api/admin/overview/analysis/yesterday',
    method: 'get'
  })
}

//  综合概况
export function getSelect (data) {
  return service({
    url: `/api/admin/overview/analysis/select`,
    method: 'post',
    data: data
  })
}

//  页面概况
export function getPageInfo (data) {
  return service({
    url: '/api/admin/overview/analysis/pagelist',
    method: 'post',
    data: data
  })
}
