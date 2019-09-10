var util = require("../../../../utils/util.js");
var spec = {
  methods: {
    bindSpecChange(e) {
      this._sel_buy = e.detail;
    },
    bindClickSpecCell(e) {
      this.showSpec();
    },
    bindCloseSpec(e) {
      this.setData({
        show_spec: this.data.show_spec = false
      });
    },
    showSpec(no_spec_show) {
      if (no_spec_show || this._sel_buy.has_spec) {
        this.setData({
          show_spec: true,
        });
      }
    },
    bindAddCart(e) {
      var that = this;
      util.api('/api/wxapp/popup/goods/data', function (res) {
        console.log(res)
        if (res.error == 0) {
          that.setData({ goodsData: res.content })
          console.log(that.showSpec(1))
        } else {
          util.showModal('提示', res.message);
        }
      }, { goods_id: e.currentTarget.dataset.goods_id },null,true)
    }
  }
};
module.exports = spec;