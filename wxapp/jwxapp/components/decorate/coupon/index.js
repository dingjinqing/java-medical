var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      for (var j in newVal.coupon_arr) {
        newVal.coupon_arr[j].denomination = parseFloat(newVal.coupon_arr[j].denomination);
        Object.assign(newVal.coupon_arr[j], newVal[j])
      }
      newVal.isCoupon = 0;
      console.log(newVal, '+++++++++++++++++++')

    },

    bindFetchCoupon (e) {
      var d = this._form_data;
      var _this = this;
      var m = this.data.m;
      if (this._input_vali != d.vali) {
        util.toast_fail(this.$t("components.decorate.wrongReceivingCode"));
      } else {
        util.api("/api/wxapp/coupon/get", function (res) {

          if (res.error == 0) {
            if (res.content == 0) {
              util.toast_success(this.$t("components.decorate.successfulReception"), function () {
                m.isCoupon = 0;
                m['coupon_arr'][d.coupon_key].status = -1;
                _this.$set();
              });
            } else {
              if (res.content == 1) {
                util.toast_fail(this.$t("components.decorate.couponDoesNotExist"));
              } else if (res.content == 2) {
                util.toast_fail(this.$t("components.decorate.coupoExpired"));
              } else if (res.content == 3) {
                util.toast_fail(this.$t("components.decorate.couponDisabled"));
              } else if (res.content == 4) {
                util.toast_fail(this.$t("components.decorate.couponStockIs"));
              } else if (res.content == 5) {
                util.toast_fail(this.$t("components.decorate.insufficientPointsAvailable"));
              } else if (res.content == 6) {
                util.toast_fail(this.$t("components.decorate.pointUpdateFailed"));
              } else if (res.content == 7) {
                util.toast_fail(this.$t("components.decorate.reachesTheUpperLimit"));
              }
              util.toast_fail(res.content);
              m.isCoupon = 0;
              m['coupon_arr'][d.coupon_key].status = -1;
              _this.$set();
            }

            // if (res.content == 0) {
            //   util.showModal('提示', '您已领取的个数已达到可领取限制');
            //   m.isCoupon = 0;
            //   m['coupon_arr'][d.coupon_key].status = -1;
            //   _this.$set();
            // } else {
            //   util.toast_success('领取成功', function () {
            //     m.isCoupon = 0;
            //     m['coupon_arr'][d.coupon_key].status = -1;
            //     _this.$set();
            //   });
            // }
          } else {
            util.toast_fail(this.$t("components.decorate.failToRreceive"));
          }
        }, {
          code: d.code,
          form_id: d.form_id,
          open_id: d.open_id
        })
      }
    },

    bindGetCoupon (e) {
      var d = this.eventData(e);
      var m = this.data.m;
      var _this = this;

      // 可领取状态
      if (d.status == 1) {
        if (d.use_score === 1 || d.exclusive != '') {
          // 积分兑换
          util.jumpLink('/pages/receiveCoupon/receiveCoupon?couponId=' + d.coupon_id);
        } else {
          // 直接领取
          wx.showLoading({
            title: '领取中···',
          })
          util.api('/api/wxapp/coupon/get', function (res) {
            if (res.error == 0) {
              wx.hideLoading();
              if (res.content == 0) {
                util.toast_success(this.$t("components.decorate.successfulReception"), function () {
                  m['coupon_arr'][d.key].status = -1;
                  _this.$set();
                });
              } else if (res.content == 1) {
                util.toast_fail(this.$t("components.decorate.couponDoesNotExist"));
              } else if (res.content == 2) {
                util.toast_fail(this.$t("components.decorate.coupoExpired"));
              } else if (res.content == 3) {
                util.toast_fail(this.$t("components.decorate.couponDisabled"));
              } else if (res.content == 4) {
                util.toast_fail(this.$t("components.decorate.couponStockIs"));
              } else if (res.content == 5) {
                util.toast_fail(this.$t("components.decorate.insufficientPointsAvailable"));
              } else if (res.content == 6) {
                util.toast_fail(this.$t("components.decorate.pointUpdateFailed"));
              } else if (res.content == 7) {
                util.toast_fail(this.$t("components.decorate.reachesTheUpperLimit"));
              }
            } else {
              util.toast_fail(this.$t("components.decorate.failToRreceive"));
            }
          }, {
              couponId: d.coupon_id
            });
          
        }
      }


      
    },

    bindCodeBlur (e) {
      this._input_code = e.detail.value;
    },
    bindClose (e) {
      this.data.m.isCoupon = 0;
      this.$set();
    },
  }
});