import request from '@/util/request'

// 首页文章列表
export function articleRequest (riid) {
  return request({
    url: '/api/official/article/list',
    method: 'post',
    data: riid
  })
}

// 新闻详情
export function newsDetailRequest (riid) {
  return request({
    url: '/api/official/article/get',
    method: 'post',
    data: riid
  })
}
