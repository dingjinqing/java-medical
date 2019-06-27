import service from '@/util/request.js'

//  申请试用  -接口

// 列表
export function applyrequest () {
  console.log(window.location)
  return service({
    url: '/apply/json/tg/juSkip.json?callback=skipFn0&menuType=index&banner=true',
    method: 'get'
  })
}

// export function get () {
//   return service({
//     url: 'v2/movie/in_theaters',
//     method: 'get'
//   })
// }
