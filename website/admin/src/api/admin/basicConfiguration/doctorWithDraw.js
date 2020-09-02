import service from '@/util/request.js'

// 获取医师返利配置
export function getWithDrawConfig (data) {
  return service({
    url: '/api/admin/rebate/get',
    method: 'post',
    data: data
  })
}
// 设置返利信息
export function setWithDrawConfig (data) {
  return service({
    url: '/api/admin/rebate/set',
    method: 'post',
    data: data
  })
}
