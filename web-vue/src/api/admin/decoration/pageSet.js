/**
 * 小程序管理-页面装修
 * user：常乐
 */
import service from '@/util/request.js'

// 自定义页面装修列表
export function pageList (data) {
  return service({
    url: '/api/admin/decorate/list',
    method: 'post',
    data: data
  })
}

// 设为首页
export function setFirstPage (data) {
  return service({
    url: '/api/admin/decorate/index/set',
    method: 'post',
    data: data
  })
}
