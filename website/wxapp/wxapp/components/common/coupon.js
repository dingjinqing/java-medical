var util = require("../../utils/util.js");
var coupon = {
  methods: {
    getCoupon(d, cb) {
      if (d.vali || d.use_score == 1 || d.exclusive == 1) {
        util.jumpLink('/pages/getcoupon/getcoupon?code=' + d.code +
          (d.exclusive != 1 && d.goods_id ? "&goods_id=" + d.goods_id : ""));
      } else {
        wx.showLoading({
          title: '领取中···',
        })
        util.api('/api/wxapp/coupon/get?code=' + d.code, function(res) {
          wx.hideLoading();
          if (res.error == 0) {
            if (res.content == 0) {
              util.showModal('提示', '您已领取的个数已达到可领取限制');
              cb(res.content);
            } else {
              util.toast_success('领取成功', function() {
                cb(res.content);
              });
            }
          } else {
            util.toast_fail(res.message.msg);
          }
        }, {
          form_id: d.form_id,
          open_id: util.getCache("openid")
        });
      }
    }
  }
};

module.exports = coupon;