import service from '@/util/request.js'

// 概览
export function shopViewApi (data) {
  return service({
    url: '/api/system/index/shopView',
    method: 'post',
    data: data
  })
}
