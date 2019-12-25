var cache = require('./cache.js');
var api = require('./api.js');

var user = {
  wxLogin (cb, options) {
    wx.login({
      success: function (res) {
        if (res.code) {
          var data = {
            code: res.code,
            path_query: wx.getLaunchOptionsSync(),
            system_verion: wx.getSystemInfoSync().system
          };
          console.log('登录', data)
          api.api("/api/wxapp/login", function (d) {
            console.log('返回信息', d)
            if (d.error == 0) {
              cache.setCache("openid", d.content.res.openid);
              cache.setCache("user_id", d.content.user_id);
              cache.setCache("mobile", d.content.res.mobile);
              cache.setCache("nickName", d.content.username);
              cache.setCache("avatarUrl", d.content.user_avatar);
              cache.setCache("shop_flag", d.content.shop_flag);
              cache.setCache("geographic_location", d.content.geographic_location);
              cache.setCache("imageHost",d.content.imageHost)
              if (cb) cb(d.content);
            }
          }, data);
        }
      }
    })
  },

  /**
   *  检查session_key是否失效，一般是获取用户信息、电话号码时，需要检查session_key，
   *  以便用session_key可以解密相应信息
   */
  checkSession (cb) {
    var _this = this;
    wx.checkSession({
      success: function (res) {
        cb(res);
      },
      fail: function (res) {
        console.log("checkSession fail:", res);
        _this.wxLogin(function (res) {
          cb(res);
        })
      }
    })
  },
  toSubscribeMessage (templateIds, module_name, cb) {
    var that = this;
    if (wx.requestSubscribeMessage) {
      wx.requestSubscribeMessage({
        tmplIds: templateIds,
        success (res) {
          console.log(res);
          that.api('/api/wxapp/common/subscribemessage', function (res) { }, { json_data: JSON.stringify(res), module_name: module_name })
        },
        fail (res) {
          console.log(res);
        },
        complete (res) {
          console.log(res);
          cb();
        }
      })
    } else {
      cb();
    }
  },
  getUserInfoCommon (e, cb) {
    if (e.detail.userInfo) {
      var user_avatar = e.detail.userInfo.avatarUrl;
      var user_name = e.detail.userInfo.nickName;
      cache.setCache("nickName", user_name);
      cache.setCache("avatarUrl", user_avatar);
      api.api('/api/wxapp/account/updateUser', function (res) {
      }, {
        username: user_name,
        user_avatar: user_avatar,
        encrypted_data: e.detail.encryptedData,
        iv: e.detail.iv
      });
      cb(e.detail.userInfo);
    } else {
      wx.getSetting({
        success (res) {
          if (res.authSetting['scope.userInfo']) {
            wx.getUserInfo({
              success: function (res) {
                var userInfo = res.userInfo;
                cache.setCache("nickName", userInfo.nickName);
                cache.setCache("avatarUrl", userInfo.avatarUrl);
                api.api('/api/wxapp/account/updateUser', function (res) {
                }, {
                  username: userInfo.nickName,
                  user_avatar: userInfo.avatarUrl,
                  encrypted_data: userInfo.encryptedData,
                  iv: userInfo.iv
                });
                cb(res.userInfo);
              },
              fail: function (res) {
                cb(false);
              }
            })
          } else {
            cb(false);
          }
        },
        fail (res) {
          cb(false);
        }
      })
    }
  }
}

module.exports = user;