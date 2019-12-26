/**
 * 赠品活动
 *
 * @author 郑保乐
 */
import service from '@/util/request'

// 赠品活动列表
export const giftList = data => service({
  url: '/api/admin/market/gift/list',
  method: 'post',
  data
})

// 创建赠品活动
export const addGift = data => service({
  url: '/api/admin/market/gift/add',
  method: 'post',
  data
})

// 删除赠品活动
export const deleteGift = id => service({
  url: `/api/admin/market/gift/delete/${id}`,
  method: 'post'
})

// 停用赠品活动
export const disableGift = id => service({
  url: `/api/admin/market/gift/disable/${id}`,
  method: 'post'
})

// 启用赠品活动
export const enableGift = id => service({
  url: `/api/admin/market/gift/enable/${id}`,
  method: 'post'
})

// 修改赠品活动优先级
export const updateGiftLevel = data => service({
  url: '/api/admin/market/gift/level/update',
  method: 'post',
  data
})

// 修改赠品活动
export const updateGift = data => service({
  url: '/api/admin/market/gift/update',
  method: 'post',
  data
})

// 查询赠品活动明细
export const getGiftDetail = id => service({
  url: `/api/admin/market/gift/detail/${id}`,
  method: 'post'
})

// 查询会员卡列表
export const getMemberCardList = () => service({
  url: `/api/admin/market/gift/member_card/list`,
  method: 'post'
})

// 查询用户标签列表
export const getTagList = () => service({
  url: `/api/admin/market/gift/tag/list`,
  method: 'post'
})

// 查询赠品赠送明细
export const getGiftGiftDetail = data => service({
  url: `/api/admin/market/gift/gift_detail`,
  method: 'post',
  data
})

// 查询商品明细
export const getProductDetail = (giftId, productId) => service({
  url: `/api/admin/market/gift/product/${giftId || 0}/${productId}`,
  method: 'post'
})

export function giftConfig (data) {
  return service({
    url: `/api/admin/market/gift/cfg/set?cfg=${data}`,
    method: 'post'
  })
}

export function queryGiftConfig (data) {
  return service({
    url: '/api/admin/market/gift/cfg/get',
    method: 'post',
    data: data
  })
}
