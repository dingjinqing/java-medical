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

// 小程序签到帮助页接口
export function signruleRequest (data) {
  return service({
    url: `/api/wxapp/sign/help?shop_id=${data.shop_id}&user_id=${data.user_id}`,
    method: 'get'
  })
}

// 富文本编辑器图片上传
export function tichTextUpLoadRequest (data) {
  return service({
    url: '/api/admin/image/base64/uploadOneImgae',
    method: 'post',
    data: data
  })
}
