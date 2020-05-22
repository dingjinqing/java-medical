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
      console.log(m, 'kanjia0000000000000000000000000000')
      let r = /(http|https):\/\/([\w.]+\/?)\S*/
      m.bargain_goods.forEach(item => {
        if (r.test(item.goods_img)) {
          item.isShowImg = true
        } else {
          item.isShowImg = false
        }
      })
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
      util.getNeedTemplateId('invite', ()=>{
        this.bindToBargain(e)
      })
      // var _this = this;
      // if (this.data.m.bottom.subscribe_message) {
      //   console.log('有订阅消息')
      //   var d = _this.eventData(e);
      //   util.toSubscribeMessage(d.template_ids, 'invite', function () {
      //     console.log(111);
      //     _this.bindToBargain(e);
      //   })
      // } else {
      //   console.log('没有订阅消息')
      //   this.bindToBargain(e)
      // }
    },
    bindToBargain (e) {
      var _this = this
      var d = _this.eventData(e);
      console.log(d)
      if (d.is_delete == 1) {
        util.showModal(this.$t("components.decorate.tips"), this.$t("components.decorate.goodsDeleted"));
      } else if (d.is_on_sale == 0 || d.goods_number <= 0) {
        util.showModal(this.$t("components.decorate.tips"), this.$t("components.decorate.offShelf"));
      } else if (d.act_status == 0 || d.time_state == 2 || d.act_del_flag == 1) {
        this.navigateToItem(d.goods_id);
      } else {
        console.log('触发', d)
        if (d.is_prd == 0) {
          console.log(d.link)
          util.jumpLink(d.link);
        } else {
          util.api("/api/wxapp/bargain/apply", function (res) {
            console.log(res)
            if (res.error == 0) {
              var data = res.content;
              if (data.resultCode == 0) {
                var url = "/pages/bargaininfo/bargaininfo?record_id=" + data.recordId;
                util.jumpLink(url);
              } else if (data.resultCode == 1) {
                util.showModal(_this.$t("components.decorate.tips"), _this.$t("components.decorate.doesNotExist"));
              } else if (data.resultCode == 2) {
                util.showModal(_this.$t("components.decorate.tips"), _this.$t("components.decorate.activityDeactivated"));
              } else if (data.resultCode == 3) {
                util.showModal(_this.$t("components.decorate.tips"), _this.$t("components.decorate.notStart"));
              } else if (data.resultCode == 4) {
                util.showModal(_this.$t("components.decorate.tips"), _this.$t("components.decorate.activityEnded"));
              } else if (data.resultCode == 5) {
                // util.showModal(_this.$t("components.decorate.tips"), _this.$t("components.decorate.insufficientInventory"));
                util.jumpLink(d.link);
              } else if (data.resultCode == -1) {
                util.showModal(_this.$t("components.decorate.tips"), _this.$t("components.decorate.operationFailed"));
              }
            } else {
              _this.navigateToItem(d.goods_id);
            }
          }, {
            bargainId: d.bargain_id,
            prdId: d.prd_id,
            goodsId: d.goods_id
          })
        }
      }
    }
  }
});