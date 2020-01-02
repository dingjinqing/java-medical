// 会员导入接口
import service from '@/util/request.js'

// 设置激活通知
export function setnoticeApi (data) {
  return service({
    url: '/api/admin/user/import/setnotice',
    method: 'post',
    data: data
  })
}
