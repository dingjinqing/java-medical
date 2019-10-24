/**
 * 我要送礼
 *
 * @author 孔德成
 */
import service from '@/util/request.js'

// 赠品活动列表
export function giveGiftList (data) {
  return service({
    url: '/api/admin/marker/givegift/list',
    method: 'post',
    data: data
  })
}

// 获取当个活动详情
export function getGiveGiftById (data) {
  return service({
    url: '/api/admin/marker/givegift/get',
    method: 'post',
    data: data
  })
}

// 添加活动
export function addGiveGift (data) {
  return service({
    url: '/api/admin/marker/givegift/add',
    method: 'post',
    data: data
  })
}

// 更新活动
export function updateGiveGift (data) {
  return service({
    url: '/api/admin/marker/givegift/update',
    method: 'post',
    data: data
  })
}

// 改变状态
export function changeGiveGift (data) {
  return service({
    url: '/api/admin/marker/givegift/change/status',
    method: 'post',
    data: data
  })
}

// 删除活动
export function deleteGiveGift (data) {
  return service({
    url: '/api/admin/marker/givegift/delete',
    method: 'post',
    data: data
  })
}

// 送礼明细列表
export function giveGiftRecordList (data) {
  return service({
    url: '/api/admin/marker/givegift/send/list',
    method: 'post',
    data: data
  })
}

// 收里明细
export function giveGiftReceiveList (data) {
  return service({
    url: '/api/admin/marker/givegift/receive/list',
    method: 'post',
    data: data
  })
}
