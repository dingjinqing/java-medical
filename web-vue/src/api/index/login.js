import request from '@/util/request'

//  登录  -接口

// 列表
export function loginRequest (riid) {
  return request({
    url: '/admin/login',
    method: 'post',
    data: riid
  })
}

// export function viewOne(id) {
//     return request({
//         url: `receivesys/note/open/${id}`,
//         method: 'get'
//     })
// }
