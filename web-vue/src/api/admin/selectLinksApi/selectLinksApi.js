/**
 * 选择链接相关api
 */
import service from '@/util/request.js'
// 常用链接接口
export function commonLinksRequest (params) {
  return service({
    url: '/api/admin/decorate/style/update',
    method: 'post',
    data: params
  })
}
// 选择链接弹窗-商品链接
export function goodsListApi (data) {
  return service({
    url: '/api/admin/decorate/goods/list',
    method: 'post',
    data
  })
}
// 选择链接弹窗-自定义页面
export function pageCustomApi (data) {
  return service({
    url: '/api/admin/decorate/page/custom',
    method: 'post',
    data
  })
}
// 拼团抽奖
export function pinListRequest () {
  return service({
    url: '/api/admin/decorate/pin/list',
    method: 'get'
  })
}
// 瓜分积分
export function integrationListRequest () {
  return service({
    url: '/api/admin/decorate/integration/list',
    method: 'get'
  })
}
// 好友助力
export function promoteListRequest () {
  return service({
    url: '/api/admin/decorate/promote/list',
    method: 'get'
  })
}
// 加购价
export function priceListRequest () {
  return service({
    url: '/api/admin/decorate/price/list',
    method: 'get'
  })
}
// 幸运抽奖
export function lotteryListRequest () {
  return service({
    url: '/api/admin/decorate/lottery/list',
    method: 'get'
  })
}
// 满减满折
export function mrkingListRequest () {
  return service({
    url: '/api/admin/decorate/mrking/list',
    method: 'get'
  })
}
// 一口价
export function packageListRequest () {
  return service({
    url: '/api/admin/decorate/package/list',
    method: 'get'
  })
}
// 优惠卷
export function voucherListRequest () {
  return service({
    url: '/api/admin/decorate/voucher/list',
    method: 'get'
  })
}
// 会员卡
export function cardListRequest () {
  return service({
    url: '/api/admin/decorate/card/list',
    method: 'get'
  })
}
// 测评
export function assessListRequest () {
  return service({
    url: '/api/admin/decorate/assess/list',
    method: 'get'
  })
}
// 优惠礼包
export function packListRequest () {
  return service({
    url: '/api/admin/decorate/pack/list',
    method: 'get'
  })
}
// 通用弹窗选择链接-添加网页跳转
export function webSaveApi (data) {
  return service({
    url: '/api/admin/decorate/web/save',
    method: 'post',
    data
  })
}
// 通用弹窗选择链接-网页跳转列表
export function webListApi () {
  return service({
    url: '/api/admin/decorate/web/list',
    method: 'get'
  })
}
// 通用弹窗选择链接-小程序跳转保存
export function linkSaveApi (data) {
  return service({
    url: '/api/admin/decorate/link/save',
    method: 'post',
    data
  })
}
// 通用弹窗选择链接-小程序跳转列表
export function linkListApi () {
  return service({
    url: '/api/admin/decorate/link/list',
    method: 'post'
  })
}
// 通用弹窗选择链接-小程序跳转列表
export function xcxApi (params) {
  return service({
    url: '/api/admin/decorate/xcx/list',
    method: 'post',
    params: params
  })
}
// 小程序跳转链接-删除接口
export function delListApi (query) {
  return service({
    url: `/api/admin/decorate/web/list?=${query}`,
    method: 'get'
  })
}
// 通用弹窗选择链接-表单页面
export function formListApi () {
  return service({
    url: '/api/admin/decorate/form/list',
    method: 'get'
  })
}
// 通用弹窗选择链接-门店链接
export function storeListApi (data) {
  return service({
    url: '/api/admin/decorate/store/list',
    method: 'post',
    data
  })
}

// 平台分类接口
export function cateListApi (params) {
  return service({
    url: '/api/admin/goods/filterItem/list',
    method: 'post',
    data: params
  })
}
