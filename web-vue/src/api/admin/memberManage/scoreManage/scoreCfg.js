import service from '@/util/request.js'

// 积分配置更新
export function userScoreConfigUpdate (data) {
  return service({
    url: '/api/admin/user/score/update',
    method: 'post',
    data
  })
}

// 获取积分数据
export function getScoreConfigRequest () {
  return service({
    url: '/api/admin/user/score/get',
    method: 'post',
    data: null
  })
}

// 积分说明获取
export function scoreCopywritingRequest () {
  return service({
    url: '/api/admin/user/score/copywriting',
    method: 'post',
    data: null
  })
}

// 积分说明保存
export function saveScoreDocumentUpdate (data) {
  return service({
    url: '/api/admin/user/score/copywriting/save',
    method: 'post',
    data
  })
}

// 积分前端页展示模板id
export function scorePageIdUpdate (data) {
  return service({
    url: '/api/admin/user/score/add',
    method: 'post',
    data
  })
}

// 积分签到会员列表
export function userScoreSign (data) {
  return service({
    url: '/api/admin/user/manage/score/sign',
    method: 'post',
    data
  })
}
