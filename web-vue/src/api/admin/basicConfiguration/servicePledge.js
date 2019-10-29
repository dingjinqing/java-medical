/**
 * admin>基础配置>服务承诺
 * @ Author 梁晨
 */
import service from '@/util/request.js'

// 服务承诺列表
export function pledgeList () {
  return service({
    url: '/api/admin/config/pledge/list',
    method: 'get'
  })
}
// 新增服务承诺
export function addPledge (data) {
  return service({
    url: '/api/admin/config/pledge/add',
    method: 'post',
    data: data
  })
}
// 服务承诺--删除(逻辑删除)
export function delPledge (data) {
  return service({
    url: '/api/admin/config/pledge/delete',
    method: 'post',
    data: data
  })
}
// 编辑服务承诺
export function editPledge (data) {
  return service({
    url: '/api/admin/config/pledge/updateInfo',
    method: 'post',
    data: data
  })
}
// 开启或关闭服务(总)
export function totalSwitch (state) {
  return service({
    url: `/api/admin/config/pledge/updateTotalSwitch?state=${state}`,
    method: 'get'
  })
}
// 开启或关闭服务(单个)
export function oneSwitch (data) {
  return service({
    url: '/api/admin/config/pledge/updateState',
    method: 'post',
    data: data
  })
}
