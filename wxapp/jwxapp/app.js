var util = require('./utils/util.js');
var pageMixins = require("./pages/common/mixins.js");
var initGlobalMixin = require("./utils/mixins/initGlobalMixin.js")
var i18n = require("./utils/i18n/i18n.js")

initGlobalMixin(pageMixins, []);

const updateManager = wx.getUpdateManager();

global.wxApp({
  globalData: {
    baseUrl: util.getUrl(""),
    imageUrl: util.getImageUrl(""),
    shopId: util.getShopId()
  },
  onLaunch: function(options) {
    updateManager.onCheckForUpdate(function(res) {})
    updateManager.onUpdateReady(function() {
      wx.showModal({
        title: i18n.trans("info.update.tip"),
        content: i18n.trans("info.new.version.restart.app"),
        success: function(res) {
          if (res.confirm) {
            updateManager.applyUpdate()
          }
        }
      })
    })
    updateManager.onUpdateFailed(function() {})
  },

  onShow: function(options) {

  },

  onPageNotFound(res) {
    console.log("onPageNotFound:", res);
    var url = util.getPath(res.path, res.query);
    var url2 = util.validUrl(url);
    if (!util.equalUrl(url, url2))
      util.jumpLink(url2,"redirectTo");
  },
})