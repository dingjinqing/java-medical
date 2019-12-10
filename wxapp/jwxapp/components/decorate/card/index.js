var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {

      // 根据背景类型来判断是采用背景颜色还是背景图片
      console.log(newVal, 'cardData')
      // 暂时改变字段调试
      newVal.status = -1
      // 处理背景
      if (newVal.bg_type == 0) {
        newVal.bg = newVal.bg_color = newVal.bg_color || '#e6cb96';
      }
      if (newVal.bg_type == 1) {
        newVal.bg = 'url(' + newVal.imageUrl + '/' + newVal.bg_img + ')';
      }
      console.log(newVal)
      // shop_img、activation字段在第二个接口
    },
    bindGetCard(e) {
      var d = this.eventData(e);
      var _this = this;
      console.log(d)
      // if (d.is_pay == 1 || d.is_pay == 2) {
      //   util.jumpLink('/pages/cardinfo/cardinfo?card_id=' + d.card_id, )
      //   return false;
      // }
      // util.api('/api/card/getcard', function(res) {
      //   if (res.error == 0) {
      //     if (res.content == -6) {
      //       util.toast_fail('当前等级已最高');
      //       return;
      //     } else if (res.content.grade_card) {
      //       var text = '没有达到该卡的条件';
      //       if (res.content.grade_card.grade_score > 0) {
      //         text = '您的积分没有达到' + res.content.grade_card.grade_score + '积分';
      //       } else {
      //         text = '您的消费金额没有达到' + res.content.grade_card.grade_money + '元';
      //       }
      //       util.showModal('', text);
      //       return;
      //     } else {
      //       if(d.card_type != '1'){
      //         _this.data.m.status = 1;
      //         _this.$set();
      //         _this.$emit('get_card_success', {
      //           m: _this.data.m
      //         });
      //       } else if (res.content.is_continue == 'not_continue'){
      //         _this.data.m.status = 1;
      //         _this.$set();
      //         _this.$emit('get_card_success', {
      //           m: _this.data.m
      //         });
      //       }
      // var card_no = res.content.card_no || res.content

      // if (d.activation == 1) {
      //   util.jumpLink('/pages/cardinfo/cardinfo?card_no=' + card_no);
      // } else {
      //   util.showModal('提示', '领取成功，可在个人中心查看', function () {
      //     console.log('触发')
      //     util.jumpLink('/pages/cardinfo/cardinfo?card_no=' + card_no, 'navigateTo')
      //   }, true, '取消', '立即查看')
      // }
      //     }
      //   } else {
      //     util.toast_fail('领取失败');
      //   }
      // }, {
      //   card_info: JSON.stringify({
      //     card_id: d.card_id
      //   })
      // })
    },
    viewCard(e) {
      var d = this.eventData(e);
      var _this = this;
      util.jumpLink('/pages/cardinfo/cardinfo?card_id=' + d.card_id)
    }
  }
});