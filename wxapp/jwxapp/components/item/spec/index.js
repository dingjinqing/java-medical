global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    productsInfo: {
      type: Object,
      value: null,
      observer(val) {}
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    showSpec: false
  },

  /**
   * 组件的方法列表
   */
  lifetimes: {
    ready() {
      console.log(this);
    }
  },
  methods: {
    specDialogShow() {
      this.setData({
        showSpec: true
      });
    }
  }
});
