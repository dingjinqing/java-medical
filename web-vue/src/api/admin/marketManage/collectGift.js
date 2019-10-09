import service from '@/util/request.js'

// 收藏有礼开关配置
export function collectGiftStatus () {
  return service({
    url: '/api/admin/market/collect/gift/status',
    method: 'get'
  })
}

// 查询收藏有礼配置信息
export function collectGiftSelect () {
  return service({
    url: '/api/admin/market/collect/gift/select',
    method: 'get'
  })
}

// 修改收藏有礼配置信息
export function collectGiftUpdate (data) {
  return service({
    url: '/api/admin/market/collect/gift/update',
    method: 'post',
    data: data
  })
}
