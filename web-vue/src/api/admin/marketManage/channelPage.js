import service from '@/util/request.js'

// 渠道分析列表页面
export function channelList (data) {
  return service({
    url: '/api/admin/market/channel/list',
    method: 'post',
    data: data
  })
}

// 停用渠道页面
export function stopChannelPage (data) {
  return service({
    url: `/api/admin/market/channel/disable/${data}`,
    method: 'post'
  })
}

// 启用渠道分析页面
export function openChannelPage (id) {
  return service({
    url: `/api/admin/market/channel/enable/${id}`,
    method: 'post'
  })
}

// 分享渠道分析页面
export function shareChannelPage (id) {
  return service({
    url: `/api/admin/market/channel/share/${id}`,
    method: 'post'
  })
}

// 添加渠道页面
export function addChannelAct (data) {
  return service({
    url: '/api/admin/market/channel/add',
    method: 'post',
    data: data
  })
}

// 渠道页面数据统计
export function channelData (data) {
  return service({
    url: '/api/admin/market/channel/statistical',
    method: 'post',
    data: data
  })
}
