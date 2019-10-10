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
