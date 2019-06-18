import axios from 'axios'
import env from '@/config/env'

// create an axios instance
const service = axios.create({
  baseURL: 'http://' + env.apiDomain, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

function api (path, cb, params, failcb) {
  params = params || {}
  // service.interceptors.response.use(function (req, res, next) {
  //   res.header('Access-Control-Allow-Origin', '*')
  //   res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE')
  //   res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
  //   next()
  // })
  service.post(path, params).then(function (res) {
    if (res.data.error === -9999) {
      window.layer.msg('你无权访问，请检查登录是否过期或者权限受限')
      return
    }
    if (cb) {
      cb(res.data)
    }
  }).catch(function (e) {
    window.layer.msg('网络请求出错，错误信息：' + e.message)
    if (failcb) {
      failcb(e.message)
    }
  })
}

export default api
