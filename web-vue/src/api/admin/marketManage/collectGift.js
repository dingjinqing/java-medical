import service from '@/util/request.js'

// 收藏有礼开关配置
export function collectGiftStatus (data) {
  return service({
    url: '/api/admin/market/collect/gift/status',
    method: 'get',
    data: data
  })
}

// 查询收藏有礼配置信息
export function collectGiftSelect (data) {
  return service({
    url: '/api/admin/market/collect/gift/select',
    method: 'get',
    data: data
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
