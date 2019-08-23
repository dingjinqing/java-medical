import { config } from "./config.js"
import { cache } from "./cache.js"
import { nav } from "./nav.js"
import { cacheLang, trans } from '../i18n/i18n.js'

var version;

function _initData(data, path) {
  data = data || {};
  data.shop_id = config.shop_id;
  var _token = _getToken()
  if (_token && path != "/api/wxapp/login")
    data.token = _token;
  return data;
}


function _cacheToken(token) {
  cache.setCache('token' + config.shop_id, token);
}

function _getToken() {
  return cache.getCache('token' + config.shop_id);
}

function _getHeader() {
  return {
    "V-ShopId": config.shop_id,
    "V-Token": _getToken()
  }
}

export function request(path, data, shadow) {
  if (shadow) {
    wx.showLoading({
      title: trans("info.loading"),
      mask: true,
    })
  }
  data = _initData(data, path);
  console.log("api request:", path, data);

  return new Promise((resolve, reject) => {
    wx.request({
      url: nav.getUrl(path),
      data: data,
      method: "POST",
      header: _getHeader(),
      dataType: "json",
      success: function (res) {
        console.log("api result " + path + ":", res.data);
        if (res.data.error != 0){
          wx.showModal({
            title: trans("info.title"),
            content: res.data.message
          })
        }else{
          if (path == "/api/wxapp/login") {
            _cacheToken(res.data.content.token);
          }
          if (res.data.language) {
            cacheLang(res.data.language);
          }
          if (shadow) wx.hideLoading();
          resolve(res.data);
        }
        
      },
      fail: function (res) {
        console.log("api fail:" + JSON.stringify(res));
        wx.showModal({
          title: trans("info.title"),
          content: trans("info.loading.failed"),
        })
        reject(res);
      }
    })
  });
}


export function uploadFile(url, tempFilePaths, data) {
  data = data || {};
  return new Promise((resolve, reject) => {
    data.shop_id = config.shop_id;
    wx.uploadFile({
      url: url, 
      header: _getHeader(),
      filePath: tempFilePaths,
      name: 'file',
      formData: data,
      success: function (res) {
        resolve(res);
      },
      fail: function (res) {
        reject(res);
      }
    })
  });
}

export function uploadImage(path,count, backfun, data) {
  return new Promise((resolve, reject) => {
    var url = nav.getUrl(path);
    wx.chooseImage({
      count: count, 
      sizeType: ['compressed'], 
      sourceType: ['album', 'camera'], 
      success: function (res) {
        var tempFilePaths = res.tempFilePaths
        if (res) {
          uploadFile(url, tempFilePaths[0], data).then(function (res) {
            resolve(res);
          }).catch(function (res) {
            reject(res);
          });
        } else {
          reject(res);
        }
      }
    })
  });
}


