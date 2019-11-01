var common = require("../common.js")
var pages = require("../../init/pages.js")
var nav = require("../../base/nav.js")
var helper = require("../../base/helper.js")
var settings = require("../../init/settings.js")
var cache = require("../../base/cache.js")
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
    getColors(){
      let helpColor = cache.getCache("main_colors");
      let mainColor = cache.getCache("help_colors");
      var colors = {
        commonColor: helpColor, //辅色,
        comColor: mainColor, // 主色
        somColor: helper.colorRgb(mainColor, 0.2), //标签背景渐变色,
        linColor: helper.colorRgb(mainColor, 0.8), //活动，下载海报按钮背景渐变色
        comColor1: helper.colorRgb(mainColor, 1), //主色rgba
        borColor: helper.colorRgb(mainColor, 0.4), //item优惠券边框颜色
        speColor: helper.colorRgb(mainColor, 0.1), //预售背景色,
      }
      return colors;
    }
  },

}