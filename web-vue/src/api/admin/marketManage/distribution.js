import service from '@/util/request.js'

// 分销推广语列表
export function advertisementList (data) {
  return service({
    url: '/api/admin/distribution/promotion/list',
    method: 'post',
    data: data
  })
}
