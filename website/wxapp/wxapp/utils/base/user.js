var cache = require('./cache.js');
var api = require('./api.js');

var user = {
  wxLogin(cb, options) {
    wx.login({
      success: function(res) {
        if (res.code) {
          var data = {
            code: res.code,
            path_query: wx.getLaunchOptionsSync(),
            system_verion: wx.getSystemInfoSync().system
          };
          api.api("/api/wxapp/login", function(d) {
            if (d.error == 0) {
              cache.setCache("openid", d.content.openid);
              cache.setCache("user_id", d.content.user_id);
              cache.setCache("mobile", d.content.mobile);
              cache.setCache("nickName", d.content.username);
              cache.setCache("avatarUrl", d.content.user_avatar);
              cache.setCache("shop_flag", d.content.shop_flag);
              cache.setCache("geographic_location", d.content.geographic_location);
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
  checkSession(cb) {
    var _this = this;
    wx.checkSession({
      success: function(res) {
        cb();
      },
      fail: function (res) {
        console.log("checkSession fail:",res);
        _this.wxLogin(function (res) {
          cb();
        })
      }
    })
  },
}

module.exports = user;