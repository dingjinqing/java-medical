import service from '@/util/request.js'

// 赠品活动列表
export const giftList = (data) => service({
  url: '/api/admin/market/gift/list',
  method: 'post',
  data
})

// 创建赠品活动
export function addGift (data) {
  return service({
    url: '/api/admin/market/gift/add',
    method: 'post',
    data
  })
}

// 删除赠品活动
export function deleteGift (id) {
  return service({
    url: `/api/admin/market/gift/delete/${id}`,
    method: 'post'
  })
}

// 停用赠品活动
export function disableGift (id) {
  return service({
    url: `/api/admin/market/gift/delete/${id}`,
    method: 'post'
  })
}

// 修改赠品活动优先级
export function updateGiftLevel (data) {
  return service({
    url: '/api/admin/market/gift/level/update',
    method: 'post',
    data
  })
}

// 修改赠品活动
export function updateGift (data) {
  return service({
    url: '/api/admin/market/gift/update',
    method: 'post',
    data
  })
}

// 查询赠品活动明细
export function getGiftDetail (id) {
  return service({
    url: `/api/admin/market/gift/detail/${id}`,
    method: 'post'
  })
}

// 查询会员卡列表
export function getMemberCardList () {
  return service({
    url: `/api/admin/market/gift/member_card/list`,
    method: 'post'
  })
}

// 查询用户标签列表
export function getTagList () {
  return service({
    url: `/api/admin/market/gift/tag/list`,
    method: 'post'
  })
}
