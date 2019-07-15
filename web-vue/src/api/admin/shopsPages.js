import service from '@/util/request.js'

// 修改用户头像、昵称保存接口
export function accountManageRequest (riid) {
  return service({
    url: '/api/admin/account/manage',
    method: 'post',
    data: riid
  })
}

// 获取店铺列表接口

export function shopListRequest (riid) {
  return service({
    url: '/api/admin/account/shop/select',
    method: 'post',
    data: riid
  })
}

// 修改登录密码保存接口
export function modifyPasswordRequest (riid) {
  return service({
    url: '/api/admin/account/manage/updatepwd',
    method: 'post',
    data: riid
  })
}

// 选择店铺接口

export function changeShopRequest (riid) {
  return service({
    url: '/api/admin/account/shop/switch',
    method: 'post',
    data: riid
  })
}

// 上传单张图片接口
export function uponeImgRequest (riid) {
  return service({
    url: '/api/admin/image/uploadOneImgae',
    method: 'post',
    data: riid
  })
}

// 上传多张图片接口
export function upmoreImgsRequest (riid) {
  return service({
    url: '/api/admin/image/upload',
    method: 'post',
    data: riid
  })
}
