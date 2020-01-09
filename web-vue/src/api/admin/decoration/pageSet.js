/**
 * 小程序管理-页面装修
 * 2019-09-27
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

// 获取分类数据
export function getPageCate (data) {
  return service({
    url: '/api/admin/decorate/cate/page',
    method: 'post',
    data: data
  })
}

// 页面设置分类
export function setPageCate (data) {
  return service({
    url: '/api/admin/decorate/cate/set',
    method: 'post',
    data: data
  })
}

// 批量设置页面分类
export function batchSet (data) {
  return service({
    url: '/api/admin/decorate/cate/batchSet',
    method: 'post',
    data: data
  })
}

// 删除装修页面
export function delPage (data) {
  return service({
    url: '/api/admin/decorate/page/del',
    method: 'post',
    data: data
  })
}

// 复制页面
export function pageCopy (data) {
  return service({
    url: '/api/admin/decorate/page/copy',
    method: 'post',
    data: data
  })
}

// 编辑获取页面信息
export function pageEdit (data) {
  return service({
    url: '/api/admin/decorate/page/get',
    method: 'post',
    data: data
  })
}

// 编辑保存页面信息
export function pageEditSave (data) {
  return service({
    url: '/api/admin/decorate/page/save',
    method: 'post',
    data: data
  })
}
