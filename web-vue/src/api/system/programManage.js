import service from '@/util/request'

//  同步微信开放平台小程序模板
export function synchronizationRequest () {
  return service({
    url: '/api/system/mp/version/syn',
    method: 'post'
  })
}

//  小程序版本管理 分页
export function smallProManRequest (data) {
  return service({
    url: '/api/system/mp/version/list',
    method: 'post',
    data: data
  })
}
//  设置为当前使用版本接口
export function setNowVersionRequest (data) {
  return service({
    url: `/api/system/mp/version/set/${data}`,
    method: 'get'
  })
}

// 当前包版本设置
export function setCurrentVersionRequest (data) {
  return service({
    url: '/api/system/mp/package/version',
    method: 'post',
    data: data
  })
}

// 小程序授权列表
export function authListRequest (data) {
  return service({
    url: '/api/system/mp/auth/list',
    method: 'post',
    data: data
  })
}

// 获取小程序版本下拉列表
export function SpinnerListRequest () {
  return service({
    url: '/api/system/mp/version/user/version/list',
    method: 'get'
  })
}

// 小程序版本统计
export function versionStatRequest (data) {
  return service({
    url: '/api/system/mp/version/stat',
    method: 'post',
    data: data
  })
}

// 小程序操作日志
export function operateLogRequest (data) {
  return service({
    url: '/api/system/mp/operate/log/list',
    method: 'post',
    data: data
  })
}

// 小程序授权信息
export function authInformationRequest (data) {
  return service({
    url: `/api/system/mp/get/${data}`,
    method: 'get'
  })
}

// 小程序授权信息页面设置弹窗统一设置接口
export function publishRequest (data) {
  return service({
    url: '/api/system/mp/publish',
    method: 'post',
    data: data
  })
}

// 当前模板id查询
export function nowIdRequest (data) {
  return service({
    url: `/api/system/mp/info/useTemplateId/${data}`,
    method: 'get'
  })
}

// 小程序授权信息页面按钮统一处理接口
export function publishSetRequest (data) {
  return service({
    url: '/api/system/mp/publish',
    method: 'post',
    data: data
  })
}
