var config = require('../config.js');
var cache = require('./cache.js');
var md5 = require('./md5.js');
var nav = require('./nav.js');
var i18n = require("../i18n/i18n.js")

function _getHeader () {
  return {
    "V-ShopId": config.shop_id,
    "V-Token": _getToken()
  }
}

function api (path, cb, data, content_type, shadow) {
  if (shadow) {
    wx.showLoading({
      title: i18n.trans("common.info.loading"),
      mask: true,
    })
  }
  data = _initData(data, path);
  console.log("api request:", path, data);
  console.log(_getHeader())
  wx.request({
    url: nav.getUrl(path),
    data: data,
    method: "POST",
    header: _getHeader(),
    dataType: "json",
    success: function (res) {
      console.log(_getHeader());
      if (path == "/api/wxapp/login" && res.data.error == 0) {
        console.log(123)
        _cacheToken(res.data.content.token);
      }
      if (shadow) wx.hideLoading();
      console.log("api result " + path + ":", res.data);
      if (cb) cb(res.data);
    },
    fail: function (res) {
      console.log("api fail:" + JSON.stringify(res));
      if (shadow) wx.hideLoading();
      wx.showModal({
        title: i18n.trans("common.info.title"),
        content: i18n.trans("common.info.loadingFailed"),
      })
    }
  })
}

function uploadFile (url, tempFilePaths, data, backfun, fail, complete) {
  if (typeof data === "undefined" || data === null) data = {};
  data.shop_id = config.shop_id;
  data.user_id = cache.getCache("user_id");
  var token = _getToken()
  if (token) data.token = token;
  data = _sign(data);
  console.log(data)
  wx.uploadFile({
    url: url, //仅为示例，非真实的接口地址
    header: _getHeader(),
    filePath: tempFilePaths,
    name: 'file',
    formData: data,
    success: function (res) {
      backfun(res);
    },
    fail: function (res) { if (fail) fail(res) },
    complete: function (res) { if (complete) complete(res) }
  })
}

function uploadImage (count, backfun, data) {
  var url = nav.getUrl('/api/wxapp/image/upload');
  wx.chooseImage({
    count: count, // 默认9
    sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
    success: function (res) {
      // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
      var tempFilePaths = res.tempFilePaths
      if (res) {
        wx.getImageInfo({
          src: tempFilePaths[0],
          success: function (obj) {
            console.log(obj)
            var params = {
              userId: cache.getCache('user_id'),
              imgCatId: -1,
              needImgWidth: obj.width,
              needImgHeight: obj.height
            }
            data = Object.assign({}, params, data);
            data.img_cat_id = -1;
            wx.showLoading({ title: i18n.trans("common.info.uploading") })
            uploadFile(url, tempFilePaths[0], data, function (e) {
              wx.hideLoading();
              backfun(e);
            }, function () {
              wx.hideLoading();
              wx.showToast({ title: i18n.trans("common.info.uploadFailed"), image: '/images/fail.png', duration: 2000 })
            });
          }
        })
      }
    }
  })
}

function uploadVideo (backfun, data, sourceType) {
  wx.chooseVideo({
    sourceType: sourceType || ['album', 'camera'],
    maxDuration: 60,
    camera: 'back',
    success: function (res) {
      // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
      if (res) {
        if (res.size > parseInt(10 * 1024 * 1024)) {
          wx.showToast({ title: i18n.trans("common.video.sizeGt10M"), image: '/images/fail.png', duration: 2000 });
          return false;
        } else if (res.duration > parseInt(180)) {
          wx.showToast({ title: i18n.trans("common.video.durationGt3Minites"), image: '/images/fail.png', duration: 2000 });
          return false;
        }
        data = data || {};
        data.video_cat_id = -1;
        wx.showLoading({ title: '上传中' })
        uploadFile(nav.getUrl('/api/wxapp/manage/video/upload'), res.tempFilePath, data, function (e) {
          wx.hideLoading();
          let res_data = JSON.parse(e.data)
          res_data.video = res;
          backfun(res_data);
        }, function () {
          wx.hideLoading();
          wx.showToast({ title: i18n.trans("common.info.uploadFailed"), image: '/images/fail.png', duration: 2000 })
        });
      }
    }
  })
}

function _initData (data, path) {
  data = data || {};
  data.user_id = cache.getCache("user_id")
  data.version = config.version || undefined;
  data.shop_id = config.shop_id;
  var _token = _getToken()
  if (_token && path != "/api/wxapp/login")
    data.token = _token;
  data = _filterData(data);
  return data;
}

function _filterData (data) {
  var ret = {};
  for (var k in data) {
    if (data[k] == null || data[k] == undefined || data[k] == 'undefined')
      ret[k] = "";
    else
      ret[k] = data[k];
  }
  return ret;
}

const key = "fads28832fds$%k(%";

function _sign (data) {
  data = data || {};
  data['timestamp'] = new Date().getTime();
  var keys = [];
  for (var i in data) {
    keys.push(i);
  }
  keys.sort(function (a, b) {
    return a.toString() > b.toString() ? 1 : (a.toString() == b.toString() ? 0 : -1);
  });
  var str = "";
  for (var i in keys) {
    str = str + data[keys[i]];
  }
  data['sign'] = md5(str + key);
  return data;
}

function _cacheToken (token) {
  cache.setCache('token' + config.shop_id, token);
}

function _getToken () {
  return cache.getCache('token' + config.shop_id);
}

module.exports = {
  api,
  uploadFile,
  uploadImage,
  uploadVideo
};