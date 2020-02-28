import service from '@/util/request.js'
// 幸运大抽奖列表
export function getLotteryList (data) {
  return service({
    url: '/api/admin/market/lottery/list',
    method: 'post',
    data: data
  })
}
// 幸运大抽奖
export function getLottery (data) {
  return service({
    url: '/api/admin/market/lottery/get',
    method: 'post',
    data: data
  })
}
// 新增抽奖活动
export function addLottery (data) {
  return service({
    url: '/api/admin/market/lottery/add',
    method: 'post',
    data: data
  })
}
// 跟新抽奖活动
export function editLottery (data) {
  return service({
    url: '/api/admin/market/lottery/update',
    method: 'post',
    data: data
  })
}
// 改变状态
export function changeStatus (data) {
  return service({
    url: '/api/admin/market/lottery/change',
    method: 'post',
    data: data
  })
}
// 删除抽奖活动
export function deleteLottery (data) {
  return service({
    url: '/api/admin/market/lottery/delete',
    method: 'post',
    data: data
  })
}
// 查询抽奖活动的记录
export function getLotteryRecordList (data) {
  return service({
    url: '/api/admin/market/lottery/record/list',
    method: 'post',
    data: data
  })
}
// 查询抽奖用户列表
export function getLotteryUserList (data) {
  return service({
    url: '/api/admin/market/lottery/user/list',
    method: 'post',
    data: data
  })
}
// 分享幸运大抽奖
export function shareLottery (data) {
  return service({
    url: '/api/admin/market/lottery/share',
    method: 'post',
    data: data
  })
}
