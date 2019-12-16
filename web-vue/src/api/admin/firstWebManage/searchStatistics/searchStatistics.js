import service from '@/util/request.js'

//  搜索历史统计
export function getHistory (data) {
  return service({
    url: `/api/admin/overview/search/analysis/history`,
    method: 'post',
    data: data
  })
}

//  搜索热词统计
export function getHot (data) {
  return service({
    url: `/api/admin/overview/search/analysis/hot`,
    method: 'post',
    data: data
  })
}
