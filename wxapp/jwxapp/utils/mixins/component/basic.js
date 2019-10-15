var common = require("../common.js")
var pages = require("../../init/pages.js")
var nav = require("../../base/nav.js")
var helper = require("../../base/helper.js")
var settings = require("../../init/settings.js")
module.exports = {
  properties: {
    name: String, // 组件名称
    imageUrl: {
      type: String,
      value: nav.getImageUrl("")
    },
    baseUrl: {
      type: String,
      value: nav.getUrl("")
    }
  },
  options: {
    multipleSlots: true,
    addGlobalClass: false
  },
  externalClasses: ['custom-class'],
  attached(e) {
    pages.addObj(this);
    this.setData({
      localePack: this.getLocalePack()
    })
    var _this = this;
    settings.PageSettings.lifecycle.forEach(v => {
      if (helper.isFunc(_this[v[0]])) {
        _this.$on(v[0], _this[v[0]]);
      }
    });
  },
  detached() {
    this.clearTimers();
    var _this = this;
    settings.PageSettings.lifecycle.forEach(v => {
      if (helper.isFunc(_this[v[0]])) {
        _this.$off(v[0]);
      }
    });
    pages.removeObj(this);
  },
  methods: {
    ...common,
  },

}