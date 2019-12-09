var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      console.log(newVal, 'coupon-------------')
      for (var j in newVal.coupon_arr) {
        newVal.coupon_arr[j].denomination = parseFloat(newVal.coupon_arr[j].denomination);
      }
      newVal.coupon_arr.forEach((item, index) => {
        Object.assign(newVal.coupon_arr[index], newVal[index])
      })
      console.log(newVal.coupon_arr)
      newVal.isCoupon = 0;
      console.log(this.data.comColor, this.data.borColor)


      // validation_code、is_exclusive、alias_code、use_score
    },

    bindFetchCoupon(e) {
      var d = this._form_data;
      var _this = this;
      var m = this.data.m;
      if (this._input_vali != d.vali) {
        util.toast_fail('领取码错误');
      } else {
        util.api("/api/wxapp/coupon/get", function (res) {
          if (res.error == 0) {
            if (res.content == 0) {
              util.showModal('提示', '您已领取的个数已达到可领取限制');
              m.isCoupon = 0;
              m['coupon_arr'][d.coupon_key].status = -1;
              _this.$set();
            } else {
              util.toast_success('领取成功', function () {
                m.isCoupon = 0;
                m['coupon_arr'][d.coupon_key].status = -1;
                _this.$set();
              });
            }
          } else {
            util.toast_fail('领取失败');
          }
        }, {
          code: d.code,
          form_id: d.form_id,
          open_id: d.open_id
        })
      }
    },
    bindGetCoupon(e) {
      var d = this.eventData(e);
      console.log(d)
      this._form_data = {
        code: d.code,
        form_id: e.detail.formId,
        open_id: util.getCache("openid"),
        coupon_key: d.key,
        vali: d.vali
      };

      var _this = this;
      var m = this.data.m;

      m['coupon_arr'][d.key].disableds = true;
      this.$set();
      console.log(d.exclusive)
      if ((d.vali != '' && d.vali != undefined) || d.use_score == 1 ||
        (d.exclusive != '' && d.exclusive == 1)) {
        util.jumpLink('/pages/getcoupon/getcoupon?code=' + d.code);
      } else {
        wx.showLoading({
          title: '领取中···',
        })
        if (m['coupon_arr'][d.key].disableds == true) {
          // util.api('/api/wxapp/coupon/get?code=' + d.code, function (res) {
          //   wx.hideLoading();
          //   if (res.error == 0) {
          //     if (res.content == 0) {
          //       util.showModal('提示', '您已领取的个数已达到可领取限制');
          //       m.isCoupon = 0;
          //       m['coupon_arr'][d.key].status = -1;
          //       m['coupon_arr'][d.key].disableds = false;
          //       _this.$set();
          //     } else {
          //       util.toast_success('领取成功', function () {
          //         m['coupon_arr'][d.key].status = -1;
          //         m['coupon_arr'][d.key].disableds = false;
          //         _this.$set();
          //       });
          //     }
          //   } else {
          //     util.toast_fail(res.message.msg);
          //   }
          // }, {
          //   form_id: _this._form_data.form_id,
          //   open_id: _this._form_data.open_id
          // });
        }
      }
      console.log(e, d)

    },
    bindCodeBlur(e) {
      this._input_code = e.detail.value;
    },
    bindClose(e) {
      this.data.m.isCoupon = 0;
      this.$set();
    },
  }
});