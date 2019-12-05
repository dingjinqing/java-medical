import service from '@/util/request.js'

export function getEvaluationGiftList (data) {
  return service({
    url: '/api/admin/market/comment/award/list',
    method: 'post',
    data: data
  })
}
