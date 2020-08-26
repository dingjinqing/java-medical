import service from '@/util/request.js'

// 获取商品列表
export function getGoodsList (data) {
  return service({
    url: '/api/admin/store/goods/list',
    method: 'post',
    data: data
  })
}

// 获取平台分类
export function getCateList () {
  return service({
    url: '/api/admin/decorate/cate/list',
    method: 'get'
  })
}

// 更新商品
export function updateGoods (data) {
  if (!data.storeId) return Promise.reject(new Error('商家id传递错误!'))
  return service({
    url: '/api/admin/store/goods/updateFromShop/' + data.storeId,
    method: 'get'
  })
}

// 上架
export function shelfGoods (data) {
  return service({
    url: '/api/admin/store/goods/on',
    method: 'post',
    data: data
  })
}

// 下架
export function obtainGoods (data) {
  return service({
    url: '/api/admin/store/goods/off',
    method: 'post',
    data: data
  })
}
