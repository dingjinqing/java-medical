import service from '@/util/request.js'

export function getExternalPageList (data) {
  return service({
    url: `/api/admin/medical/external/page/list`,
    method: 'post',
    data: data
  })
}
/* 保存his和药房已经匹配的数据 */
export function insertMatchedGoodsList (data) {
  return service({
    url: `/api/admin/medical/external/save/matched/goods`,
    method: 'post',
    data: data
  })
}

/* 确认匹配失败 */
export function failMatch (data) {
  return service({
    url: `/api/admin/medical/external/fail/match/goods`,
    method: 'post',
    data: data
  })
}
