var common = require("../common.js")
var pages = require("../../init/pages.js")
var nav = require("../../base/nav.js")
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
  },
  detached() {
    this.clearTimers();
    pages.removeObj(this);
  },
  methods: {
    $emit() {
      this.triggerEvent.apply(this, arguments);
    },
    ...common
  },

}