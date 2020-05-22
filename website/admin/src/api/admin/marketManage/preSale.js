/**
 * 定金膨胀活动
 *
 * @author 郑保乐
 */
import service from '@/util/request'

// 定金膨胀活动
export const getPageList = data => service({
  url: '/api/admin/market/pre_sale/list',
  method: 'post',
  data
})

// 创建定金膨胀活动
export const createPreSale = data => service({
  url: '/api/admin/market/pre_sale/add',
  method: 'post',
  data
})

// 删除定金膨胀活动
export const deletePreSale = id => service({
  url: `/api/admin/market/pre_sale/delete/${id}`,
  method: 'post'
})

// 停用定金膨胀活动
export const disablePreSale = id => service({
  url: `/api/admin/market/pre_sale/disable/${id}`,
  method: 'post'
})

// 启用定金膨胀活动
export const enablePreSale = id => service({
  url: `/api/admin/market/pre_sale/enable/${id}`,
  method: 'post'
})

// 定金膨胀活动订单
export const getOrderList = data => service({
  url: `/api/admin/market/pre_sale/order`,
  method: 'post',
  data
})

// 定金膨胀活动明细
export const getDetailPageList = data => service({
  url: '/api/admin/market/pre_sale/detail',
  method: 'post',
  data
})

// 定金膨胀活动编辑 - 查询明细
export const getDetail = id => service({
  url: `/api/admin/market/pre_sale/detail/${id}`,
  method: 'post'
})

// 定金膨胀活动编辑 - 更新
export const updatePreSale = data => service({
  url: '/api/admin/market/pre_sale/update',
  method: 'post',
  data
})

// 定金膨胀活动 - 分享
export const sharePreSale = id => service({
  url: `/api/admin/market/pre_sale/share/${id}`,
  method: 'post'
})

// 定金膨胀活动订单 - 导出 Excel
export const exporOrderExcel = data => service({
  url: '/api/admin/market/pre_sale/order/export',
  method: 'post',
  data: data,
  responseType: 'blob'
})
