var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")
var activity_goods = require("../common/activity_goods.js");
var spec_mixin = require("../../item/popup/spec/spec.js")

global.wxComponent({
  mixins: [base, activity_goods, spec_mixin],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      for (var i in newVal.goods_items) {
        var item = newVal.goods_items[i];
        var g = (item.goods_price).toString();
        var g_len = 0;
        var m_len = 0;
        if (g.indexOf('.') != -1) {
          var gs = g.split('.');
          g_len = gs[0].length + gs[1].length;
        } else {
          g_len = g.length;
        }
        if (item.market_price != undefined) {
          var m = (item.market_price).toString();
          if (m.indexOf('.') != -1) {
            var ms = m.split('.');
            m_len = ms[0].length + ms[1].length;
          } else {
            m_len = m.length;
          }
        }
        var len = g_len + m_len;
        item.len = len;
        item.max_price = parseFloat(item.max_price);
        item.reduce_price = parseFloat(item.reduce_price);
      }
      newVal.show_style = newVal.col_type;
    },
    bindMenuClick(e) {
      var d = this.eventData(e);
      var _this = this;
      var m = this.data.m;
      util.jumpLink('/pages/searchs/search?cur_idx_goods=' + m.idx);
    },


  }
});