/**
 * 优惠券列表
 * user:常乐
 */
let util = require("../../utils/util.js")
let config = require("../../utils/config.js")
var app = getApp();
var set_time_out;
// pages/test/test.js
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    page_id:1,
    can_used_flag: true,
    cou_used_flag: false,
    cou_guoqi_flag: false,
    can_used_header_flag: false,
    cou_used_header_flag: false,
    cou_guoqi_header_flag: false,
    // imageUrl: app.globalData.imageUrl,
    imageUrl: "http://miniimg.cn",
    unusedNum:0,
    usedNum: 0,
    expiredNum:0,
    this_type: 0,
    allCoupon:[],
    page: 1,
    last_page: 1,
    pageRows: 20,
  },
 
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var _this = this;
    clearTimeout(set_time_out);
    _this.dataList()
  },

  /**
   * 优惠券列表
   */
  dataList: function (){
    var _this = this;
    wx.showLoading({
      title: '加载中···',
    })
    clearTimeout(set_time_out);
    util.api('/api/wxapp/coupon/list', function (res) {
      if (res.error == 0) {
        _this.data.last_page = res.content.couponList.page.lastPage; 
        _this.setData({
          unusedNum: res.content.unusedNum,
          usedNum: res.content.usedNum,
          expiredNum: res.content.expiredNum,
          allCoupon: res.content.couponList.dataList
        }) 
        // 格式化时间
        if (_this.data.allCoupon.length > 0) {
          _this.data.allCoupon.forEach(function (item) {
            if (item.startTime && item.endTime) {
              item.startTime = item.startTime.toString().slice(0, 10)
              item.endTime = item.endTime.toString().slice(0, 10)
            }
            item.remain_seconds_all = item.remainHours * 3600 + item.remainMinutes * 60 + item.remainSeconds
          })
          // 倒计时
          _this.countdown(_this, _this.data.allCoupon);
        }

        console.log(_this.data.allCoupon)

        _this.setData({
          allCoupon: _this.data.allCoupon
        }) 
        wx.hideLoading();
      } else {
        util.showModal("提示", res.message, function () {
          util.jumpLink("pages/index/index", 'redirectTo');
        }, false);
        return false;
      }
    }, { 
      nav: _this.data.this_type,
      currentPage: _this.data.page,
      pageRows: _this.data.pageRows
    });
  },

  /**
   * 倒计时
   */
  countdown: function (that, dataList) {
    set_time_out = setTimeout(function () {
      // 放在最后--
      for (var i in dataList) {
        dataList[i].remain_seconds_all -= 1;
        if (dataList[i].remain_seconds_all < 0 || dataList[i].remain_seconds_all == NaN) {
          dataList[i].time_tips = "";
        } else {
          dataList[i].time_tips = util.dateformat(dataList[i].remain_seconds_all);
          // console.log(dataList[i].time_tips)
        }
      }
      that.setData({
        allCoupon: dataList
      });
      that.countdown(that, dataList);
    }, 1000)
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var that = this;
    if (that.data.page == that.data.last_page) { return false };
    that.data.page = that.data.page + 1;
    clearTimeout(set_time_out);
    wx.showLoading({
      title: '加载中···',
    })
    util.api('/api/wxapp/coupon/list', function (res) {
      if (res.error == 0) {
        var cou_listL = res.content.couponList.dataList;
        var cou_list = [];
        that.data.last_page = res.content.couponList.page.lastPage;
        if (cou_listL.length > 0) {
          cou_list = cou_listL;
          cou_list = that.data.allCoupon.concat(cou_list);
          // 格式化时间
          cou_listL.forEach(function (item) {
            if (item.startTime && item.endTime) {
              item.startTime = item.startTime.toString().slice(0, 10)
              item.endTime = item.endTime.toString().slice(0, 10)
            }
            item.remain_seconds_all = item.remainHours * 3600 + item.remainMinutes * 60 + item.remainSeconds
          })
          that.countdown(that, cou_list);
        }
        wx.hideLoading();
        that.setData({
          allCoupon: cou_list,
        })
      } else {
        util.showModal("提示", res.message, function () {
          util.jumpLink("pages/index/index", 'redirectTo');
        }, false);
        return false;
      }
    }, {
        nav: that.data.this_type,
        currentPage: that.data.page,
        pageRows: that.data.pageRows
    });
    console.log(this.data)
  },

  /**
   * 优惠券删除
   */
  // coupon_del: function (e) {
  //   var that = this;
  //   var coupon_sn = e.currentTarget.dataset.couponSn;
  //   util.showModal('', '您确定要删除该优惠券？', function () {
  //     var animate = '';
  //     var Coupon = that.data.cou_list;
  //     // util.api('/api/wxapp/coupon/del', function (res) {
  //     //   if (res.error === 0) {
  //     //     for (let i = 0; i < Coupon.length; i++) {
  //     //       Coupon[i].right = 0;
  //     //       if (coupon_sn == Coupon[i].coupon_sn) {
  //     //         Coupon.splice(i, 1)
  //     //         i--;
  //     //       }
  //     //     }
  //     //     clearTimeout(set_time_out);
  //     //     cou_request(that, this_type);
  //     //     that.setData({
  //     //       cou_list: Coupon,
  //     //       animate: animate
  //     //     })
  //     //   }
  //     // }, { coupon_sn: coupon_sn })
  //   }, true, '取消', '确定')
  // },

  /**
   * 优惠券状态tab切换
   */
  change: function (e) {
    var _this = this;
    var name = e.target.dataset.name;
    console.log(name)
    if (name == 'can') {
      _this.setData({
        this_type: 0,
      }) 
    }
    if (name == 'used') {
      _this.setData({
        this_type:1,
      }) 
    }
    if (name == 'time') {
      _this.setData({
        this_type: 2,
      })
    }
    _this.data.allCoupon = []
    _this.data.page = 1;
    clearTimeout(set_time_out);
    _this.dataList()
  },

  /**
   * 优惠券详情
   */
  couponDetail:function(opt){
    var couponSn = opt.currentTarget.dataset.couponsn;
    util.jumpLink('/pages/getCoupon/getCoupon?couponSn=' + couponSn);
  },

  /**
   * 券购搜素
   */
  to_search: function (opt) {
    var coupon_sn = opt.currentTarget.dataset.coupon_sn;
    util.jumpLink('/pages/searchs/search?coupon_sn=' + coupon_sn);
  }
})