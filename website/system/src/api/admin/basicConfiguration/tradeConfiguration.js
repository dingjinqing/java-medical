/**
 * 交易配置
 */
import service from '@/util/request'

// 支付配置更新
export const payUpdate = data => service({
  url: '/api/admin/config/trade/enablePayment',
  method: 'post',
  data
})

// 查询支付配置
export const paySelect = () => service({
  url: '/api/admin/config/trade/getPaymentEnabled',
  method: 'post'
})
// 微信支付配置更新
export const wechatPayUpdate = data => service({
  url: '/api/admin/config/trade/wxpayConfig',
  method: 'post',
  data
})

// 查询微信支付配置
export const wechatPaySelect = () => service({
  url: '/api/admin/config/trade/getWxpayConfig',
  method: 'get'
})

// 交易流程配置更新
export const tradeUpdate = data => service({
  url: '/api/admin/config/trade/orderProcess',
  method: 'post',
  data
})

// 查询交易流程配置
export const tradeSelect = () => service({
  url: '/api/admin/config/trade/getOrderProcessConfig',
  method: 'post'
})

// 退换货配置更新
export const retrunUpdate = data => service({
  url: '/api/admin/config/trade/returnConfig',
  method: 'post',
  data
})

// 查询退换货配置
export const returnSelect = () => service({
  url: '/api/admin/config/trade/getReturnConfig',
  method: 'post'
})

// 服务条款配置
export const termUpdate = data => service({
  url: '/api/admin/config/trade/conftermsofservice',
  method: 'post',
  data
})

// 查询服务条款配置
export const termSelect = data => service({
  url: '/api/admin/config/trade/gettermsofservice',
  method: 'get',
  data
})
// 微信物流助手-绑定物流公司
export const bindaccount = data => service({
  url: '/api/admin/config/trade/bindaccount',
  method: 'post',
  data
})
