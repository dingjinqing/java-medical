// USERCARDLIST.JS 2018.03.06
var app = new getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var card_list = [];
var goods_id = 0;
var strategy_id;
var is_fullprice = 0;
var couponId = 0;
var seckillId = 0;
var code = 0;
var gift_id = 0;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    card_list: [],
  },

  cardInfo: function (e) {
    var card_id = e.currentTarget.dataset.card_id;
    var card_info;
    card_list.forEach(item => {
      if (item.id == card_id) {
        card_info = item;
        return;
      }
    })
    if (card_info.flag == 2) {
      util.showModal('提示', '该会员卡已停用');
      return false;
    }
    if (card_info.expire_type == 0) {
      var date = new Date(card_info.end_time);
      var now_time = new Date().getTime();
      var end_time = date.getTime();
      if (now_time >= end_time) {
        util.showModal('提示', '该会员卡已过期');
        return false;
      }
    }
    if (is_fullprice == 0 && code == 0 && seckillId == 0 && goods_id == 0 && gift_id == 0) {
      util.navigateTo({
        url: '../usercardinfo/usercardinfo?card_list=1&card_id=' + card_id,
      })
    } else if (is_fullprice || code || seckillId || goods_id || gift_id) {
      util.navigateTo({
        url: '../usercardinfo/usercardinfo?card_list=1&card_id=' + card_id + "&is_fullprice=" + is_fullprice + "&code=" + code + "&seckillId=" + seckillId + '&goods_id=' + goods_id + '&gift_id=' + gift_id,
      })
    }

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    goods_id = options.goods_id ? options.goods_id : 0
    strategy_id = options.strategy_id;
    couponId = options.coupon_id;
    if (options.seckillId) {
      seckillId = options.seckillId;
    } else {
      seckillId = 0;
    }
    if (options.code) {
      code = options.code
    } else {
      code = 0
    }
    if (options.is_fullprice) {
      is_fullprice = options.is_fullprice;
    } else {
      is_fullprice = 0
    }
    if (options.gift_id) {
      gift_id = options.gift_id;
    } else {
      gift_id = 0
    }
    console.log(options);
    this.cardList();
  },

  cardList: function () {
    var that = this;
    // util.api('/api/wxapp/card/exclusive', function (res) {
    //   console.log(res)
    //   card_list = res.content;
    //   for (var i in card_list) {
    //     card_list[i].discount = parseFloat(card_list[i].discount);
    //     card_list[i].bg_img = imageUrl + card_list[i].bg_img;
    //     card_list[i].buy_score = JSON.parse(card_list[i].buy_score);
    //     card_list[i].right = 0;
    //     if (card_list[i].shop_avatar == null) {
    //       card_list[i].shop_avatar = imageUrl + 'image/wxapp/shop_logo_default.png';
    //     }
    //     if (card_list[i].date_type == 0) {
    //       card_list[i].data_type_name = "日";
    //     } else if (card_list[i].date_type == 1) {
    //       card_list[i].data_type_name = "周";
    //     } else {
    //       card_list[i].data_type_name = "月";
    //     }
    //     if (card_list[i].is_pay == 1 && card_list[i].pay_type == 1) {
    //       card_list[i].pay_fee = card_list[i].pay_fee.substring(0, card_list[i].pay_fee.length - 3);
    //     }
    //     if ((card_list[i].card_type == 0 || card_list[i].card_type == 2) && card_list[i].buy_score) {
    //       if (card_list[i].buy_score[0].value == 0) {
    //         var arrlen = card_list[i].buy_score.length;
    //         if (card_list[i].buy_score[arrlen - 1].each_money) {
    //           card_list[i].buy_score1 = card_list[i].buy_score.slice(1, arrlen - 1);
    //         } else {
    //           card_list[i].buy_score1 = card_list[i].buy_score.slice(1);
    //         }
    //       }
    //       if (card_list[i].buy_score[0].value == 1) {
    //         card_list[i].buy_score1 = card_list[i].buy_score[card_list[i].buy_score.length - 1];
    //       }
    //     }
    //   }
    //   that.setData({
    //     card_list: card_list
    //   })
    // }, { goods_id: goods_id, strategy_id: strategy_id, seckill_id: seckillId, coupon_id: couponId })
  },

  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },

  imageLoad: function () {
    var imageWidth = wx.getSystemInfoSync().windowWidth;
    this.setData({
      imageWidth: wx.getSystemInfoSync().windowWidth
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.cardList();
  },

  card_del: function (e) {
    var that = this;
    var card_no = e.currentTarget.dataset.card_no;
    var card_List = this.data.card_list;
    util.showModal('', '您确定要删除该会员卡？', function () {
      var animate = '';
      // util.api('/api/wxapp/card/del', function (res) {
      //   if (res.error === 0 && res.content.error === 0) {
      //     for (var i = 0; i < card_List.length; i++) {
      //       card_List[i].right = 0;
      //       if (card_no === card_List[i].card_no) {
      //         card_List.splice(i, 1);
      //         i--
      //       }
      //     }
      //     that.setData({
      //       card_list: card_List,
      //       animate: animate
      //     })

      //   }
      // }, { card_no: card_no })
    }, true, '取消', '确定')
  },

  to_renew(e) {
    var card_no = e.currentTarget.dataset.card_no;
    // util.jumpLink("/pages/usercardrenew/usercardrenew?card_no=" + card_no)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

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

  }
  
})
