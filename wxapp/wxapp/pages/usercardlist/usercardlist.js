// USERCARDLIST.JS 2018.03.06
var app = new getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var startX = 0;
var endX;
var maxRight = 146;
var card_list = [];
var is_fullprice = 0;
var code = 0;
var seckillId = 0;
var goods_id = 0;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    card_list: []
  },

  cardInfo: function(e) {
    var card_no = e.currentTarget.dataset.card_no;
    if (is_fullprice == 0 && code == 0 && seckillId == 0 && goods_id == 0){
      util.navigateTo({
        url: '../usercardinfo/usercardinfo?card_list=1&card_no=' + card_no,
      })
    }else{
      util.navigateTo({
        url: '../usercardinfo/usercardinfo?card_list=1&card_no=' + card_no + "&is_fullprice=" + is_fullprice + "&code=" + code + "&seckillId=" + seckillId + '&goods_id=' + goods_id,
      })
    }

  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    if (options.is_fullprice) {
      is_fullprice = options.is_fullprice;
    } else {
      is_fullprice = 0
    }
    seckillId = options.seckillId ? options.seckillId : 0;
    code = options.code ? options.code : 0;
    goods_id = options.goods_id ? options.goods_id : 0;
    this.cardList();
  },
  cardList:function(){
    var that = this;
    util.api('/api/card/list', function (res) {
      card_list = res.content;
      for (var i in card_list) {
        card_list[i].discount = parseFloat(card_list[i].discount);
        card_list[i].bg_img = imageUrl + card_list[i].bg_img;
        card_list[i].buy_score = JSON.parse(card_list[i].buy_score);
        card_list[i].right = 0;
        if (card_list[i].shop_avatar == null) {
          card_list[i].shop_avatar = imageUrl + 'image/wxapp/shop_logo_default.png';
        }
        if ((card_list[i].card_type == 0 || card_list[i].card_type == 2) && card_list[i].buy_score) {
          if (card_list[i].buy_score[0].value == 0) {
            var arrlen = card_list[i].buy_score.length;
            if (card_list[i].buy_score[arrlen - 1].each_money) {
              card_list[i].buy_score1 = card_list[i].buy_score.slice(1, arrlen - 1);
            } else {
              card_list[i].buy_score1 = card_list[i].buy_score.slice(1);
            }
          }
          if (card_list[i].buy_score[0].value == 1) {
            card_list[i].buy_score1 = card_list[i].buy_score[card_list[i].buy_score.length - 1];
          }
        }
      }
      that.setData({
        card_list: card_list
      })
    }, {   })
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
  drawStart:function(e){
    var touch = e.touches[0];
    startX = touch.clientX;
    var userCardList = this.data.card_list;
    var animate = '';
    for(var i in userCardList){
      var data = userCardList[i];
      data.startRight = data.right;
    }
    this.setData({
      animate:'all .1s ease-out'
    })
  },
  drawMove:function(e){
    var self = this;
    var dataNo = e.currentTarget.dataset.card_no;
    var userCardList = this.data.card_list;
    var touch = e.touches[0];
    endX = touch.clientX;
    if((endX - startX) < 0){
      for(var k in userCardList){
        if ((dataNo === userCardList[k].card_no) && (userCardList[k].is_expire === 1)){
          var startRight = userCardList[k].startRight;
          var change = startX - endX;
          startRight += change;
          if(startRight > maxRight) startRight = maxRight;
          userCardList[k].right = startRight;
        }
      }
    } else {
      for(var k in userCardList){
        if ((dataNo === userCardList[k].card_no) && (userCardList[k].is_expire === 1)){
          var startRight = userCardList[k].startRight;
          var change = startX - endX;
          startRight += change;
          if(startRight < 0) startRight = 0;
          userCardList[k].right = startRight;
        }
      }
    }
    self.setData({
      card_list:userCardList
    })
  },
  drawEnd:function(e){
    var self = this;
    var dataNo = e.currentTarget.dataset.card_no;
    var userCardList = this.data.card_list;
    if((endX - startX) < 0){
      for(var k in userCardList){
        if ((dataNo === userCardList[k].card_no) && (userCardList[k].is_expire === 1)){
          var startRight = userCardList[k].startRight;
          var change = startX - endX;
          startRight += change;
          if(startRight > maxRight) startRight = maxRight;
          if(startRight < maxRight / 2){
            userCardList[k].right = 0;
          } else {
            userCardList[k].right = 146;
          }
        }
      }
    } else {
      for(var k in userCardList){
        if ((dataNo === userCardList[k].card_no) && (userCardList[k].is_expire === 1)){
          var startRight = userCardList[k].startRight;
          var change = startX - endX;
          startRight += change;
          if(startRight < 0) startRight = 0;
          if(startRight > maxRight / 2){
            userCardList[k].right = 146;
          } else {
            userCardList[k].right = 0;
          }
        }
      }
    }
    self.setData({
      card_list: userCardList
    })
  },
  card_del:function(e){
    var that = this;
    var card_no = e.currentTarget.dataset.card_no;
    var card_List = this.data.card_list;
    util.showModal('','您确定要删除该会员卡？',function(){
      var animate = '';
      util.api('/api/wxapp/card/del',function(res){
        if(res.error === 0  && res.content.error === 0){
          for(var i = 0; i < card_List.length; i++){
            card_List[i].right = 0;
            if(card_no === card_List[i].card_no){
              card_List.splice(i,1);
              i--
            }
          }
          that.setData({
            card_list:card_List,
            animate:animate
          })

        }
      },{card_no:card_no })
    },true,'取消','确定')
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.cardList();
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

  },
})
