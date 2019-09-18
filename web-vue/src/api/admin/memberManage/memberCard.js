import service from '@/util/request.js'

// 获取所有会员卡以分类的形式返回
export function getAllMemberCardByClassRequest () {
  return service({
    url: '/api/admin/member/card/all/list',
    method: 'post',
    data: null
  })
}

// 为会员设置会员卡
export function setCardForMemberRequest (data) {
  return service({
    url: '/api/admin/member/card/all/add',
    method: 'post',
    data: data
  })
}

// 获取会员持有的所有可用会员 卡
export function getAllAvailableMemberCardRequest (userId) {
  return service({
    url: `/api/admin/member/card/all/get/${userId}`,
    method: 'post',
    data: null
  })
}
