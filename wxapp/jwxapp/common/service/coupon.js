var util = require("../../utils/util.js");
var coupon = {
  getCoupon(d, cb) {
    util.api('api/wxapp/coupon/get', function (res) {
      wx.hideLoading();
      if (res.error == 0) {
        if (res.content == 0) {
          util.toast_success('领取成功', function () {
            cb('领取成功');
          });
        } else if (res.content == 1) {
          util.toast_fail('优惠券不存在');
          cb('优惠券不存在');
        } else if (res.content == 2) {
          util.toast_fail('优惠券已过期');
          cb('优惠券已过期');
        } else if (res.content == 3) {
          util.toast_fail('优惠券已停用');
          cb('优惠券已停用');
        } else if (res.content == 4) {
          util.toast_fail('优惠券库存为0');
          cb('优惠券库存为0');
        } else if (res.content == 5) {
          util.toast_fail('可用积分不足');
          cb('可用积分不足');
        } else if (res.content == 6) {
          util.toast_fail('积分更新失败');
          cb('积分更新失败');
        } else if (res.content == 7) {
          util.toast_fail('领取次数达上限');
          cb('领取次数达上限');
        }
      } else {
        util.toast_fail('领取失败');
      }
    }, {
        cuponId: d
        // form_id: d.form_id,
        // open_id: util.getCache("openid")
      });


    // if (d.vali || d.use_score == 1 || d.exclusive == 1) {
    //   util.jumpLink('/pages/getCoupon/getCoupon?code=' + d.code +
    //     (d.exclusive != 1 && d.goods_id ? "&goods_id=" + d.goods_id : ""));
    // } else {        
    //   // util.api('api/wxapp/coupon/get?code=' + d.code, function (res) {
    //   //   wx.hideLoading();
    //   //   if (res.error == 0) {
    //   //     if (res.content == 0) {
    //   //       util.showModal('提示', '您已领取的个数已达到可领取限制');
    //   //       cb(res.content);
    //   //     } else {
    //   //       util.toast_success('领取成功', function () {
    //   //         cb(res.content);
    //   //       });
    //   //     }
    //   //   } else {
    //   //     util.toast_fail(res.message.msg);
    //   //   }
    //   // }, {
    //   //     form_id: d.form_id,
    //   //     open_id: util.getCache("openid")
    //   //   });
    // }
  }
};

module.exports = coupon;