import service from '@/util/request.js'

/**
 * 预约管理
 */
// 预约列表
export function getList (data) {
  return service({
    url: '/api/admin/store/service/reserve/list',
    method: 'post',
    data: data
  })
}

// 预约详情
export function detail (data) {
  return service({
    url: `/api/admin/store/service/reserve/detail?orderSn=${data}`,
    method: 'get'
  })
}

// 添加卖家留言
export function addMessage (data) {
  return service({
    url: '/api/admin/store/service/reserve/message/add',
    method: 'post',
    data: data
  })
}

// 后台添加预约服务
export function add (data) {
  return service({
    url: '/api/admin/store/service/reserve/add',
    method: 'post',
    data: data
  })
}

// 核销预约
export function charge (data) {
  return service({
    url: '/api/admin/store/service/reserve/charge',
    method: 'post',
    data: data
  })
}

// 取消预约
export function cancel (data) {
  return service({
    url: '/api/admin/store/service/reserve/cancel',
    method: 'post',
    data: data
  })
}

// 技师下拉
export function techList (data) {
  return service({
    url: '/api/admin/store/services/technician/getTechByStoreService',
    method: 'post',
    data: data
  })
}

// 可用会员卡列表下拉
export function availableCard (data) {
  return service({
    url: '/api/admin/user/card/available',
    method: 'post',
    data: data
  })
}

// 被核销用户余额
export function getChargeAccount (data) {
  return service({
    url: `/api/admin/store/charge/${data}`,
    method: 'get'
  })
}
