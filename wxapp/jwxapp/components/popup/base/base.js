var base = {
  properties: {
    show: Boolean,
    click_back_close: Boolean,
  },

  /**
   * 组件的方法列表
   */
  methods: {
    bindClose(e) {
      debugger
      this.setData({
        show: false
      });
      // this.$emit("close");
      this.triggerEvent("close")
    },
    bindNullClick(e) { },
    bindBackTap(e) {
      debugger
      if (this.data.click_back_close) {
        this.setData({
          show: false
        });
        this.$emit("close");
      }
      this.$emit("back_tap");
    }
  }
};
module.exports = base;