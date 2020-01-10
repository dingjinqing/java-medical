// 会员导入接口
import service from '@/util/request.js'

// 查询会员导入列表
export function getImportList (data) {
  return service({
    url: '/api/admin/user/import/list',
    method: 'post',
    data: data
  })
}

// 下载激活数据
export function exportActivate (data) {
  return service({
    url: '/api/admin/user/import/exportActivate',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
// 下载失败数据
export function exportFailData (data) {
  return service({
    url: '/api/admin/user/import/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}

// 查询激活通知
export function getnoticeApi () {
  return service({
    url: '/api/admin/user/import/getnotice',
    method: 'get'
  })
}
// 设置激活通知
export function setnoticeApi (data) {
  return service({
    url: '/api/admin/user/import/setnotice',
    method: 'post',
    data: data
  })
}

// 获取模板
export function getTemplate () {
  return service({
    url: '/api/admin/user/import/getTemplate',
    method: 'get',
    responseType: 'blob'
  })
}

// 分销员分组下拉
export function getDistributionGroup (params) {
  return service({
    url: '/api/admin/distribution/distributor/group',
    method: 'get'
  })
}

// 上传会员表格
export function importInsert (data) {
  return service({
    url: '/api/admin/user/import/insert',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 查看未激活会员列表
export function getNoActiveList (data) {
  return service({
    url: '/api/admin/user/import/list/noActive',
    method: 'post',
    data: data
  })
}
