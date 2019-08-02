import service from '@/util/request.js'

// 会员列表分页查询
export function membershipListRequest (data) {
  return service({
    url: '/api/admin/member/list',
    method: 'post',
    data: data
  })
}

// 余额修改
export function accountAddRequest (data) {
  return service({
    url: '/api/admin/member/account/add',
    method: 'post',
    data: data
  })
}
