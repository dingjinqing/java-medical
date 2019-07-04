import service from '@/util/request.js'

//  申请试用  -接口

// 列表
export function applyrequest (riid) {
  console.log(window.location)
  return service({
    url: '/api/official/experience/add',
    method: 'post',
    data: riid
  })
}

// export function get () {
//   return service({
//     url: 'v2/movie/in_theaters',
//     method: 'get'
//   })
// }
