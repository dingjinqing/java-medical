var util = require("../../../utils/util.js")

var activity_goods = {
  methods: {
    /**
     * 活动商品通用倒计时
     * 
     * @param {string} name 计时器名称
     * @param {object} left_secs 返回剩余秒数对象
     */
    startActivityTimer (name, left_secs, format_time_cb = null) {
      console.log(name, left_secs)
      this._left_secs = this._left_secs || {};
      this._left_secs[name] = left_secs;
      var _this = this;
      var m = this.data.m;
      m.times_arr = m.times_arr || {};

      function timerProc () {
        var m = _this.data.m;
        var remain = _this._left_secs[name];
        var count = 0,
          total = 0;
        for (var i in remain) {
          var secs = parseInt(remain[i]);
          secs = (isNaN(secs) || secs <= 0) ? 0 : secs;
          m.times_arr[i] = secs <= 0 ? 0 : (format_time_cb ? format_time_cb(secs) : util.dateformat2(secs));
          remain[i]--;
          if (secs <= 0) count--;
          total++;
        }
        if (count == total) {
          _this.killTimer('interval', name);
        }
        _this.setData({
          "m.times_arr": m.times_arr
        })
      }

      timerProc();
      this.createTimer("interval", name, timerProc, 1000);

    },
    /**
     * 活动商品开始时间通用格式化
     * @param {object} goods
     */
    formatActivityBeginTime (goods) {
      if (!goods) return;
      for (var i in goods) {
        var g = goods[i];
        var time = g.act_begin_time || g.start_time;
        console.log(time)
        g.format_begin_time = time.substr(5, 2) + "月" + time.substr(8, 2) + "日" + time.substr(11);
        console.log(g)
      }
    },
    /**
     * 跳转到原商品
     */
    navigateToItem (goods_id) {
      util.api("/api/wxapp/goods", function (data) {
        if (data.error === 0) {
          util.jumpLink('/pages/item/item?goods_id=' + goods_id)
        } else {
          util.showModal('提示', data.message);
          return false;
        }
      }, {
        goods_id: goods_id
      })
    },
    bindToKanJia (e) {
      var d = this.eventData(e);
      var choose_info = (d.group == 1) ? this.data.m.first_group_goods[d.zhujian] : this.data.m.goods_items[d.zhujian];
      var choose_list = {};
      if (d.is_prd == 0) {
        choose_list.user_id = util.getCache('user_id');
        choose_list.bargain_id = choose_info.bargin_id;
        choose_list.goods_id = choose_info.goods_id;
        choose_list.goods_price = choose_info.goods_price;
        choose_list.prd_id = choose_info.prd_id;
      }

      if (d.is_prd == 1) {
        util.jumpLink(d.link);
      } else if (d.is_prd == 0) {
        util.api("/api/wxapp/bargain/apply", function (res) {
          if (res.error == 0) {
            var url = "/pages/bargaininfo/bargaininfo?record_id=" + res.content.record_id + "&bargain_money=" + res.content.bargain_money;
            util.jumpLink(url);
          } else {
            util.showModal('提示', res.content);
          }
        }, {
          choose_list: JSON.stringify(choose_list)
        })
      }
    },
    bindToSecKill: function (e) {
      var d = this.eventData(e);
      if ((d.act_del_flag == 0 && d.time_state == 1 && d.act_status == 1) && d.is_on_sale == 0 || d.is_delete == 1) {
        if (d.is_on_sale == 0 || d.goods_number <= 0) {
          util.showModal('提示', '商品已下架');
        } else if (d.is_delete == 1) {
          util.showModal('提示', '商品已删除');
        }
        return false;
      }

      if (d.act_status == 0 && d.act_del_flag == 0) {
        this.navigateToItem(d.goods_id);
        return false;
      }

      if (d.time_state == 2 || d.act_del_flag == 1) {
        if (d.act_del_flag == 1) {
          this.navigateToItem(d.goods_id);
        } else if (d.time_state == 2) {
          this.navigateToItem(d.goods_id);
        }
      } else {
        util.jumpLink("/pages/seckillitem/seckillitem?sk_id=" + d.sk_id)
      }
    },
    bindTopre: function (e) {
      var d = this.eventData(e);
      util.jumpLink('pages/presaleitem/presaleitem?presale_id=' + d.presale_id, 'navigateTo');
    },
  }
};
module.exports = activity_goods;