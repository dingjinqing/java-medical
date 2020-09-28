import service from '@/util/request.js'

// 获取门店账户列表
export function getAccountListApi (data) {
  return service({
    url: '/api/store/account/list',
    data,
    method: 'post'
  })
}

// 添加门店账户
export function addAccountApi (data) {
  return service({
    url: '/api/store/account/create',
    data,
    method: 'post'
  })
}
// 操作门店账户
export function editAccountApi (data) {
  return service({
    url: '/api/store/account/manage',
    data,
    method: 'post'
  })
}
// 读取账户信息
export function getAccountApi (accountId) {
  return service({
    url: `/api/store/account/getOne/${accountId}`,
    method: 'get'
  })
}

// 更新账户信息
export function updateAccountApi (data) {
  return service({
    url: '/api/store/account/edit',
    data,
    method: 'post'
  })
}

// 获取所有门店来源
export function allSourceRequest () {
  return service({
    url: '/api/store/all/get',
    method: 'post'
  })
}
// 门店分组列表
export function storeGroupList (data) {
  return service({
    url: '/api/store/group/list',
    method: 'post',
    data: data
  })
}
// 门店分组新增
export function addStoreGroup (data) {
  return service({
    url: '/api/store/group/add',
    method: 'post',
    data: data
  })
}
// 门店分组删除
export function delStoreGroup (data) {
  return service({
    url: '/api/store/group/del',
    method: 'post',
    data: data
  })
}

// 门店分组编辑
export function updateStoreGroup (data) {
  return service({
    url: '/api/store/group/update',
    method: 'post',
    data: data
  })
}

// 门店列表
export function storeList (data) {
  return service({
    url: '/api/store/list',
    method: 'post',
    data: data
  })
}

// 全部门店分组
export function allStoreGroup () {
  return service({
    url: '/api/store/group/all',
    method: 'get'
  })
}

// 门店更新
export function updateStore (data) {
  return service({
    url: '/api/store/store/update',
    method: 'post',
    data: data
  })
}

// 门店删除
export function delStore (data) {
  return service({
    url: '/api/store/del',
    method: 'post',
    data: data
  })
}
// 请求基础配置中的设置
export function getDeliveryConfig () {
  return service({
    url: '/api/store/get/config',
    method: 'get'
  })
}

export function getStoreList (data) {
  return service({
    url: '/api/store/store/list',
    method: 'post',
    data
  })
}

// 获取门店权限列表和选中状态
export function getSettingApi () {
  return service({
    url: '/api/store/account/getSetting',
    method: 'get'
  })
}

export function setStorePermissionApi (data) {
  return service({
    url: '/api/store/account/setting',
    data,
    method: 'post'
  })
}
// 权限判断
export function judgeJurisdictionRequest (data) {
  return service({
    url: '/api/admin/checkMenu',
    method: 'post',
    data: data
  })
}
// 导航权限判断
export function getShowMenu (data) {
  return service({
    url: '/api/store/showMenu',
    method: 'post',
    data: data
  })
}
// 首页查询未发货订单数
export function getUnfilledOrderNum () {
  return service({
    url: '/api/store/overview/wait/data',
    method: 'get'
  })
}
// 首页门店下单和支付统计数据
export function getOrderNum (data) {
  return service({
    url: '/api/store/overview/statistic/data',
    method: 'post',
    data
  })
}
// 公告列表
export function getArticleList (data) {
  return service({
    url: '/api/store/overview/article/list',
    method: 'post',
    data
  })
}
// 公告详情
export function getArticleDetail (articleId) {
  return service({
    url: `/api/store/overview/article/get/${articleId}`,
    method: 'get'
  })
}
// 获取门店下拉列表数据
export function getAllStoreList (data) {
  return service({
    url: '/api/store/all/get',
    method: 'post',
    data
  })
}
// 获取门店下拉列表数据
export function getOrderList (data) {
  return service({
    url: '/api/store/order/list',
    method: 'post',
    data
  })
}
// 获取绑定状态
export function getBindStatus () {
  return service({
    url: '/api/store/malloverview/getbindUnBindStatus',
    method: 'get'
  })
}
// 获取绑定二维码
export function getQrCode () {
  return service({
    url: '/api/store/public/service/bind/getOfficialQrCode',
    method: 'get'
  })
}
// 绑定解绑接口
export function setBind (data) {
  return service({
    url: '/api/store/survey/official/bind',
    method: 'post',
    data
  })
}
// 获取销售数据
export function getBestsellers (data) {
  return service({
    url: '/api/store/view/bestsellers',
    method: 'post',
    data
  })
}
