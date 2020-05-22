var cache = require('./cache.js');
var helper = require('./helper.js')
var api = require('./api.js');

var user = {
  wxLogin (cb, options) {
    wx.login({
      success: function (res) {
        if (res.code) {
          let path_query = wx.getLaunchOptionsSync()
          if(path_query.query.scene) {
            path_query.query = {...path_query.query,...helper.resetScene(path_query.query.scene)}
            delete path_query.query.scene
          }
          var data = {
            code: res.code,
            path_query: path_query,
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
  getNeedTemplateId(typs,cb){
    this.api('/api/wxapp/subscribe/getNeedTemplateId',(res)=>{
      if(res.error === 0){
        this.toSubscribeMessage(res.content,cb)
      } else {
        cb && cb()
      }
    },{typs:typs})
  },
  toSubscribeMessage (content, cb) {
    var that = this;
    let templateIds = content.map(item=>item.templateId)
    if (wx.requestSubscribeMessage) {
      wx.requestSubscribeMessage({
        tmplIds: templateIds,
        success (res) {
          let params = {}
          Object.keys(res).forEach(item=>{
            if(item === 'errMsg') return
            if(!params[res[item]]){
              params[res[item]] = [content.find(templateItem=>{return templateItem.templateId === item})]
            } else {
              if(params[res[item]]) params[res[item]] =  [...params[res[item]],content.find(templateItem=>{return templateItem.templateId === item})]
            }
          })
          that.api('/api/wxapp/subscribe/updateTemplate', function (res) { }, { ...params })
        },
        fail (res) {
          console.log(res);
        },
        complete (res) {
          console.log(res);
          cb && cb();
        }
      })
    } else {
      cb && cb();
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