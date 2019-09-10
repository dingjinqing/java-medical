// pages/userinfo/userinfo.js
var app = new getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var mobile;
var user_info = {};
var dates;
var region = ['', '', ''];
var real_name = '';
var sex_index = 0;
var act = 0;
var user_block = 0;
var card_no;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    dates: '选择您的生日',
    region: ['', '', ''],
    imageUrl: app.globalData.imageUrl,
    array: ['请选择','男', '女'],
    sex_index: 0,
    act: 0,
    user_avatar:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    user_block = 0;
    sex_index = 0;
    this.setData({
      user_block: user_block
    })
    if (!util.check_setting(options)) return;
    if (options.act){
      act = options.act;
      card_no = options.card_no;
      this.setData({
        act: act
      })
    }
    mobile = util.getCache('mobile');
    if (util.getCache('avatarUrl') != "" && util.getCache('avatarUrl')){
      this.data.user_avatar = util.getCache('avatarUrl');
    }else{
      this.data.user_avatar = imageUrl + "image/wxapp/custom_user_img.png";
    }
    
    user_info = {}
    region = ['', '', ''];
    dates = '选择您的生日';
    var that = this;
    wx.showLoading({
      title: '加载中',
    })
    setTimeout(function () {
      wx.hideLoading();
      user_block = 1;
      that.setData({
        user_block: user_block
      })
    }, 500)
    util.api('/api/wxapp/account/setting', function (res) {
      if(res.error == 0){
        user_info = res.content;
        //生日
        if (user_info.birthday_day != null && user_info.birthday_day != 0){
          if (parseInt(user_info.birthday_month) < 10){
            user_info.birthday_month = '0' + user_info.birthday_month;
          }
          if (parseInt(user_info.birthday_day) < 10) {
            user_info.birthday_day = '0' + user_info.birthday_day;
          }
          dates = user_info.birthday_year + '-' + user_info.birthday_month + '-' + user_info.birthday_day;
          that.setData({
            date: dates
          })
        }
        //性别
        if(user_info.sex != null){
          if(user_info.sex == "f"){
            sex_index = 2;
          }else{
            sex_index = 1;
          }
        }
        //所在地
        if (user_info.city_code != null){
          region[0] = user_info.province_code;
          region[1] = user_info.city_code;
          region[2] = user_info.district_code;
        }
        if (user_info.real_name != null){
          real_name = user_info.real_name;
        }
        that.setData({
          user_info: user_info,
          dates: dates,
          region: region,
          mobile: mobile,
          sex_index: sex_index,
          user_avatar: that.data.user_avatar
        })
      }

    }, {  })
  },
  toSave: function (e) {
    var that = this;
    setTimeout(function () {
      that.bind_submit(e)
    }, 100);
  },
  bind_submit: function (e){
    user_info = {};
    user_info.is_setting = 1;
    user_info.mobile = mobile;
    user_info.user_id = util.getCache('user_id');
    user_info.open_id = util.getCache("openid");
    user_info.form_id = e.detail.formId;
    //生日
    var date_arr = dates.split('-');
    user_info.birthday_year = date_arr[0];
    user_info.birthday_month = date_arr[1];
    user_info.birthday_day = date_arr[2];
    //名字
    if (user_info.real_name == "null") {
      user_info.real_name = '';
    }
    user_info.real_name = real_name;
    //所在地
    user_info.province_code = region[0];
    user_info.city_code = region[1];
    user_info.district_code = region[2];
    //性别
    if (sex_index == 1) {
      user_info.sex = 'm';
    } else if (sex_index == 2) {
      user_info.sex = 'f';
    }
    //激活
    if (act == 1) {
      if (sex_index == 0) {
        util.toast_fail('请填写完整信息');
        return;
      }
      if (user_info.birthday_year == "选择您的生日") {
        util.toast_fail('请填写完整信息');
        return;
      }
      if (user_info.real_name == "") {
        util.toast_fail('请填写完整信息');
        return;
      }
      if (user_info.city_code == "") {
        util.toast_fail('请填写完整信息');
        return;
      }
      user_info.card_no = card_no;
      user_info.is_setting = 2;
    }
    util.api('/api/wxapp/account/setting', function (res) {
      if (res.error == 0) {
        if (act == 1) {
          util.toast_success('激活成功', function () {
            setTimeout(function () {
              wx.navigateBack({
                delta: 2
              })
            }, 2000);
          });
        } else {
          util.toast_success('保存成功', function () { });
        }
      } else {
        util.toast_fail(res.message);
      }
    }, user_info)
  },
  bindDateChange: function (e) {
    dates = e.detail.value;
    this.setData({
      dates: e.detail.value
    })
  },
  bindRegionChange: function (e) {
    region = e.detail.value;
    this.setData({
      region: e.detail.value
    })
  },
  bindSexChange:function(e){
    sex_index = e.detail.value;
    this.setData({
      sex_index: e.detail.value
    })
  },
  inputBlur:function(e){
    if (/^1[3456789]\d{9}$/.test(e.detail.value)) {
      mobile = e.detail.value;
    } else {
      util.showModal('提示', "请输入正确的手机号！");
      return false;
    }
  },
  realName:function(e){
    real_name = e.detail.value;
  },
  getUserInfo: function (e) {
    var that = this;
    wx.showLoading({
      title: '获取中',
    })
    var canIUse = wx.canIUse('button.open-type.getUserInfo');
    if (e.detail.userInfo) {
      if (canIUse) {
        wx.hideLoading();
        var user_avatar = e.detail.userInfo.avatarUrl;
        var user_name = e.detail.userInfo.nickName;
        util.setCache("nickName", user_name);
        util.setCache("avatarUrl", user_avatar);
        user_info.username = user_name;
        user_info.user_avatar = user_avatar;
        that.data.user_avatar = user_avatar;
        that.setData({
          user_info: user_info,
          user_avatar: that.data.user_avatar
        });
        util.api('/api/wxapp/account/updateUser', function (res) {
        }, {

            username: user_name,
            user_avatar: user_avatar
          });
      } else {
        wx.getUserInfo({
          success: res => {
            wx.hideLoading();
            var user_avatar = e.detail.userInfo.avatarUrl;
            var user_name = e.detail.userInfo.nickName;
            util.setCache("nickName", user_name);
            util.setCache("avatarUrl", user_avatar);
            user_info.username = user_name;
            user_info.user_avatar = user_avatar;
            that.data.user_avatar = user_avatar;
            that.setData({
              user_info:user_info,
              user_avatar: that.data.user_avatar
            });
            util.api('/api/wxapp/account/updateUser', function (res) {
            }, {

                username: user_name,
                user_avatar: user_avatar
              });
          }
        })
      }
      that.setData({
        nickName: user_name,
      })
    }
  },
  getPhoneNumber: function (e) {
    var that = this;
    if (e.detail.errMsg == 'getPhoneNumber:ok') {
      var iv = e.detail.iv;
      var encryptedData = e.detail.encryptedData;
      util.checkSession(function () {
        that.parseMobile(iv, encryptedData);
      })
    }
  },
  // 解析手机号
  parseMobile: function (iv, data) {
    var that = this;
    util.api('/api/wxapp/wxdecrypt', function (res) {
      if (res.error == 0) {
        util.setCache("mobile", res.content.phoneNumber);
        mobile = res.content.phoneNumber;
        that.setData({
          mobile: mobile,
        })
      } else if (res.error == 41001) {
        util.wxLogin(function () {

        })
      } else {
        util.showModal('提示', '授权失败，请重试！', function () {
        }, false);
      }
    }, { iv: iv, crypt_data: data })
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
      wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
