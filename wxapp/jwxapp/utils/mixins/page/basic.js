var common = require("../common.js")
var pages = require("../../init/pages.js")
var settings = require("../../init/settings.js")

function page_event() {
  var e = {};
  settings.PageSettings.lifecycle.forEach(v => {
    var m = v[0];
    (function (m) {
      e[m] = function () {
        if (m == 'onLoad') {
          pages.addObj(this);
          this.setData({
            locale: this.getLocale(),
            localePack: this.getLocalePack()
          })
        } else if (m == 'onUnload') {
          this.clearTimers();
          pages.removeObj(this);
        } else if (m == 'onShow') {
          if (this.data.locale != this.getLocale()){
            this.setData({
              locale: this.getLocale(),
              localePack: this.getLocalePack()
            })
          }
        }
        this.$emit(m, ...arguments)
      };
    }(m));
  });
  return e;
}

module.exports = {
  ...page_event(),
  ...common
}