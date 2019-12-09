// pages/userinfo/userinfo.js
var app = new getApp();
var util = require("../../utils/util.js");
var imageUrl = app.globalData.imageUrl;
var mobile;
var user_info = {
  username: "",
  user_avatar: ""
};
var dates;
var region = ["", "", ""];
var real_name = "";
var sex_index = 0;
var act = 0;
var user_block = 0;
var card_no;
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    dates: "选择您的生日",
    region: ["", "", ""],
    imageUrl: app.globalData.imageUrl,
    array: ["请选择", "男", "女"],
    sex_index: 0,
    act: 0,
    user_avatar: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    user_block = 0;
    sex_index = 0;
    this.setData({
      user_block: user_block
    });
    if (!util.check_setting(options)) return;
    wx.hideShareMenu();
    if (options.act) {
      act = options.act;
      card_no = options.card_no;
      this.setData({
        act: act
      });
    }
    mobile = util.getCache("mobile");
    if (util.getCache("avatarUrl") != "" && util.getCache("avatarUrl")) {
      this.data.user_avatar = util.getCache("avatarUrl");
    } else {
      this.data.user_avatar = imageUrl + "image/wxapp/custom_user_img.png";
    }

    user_info = {};
    region = ["", "", ""];
    dates = "选择您的生日";
    var that = this;
    wx.showLoading({
      title: "加载中"
    });
    setTimeout(function () {
      wx.hideLoading();
      user_block = 1;
      that.setData({
        user_block: user_block
      });
    }, 500);
    util.api(
      "/api/wxapp/account/setting",
      function (res) {
        if (res.error == 0) {
          user_info = res.content;
          // 头像
          user_info.user_avatar = res.content.userInfo.userAvatar;
          // 用户名
          user_info.username = res.content.userInfo.username;
          //性别
          if (user_info.userInfo.sex != null) {
            if (user_info.userInfo.sex == "f") {
              sex_index = 2;
            } else {
              sex_index = 1;
            }
          }
          //生日
          if (
            user_info.userInfo.birthdayDay != null &&
            user_info.userInfo.birthdayDay != 0
          ) {
            if (parseInt(user_info.userInfo.birthdayMonth) < 10) {
              user_info.userInfo.birthdayMonth =
                "0" + user_info.userInfo.birthdayMonth;
            }
            if (parseInt(user_info.userInfo.birthdayDay) < 10) {
              user_info.userInfo.birthdayDay =
                "0" + user_info.userInfo.birthdayDay;
            }
            dates =
              user_info.userInfo.birthdayYear +
              "-" +
              user_info.userInfo.birthdayMonth +
              "-" +
              user_info.userInfo.birthdayDay;
            that.setData({
              date: dates
            });
          }
          // 真实姓名
          if (user_info.userInfo.realName != null) {
            real_name = user_info.userInfo.realName;
          }
          //所在地
          if (user_info.cityCode != null) {
            region[0] = user_info.provinceCode;
            region[1] = user_info.cityCode;
            region[2] = user_info.districtCode;
          }
          that.setData({
            user_info: user_info,
            dates: dates,
            region: region,
            mobile: mobile,
            sex_index: sex_index,
            user_avatar: that.data.user_avatar
          });
        }
      },
      {}
    );
  },
  toSave: function (e) {
    var that = this;
    setTimeout(function () {
      that.bind_submit(e);
    }, 100);
  },
  bind_submit: function (e) {
    user_info = {};
    // user_info.is_setting = 1;
    user_info.isSetting = 1;
    // user_info.mobile = mobile;
    // user_info.user_id = util.getCache('user_id');
    // user_info.open_id = util.getCache("openid");
    // user_info.form_id = e.detail.formId;
    //生日
    var date_arr = dates.split("-");
    if (date_arr.length == 1) {
      user_info.birthdayYear = 0;
      user_info.birthdayMonth = 0;
      user_info.birthdayDay = 0;
    } else {
      user_info.birthdayYear = date_arr[0];
      user_info.birthdayMonth = date_arr[1];
      user_info.birthdayDay = date_arr[2];
    }
    console.log(user_info.birthdayMonth);
    console.log(user_info.birthdayDay);
    //名字
    if (user_info.realName == "null") {
      user_info.realName = "";
    }
    user_info.realName = real_name;
    //所在地
    user_info.provinceCode = region[0];
    user_info.cityCode = region[1];
    user_info.districtCode = region[2];
    //性别
    if (sex_index == 1) {
      user_info.sex = "m";
    } else if (sex_index == 2) {
      user_info.sex = "f";
    }
    //激活
    if (act == 1) {
      if (sex_index == 0) {
        util.toast_fail("请填写完整信息");
        return;
      }
      if (user_info.birthdayYear == "选择您的生日") {
        util.toast_fail("请填写完整信息");
        return;
      }
      if (user_info.realName == "") {
        util.toast_fail("请填写完整信息");
        return;
      }
      if (user_info.cityCode == "") {
        util.toast_fail("请填写完整信息");
        return;
      }
      user_info.cardNo = card_no;
      user_info.isSetting = 2;
    }
    util.api(
      "/api/wxapp/account/setting",
      function (res) {
        if (res.error == 0) {
          if (act == 1) {
            util.toast_success("激活成功", function () {
              setTimeout(function () {
                wx.navigateBack({
                  delta: 2
                });
              }, 2000);
            });
          } else {
            util.toast_success("保存成功", function () { });
          }
        } else {
          util.toast_fail(res.message);
        }
      },
      user_info
    );
  },
  bindDateChange: function (e) {
    dates = e.detail.value;
    this.setData({
      dates: e.detail.value
    });
  },
  bindRegionChange: function (e) {
    region = e.detail.value;
    this.setData({
      region: e.detail.value
    });
  },
  bindSexChange: function (e) {
    sex_index = e.detail.value;
    this.setData({
      sex_index: e.detail.value
    });
  },
  inputBlur: function (e) {
    if (/^1[3456789]\d{9}$/.test(e.detail.value)) {
      mobile = e.detail.value;
    } else {
      util.showModal("提示", "请输入正确的手机号！");
      return false;
    }
  },
  realName: function (e) {
    real_name = e.detail.value;
  },
  getUserInfo: function (e) {
    var that = this;
    var canIUse = wx.canIUse("button.open-type.getUserInfo");
    if (e.detail.userInfo) {
      if (canIUse) {
        console.log(e.detail.userInfo);
        var user_avatar = e.detail.userInfo.avatarUrl;
        var user_name = e.detail.userInfo.nickName;
        util.setCache("nickName", user_name);
        util.setCache("avatarUrl", user_avatar);
        user_info.username = user_name;
        user_info.user_avatar = user_avatar;
        that.data.user_avatar = user_avatar;
        util.toast_success("已更新");
        that.setData({
          user_info: user_info,
          user_avatar: that.data.user_avatar
        });
        util.api("/api/wxapp/account/updateUser", function (res) { }, {
          iv: e.detail.iv,
          encrypted_data: e.detail.encryptedData,
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
              user_info: user_info,
              user_avatar: that.data.user_avatar
            });
            util.api("/api/wxapp/account/updateUser", function (res) { }, {
              encrypted_data: e.detail.encryptedData,
              iv: e.detail.iv,
              username: user_name,
              user_avatar: user_avatar
            });
          }
        });
      }
      that.setData({
        nickName: user_name
      });
    }
  },
  getPhoneNumber: function (e) {
    var that = this;
    if (e.detail.errMsg == "getPhoneNumber:ok") {
      var iv = e.detail.iv;
      var encryptedData = e.detail.encryptedData;
      util.checkSession(function () {
        that.parseMobile(iv, encryptedData);
      });
    }
  },
  // 解析手机号
  parseMobile: function (iv, data) {
    var that = this;
    util.api(
      "/api/wxapp/wxdecrypt",
      function (res) {
        if (res.error == 0) {
          util.setCache("mobile", res.content.phoneNumber);
          mobile = res.content.phoneNumber;
          that.setData({
            mobile: mobile
          });
        } else if (res.error == 41001) {
          util.wxLogin(function () { });
        } else {
          util.showModal("提示", "授权失败，请重试！", function () { }, false);
        }
      },
      { iv: iv, crypt_data: data }
    );
  }
});
