import request from '@/util/request'

// system登录接口
export function loginRequest (riid) {
  return request({
    url: '/api/system/login',
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
