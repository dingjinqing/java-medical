// pages/agreement/agreement.js
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
  
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var charge_money = JSON.parse(options.charge_money);
    var offset_momey = charge_money[0].value;
    var recharge_arr = [];
    if (offset_momey == 0){
      for (var i = 1; i < (charge_money.length - 1); i++) {
        recharge_arr.push(charge_money[i])
      }
    }
    if (offset_momey == 1){
      recharge_arr = charge_money[charge_money.length - 1];
    }
    this.setData({
      offset_momey: offset_momey,
      recharge_arr: recharge_arr
    })
  },
})