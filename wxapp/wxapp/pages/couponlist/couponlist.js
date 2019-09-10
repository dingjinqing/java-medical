// pages/couponlist/couponlist.js
var app = getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var startX = 0;
var endX;
var maxRight = 146;
var this_type = 1;
var set_time_out;
global.wxPage({
  data: {
    can_used_flag: true,
    cou_used_flag: false,
    cou_guoqi_flag: false,
    can_used_header_flag: false,
    cou_used_header_flag: false,
    cou_guoqi_header_flag: false,
    imageUrl: app.globalData.imageUrl,
    info:{},
    this_type:1,
    cou_list:[],
    page: 1,
    last_page: 1,
  },

  onLoad: function (option) {
    if (!util.check_setting(option)) return;
    var that = this;
    this_type = 1;
    cou_request(that,this_type);
    clearTimeout(set_time_out);
  },
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },
  // 切换tab
  change: function (e) {
    var that = this;
    var name = e.target.dataset.name;
    if (name == 'can') {
      this_type = 1;
    } 
    if (name == 'used') {
      this_type = 2;
    } 
    if (name == 'time') {
      this_type = 3;
    }
    that.data.page = 1;
    clearTimeout(set_time_out);
    cou_request(that, this_type);
  },
  // 领取
  getCoupon: function (opt) {
    var coupon_sn = opt.currentTarget.dataset.coupon_sn;
    util.jumpLink('/pages/getcoupon/getcoupon?list=1&coupon_sn=' + coupon_sn);
  },
  // 券购搜素
  to_search: function (opt) {
    var coupon_sn = opt.currentTarget.dataset.coupon_sn;
    util.jumpLink('/pages/searchs/search?coupon_sn=' + coupon_sn);
  },
  // 删除
  drawStart:function(e){
    var touch = e.touches[0];
    startX = touch.clientX;
    var check_action = e.currentTarget.dataset.check_action;
    var animate = '';
    var Coupon = this.data.cou_list
    for(var i in Coupon){
      var data = Coupon[i];
      data.startRight = data.right;
    }
    this.setData({
      animate:'all .1s ease-out'
    })
  },
  drawMove:function(e){
    var self = this;
    var dataSn = e.currentTarget.dataset.coupon_sn;
    var Coupon = this.data.cou_list;
    var touch = e.touches[0];
    endX = touch.clientX;
    if((endX - startX) < 0){
      for(var k in Coupon){
        if(dataSn === Coupon[k].coupon_sn){
          var startRight = Coupon[k].startRight;
          var change = startX - endX;
          startRight += change;
          if(startRight > maxRight) startRight = maxRight;
          Coupon[k].right = startRight;
        }
      }
    } else {
      for(var k in Coupon){
        if(dataSn === Coupon[k].coupon_sn){
          var startRight = Coupon[k].startRight;
          var change = startX - endX;
          startRight += change;
          if(startRight < 0) startRight = 0;
          Coupon[k].right = startRight;
        }
      }
    }
    
    self.setData({
      cou_list:Coupon
    })
    
  },
  drawEnd:function(e){
    var self = this;
    var dataSn = e.currentTarget.dataset.coupon_sn;
    var touch = e.touches[0];
    var Coupon = this.data.cou_list;
    if((endX - startX) < 0){
      for(var k in Coupon){
        if(dataSn === Coupon[k].coupon_sn){
          var startRight = Coupon[k].startRight;
          var change = startX - endX;
          startRight += change;
          if(startRight > maxRight) startRight = maxRight;
          if(startRight < maxRight / 2){
            Coupon[k].right = 0;
          } else {
            Coupon[k].right = 146;
          }
        }
      }
    } else {
      for(var k in Coupon){
        if(dataSn === Coupon[k].coupon_sn){
          var startRight = Coupon[k].startRight;
          var change = startX - endX;
          startRight += change;
          if(startRight < 0) startRight = 0;
          if(startRight > maxRight / 2){
            Coupon[k].right = 146;
          } else {
            Coupon[k].right = 0;
          }
        }
      }
    }
    self.setData({
      cou_list:Coupon
    })
  },
  coupon_del:function(e){
    var that = this;
    var coupon_sn = e.currentTarget.dataset.coupon_sn;
    util.showModal('','您确定要删除该优惠券？',function(){
      var animate = '';
      var Coupon = that.data.cou_list;
      util.api('/api/wxapp/coupon/del',function(res){
        if(res.error === 0){
          for(let i = 0;i < Coupon.length; i++){
            Coupon[i].right = 0;
            if(coupon_sn == Coupon[i].coupon_sn){
              Coupon.splice(i,1)
              i--;
            }
          }
          clearTimeout(set_time_out);
          cou_request(that, this_type);
          that.setData({
            cou_list: Coupon,
            animate: animate
          })
        }
      },{coupon_sn:coupon_sn})
    },true,'取消','确定')
  },


  // 倒计时
  countdown: function (that, cou_list) {
    set_time_out = setTimeout(function () {
      // 放在最后--
      for (var i in cou_list) {
        cou_list[i].remain_seconds_all = cou_list[i].remain_seconds_all - 1;
        if (cou_list[i].remain_seconds_all < 0 || cou_list[i].remain_seconds_all == NaN) {
          cou_list[i].time_tips = "";

        } else {
          cou_list[i].time_tips = util.dateformat(cou_list[i].remain_seconds_all);
        }
      }
      that.countdown(that, cou_list);
      that.setData({
        cou_list: cou_list
      });
    }, 1000)
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var that = this;
    if(that.data.page == that.data.last_page){return false};
    that.data.page = that.data.page + 1;
    clearTimeout(set_time_out);
    wx.showLoading({
      title: '加载中···',
    })
    util.api('/api/wxapp/coupon/list', function (res) {
      if (res.error == 0) {
        var cou_listL = res.content.coupons;
        var cou_list = [];
        that.data.last_page = cou_listL.last_page;
        if (cou_listL.data.length > 0) {
          cou_list = cou_listL.data;
          cou_list = that.data.cou_list.concat(cou_list);
          that.countdown(that, cou_list);
        }
        wx.hideLoading();
        that.setData({
          info: res.content,
          cou_list: cou_list,
          this_type: this_type
        })
      } else {
        util.showModal("提示", res.message, function () {
          util.jumpLink("pages/index/index", 'redirectTo');
        }, false);
        return false;
      }
    }, { page: that.data.page, action: this_type });
  },
})
function cou_request(that, this_type){
  wx.showLoading({
    title: '加载中···',
  })
  util.api('/api/wxapp/coupon/list', function (res) {
    if(res.error == 0){
      var cou_listL = res.content.coupons;
      var cou_list = [];
      that.data.last_page = cou_listL.last_page;
      if (cou_listL.data.length > 0) {
        cou_list = cou_listL.data;
        that.countdown(that,cou_list);
      }
      wx.hideLoading();
      that.setData({
        info: res.content,
        cou_list: cou_list,
        this_type: this_type
      })
    }else{
      util.showModal("提示",res.message,function(){
        util.jumpLink("pages/index/index",'redirectTo');
      },false);
      return false;
    }
  }, { page: that.data.page, action: this_type});
}