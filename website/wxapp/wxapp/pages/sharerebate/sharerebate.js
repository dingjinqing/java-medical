// pages/sharerebate/sharerebate.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    chooseGoods: {},
    advicePrice: {},
    selectCoupon: {},
    is_send_coupon:1,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    this.data.goods_id = options.goods_id;
    this.data.rebate_id = options.rebate_id;
    var that = this;
    util.api('/api/wxapp/rebate/goods/config',function(res){
      console.log(res)
      if (res.error == 0) {
        var rebate_list = [];
        if (res.content.goods.can_rebate == 1) {
          for (var r_index in res.content.rebate_price) {
            that.data.chooseGoods[r_index] = res.content.rebate_price[r_index].advise_price;
            that.data.advicePrice[r_index] = res.content.rebate_price[r_index].advise_price;
            rebate_list.push(res.content.rebate_price[r_index]);
          }
        }
        if (res.content.goods.send_coupon == 1) {
          for (var c_index in res.content.coupon_list) {
            let coupon_code = res.content.coupon_list[c_index].alias_code;
            that.data.selectCoupon[coupon_code] = false;
          }
        }
        that.setData(res.content)
        that.setData({
          rebate_list: rebate_list,
          chooseGoods: that.data.chooseGoods,
          max_rebate_money: parseFloat(res.content.rebate_ratio * that.getMaxPrice()).toFixed(2)
        })
      } else {
        util.showModal('提示', res.message)
      }
    }, { goods_id: options.goods_id, rebate_id: options.rebate_id })
  },
  send_coupon:function(e){
    this.setData({
      is_send_coupon: e.currentTarget.dataset.send
    })
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (e) {
    var rebateConfig = {};
    if (this.data.goods.can_rebate == 1) {
      rebateConfig['rebate_price'] = this.data.chooseGoods;
    }
    if (this.data.is_send_coupon == 1 && this.getCoupons().length > 0) {
      rebateConfig['coupons'] = this.getCoupons();
    }
    var date = new Date();
    rebateConfig['rebate_time'] = parseInt(date.getTime()/1000)
    console.log('/pages/item/item?goods_id=' + this.data.goods_id +
    '&rebate_config=' +JSON.stringify(rebateConfig))
    return {
      title: '【特价专享】唯一渠道，专享价格，等你来抢！',
      path: '/pages/item/item?goods_id=' + this.data.goods_id + '&invite_id=' 
       + util.getCache('user_id') + '&rebate_config=' + JSON.stringify(rebateConfig),
      imageUrl: this.data.rebate_image
    }
  },

  setProductPrice: function(e) {
    let product_id = e.currentTarget.dataset.productId;
    let set_price = parseFloat(e.detail.value);
    let product = this.data.rebate_price[product_id];
    if (set_price > product.max_price){
      util.showModal('提示', '设置金额不能大于最高售价');
    } else if (set_price < product.min_price) {
      util.showModal('提示', '设置金额不能小于最低售价');
    } else {
      this.data.chooseGoods[product_id] = set_price.toFixed(2);
    }
    
    this.setData({
      chooseGoods: this.data.chooseGoods,
      max_rebate_money: parseFloat(this.data.rebate_ratio * this.getMaxPrice()).toFixed(2)
    })
  },
  
  resetProductPrice: function (e) {
    let product_id = e.currentTarget.dataset.productId;
    this.data.chooseGoods[product_id] = this.data.advicePrice[product_id];
    this.setData({
      chooseGoods: this.data.chooseGoods,
      max_rebate_money: parseFloat(this.data.rebate_ratio * this.getMaxPrice()).toFixed(2)
    })
  },

  getMaxPrice: function()
  {
    let max_price = 0;
    for (var prd_id in this.data.chooseGoods) {
      let temp = parseFloat(this.data.chooseGoods[prd_id]);
      if (temp > max_price) {
        max_price = temp;
      }
    }
    return max_price;
  },

  selectCoupon: function(e) {
    let aliasCode = e.currentTarget.dataset.aliasCode;
    if (!this.data.selectCoupon[aliasCode] && this.getCoupons().length >= 5) {
      util.showModal('提示','最多选择5张优惠券')
      return false;
    }
    this.data.selectCoupon[aliasCode] = !this.data.selectCoupon[aliasCode];
    this.setData({
      selectCoupon: this.data.selectCoupon
    })
  },

  getCoupons: function() {
    let coupon = [];
    for (var alias_code in this.data.selectCoupon) {
      if (this.data.selectCoupon[alias_code]) coupon.push(alias_code);
    }
    return coupon;
  }
})