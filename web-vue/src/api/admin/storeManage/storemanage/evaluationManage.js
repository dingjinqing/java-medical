import service from '@/util/request.js'

/**
 * 评价管理
 */
// 分页查询服务评价
export function getList (data) {
  return service({
    url: '/api/admin/store/services/comment/list',
    method: 'post',
    data: data
  })
}

// 删除服务评价
export function batchDel (data) {
  return service({
    url: '/api/admin/store/services/comment/delete',
    method: 'post',
    data: data
  })
}

// 服务评价审核通过
export function pass (data) {
  return service({
    url: '/api/admin/store/services/comment/pass',
    method: 'post',
    data: data
  })
}

// 服务评价审核拒绝
export function refuse (data) {
  return service({
    url: '/api/admin/store/services/comment/refuse',
    method: 'post',
    data: data
  })
}
