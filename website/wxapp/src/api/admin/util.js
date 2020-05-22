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

// 小程序瓜分积分规则接口
export function integrationRules (data) {
  return service({
    url: `/api/wxapp/pinintegration/help?shop_id=${data.shop_id}&pid=${data.pid}`,
    method: 'get'
  })
}

// 问题反馈
export function feedbackRequest (data) {
  return service({
    url: '/api/admin/question/feedback',
    method: 'post',
    data: data
  })
}

// 小程序拼团抽奖规则接口
export function pinLotteryRules (data) {
  return service({
    url: `/api/wxapp/groupDraw/help?shopId=${data.shop_id}&groupDrawId=${data.gid}`,
    method: 'get'
  })
}

// 小程序拼团规则接口
export function pinRulesApi (data) {
  return service({
    url: '/api/wxapp/groupbuy/copywriting?shopId=' + data.shopId + '&id=' + data.id,
    method: 'get'
  })
}

// 小程序好友助力规则接口
export function promoteRules (data) {
  return service({
    url: `/api/wxapp/promote/actCopywriting?shopId=${data.shopId}&actCode=${data.actCode}`,
    method: 'get'
  })
}

// 小程序砍价规则接口
export function bargainRules (data) {
  return service({
    url: `/api/wxapp/bargain/rule?shopId=${data.shopId}&id=${data.id}`,
    method: 'get'
  })
}
