import service from '@/util/request.js'

// 表单装修保存接口
export function formDecorationAdd (data) {
  return service({
    url: '/api/admin/formstatistics/addforminfo',
    method: 'post',
    data: data
  })
}
