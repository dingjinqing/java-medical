// pages/usercenter/usercenter.js
var util = require('../../utils/util.js');
var app = getApp()
var Url = app.globalData.baseUrl;
var imageUrl = app.globalData.imageUrl;
var is_sign = 0;
var is_grade = 0;
var user_center;
var page_style = 1;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    user_name: app.globalData.input_array.mobile,
    wx_user_avatar: '',
    user_mobile: '',
    user_avatar: '',
    is_sign: 0,
    is_grade: 0,
    main_url: app.globalData.baseUrl,
    imageUrl: app.globalData.imageUrl,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    is_block: 0,
    page_style: 1
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    var win_h = wx.getSystemInfoSync().windowHeight;
    this.setData({
      win_h: win_h
    })
    that.usercenterRequest(that);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  usercenterRequest: function (that) {
    util.api('/api/wxapp/account/usercenter', function (res) {
      if (res.error == 0) {
        var user_center_all = res.content.module_data;
        page_style = user_center_all[0].page_style;
        if (user_center_all[0].module_name == "global") {
          user_center = user_center_all.slice(1);
        } else {
          user_center = user_center_all
        }

        console.log(user_center);

        for (var i in user_center) {
          if (user_center[i].module_name == "center_header") {
            if (user_center[i].qrcode && user_center[i].qrcode.status == 1) {
              var myQrCode = user_center[i].qrcode.qrcode.qrcode_img
              wx.setStorage({
                key: 'myQrCode',
                data: myQrCode,
              })
              that.setData({
                myQrCode: myQrCode
              })
            }
          }
          if (user_center[i].module_name == "appointment") {
            if (user_center[i].appointment_info && user_center[i].appointment_info.serviceImg) {
              user_center[i].appointment_info.serviceImg = JSON.parse(user_center[i].appointment_info.serviceImg)[0];
            }
          }
        }
        that.setData({
          user_center: user_center,
          page_style: page_style
        })
      }
    }, {})
  },
  click_to_detail: function (e) {
    var service_id = e.currentTarget.dataset.service_id;
    util.navigateTo({
      url: '/pages/appointment/appointment?service_id=' + service_id
    })
  },
  to_where: function (e) {
    var judge_statuss = e.currentTarget.dataset.judge_status;
    var is_distributors = e.currentTarget.dataset.is_distributor;
    if (is_distributors == 0 && judge_statuss == 1) {
      util.navigateTo({
        url: "/pages/distributionspread/distributionspread",
      })
    } else {
      var names = e.currentTarget.dataset.names;
      util.navigateTo({
        url: '/pages/distribution/distribution?names=' + names,
      })
    }
  },
  toSign: function (e) {
    is_sign = 1;
    this.setData({
      is_sign: is_sign
    })
  },
  closeSign: function (e) {
    is_sign = 0;
    this.setData({
      is_sign: is_sign
    })
  },
  closeGrade: function (e) {
    is_grade = 0;
    util.setCache('refuse_grade', 1);
    this.setData({
      is_grade: is_grade
    })
  },
  GetCrade: function (e) {
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.api('/api/card/getcard', function (res) {
      if (res.content >= 0) {
        is_grade = 0;
        user_center[0].get_grade = 0
        that.setData({
          is_grade: is_grade,
          user_center: user_center
        })
        util.toast_success('领取成功', function () {
          setTimeout(function () {
            util.jumpLink(`/pages/usercardinfo/usercardinfo?card_no=${res.content}`, 'navigateTo')
          }, 2000);
        });
      } else if (res.content == -1) {
        util.toast_fail('此卡已存在');
      } else {
        util.toast_fail('领取失败');
      }
    }, { card_info: '', type: 1, form_id: form_id, open_id: open_id });
  },
  lookRule: function (e) {
    var sign_rule = e.currentTarget.dataset.rule;
    util.jumpToWeb('/wxapp/sign/help', '&sign_rule=' + JSON.stringify(sign_rule))
  },
  signScore: function (e) {
    var score = user_center[0].sign_score.sign_data.receive_score;
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.api('api/wxapp/score/signAdd', function (res) {
      if (res.error == 0) {
        util.toast_success('签到成功');
        is_sign = 0;
        that.setData({
          is_sign: is_sign
        })
        that.usercenterRequest(that);
      } else {
        util.toast_fail('签到失败');
      }
    }, { score: score, form_id: form_id, open_id: open_id })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var user_name = util.getCache('nickName');
    var wx_user_avatar = util.getCache('avatarUrl');
    var that = this;
    if (user_name != '' && wx_user_avatar != '') {
      this.setData({
        wx_user_avatar: wx_user_avatar,
        user_name: user_name
      })
    }
    that.usercenterRequest(that);
  },
  to_account: function (e) {
    var num = e.currentTarget.dataset.num;
    if (num == null) {
      num = '0.00';
    }
    util.navigateTo({
      url: '/pages/balance/balance'
    })
  },
  to_integral: function (e) {
    var num = e.currentTarget.dataset.num;
    if (num == null) {
      num = 0;
    }
    util.navigateTo({
      url: '/pages/integral/integral?num=' + num
    })
  },
  to_collect: function () {
    util.navigateTo({
      url: '/pages/collect/collect'
    })
  },
  allOrder: function () {
    util.navigateTo({
      url: '/pages/orderlist/orderlist'
    })
  },
  // 查看全部预约
  allReserve: function () {
    util.navigateTo({
      url: '/pages/appointlist/appointlist'
    })
  },
  bindOrderNav: function (e) {
    var datas = JSON.stringify(e.currentTarget.dataset);
    console.log(datas)
    if (e.currentTarget.dataset.type == 'FINISHED') {
      util.jumpLink('/pages/comment/comment')
    } else {
      util.navigateTo({ url: '/pages/orderlist/orderlist?datas=' + datas })
    }

  },
  to_codeverification: function () {
    if (!util.getCache('mobile')) {
      var that = this;
      util.showModal('提示', '请授权手机号', function () {
        util.checkSession(function () {
          that.setData({
            is_block: 1
          })
        });
      });
      return false;
    }
    util.navigateTo({
      url: '/pages/codeverification/codeverification',
    })
  },
  to_search: function (e) {
    var types = e.currentTarget.dataset.types;
    var action;
    if (types == 'history') {
      action = 1;
    } else {
      action = 2;
    }
    util.jumpLink("/pages1/history/history?action=" + action);
  },
  to_present: function () {
    util.jumpLink("/pages1/presentlist/presentlist")
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
    // 显示导航栏loading
    wx.showNavigationBarLoading();
    // 调用接口加载数据
    var that = this;
    that.usercenterRequest(that);
    // 隐藏导航栏loading
    wx.hideNavigationBarLoading();
    // 当处理完数据刷新后，wx.stopPullDownRefresh可以停止当前页面的下拉刷新
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },
})
