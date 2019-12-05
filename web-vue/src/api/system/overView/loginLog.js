import service from '@/util/request.js'

// 登录日志
export function loginLog (data) {
  return service({
    url: '/api/system/overView/loginRecord',
    method: 'post',
    data: data
  })
}
