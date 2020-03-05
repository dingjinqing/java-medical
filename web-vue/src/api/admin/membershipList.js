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
    url: '/api/admin/member/account/update',
    method: 'post',
    data: data
  })
}

// 积分修改
export function scoreUpdateRequest (data) {
  return service({
    url: '/api/admin/member/score/update',
    method: 'post',
    data: data
  })
}

// 获取会员卡
export function allUserCardRequest () {
  return service({
    url: '/api/admin/member/card/all/get',
    method: 'post'
  })
}

// 获取所有门店来源
export function allSourceRequest () {
  return service({
    url: '/api/admin/store/all/get',
    method: 'post'
  })
}
// 获取所有标签
export function allTagRequest () {
  return service({
    url: '/api/admin/tag/all/get',
    method: 'post'
  })
}
// 获取某个会员的标签列表
export function getTagForMemberRequest (data) {
  return service({
    url: '/api/admin/member/tag/get',
    method: 'post',
    data: data
  })
}

// 打标签
export function setTagForMemberRequest (data) {
  return service({
    url: '/api/admin/member/tag/edit',
    method: 'post',
    data: data
  })
}

// 获取会员的余额明细
export function accountDetailRequest (data) {
  return service({
    url: '/api/admin/member/account/list',
    method: 'post',
    data: data
  })
}

// 获取会员的余额明细
export function scoreDetailRequest (data) {
  return service({
    url: '/api/admin/member/score/list',
    method: 'post',
    data: data
  })
}

// 改变用户登录状态： 禁止登录 || 恢复登录
export function loginStatusRequest (data) {
  return service({
    url: '/api/admin/member/manage/update',
    method: 'post',
    data: data
  })
}

// 获取用户的详细信息
export function memberInfoRequest (userId) {
  return service({
    url: '/api/admin/member/manager/center/' + userId,
    method: 'post',
    data: null
  })
}

// 更新会员用户信息
export function updateMemberInfoRequest (data) {
  return service({
    url: '/api/admin/member/info/update',
    method: 'post',
    data: data
  })
}

// 获取所有行业信息
export function getAllIndustryRequest () {
  return service({
    url: '/api/admin/member/industry/get',
    method: 'post',
    data: null
  })
}

// 获取会员导出信息
export function getExportCfg (data) {
  return service({
    url: '/api/admin/member/export/cfg',
    method: 'post',
    data: data
  })
}

// 会员导出
export function exportCfg (data) {
  return service({
    url: '/api/admin/member/list/export/new',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
