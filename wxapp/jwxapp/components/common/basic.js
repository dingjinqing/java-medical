var util = require("../../utils/util.js");
var common_basic = require("../../common/mixins/basic.js");

var basic = {
  options: {
    multipleSlots: true,
    addGlobalClass: false
  },
  externalClasses: ['custom-class'],

  properties: {
    name: String, // 组件名称
    imageUrl: {
      type: String,
      value: util.getImageUrl("")
    },
    baseUrl: {
      type: String,
      value: util.getUrl("")
    }
  },
  lifetimes: {
    attached() {
      this.onAttach();
    },
    detached() {
      this.onDetatch();
    },
  },
  attached() {
    this.onAttach();
  },
  detached() {
    this.onDetatch();
  },
  methods: {
    $emit() {
      this.triggerEvent.apply(this, arguments);
    },
    onAttach() {
      var page = util.getCurrentPage();
      if (page && page.addComponent) page.addComponent(this);
      if (page && page._getColors) this.setData(page._getColors());
      this.$emit("attached", this);
    },
    onDetatch() {
      var page = util.getCurrentPage();
      if (page && page.removeComponent)
        page.removeComponent(this);
      this.$emit("detached", this);
      this.clearTimers(); // 清除计时器
    },
    ...common_basic,
  }
};

module.exports = basic;