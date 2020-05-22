/**
 * 好友代付
 */
import service from '@/util/request.js'

// 获取好友代付配置
export function getFriendPayApi () {
  return service({
    url: '/api/admin/market/instead/pay/get',
    method: 'get'
  })
}

// 更新好友代付配置
export function updateFriendPayApi (data) {
  return service({
    url: '/api/admin/market/instead/pay/set',
    method: 'post',
    data: data
  })
}
