var util = require('./utils/util.js');
var pageMixins = require("./pages/common/mixins.js");
var initGlobalMixin = require("./utils/mixins/initGlobalMixin.js")
var i18n = require("./utils/i18n/i18n.js")
var locale = require("./utils/base/locale.js")

initGlobalMixin(pageMixins, []);

const updateManager = wx.getUpdateManager();

global.wxApp({
  globalData: {
    input_array: {},
    baseUrl: util.getUrl(""),
    imageUrl: util.getImageUrl(""),
    shopId: util.getShopId()
  },
  onLaunch: function (options) {
    locale.loadLocalePack();
    updateManager.onCheckForUpdate(function (res) { })
    updateManager.onUpdateReady(function () {
      wx.showModal({
        title: i18n.trans("common.info.updateTip"),
        content: i18n.trans("common.info.newVersionReadyWhetherRestartApp"),
        success: function (res) {
          if (res.confirm) {
            updateManager.applyUpdate()
          }
        }
      })
    })
    updateManager.onUpdateFailed(function () { })
  },

  onShow: function (options) {
    util.setCache('is_login', 1);
  },

  onPageNotFound(res) {
    console.log("onPageNotFound:", res);
    var url = util.getPath(res.path, res.query);
    var url2 = util.validUrl(url);
    if (!util.equalUrl(url, url2))
      util.jumpLink(url2, "redirectTo");
  },
})