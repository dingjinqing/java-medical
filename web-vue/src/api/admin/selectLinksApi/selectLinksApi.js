/**
 * 选择链接相关api
 */
import service from '@/util/request.js'
// 选择链接弹窗-商品链接
export function goodsListApi (params) {
  return service({
    url: '/api/admin/decorate/goods/list',
    method: 'post',
    params
  })
}
// 选择链接弹窗-自定义页面
export function pageCustomApi (params) {
  return service({
    url: '/api/admin/decorate/page/custom',
    method: 'post',
    params
  })
}
// 通用弹窗选择链接-添加网页跳转
export function webSaveApi (params) {
  return service({
    url: '/api/admin/decorate/web/save',
    method: 'post',
    params
  })
}
// 通用弹窗选择链接-网页跳转列表
export function webListApi () {
  return service({
    url: '/api/admin/decorate/web/list',
    method: 'get'
  })
}
