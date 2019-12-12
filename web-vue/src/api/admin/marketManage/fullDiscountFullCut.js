import service from '@/util/request.js'

// 满折满减活动列表
export function fullCutTableDataSearchApi (data) {
  return service({
    url: '/api/admin/market/fullcut/list',
    method: 'post',
    data: data
  })
}

// 新增满折满减活动
export function addFullCutActivityApi (data) {
  return service({
    url: '/api/admin/market/fullcut/add',
    method: 'post',
    data: data
  })
}

// 新增满折满减活动中的会员专享活动接口
export function memberCardActivityName (data) {
  return service({
    url: '/api/admin/member/card/all/get',
    method: 'post',
    data: data
  })
}

// 取单个满折满减活动
export function getOneFullCutActivityInfo (data) {
  return service({
    url: '/api/admin/market/fullcut/get',
    method: 'post',
    data: data
  })
}

// 更新满折满减
export function updateFullCut (data) {
  return service({
    url: '/api/admin/market/fullcut/update',
    method: 'post',
    data: data
  })
}
