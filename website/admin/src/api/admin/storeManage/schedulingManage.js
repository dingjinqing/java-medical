// 排班管理
import service from '@/util/request.js'

// 查询所有班次
export function getAllShift (data) {
  return service({
    url: '/api/admin/store/services/schedule/list/' + data.storeId,
    method: 'get'
  })
}

// 添加班次
export function addSchedule (data) {
  return service({
    url: '/api/admin/store/services/schedule/add',
    method: 'post',
    data: data
  })
}

// 更新班次
export function updateScheduleAPI (data) {
  return service({
    url: '/api/admin/store/services/schedule/update',
    method: 'post',
    data: data
  })
}

//  删除班次
export function deleteScheduleAPI (data) {
  return service({
    url: '/api/admin/store/services/schedule/delete/' + data.scheduleId,
    method: 'post'
  })
}

// 查询该技师的班次
export function getScheduleList (data) {
  return service({
    url: '/api/admin/store/services/technician/schedule/list',
    method: 'post',
    data: data
  })
}

// 设置技师班次
export function saveScheduleAPI (data) {
  return service({
    url: '/api/admin/store/services/technician/schedule/save',
    method: 'post',
    data: data
  })
}
