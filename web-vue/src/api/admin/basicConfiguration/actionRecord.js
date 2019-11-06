import service from '@/util/request.js'

// 操作记录查询
export function getListRequest (data) {
  return service({
    url: '/api/admin/config/record/getPage',
    method: 'post',
    data: data
  })
}
