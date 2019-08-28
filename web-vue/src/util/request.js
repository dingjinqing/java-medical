import axios from 'axios'
import Cookies from 'js-cookie'
// import qs from 'qs'
import router from '@/router/index.js'
import { Message } from 'element-ui'

// 环境的切换
let baseURL = ''
if (process.env.NODE_ENV === 'development') {
  baseURL = '/vpb'
  // localStorage.setItem('V-ImageHost', 'http://mpimg2.weipubao.cn')
} else if (process.env.NODE_ENV === 'testing') {
  // baseURL = 'https://www.ceshi.com'
} else if (process.env.NODE_ENV === 'production') {
  // baseURL = 'https://www.production.com'
  // localStorage.setItem('V-ImageHost', 'http://mpimg2.weipubao.cn')
}
// console.log(process.env.NODE_ENV, baseURL)
// 创建axios实例
const service = axios.create({
  baseURL: baseURL, // api的base_url
  timeout: 50000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  config => {
    console.log(config.url.split('/')[2])

    config.headers['Content-Type'] = localStorage.getItem('contentType')
    // console.log(Cookies.get('V-Token'))
    if (config.url.split('/')[2] === 'admin') {
      config.headers['V-Token'] = Cookies.get('V-Index-Token')
    } else if (config.url.split('/')[2] === 'system') {
      config.headers['V-Token'] = Cookies.get('V-System-Token')
    }
    if (!localStorage.getItem('WEPUBAO_LANGUAGE')) {
      localStorage.setItem('WEPUBAO_LANGUAGE', 'zh_CN')
    }
    config.headers['V-Lang'] = localStorage.getItem('WEPUBAO_LANGUAGE')
    // config.data = qs.stringify(config.data)

    return config
  },
  error => {
    // Do something with request error
    Message.error({ message: '请求超时!' })
    // console.log('err：' + error) // for debug
    Promise.reject(error)
  }
)

// respone拦截器
service.interceptors.response.use(
  response => {
    console.log(response)
    const res = response
    let flag = localStorage.getItem('V-overallFlag')

    // console.log(flag, res)
    console.log(res)
    if (res) {
      switch (res.status) {
        // 成功
        case 200:
          if (res.data.error === 100004 && flag !== 'false') {
            console.log(res)
            Message.error({
              message: res.data.message,
              showClose: true
            })
            localStorage.removeItem('V-Username')
            router.push('/index/login')
          } else if (res.data.error === 100033 && flag !== 'false') {
            Message.error({
              message: res.data.message,
              showClose: true
            })
            router.push('/system/login')
          }
          if (res.data.error === 100001 && flag !== 'false') {
            Message.error({
              message: res.data.message,
              showClose: true
            })
          }
          localStorage.setItem('V-overallFlag', false)
          if (flag !== 'false') {
            setTimeout(() => {
              localStorage.setItem('V-overallFlag', true)
            }, 3000)
          }
          return res.data
      }
    }

    return res
  },
  error => {
    console.log(2, error)
    let flag = localStorage.getItem('V-overallFlag')

    if (error && error.response) {
      switch (error.response.status) {
        case 401:
          if (flag !== 'false') {
            error.message = '抱歉，您没有访问此操作的权限！'
          }
          // eslint-disable-next-line
          break;
        case 404:
          if (flag !== 'false') {
            error.message = '抱歉，您请求的资源不存在！'
          }
          // eslint-disable-next-line
          break;
        default:
          if (flag !== 'false') {
            error.message = `服务正在处理，请稍后。`
          }
      }
    } else {
      if (flag !== 'false') {
        error.message = '服务正在处理，请稍后。'
      }
    }

    localStorage.setItem('V-overallFlag', false)
    if (flag !== 'false') {
      setTimeout(() => {
        localStorage.setItem('V-overallFlag', true)
      }, 3000)
    }

    // console.log(flag)

    return Promise.reject(error)
  }
)

export default service
