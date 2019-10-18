import service from '@/util/request.js'

// 权限判断
export function judgeJurisdictionRequest (data) {
  return service({
    url: '/api/admin/checkMenu',
    method: 'post',
    data: data
  })
}

// 小程序使用积分页面接口
export function scoreDocumentRequest (data) {
  return service({
    url: `/api/wxapp/score/scoreDocument?shop_id=${data.shop_id}&user_id=${data.user_id}`,
    method: 'get'
  })
}
