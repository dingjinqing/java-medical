var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")
var activity_goods = require("../common/activity_goods.js");

global.wxComponent({
  mixins: [base, activity_goods],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      var m = this.data.m = newVal;
      this.formatActivityBeginTime(m.bargain_goods);

      this.startActivityTimer('bargain', this._getLeftSecs(m));
      newVal.bottom = util.getCache("bottom");
      console.log(m, 'kanjia')
    },
    _getLeftSecs (m) {
      console.log(m)
      var time_arr = {};
      for (var i in m.bargain_goods) {
        m.bargain_goods[i].remaining_time -= m.elapse_secs;
        console.log(m.bargain_goods[i].remaining_time)
        time_arr[m.bargain_goods[i].act_id] = m.bargain_goods[i].remaining_time;
      }
      return time_arr;
    },
    handleToBargain (e) {
      var _this = this;
      if (this.data.m.bottom.subscribe_message) {
        console.log('有订阅消息')
        var d = _this.eventData(e);
        util.toSubscribeMessage(d.template_ids, 'invite', function () {
          console.log(111);
          _this.bindToBargain(e);
        })
      } else {
        console.log('没有订阅消息')
        this.bindToBargain(e)
      }
    },
    bindToBargain (e) {
      var _this = this
      var d = _this.eventData(e);
      console.log(d)
      if (d.is_delete == 1) {
        util.showModal('提示', '商品已删除');
      } else if (d.is_on_sale == 0 || d.goods_number <= 0) {
        util.showModal('提示', '商品已下架');
      } else if (d.act_status == 0 || d.time_state == 2 || d.actDelFlag == 1) {
        this.navigateToItem(d.goods_id);
      } else {
        if (d.is_prd == 1) {
          console.log(d.link)
          util.jumpLink(d.link);
        } else if (d.is_prd == 0) {
          var choose_list = util.values(d, ['bargain_id', 'goods_id', 'goods_price', 'prd_id']);
          choose_list.user_id = util.getCache('user_id');
          util.api("/api/wxapp/bargain/apply", function (res) {
            console.log(res)
            if (res.error == 0) {
              var data = res.content;
              var url = "/pages/bargaininfo/bargaininfo?record_id=" + data.record_id + "&bargain_money=" + data.bargain_money;
              util.jumpLink(url);
            } else {
              _this.navigateToItem(d.goods_id);
            }
          }, {
            choose_list: JSON.stringify(choose_list)
          })
        }
      }
    }
  }
});