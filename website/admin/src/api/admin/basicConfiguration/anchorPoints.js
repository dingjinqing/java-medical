import service from '@/util/request.js'

// 获取事件和关键字
export function getEventKeyMap (data) {
  return service({
    url: '/api/admin/anchor/points/event/key/map',
    method: 'post',
    data: data
  })
}

// 查询埋点数据
export function getAnchorPointsList (data) {
  return service({
    url: '/api/admin/anchor/points/list',
    method: 'post',
    data: data
  })
}
