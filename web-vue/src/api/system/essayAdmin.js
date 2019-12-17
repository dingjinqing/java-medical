import service from '@/util/request.js'

// 文章分类
export function getCategoryRequest (data) {
  return service({
    url: '/api/system/article/category/allList',
    method: 'post',
    data: data
  })
}

// 获取文章列表
export function getListRequest (data) {
  return service({
    url: '/api/system/article/list',
    method: 'post',
    data: data
  })
}

// 修改文章状态
export function updateStatusRequest (data) {
  return service({
    url: '/api/system/article/updateStatus',
    method: 'post',
    data: data
  })
}

// 删除文章
export function deleteRequest (data) {
  return service({
    url: '/api/system/article/delete',
    method: 'post',
    data: data
  })
}

// 文章分类分页查询
export function searchOneAccountRequest (data) {
  return service({
    url: '/api/system/article/category/list',
    method: 'post',
    data: data
  })
}

// 文章分类编辑
export function updatecategoryRequest (data) {
  return service({
    url: '/api/system/article/category/update',
    method: 'post',
    data: data
  })
}

// 文章分类删除
export function deleteCategoryRequest (data) {
  return service({
    url: '/api/system/article/category/delete',
    method: 'post',
    data: data
  })
}

// 文章分类删除前查询有多少文章
export function beforedeleteCategoryRequest (data) {
  return service({
    url: '/api/system/article/category/listNum',
    method: 'post',
    data: data
  })
}

// 增加文章分类
export function addCategoryRequest (data) {
  return service({
    url: '/api/system/article/category/add',
    method: 'post',
    data: data
  })
}

// 编辑文章分类
export function editCategoryRequest (data) {
  return service({
    url: '/api/system/article/category/update',
    method: 'post',
    data: data
  })
}

// 增加文章
export function addArticleRequest (data) {
  return service({
    url: '/api/system/article/add',
    method: 'post',
    data: data
  })
}

// 获取单一文章信息
export function getArticleRequest (data) {
  return service({
    url: '/api/system/article/get',
    method: 'post',
    data: data
  })
}

// 更新单一文章信息
export function updateArticleRequest (data) {
  return service({
    url: '/api/system/article/update',
    method: 'post',
    data: data
  })
}
