import moment from 'moment'

export const format = s => {
  if (!s) {
    return null
  }
  return moment(s).format('YYYY-MM-DD HH:mm:ss')
}

/* 解析日期字符串为js Date对象  */
export const parseDate = dateStr => {
  if (!dateStr) {
    return null
  }
  let m = moment(dateStr, 'YYYY-MM-DD HH:mm:ss')
  return m.toDate()
}

export const range = range => ({
  v1: range[0],
  v2: range[1]
})
