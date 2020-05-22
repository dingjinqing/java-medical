/* 字符串是否为空 */
export function isStrBlank (str) {
  if (str === undefined || str === null || str.trim() === '') {
    return true
  }
  return false
}
/* 数字是否为空值 */
export function isNumberBlank (num) {
  if (num === undefined || num === null || num === '') {
    return true
  }
  return false
}

export function isSixNumberWithTwoDecimal (num) {
  return /^\d{0,9}(\.\d{1,2})?$/.test(num)
}
