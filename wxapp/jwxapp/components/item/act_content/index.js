var util = require("../../../utils/util.js");
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {},

  /**
   * 组件的初始数据
   */
  data: {
    total_micro_second: 1579824000,
    time: null
  },
  lifetimes: {
    ready() {
      if ("限时降价TODO" == "限时降价") return;
      this.countdown(this.data.total_micro_second);
    },
    detached() {
      clearTimeout(this.data.time);
    }
  },
  /**
   * 组件的方法列表
   */
  methods: {
    countdown(total_micro_second) {
      let clock =
        total_micro_second <= 0
          ? "已经截至"
          : util.dateformat(total_micro_second);
      this.setData({
        clock: clock
      });
      this.triggerEvent("actStatus", total_micro_second);
      if (total_micro_second <= 0) return;
      this.setData({
        time: setTimeout(() => {
          // 放在最后--
          total_micro_second -= 1;
          this.countdown(total_micro_second);
        }, 1000)
      });
    }
  }
});
