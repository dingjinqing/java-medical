import service from '@/util/request.js'

// 拼团抽奖列表
export function lotteryDrawList (data) {
  return service({
    url: '/api/admin/group_draw/list',
    method: 'post',
    data: data
  })
}

// 添加
export function addLotteryDraw (data) {
  return service({
    url: '/api/admin/group_draw/add',
    method: 'post',
    data: data
  })
}

// 获取编辑详情
export function getLotteryDetail (data) {
  return service({
    url: `/api/admin/group_draw/detail/${data}`,
    method: 'post',
    data: data
  })
}

// 更新
export function updateLotteryDraw (data) {
  return service({
    url: '/api/admin/group_draw/update',
    method: 'post',
    data: data
  })
}

// 删除
export function deleteLotteryDraw (data) {
  return service({
    url: `/api/admin/group_draw/delete/${data}`,
    method: 'post',
    data: data
  })
}

// 停用
export function updateStatus (data) {
  return service({
    url: `/api/admin/group_draw/disable/${data}`,
    method: 'post',
    data: data
  })
}

// 分享
export function shareLotteryDraw (data) {
  return service({
    url: '',
    method: 'get',
    data: data
  })
}

// 查询订单列表
export function orderLotteryList (data) {
  return service({
    url: '/api/admin/group_draw/order/list',
    method: 'post',
    data: data
  })
}

// 查询用户明细列表
export function detailLotteryList (data) {
  return service({
    url: '/api/admin/group_draw/invited_user/list',
    method: 'post',
    data: data
  })
}

// 查询参与用户列表
export function userLotteryList (data) {
  return service({
    url: '/api/admin/group_draw/join_user/list',
    method: 'post',
    data: data
  })
}

// 开团明细
export function groupLotteryList (data) {
  return service({
    url: '/api/admin/group_draw/group/list',
    method: 'post',
    data: data
  })
}

// 查询活动数据
export function effactLotteryList (data) {
  return service({
    url: '',
    method: 'post',
    data: data
  })
}
