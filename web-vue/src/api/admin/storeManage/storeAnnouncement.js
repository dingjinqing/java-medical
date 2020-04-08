import service from '@/util/request.js'

export function announcementListApi (data) {
  return service({
    url: '/api/admin/store/article/list',
    data: data,
    method: 'post'
  })
}

export function announcementAddApi (data) {
  return service({
    url: '/api/admin/store/article/add',
    data,
    method: 'post'
  })
}

export function announcementDetailApi (data) {
  return service({
    url: '/api/admin/store/article/get',
    data,
    method: 'post'
  })
}

export function announcementUpdateApi (data) {
  return service({
    url: '/api/admin/store/article/update',
    data,
    method: 'post'
  })
}

export function announcementDeleteApi (data) {
  return service({
    url: '/api/admin/store/article/del',
    data,
    method: 'post'
  })
}
