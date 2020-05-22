var util = require('./utils/util.js');
var pageMixins = require("./pages/common/mixins.js");
var componentMixins = require("./components/common/basic.js");
var initGlobalMixin = require("./utils/mixins/initGlobalMixin.js")

initGlobalMixin(pageMixins, [componentMixins]);


const updateManager = wx.getUpdateManager();

global.wxApp({
  globalData: {
    input_array: {},
    tmplWidth: 720,
    baseUrl: util.getUrl(""),
    imageUrl: util.getImageUrl(""),
    shopId: util.getShopId()
  },
  onLaunch: function (options) {
    updateManager.onCheckForUpdate(function (res) { })
    updateManager.onUpdateReady(function () {
      wx.showModal({
        title: '更新提示',
        content: '新版本已经准备好，是否重启应用？',
        success: function (res) {
          if (res.confirm) {
            updateManager.applyUpdate()
          }
        }
      })
    })
    updateManager.onUpdateFailed(function () { })
    util.setCache('launchScene', JSON.stringify(options));
  },

  onShow: function (options) {
    if (util.getCache('user_id')) {
      if (util.getCache('geographic_location') == 1) {
        util.getUserLocation(function (location) {
          util.api('/api/wxapp/access/record', false, {
            lng: location.longitude, lat: location.latitude, record_type: 0,
            mp_scene: JSON.stringify(options)
          })
        })
      } else {
        util.api('/api/wxapp/access/record', false, {
          lng: null, lat: null, record_type: 0,
          mp_scene: JSON.stringify(options)
        })
      }
    }
    util.setCache('is_login', 1);
    util.removeCache('isPageJumping')
  },
  getDistance: function (lat1, lng1, lat2, lng2) {
    return util.getDistance(lat1, lng1, lat2, lng2);
  },
  onPageNotFound(res) {
    console.log("onPageNotFound:", res);
    var url = util.getPath(res.path, res.query);
    var url2 = util.validUrl(url);
    if (!util.equalUrl(url, url2))
      util.jumpLink(url2, "redirectTo");
  },
})