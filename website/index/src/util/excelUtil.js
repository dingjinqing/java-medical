// 下载文件
export function download (data, fileName) {
  if (!data) {
    return
  }
  let url = window.URL.createObjectURL(new Blob([data]))
  let link = document.createElement('a')
  link.style.display = 'none'
  link.href = url
  link.setAttribute('download', decodeURI(fileName))

  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
  // 释放掉blob资源
  window.URL.revokeObjectURL(url)
}
