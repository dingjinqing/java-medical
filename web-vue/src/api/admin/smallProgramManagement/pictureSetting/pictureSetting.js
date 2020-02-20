import service from '@/util/request.js'

// 页面装修-页面装修列表
export function shopDecorateList (data) {
  return service({
    url: '/api/admin/decorate/list',
    method: 'post',
    data: data
  })
}

// 页面装修编辑保存接口
export function saveDecorationPage (data) {
  return service({
    url: '/api/admin/decorate/page/add',
    method: 'post',
    data: data
  })
}

// 商品列表模块接口
export function queryDataList (data) {
  return service({
    url: '/api/admin/goods/mp/list',
    method: 'post',
    data: data
  })
}

// 页面编辑保存
export function editSave (data) {
  return service({
    url: '/api/admin/decorate/page/save',
    method: 'post',
    data: data
  })
}

// 获取页面装修页面分类数据
export function getClassifyData (data) {
  return service({
    url: '/api/admin/decorate/cate/page',
    method: 'post'
  })
}

// 拼团抽奖活动下拉
export function getFightGroup (data) {
  return service({
    url: '/api/admin/decorate/pin/list',
    method: 'get'
  })
}

// 页面装修会员卡弹窗接口
export function allCardData (data) {
  return service({
    url: '/api/admin/member/card/all/list',
    method: 'post',
    data: data
  })
}

// 页面装修模块权限
export function getModulesJusList () {
  return service({
    url: '/api/admin/checkMenu/showMa',
    method: 'get'
  })
}

// 瓜分积分下拉框
export function getDropDownBox () {
  return service({
    url: '/api/admin/market/integration/selectlist',
    method: 'get'
  })
}

// 积分兑换活动弹窗
export function getIntegralExchange (data) {
  return service({
    url: '/api/admin/market/integral/convert/pop',
    method: 'post',
    data: data
  })
}

// 商品分组模块接口
export function getGoodsGroupData (data) {
  return service({
    url: '/api/admin/goods/mp/group/list',
    method: 'post',
    data: data
  })
}

// 商品分组-根据条件获取商品数量
export function getGoodsNum (data) {
  return service({
    url: '/api/admin/goods/nums',
    method: 'post',
    data: data
  })
}

// 装修模板弹窗数据获取
export function getTemplatesData () {
  return service({
    url: '/api/admin/decorate/templates',
    method: 'get'
  })
}
// 获取处理过得装修弹窗数据
export function getHandleTemplatesData (data) {
  return service({
    url: `/api/admin/decorate/templates/get?id=${data}`,
    method: 'get'
  })
}
