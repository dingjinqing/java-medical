import service from '@/util/request.js'

//  获取绑定解绑状态
export function getbindUnBindStatus () {
  return service({
    url: '/api/admin/malloverview/getbindUnBindStatus',
    method: 'get'
  })
}

//  获取首页绑定时候的二维码
export function getOfficialQrCode () {
  return service({
    url: `/api/admin/public/service/bind/getOfficialQrCode`,
    method: 'get'
  })
}

//  绑定解绑
export function checkGoodThingRequest (data) {
  return service({
    url: '/api/admin/survey/official/bind',
    method: 'post',
    data: data
  })
}
