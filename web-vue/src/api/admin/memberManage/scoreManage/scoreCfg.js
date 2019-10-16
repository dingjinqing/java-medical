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
