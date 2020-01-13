import service from '@/util/request.js'

// -------------分销配置--------------------
// 获取分销配置
export function getDistribution (data) {
  return service({
    url: '/api/admin/distribution/get',
    method: 'get',
    data: data
  })
}

// 分销配置模板弹窗表格接口
export function shopDecorateList (data) {
  return service({
    url: '/api/admin/decorate/list',
    method: 'post',
    data: data
  })
}

// 查询商品
export function getSelectGoods (data) {
  return service({
    url: '/api/admin/goods/select',
    method: 'post',
    data: data
  })
}

// 查询模板
export function getSelectTemplate (data) {
  return service({
    url: '/api/admin/decorate/page/get',
    method: 'post',
    data: data
  })
}

// 设置分销配置
export function setDistribution (data) {
  return service({
    url: '/api/admin/distribution/set',
    method: 'post',
    data: data
  })
}

// 设置推广文案
export function setDocument (data) {
  return service({
    url: '/api/admin/distribution/document/set',
    method: 'post',
    data: data
  })
}

// 获取推广文案
export function getDocument (data) {
  return service({
    url: '/api/admin/distribution/document/get',
    method: 'get',
    data: data
  })
}

// -------------分销员等级配置-------------------
// 设置分销员等级配置
export function setDistributionLevel (data) {
  return service({
    url: '/api/admin/distribution/level/save',
    method: 'post',
    data: data
  })
}

// 获取分销员等级配置
export function getDistributionLevel (data) {
  return service({
    url: '/api/admin/distribution/level/list',
    method: 'post',
    data: data
  })
}

// 停用分销员配置
export function stopDistribution (data) {
  return service({
    url: `/api/admin/distribution/level/pause?id=${data}`,
    method: 'get',
    data: data
  })
}

// 启用分销员配置
export function startDistribution (data) {
  return service({
    url: `/api/admin/distribution/level/open?id=${data}`,
    method: 'get',
    data: data
  })
}

// -------------返利策略配置---------------------
// 获取返利策略列表
export function getPolicyList (data) {
  return service({
    url: '/api/admin/distribution/rebate/list',
    method: 'post',
    data: data
  })
}

// 添加返利策略
export function addPolicy (data) {
  return service({
    url: '/api/admin/distribution/rebate/set',
    method: 'post',
    data: data
  })
}

// 获取单个返利策略
export function getPolicyDetail (data) {
  return service({
    url: `/api/admin/distribution/rebate/edit?id=${data}`,
    method: 'get',
    data: data
  })
}

// 编辑返利策略
export function editPolicy (data) {
  return service({
    url: '/api/admin/distribution/rebate/save',
    method: 'post',
    data: data
  })
}

// 删除返利策略
export function deletePolicy (data) {
  return service({
    url: `/api/admin/distribution/rebate/delete?id=${data}`,
    method: 'get',
    data: data
  })
}

// 停用返利策略
export function stopPolicy (data) {
  return service({
    url: `/api/admin/distribution/rebate/pause?id=${data}`,
    method: 'get',
    data: data
  })
}

// 启用返利策略
export function startPolicy (data) {
  return service({
    url: `/api/admin/distribution/rebate/open?id=${data}`,
    method: 'get',
    data: data
  })
}

// 分销推广语列表
export function advertisementList (data) {
  return service({
    url: '/api/admin/distribution/promotion/list',
    method: 'post',
    data: data
  })
}

// 添加分销推广语
export function advertisementAdd (data) {
  return service({
    url: '/api/admin/distribution/promotion/add',
    method: 'post',
    data: data
  })
}

// 编辑分销推广语获取单条信息
export function advertisementGetOne (data) {
  return service({
    url: `/api/admin/distribution/promotion/edit?id=${data}`,
    method: 'get',
    data: data
  })
}

// 编辑保存分销推广语
export function advertisementSave (data) {
  return service({
    url: `/api/admin/distribution/promotion/save`,
    method: 'post',
    data: data
  })
}

// 停用分销推广语
export function advertisementPause (data) {
  return service({
    url: `/api/admin/distribution/promotion/pause?id=${data}`,
    method: 'get',
    data: data
  })
}

