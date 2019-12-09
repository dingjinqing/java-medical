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
    url: '/api/admin/market/comment/award/add',
    method: 'post',
    data: data
  })
}

// 更新评价有礼活动
export function updateEvaluationGift (data) {
  return service({
    url: '/api/admin/market/comment/award/update',
    method: 'post',
    data: data
  })
}

// 查看评价有礼活动
export function getEvaluationGift (data) {
  return service({
    url: '/api/admin/market/comment/award/get',
    method: 'post',
    data: data
  })
}

// 停用，启用评价有礼
export function toggleEvaluationGift (data) {
  return service({
    url: '/api/admin/market/comment/award/change/status',
    method: 'post',
    data: data
  })
}

// 删除评价有礼
export function deleteEvaluationGift (data) {
  return service({
    url: '/api/admin/market/comment/award/delete',
    method: 'post',
    data: data
  })
}

// 评价有礼下拉
export function getAllCoupon (data) {
  return service({
    url: '/api/admin/coupon/all',
    method: 'post',
    data: data
  })
}
