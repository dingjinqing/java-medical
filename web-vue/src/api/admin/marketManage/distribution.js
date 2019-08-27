import service from '@/util/request.js'

// 分销推广语列表
export function advertisementList (data) {
  return service({
    url: '/api/admin/distribution/promotion/list',
    method: 'post',
    data: data
  })
}

// 添加分销推广语
export function advertisementAdd (data) {
  return service({
    url: '/api/admin/distribution/promotion/add',
    method: 'post',
    data: data
  })
}

// 停用分销推广语
export function advertisementPause (data) {
  return service({
    url: `/api/admin/distribution/promotion/pause?id=${data}`,
    method: 'get',
    data: data
  })
}

// 删除分销推广语
export function advertisementDelete (data) {
  return service({
    url: `/api/admin/distribution/promotion/delete?id=${data}`,
    method: 'get',
    data: data
  })
}

// 分销员分组
export function distributionGroup (data) {
  return service({
    url: '/api/admin/distribution/group/list',
    method: 'post',
    data: data
  })
}