//  启用分销推广语
export function advertisementStart (data) {
  return service({
    url: `/api/admin/distribution/promotion/open?id=${data}`,
    method: 'get',
    data: data
  })
}

// 删除分销推广语
export function advertisementDelete (data) {
  return service({
    url: `/api/admin/distribution/promotion/delete?id=${data}`,
    method: 'get',
    data: data
  })
}

// 分销员分组列表
export function distributionGroup (data) {
  return service({
    url: '/api/admin/distribution/group/list',
    method: 'post',
    data: data
  })
}

// 添加分销员分组
export function distributionGroupAdd (data) {
  return service({
    url: '/api/admin/distribution/group/add',
    method: 'post',
    data: data
  })
}

// 获取分销员分组单条信息
export function distributionGroupEdit (data) {
  return service({
    url: `/api/admin/distribution/group/edit?id=${data}`,
    method: 'get',
    data: data
  })
}

// 编辑保存
export function distributionGroupSave (data) {
  return service({
    url: '/api/admin/distribution/group/edit',
    method: 'post',
    data: data
  })
}

// 删除分销员分组
export function distributionGroupDel (data) {
  return service({
    url: `/api/admin/distribution/group/del?id=${data}`,
    method: 'get',
    data: data
  })
}

// 分销员列表
export function distributorList (data) {
  return service({
    url: '/api/admin/distribution/distrobutor/list',
    method: 'post',
    data: data
  })
}

// 分销员等级列表
export function distributorLevelList (data) {
  return service({
    url: '/api/admin/distribution/distributor/level',
    method: 'get',
    data: data
  })
}
// 分销员分组列表
export function distributorGroupList (data) {
  return service({
    url: '/api/admin/distribution/distributor/group',
    method: 'get',
    data: data
  })
}

// 分销员已邀请用户列表
export function inviteUserList (data) {
  return service({
    url: '/api/admin/distribution/invited/list',
    method: 'post',
    data: data
  })
}

// 清除分销员身份
export function delDistributor (data) {
  return service({
    url: `/api/admin/distribution/distributor/del?userId=${data}`,
    method: 'get',
    data: data
  })
}

// 分销员分组添加分销员
export function addDistributor (data) {
  return service({
    url: '/api/admin/distribution/distributor/add',
    method: 'post',
    data: data
  })
}

// 佣金统计列表
export function brokerageList (data) {
  return service({
    url: '/api/admin/distributio/brokerage/list',
    method: 'post',
    data: data
  })
}

// 商品返利统计
export function goodsReturnStatistics (data) {
  return service({
    url: '/api/admin/distribution/rebate/goods/list',
    method: 'post',
    data: data
  })
}

// 查看商品返利详情
export function goodsReturnStatisticsDetail (data) {
  return service({
    url: '/api/admin/distribution/rebate/goods/list',
    method: 'post',
    data: data
  })
}

// 返利提现审核
export function withdrawCheck (data) {
  return service({
    url: '/api/admin/distribution/withdraw/list',
    method: 'post',
    data: data
  })
}

// 查看返利提现审核详情
export function withdrawDetail (data) {
  return service({
    url: `/api/admin/distribution/withdraw/detail?id=${data}`,
    method: 'get',
    data: data
  })
}

// 设置默认分组
export function setDefaultGroup (data) {
  return service({
    url: `/api/admin/distribution/group/default?id=${data}`,
    method: 'get',
    data: data
  })
}
// 取消默认分组
export function cancleDefaultGroup (data) {
  return service({
    url: `/api/admin/distribution/group/cancle?id=${data}`,
    method: 'get',
    data: data
  })
}

// -------------分销员审核--------------------
// 分销员审核列表
export function getCheckList (data) {
  return service({
    url: '/api/admin/distribution/distributor/check/list',
    method: 'post',
    data: data
  })
}

// 分销审核通过
export function getCheckPass (data) {
  return service({
    url: '/api/admin/distribution/distributor/check/pass',
    method: 'post',
    data: data
  })
}

// 分销审核不通过
export function getCheckRefuse (data) {
  return service({
    url: '/api/admin/distribution/distributor/check/refuse',
    method: 'post',
    data: data
  })
}
