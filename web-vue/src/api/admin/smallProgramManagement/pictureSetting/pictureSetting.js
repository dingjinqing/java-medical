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
