import service from '@/util/request.js'

// 修改用户头像、昵称接口
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
