import service from '@/util/request.js'

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
