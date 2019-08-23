import { login } from "../api/basic.js"

export var user = {
  wxLogin(cb, options) {
    wx.login({
      success: function (res) {
        if (res.code) {
          var data = {
            code: res.code,
            path_query: wx.getLaunchOptionsSync(),
            system_verion: wx.getSystemInfoSync().system
          };
          login(data).then(function (d) {
            if (cb) cb(d.content);
          })
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
      success: function (res) {
        cb();
      },
      fail: function (res) {
        console.log("checkSession fail:", res);
        _this.wxLogin(function (res) {
          cb();
        })
      }
    })
  },
}

