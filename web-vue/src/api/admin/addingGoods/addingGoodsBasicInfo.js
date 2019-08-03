import service from '@/util/request.js'

// 选择平台分类
export function selectPlatformClassification (parentId) {
  return service({
    url: `/api/admin/decorate/cate/child?parentId=${parentId}`,
    method: 'get'
  })
}
