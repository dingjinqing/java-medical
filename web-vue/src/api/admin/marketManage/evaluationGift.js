import service from '@/util/request.js'

// 查询列表
export function getEvaluationGiftList (data) {
  return service({
    url: '/api/admin/market/comment/award/list',
    method: 'post',
    data: data
  })
}

// 添加评价有礼活动
export function addEvaluationGift (data) {
  return service({
    url: '/api/admin/marker/comment/award/add',
    method: 'post',
    data: data
  })
}
