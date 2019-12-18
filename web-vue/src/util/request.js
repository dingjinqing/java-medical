import axios from 'axios'
import Cookies from 'js-cookie'
import vm from '../main'
// import qs from 'qs'
import router from '@/router/index.js'

// 环境的切换
let baseURL = ''
if (process.env.NODE_ENV === 'development') {
  baseURL = '/vpb'
} else if (process.env.NODE_ENV === 'testing') {
} else if (process.env.NODE_ENV === 'production') {
}

// 创建axios实例
const service = axios.create({
  baseURL: baseURL, // api的base_url
  timeout: 50000 // 请求超时时间
})
// request拦截器
service.interceptors.request.use(
  config => {
    // console.log(config.url.split('/')[2])

    config.headers['Content-Type'] = localStorage.getItem('contentType')
    if(!config.headers['Content-Type']) {
      config.headers['Content-Type'] = 'application/json;charset=UTF-8'
    }
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
    vm.$message.error({ message: '请求超时!' })
    // console.log('err：' + error) // for debug
    Promise.reject(error)
  }
)

// respone拦截器
service.interceptors.response.use(
  response => {
    // console.log(response)
    const res = response
    // let flag = localStorage.getItem('V-overallFlag')
    // console.log(flag, res)
    console.log(vm)
    console.log(res)
    localStorage.setItem('V-content-disposition', res.headers['content-disposition'])
    if (res) {
      switch (res.status) {
        // 成功
        case 200:
          if (res.data.error === 100004) {
            // console.log(res)
            vm.$message.error({
              message: res.data.message,
              showClose: true
            })
            localStorage.removeItem('V-Username')
            router.push('/index/login')
          } else if (res.data.error === 100033) {
            vm.$message.error({
              message: res.data.message,
              showClose: true
            })
            router.push('/system/login')
          }
          if (res.data.error === 100001) {
            console.log('test message')
            vm.$message.error({
              message: res.data.message,
              showClose: true
            })
          }
          return res.data
      }
    }
    return res
  },
  error => {
    if (error && error.response) {
      console.log(error.response.status)
      switch (error.response.status) {
        case 401:
          error.message = vm.$t('messageHint.noAuthority')
          break
        case 404:
          error.message = vm.$t('messageHint.resourceNotExist')
          break
        case 400:
          error.message = vm.$t('messageHint.parameterError')
          // console.log(window.vm.$t('case.title'))
          break
        default:
          error.message = vm.$t('messageHint.beingProcessed')
      }
    } else {
      error.message = vm.$t('messageHint.beingProcessed')
    }
    vm.$message.error({
      message: error.message,
      showClose: true
    })
    return Promise.reject(error)
  }
)

export default service
