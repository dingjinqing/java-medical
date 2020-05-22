import service from '@/util/request.js'

export function getVerifierList (data) {
  return service({
    url: '/api/admin/store/verifier/list',
    method: 'post',
    data: data
  })
}

export function getAllVerifierList (data) {
  return service({
    url: '/api/admin/member/common/list',
    method: 'post',
    data: data
  })
}

// 添加核销员
export function addVerifier (data) {
  return service({
    url: '/api/admin/store/verifier/add',
    method: 'post',
    data: data
  })
}

// 删除核销员
export function delVerifier (data) {
  return service({
    url: '/api/admin/store/verifier/del',
    method: 'post',
    data: data
  })
}

// 导出核销员列表
export function exportStoreVerifierList (data) {
  return service({
    url: '/api/admin/store/verifier/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
