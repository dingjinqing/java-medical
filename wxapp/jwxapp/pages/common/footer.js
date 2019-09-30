let util = require("../../utils/util.js")
let config = require("../../utils/config.js")

var footer = {

  onLoad(options) {

  },
  
  onClickSupport: function (e) {
    var link = util.getUrl("") + "/index/free/experience?shop_id=" + config.shop_id +
      "&user_id=" + util.getCache('user_id');
    util.jumpLink('/pages/webview/webview?url=' + encodeURIComponent(link));
  },
  to_somwhere: function (e) {
    var link_path = e.currentTarget.dataset.link;
    if (link_path == "" || !link_path || link_path == undefined) {
      util.jumpLink('/pages/index/index', "reLaunch");
    } else {
      util.jumpLink(link_path, "redirectTo");
    }
  },
  onClickBottomNavigator: function (e) {
    var d = e.currentTarget.dataset;
    if (d.open_type) return false;
    return util.jumpLink(d.link, 'reLaunch');
  },

  onGetUserInfo: function (e) {
    var link = e.currentTarget.dataset.link;
    var idx = e.currentTarget.dataset.idx;
    util.setCache('idx', idx);
    if (e.detail.userInfo) {
      var user_avatar = e.detail.userInfo.avatarUrl;
      var user_name = e.detail.userInfo.nickName;
      util.setCache("nickName", user_name);
      util.setCache("avatarUrl", user_avatar);
      util.api('/api/wxapp/account/updateUser', function (res) { }, {
        username: user_name,
        user_avatar: user_avatar,
        encrypted_data: e.detail.encryptedData,
        iv: e.detail.ivx
      });
      return util.jumpLink(link, 'reLaunch');
    } else {
      return util.jumpLink(link, 'reLaunch');
    }
  },
}
module.exports = footer;