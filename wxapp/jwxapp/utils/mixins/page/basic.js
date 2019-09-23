var common = require("../common.js")
var pages = require("../../init/pages.js")
var settings = require("../../init/settings.js")

function page_event() {
  var e = {};
  settings.PageSettings.lifecycle.forEach(v => {
    var m = v[0];
    (function (m) {
      e[m] = function () {
        this.$emit(m, ...arguments)
      };
    }(m));
  });
  return e;
}

module.exports = {
  onLoad(e) {
    pages.addObj(this);
  },
  onUnload() {
    this.clearTimers();
    pages.removeObj(this);
  },
  ...page_event(),
  ...common
}