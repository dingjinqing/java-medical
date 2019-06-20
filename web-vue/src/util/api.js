// import axios from 'axios'
import env from '@/config/env'
let $ = window.jQuery
let layer = window.layer

function api (path, cb, params, failcb, showLoading) {
  var url = 'http://' + env.apiDomain + path
  params = params || {}
  var layerIndex = null
  if (showLoading) {
    layerIndex = layer.load(1)
  }
  $.ajax({
    type: 'post',
    url: url,
    data: params,
    dataType: 'json',
    success: function (data) {
      if (layerIndex) {
        layer.close(layerIndex)
      }
      try {
        if (data.error === -9999) {
          layer.msg('你无权访问，请检查登录是否过期或者权限受限')
          return false
        }
        cb(data)
      } catch (e) {
        if (e && e.message) {
          layer.msg(e.message)
        } else {
          layer.msg('访问失败！')
        }

        if (failcb) {
          failcb()
        }
      }
    },
    error: function (XmlHttpRequest, textStatus, errorThrown) {
      if (layerIndex) {
        layer.close(layerIndex)
      }
      layer.msg('网络错误！')
    }
  })
}

export default api
