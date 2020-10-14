import service from '@/util/request.js'

export function getExternalPageList (data) {
  return service({
    url: `/api/admin/medical/external/page/list`,
    method: 'post',
    data: data
  })
}
