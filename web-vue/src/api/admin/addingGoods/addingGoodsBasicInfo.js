import service from '@/util/request.js'

// 平台分类-根据父ID获取下级分类
export function selectPlatformClassification (parentId) {
  return service({
    url: `/api/admin/decorate/cate/child?parentId=${parentId}`,
    method: 'get'
  })
}
// 选择平台分类
export function cateList () {
  return service({
    url: `/api/admin/decorate/cate/list`,
    method: 'get'
  })
}
