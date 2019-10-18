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

/**
 * @description 日期格式化
 * 使用: new Date().format('yyyy-MM-dd hh:mm:ss')
 */
// eslint-disable-next-line no-extend-native
Date.prototype.format = function (format) {
  var o = {
    'M+': this.getMonth() + 1, // month
    'd+': this.getDate(), // day
    'h+': this.getHours(), // hour
    'm+': this.getMinutes(), // minute
    's+': this.getSeconds(), // second
    'q+': Math.floor((this.getMonth() + 3) / 3), // quarter
    'S': this.getMilliseconds() // millisecond
  }

  if (/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length))
  }

  for (var k in o) {
    if (new RegExp('(' + k + ')').test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length))
    }
  }
  return format
}
