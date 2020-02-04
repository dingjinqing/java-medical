var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {

      // 根据背景类型来判断是采用背景颜色还是背景图片
      console.log(newVal, 'cardData11111111111111111111111')
      // 处理背景
      if (newVal.bg_type == 0) {
        newVal.bg = newVal.bg_color = newVal.bg_color || '#e6cb96';
      }
      if (newVal.bg_type == 1) {
        let flag = newVal.bgImg
        if (!flag) return
        newVal.bg = 'url(' + newVal.bgImg + ')';
      }
      console.log(newVal)
      // shop_img、activation字段在第二个接口
    },
    bindGetCard (e) {
      var d = this.eventData(e);
      var _this = this;
      console.log(d)
      if (d.status == 4) {
        util.toast_fail('已达到领取上限');
        return
      } else if (d.status == 5) {
        util.toast_fail('无库存');
        return
      }
      if (d.is_pay == 1 || d.is_pay == 2) {
        util.jumpLink('/pages/cardinfo/cardinfo?cardId=' + d.card_id)
        return false;
      }
      util.api('/api/card/getCard', function (res) {
        console.log(res)
        if (res.error == 0) {
          if (res.content.isMostGrade) {
            util.toast_fail(this.$t("components.decorate.highestGrade"));
            return;
          } else if (res.content.gradeCard) {
            var text = this.$t("components.decorate.conditionsNotMet");
            if (res.content.gradeCard.gradeScore > 0) {
              text = this.$t("components.decorate.PointsNotAchieved") + res.content.gradeCard.gradeScore + this.$t("components.decorate.integral");
            } else {
              text = this.$t("components.decorate.amountNotReached") + res.content.gradeCard.gradeMoney + this.$t("components.decorate.element");
            }
            util.showModal('', text);
            return;
          } else {
            if (d.card_type != '1') {
              _this.data.m.status = 1;
              _this.$set();
              _this.$emit('get_card_success', {
                m: _this.data.m
              });
            } else if (res.content.is_continue == 'not_continue') {
              _this.data.m.status = 1;
              _this.$set();
              _this.$emit('get_card_success', {
                m: _this.data.m
              });
            }
            var cardNo = res.content.cardNo
            console.log(cardNo)
            if (d.activation == 1) {
              util.jumpLink('/pages/cardinfo/cardinfo?cardNo=' + cardNo);
            } else {
              util.showModal(this.$t("components.decorate.tips"), this.$t("components.decorate.successfulReception"), function () {
                console.log('触发')
                util.jumpLink('/pages/cardinfo/cardinfo?cardNo=' + cardNo, 'navigateTo')
              }, true, this.$t("components.decorate.cancel"), this.$t("components.decorate.checkNow"))
            }
          }
        } else {
          util.toast_fail(this.$t("components.decorate.failToRreceive"));
        }
      }, {
        cardId: d.card_id
      })
    },
    viewCard (e) {
      var d = this.eventData(e);
      console.log(d)
      var _this = this;
      util.jumpLink('/pages/cardinfo/cardinfo?cardId=' + d.card_id)
    }
  }
});