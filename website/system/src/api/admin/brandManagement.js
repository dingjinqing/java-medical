import service from '@/util/request.js'

// 商品分页查询
export function brandAllGetRequest (data) {
  return service({
    url: '/api/admin/goods/brand/list',
    method: 'post',
    data: data
  })
}

// 添加品牌
export function brandAddRequest (data) {
  return service({
    url: '/api/admin/goods/brand/add',
    method: 'post',
    data: data
  })
}

// 商品品牌删除
export function brandDeleteGetRequest (data) {
  return service({
    url: `/api/admin/goods/brand/delete/${data}`,
    method: 'get'
  })
}

// 商品品牌修改
export function brandUpdateGetRequest (data) {
  return service({
    url: '/api/admin/goods/brand/update',
    method: 'post',
    data: data
  })
}

// 根据ID查询品牌
export function brandSelectGetRequest (data) {
  return service({
    url: '/api/admin/goods/brand/select',
    method: 'post',
    data: data
  })
}

// 品牌分类下拉框数据获取
export function classificationSelectRequest () {
  return service({
    url: '/api/admin/goods/brand/classify/name/list',
    method: 'get'
  })
}

// 修改品牌分类
export function modifyGrandRequest (data) {
  return service({
    url: '/api/admin/goods/brand/classify/update',
    method: 'post',
    data: data
  })
}

// 新增品牌分类
export function addGrandClassRequest (data) {
  return service({
    url: '/api/admin/goods/brand/classify/add',
    method: 'post',
    data: data
  })
}

// 商品分页查询
export function allGoodsQueryRequest (data) {
  return service({
    url: '/api/admin/goods/list',
    method: 'post',
    data: data
  })
}

// 根据ids 查询商品
export function getGoodsListByIds (data) {
  return service({
    url: '/api/admin/goods/info/list',
    method: 'post',
    data: data
  })
}
// 商品规格查询
export const getProductListByIds = data => {
  return service({
    url: '/api/admin/goods/product/info/list',
    method: 'post',
    data
  })
}
// 全部商品id
export function getGoodsIdsListAll (data) {
  return service({
    url: '/api/admin/goods/listAllIds',
    method: 'post',
    data: data
  })
}
// 商品规格查询
export const getGoodsProductList = data => {
  return service({
    url: '/api/admin/goods/product/list',
    method: 'post',
    data
  })
}
// 全部商品规格id
export const getProductIdsListAll = data => {
  return service({
    url: '/api/admin/goods/product/listAllIds',
    method: 'post',
    data
  })
}
//  查询商品全部规格
export const getAllGoodsProductList = goodsId => {
  return service({
    url: `/api/admin/goods/product/all/${goodsId}`,
    method: 'get'
  })
}

// 根据id查询品牌
export function queryGoodsIdRequest (data) {
  return service({
    url: `/api/admin/goods/brand/select/${data}`,
    method: 'get'
  })
}

// 分页查询品牌分类
export function pagingBrandQueryRequest (data) {
  return service({
    url: '/api/admin/goods/brand/classify/list',
    method: 'post',
    data: data
  })
}

// 品牌分类页品牌删除接口
export function pagingBrandDelRequest (data) {
  return service({
    url: `/api/admin/goods/brand/classify/delete/${data}`,
    method: 'get'
  })
}

// 品牌分类页品牌修改接口
export function pagingBrandUpdateRequest (data) {
  return service({
    url: '/api/admin/goods/brand/classify/update',
    method: 'post',
    data: data
  })
}

// 品牌展示设置回显
export function showBrandgetRequest () {
  return service({
    url: '/api/admin/goods/brand/config/get',
    method: 'get'
  })
}

// 品牌展示设置保存
export function saveShowBrandgetRequest (data) {
  return service({
    url: '/api/admin/goods/brand/config/set',
    method: 'post',
    data: data
  })
}

// 选择商品弹窗顶部下拉框统一接口
export function initGrandgetRequest (data) {
  data = data || {}
  return service({
    url: '/api/admin/goods/page/init',
    method: 'post',
    data
  })
}

export function test (data) {
  return service({
    url: '/api/admin/market/bargain/record/list/export',
    method: 'post',
    data: data
  })
}

export function batchBind (data) {
  return service({
    url: '/api/admin/goods/brand/classify/batch/bind',
    method: 'post',
    data: data
  })
}
