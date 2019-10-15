var api = require("./api.js")
var i18n = require("../i18n/i18n.js")

var loadPacking = false

/**
 * 加载语言包
 */
function loadLocalePack() {
  if (!loadPacking) {
    loadPacking = true;
    api.api("/api/wxapp/locale/get", function (res) {
      if (res.content) {
        try {
          i18n.setLocale(res.language)
          if (res.language != i18n.defaultLocale()) {
            var pack = JSON.parse(res.content);
            i18n.setLocalePack(res.language, pack)
          }
        } catch (e) {
        }
        loadPacking = false;
      }
    });
  }
}

module.exports = {
  loadLocalePack: loadLocalePack
}
