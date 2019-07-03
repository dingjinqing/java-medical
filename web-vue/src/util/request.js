import axios from 'axios'
// import qs from 'qs'
import { Message } from 'element-ui'

// 环境的切换
let baseURL = ''
if (process.env.NODE_ENV === 'development') {
  baseURL = 'vpb'
  localStorage.setItem('V-ImageHost', 'http://mpimg2.weipubao.cn')
} else if (process.env.NODE_ENV === 'testing') {
  // baseURL = 'https://www.ceshi.com'
} else if (process.env.NODE_ENV === 'production') {
  // baseURL = 'https://www.production.com'
}
console.log(process.env.NODE_ENV, baseURL)
// 创建axios实例
const service = axios.create({
  baseURL: baseURL, // api的base_url
  timeout: 50000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  config => {
    if (config.method === 'post') {
      console.log(localStorage.getItem('contentType'))
      config.headers['Content-Type'] = localStorage.getItem('contentType')
      if (localStorage.getItem('V-Token')) {
        config.headers['V-Token'] = localStorage.getItem('V-Token')
      }
      config.headers['V-Lang'] = localStorage.getItem('WEPUBAO_LANGUAGE')
      // config.data = qs.stringify(config.data)
    }

    return config
  },
  error => {
    // Do something with request error
    Message.error({ message: '请求超时!' })
    console.log('err：' + error) // for debug
    Promise.reject(error)
  }
)

// respone拦截器
service.interceptors.response.use(
  response => {
    const res = response
    if (res) {
      switch (res.status) {
        // 成功
        case 200:
          return res.data
        default:
          Message.error({
            message: res.data.message,
            showClose: true
          })
      }
    }
    return res
  },
  error => {
    if (error && error.response) {
      switch (error.response.status) {
        case 401:
          error.message = '抱歉，您没有访问此操作的权限！'
          break
        case 404:
          error.message = '抱歉，您请求的资源不存在！'
          break
        default:
          error.message = `服务正在处理，请稍后。`
      }
    } else {
      error.message = '服务正在处理，请稍后。'
    }
    Message.error({
      message: error.message,
      showClose: true
    })
    return Promise.reject(error)
  }
)

export default service
