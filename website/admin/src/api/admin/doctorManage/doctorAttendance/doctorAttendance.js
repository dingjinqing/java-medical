import service from '@/util/request.js'
export function getAttendanceSummaryList (data) {
  return service({
    url: '/api/admin/doctors/attendance/summary/list',
    method: 'post',
    data: data
  })
}
export function getDoctorSummaryList (data) {
  return service({
    url: '/api/admin/doctors/summary/list',
    method: 'post',
    data: data
  })
}
export function getDepartment () {
  return service({
    url: '/api/admin/doctor/department/select/list',
    method: 'get'
  })
}
export function getAttendanceDivide (data) {
  return service({
    url: '/api/admin/doctors/attendance/summary/divide',
    method: 'post',
    data: data
  })
}
