var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")
var activity_goods = require("../common/activity_goods.js");

global.wxComponent({
  mixins: [base, activity_goods],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      var m = this.data.m = newVal;
      console.log(m, 'miaosha');
      let r = /(http|https):\/\/([\w.]+\/?)\S*/
      m.seckill_goods.forEach(item => {
        if (r.test(item.goods_img)) {
          item.isShowImg = true
        } else {
          item.isShowImg = false
        }
      })
      console.log(m)
      for (var i in m.seckill_goods) {
        var g = m.seckill_goods[i];
        var bfb = 1;
        var pp = parseFloat(g.seckill_sale_num) +
          parseFloat(g.seckill_num) +
          parseFloat(g.unpaidGoodsNum) +
          parseFloat(g.base_sale)
        if (!isNaN(pp) && pp != 0) {
          bfb = parseFloat((parseFloat((g.seckill_sale_num)) + parseFloat((g.base_sale))) / pp);
        }
        console.log(10999);

        console.log(bfb);
        var dd = bfb * 100;
        g.percent = parseFloat(dd.toFixed(2));
        if (g.sec_price_int == 1 && m.list_style == 0) {
          g.sec_price = parseFloat(g.sec_price).toFixed(0);
          g.goods_price = parseFloat(g.goods_price).toFixed(0);
        }
      }
      this.startActivityTimer('seckill', this._getLeftSecs(m));
    },
    _getLeftSecs (m) {
      var time_arr = {};
      for (var i in m.seckill_goods) {
        time_arr[m.seckill_goods[i].act_id] = m.seckill_goods[i].remaining_time;
      }
      return time_arr;
    },
  }
});