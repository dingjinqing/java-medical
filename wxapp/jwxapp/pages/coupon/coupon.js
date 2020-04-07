/**
 * 优惠券列表
 * user:常乐
 */
let util = require("../../utils/util.js")
let config = require("../../utils/config.js")
var app = getApp();
var set_time_out;
var startX = 0;
var endX;
var maxRight = 146;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    page_id:1,
    imageUrl: app.globalData.imageUrl,
    unusedNum:0, // 未使用个数
    usedNum: 0, // 已使用个数
    expiredNum:0, // 已过期个数
    this_type: 0, // 状态
    allCoupon:[], // 列表数据
    page: 1,
    last_page: 1,
    pageRows: 20,
  },
 
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    clearTimeout(set_time_out);
    this.dataList()
  },

  /**
   * 优惠券列表
   */
  dataList: function (){
    var that = this;
    wx.showLoading({
      title: '加载中···',
    })
    clearTimeout(set_time_out);
    util.api('/api/wxapp/coupon/list', function (res) {
      if (res.error == 0) {
        that.setData({
          last_page: res.content.couponList.page.lastPage,
          unusedNum: res.content.unusedNum,
          usedNum: res.content.usedNum,
          expiredNum: res.content.expiredNum,
          allCoupon: res.content.couponList.dataList
        }) 
        // 格式化时间
        if (that.data.allCoupon.length > 0) {
          that.data.allCoupon.forEach(function (item) {
            if (item.startTime && item.endTime) {
              item.startTime = item.startTime.toString().slice(0, 10)
              item.endTime = item.endTime.toString().slice(0, 10)
            }
            item.remain_seconds_all = item.remainHours * 3600 + item.remainMinutes * 60 + item.remainSeconds
          })
          // 倒计时
          that.countdown(that, that.data.allCoupon);
        }

        console.log(that.data.allCoupon)

        that.setData({
          allCoupon: that.data.allCoupon
        }) 
        wx.hideLoading();
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
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    var that = this;
    var user = res.target.dataset.user;
    var couponSn = res.target.dataset.coupon_sn;
    var couponId = res.target.dataset.coupon_id;
    var idx = Number(res.target.dataset.index);
    // var cou = 'allCoupon[' + idx + '].is_share';
    // util.api('/api/wxapp/divsionCoupon/share', function (res) {
    //   that.setData({
    //     [cou]: 1,
    //   })
    // }, { coupon_sn: coupon_sn });
    return {
      title: '分享优惠券',
      path: '/pages/splitinfo/splitinfo?user=' + user + "&couponSn=" + couponSn + "&couponId=" + couponId + "&inviteId=" + util.getCache('user_id'),
      imageUrl: that.data.imageUrl + 'image/wxapp/share_icon.jpg',
    }
  },

  /**
   * 分享已满员
   */
  full_people: function (e) {
    var user = e.target.dataset.user;
    var couponSn = e.target.dataset.coupon_sn;
    var couponId = e.target.dataset.coupon_id;
    util.showModal("提示", '领取人数已满', function () {
      util.navigateTo({
        url: '/pages/splitinfo/splitinfo?user=' + user + "&couponSn=" + couponSn + "&couponId=" + couponId,
      })
    }, true, '取消', '领取记录');
  },

  /**
   * 优惠券删除
   */
  drawStart: function (e) {
    var touch = e.touches[0];
    startX = touch.clientX;
    var check_action = e.currentTarget.dataset.check_action;
    var animate = '';
    var Coupon = this.data.allCoupon
    for (var i in Coupon) {
      var data = Coupon[i];
      data.startRight = data.right;
    }
    this.setData({
      animate: 'all .1s ease-out'
    })
  },
  drawMove: function (e) {
    var self = this;
    var dataSn = e.currentTarget.dataset.couponsn;
    var Coupon = this.data.allCoupon;
    var touch = e.touches[0];
    endX = touch.clientX;
    if ((endX - startX) < 0) {
      for (var k in Coupon) {
        if (dataSn === Coupon[k].couponSn) {
          var startRight = Coupon[k].startRight;
          var change = startX - endX;
          startRight += change;
          if (startRight > maxRight) startRight = maxRight;
          Coupon[k].right = startRight;
        }
      }
    } else {
      for (var k in Coupon) {
        if (dataSn === Coupon[k].couponSn) {
          var startRight = Coupon[k].startRight;
          var change = startX - endX;
          startRight += change;
          if (startRight < 0) startRight = 0;
          Coupon[k].right = startRight;
        }
      }
    }

    self.setData({
      allCoupon: Coupon
    })

  },
  drawEnd: function (e) {
    var self = this;
    var dataSn = e.currentTarget.dataset.couponsn;
    var touch = e.touches[0];
    var Coupon = this.data.allCoupon;
    if ((endX - startX) < 0) {
      for (var k in Coupon) {
        if (dataSn === Coupon[k].couponSn) {
          var startRight = Coupon[k].startRight;
          var change = startX - endX;
          startRight += change;
          if (startRight > maxRight) startRight = maxRight;
          if (startRight < maxRight / 2) {
            Coupon[k].right = 0;
          } else {
            Coupon[k].right = 146;
          }
        }
      }
    } else {
      for (var k in Coupon) {
        if (dataSn === Coupon[k].couponSn) {
          var startRight = Coupon[k].startRight;
          var change = startX - endX;
          startRight += change;
          if (startRight < 0) startRight = 0;
          if (startRight > maxRight / 2) {
            Coupon[k].right = 146;
          } else {
            Coupon[k].right = 0;
          }
        }
      }
    }
    self.setData({
      allCoupon: Coupon
    })
  },

  /**
   * 删除优惠券
   */
  coupon_del: function (e) {
    var that = this;
    var coupon_id = e.currentTarget.dataset.coupon_id;
    var coupon_sn = e.currentTarget.dataset.coupon_sn;
    util.showModal('', '您确定要删除该优惠券？', function () {
      var animate = '';
      var Coupon = that.data.allCoupon;
      util.api('api/wxapp/coupon/del', function (res) {
        if (res.error === 0) {
          for (let i = 0; i < Coupon.length; i++) {
            Coupon[i].right = 0;
            if (coupon_id == Coupon[i].id) {
              Coupon.splice(i, 1)
              i--;
            }
          }
          clearTimeout(set_time_out);
          // that.dataList()
          that.setData({
            allCoupon: Coupon,
            animate: animate
          })
        }
      }, { couponId: coupon_id })
    }, true, '取消', '确定')
  },

  /**
   * 优惠券状态tab切换
   */
  change: function (e) {
    var that = this;
    var name = e.target.dataset.name;
    console.log(name)
    if (name == 'can') {
      that.setData({
        this_type: 0,
      }) 
    }
    if (name == 'used') {
      that.setData({
        this_type:1,
      }) 
    }
    if (name == 'time') {
      that.setData({
        this_type: 2,
      })
    }
    that.data.allCoupon = []
    that.data.page = 1;
    clearTimeout(set_time_out);
    that.dataList()
  },

  /**
   * 优惠券详情
   */
  couponDetail:function(opt){
    var couponSn = opt.currentTarget.dataset.couponsn;
    util.jumpLink('/pages/getCoupon/getCoupon?couponSn=' + couponSn + '&type=' + this.data.this_type);
  },

  /**
   * 券购搜素
   */
  to_search: function (opt) {
    var coupon_sn = opt.currentTarget.dataset.coupon_sn;
    util.jumpLink('/pages1/search/search?couponSn=' + coupon_sn);
  }
})