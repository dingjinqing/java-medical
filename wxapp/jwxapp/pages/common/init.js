let util = require("../../utils/util.js")
let config = require("../../utils/config.js")
var init = {
  data: {
    status: 0,
  },
  onLoad(options) {
    console.log(options)
    this._options = options;
    this.currentUrl = util.getCurrentPath(options);
    this.user_id = util.getCache("user_id");
    this.bottom = this._converBottom(util.getCache("bottom"));
    this.loading = (!this.user_id || !this.bottom);
    this.setData({
      loading: this.loading
    });
    console.log("init onLoad path: ", this.currentUrl);
    console.log(this.loading, this.bottom.status)
    //暂时注释
    if (this.loading) {
      if (!this.isBottomPage()) {
        var url = "/pages/bottom/bottom?url=" + encodeURIComponent(this.currentUrl);
        util.jumpLink(url, "reLaunch");
      } else {
        this.loadSetting(options);
      }
      return false;
    }
    console.log(this.bottom.status, this.isBottomPage())
    if (this.bottom.status == 0 && this.isBottomPage()) {
      this._initRequest(options);
      this.bottomPageJump(options);
      // util.jumpLink('pages/index/index', "reLaunch");
      return false;
    }

    if (this.bottom.status > 0 && !this.isBottomPage()) {
      var url = "/pages/bottom/bottom?url=" + encodeURIComponent(this.currentUrl);
      util.jumpLink(url, "reLaunch");
      return false;
    }

    this._getUserGlobalConfig().then(() => {
      this.bottom = this._converBottom(util.getCache("bottom"));
      if (this.bottom.status > 0 && !this.isBottomPage()) {
        var url = "/pages/bottom/bottom?url=" + encodeURIComponent(this.currentUrl);
        util.jumpLink(url, "reLaunch");
      }
    });
    // 分享进入记录分享信息
    this._initRequest(options);
    this._initSetCommonData();
  },
  isBottomPage() {
    return util.equalUrl(this.currentUrl, "pages/bottom/bottom", false);
  },
  bottomPageJump(options) {
    var bottom = util.getCache("bottom");
    var url = 'pages/index/index';
    if (options.url) url = decodeURIComponent(options.url);
    console.log("bottom onload jumpLink: ", url);
    util.jumpLink(url, 'reLaunch');
  },
  loadSetting(options) {
    var _this = this;
    var stack = [this._wxLogin(), this._getUserGlobalConfig()];

    if (this.user_id) stack.splice(0, 1);
    Promise.all(stack).then(function (res) {
      console.log(res, options)
      _this.onLoad(options);
    });
    return false;
  },

  onShow() {
    if (this.loading) return false;
  },

  _initSetCommonData() {
    var c_url = util.parseUrl(this.currentUrl);
    var idx = this.currentIdx = this._bottomPathIndex(this.currentUrl);
    var show_bottom = idx != -1;
    var show_back = c_url.path != "/pages/index/index" && !show_bottom && getCurrentPages().length == 1;
    var margin_top_nav = 0
    if (typeof wx.getMenuButtonBoundingClientRect === 'function') {
      margin_top_nav = wx.getMenuButtonBoundingClientRect().bottom
    } else {
      wx.getSystemInfo({
        success: (res) => {
          margin_top_nav = res.statusBarHeight * 3
        }
      })
    }
    var data = {
      bottom: this.bottom,
      imageUrl: util.getCache("imageHost") || util.getImageUrl(),
      isIpx: util.isIPhoneX() ? 1 : 0,
      shop_flag: this.bottom.setting.shop_flag,
      hid_bottom: this.bottom.setting.hid_bottom,
      show_poster: this.bottom.show_poster,
      logo: this.bottom.logo ? this.bottom.logo : '',
      status: this.bottom.status,
      needGetUserInfo: this._needGetUserInfoBtn(),
      show_bottom: show_bottom,
      idx: idx,
      show_back: show_back,
      show_logo: this.bottom.show_logo,
      logo_link: this.bottom.logo_link ? this.bottom.logo_link : "",
      margin_top_nav: margin_top_nav
    }
    console.log(data)
    data = Object.assign(data, this._getColors());

    this.setData(data);
  },


  _initRequest(options) {
    if (this.user_id && options.invite_id) { //邀请人
      util.api("/api/wxapp/user/invite", function (res) { }, {
        invite_id: options.invite_id
      })
    }
    if (this.user_id && options.template_config_id) {
      util.api("/api/wxapp/user/visit", function (res) { }, {
        template_config_id: options.template_config_id
      })
    }
    if (this.user_id && options.channel) { //渠道分享
      util.api("/api/wxapp/channel/record", function (res) { }, {
        channel: options.channel
      })
    }
  },

  _needGetUserInfoBtn() {
    if (wx.canIUse("button.open-type.getUserInfo")) {
      var user_name = util.getCache('nickName');
      var user_avatar = util.getCache('avatarUrl');
      if (!user_name || user_name == '用户' + parseInt(util.getCache('user_id') + 10000) ||
        user_name == util.getCache('openid') || !user_avatar ||
        user_avatar.indexOf('image/admin/head_icon.png') > -1) {
        return true;
      }
    }
    return false;
  },

  _bottomPathIndex(currentUrl) {
    function addDefaultParam(url, path, key, value) {
      if (url.path == path) {
        url.query[key] = url.query[key] || value;
      }
      return url;
    }

    var c_url = util.parseUrl(currentUrl);
    var idx = -1;
    var matchNumber = -1;
    c_url = addDefaultParam(c_url, "/pages/index/index", 'page', 0);
    for (var i in this.bottom.img_list) {
      var url = util.parseUrl(this.bottom.img_list[i].page);
      url = addDefaultParam(url, "/pages/index/index", 'page', 0);
      if (c_url.path == url.path) {
        var mustKeys = (url.path == '/pages/index/index') ? ['page'] : [];
        var number = util.matchParams(c_url.query, url.query, mustKeys);
        if (number > matchNumber) {
          idx = i;
          matchNumber = number;
        }
      }
    }
    return idx;
  },

  _getColors() {
    console.log(this.bottom)
    if (!this.bottom) {
      this.bottom = this._converBottom(util.getCache("bottom"));
      if (!this.bottom) return {};
    }
    var mainColor = this.bottom.setting.shop_style[1];
    if (this.bottom.setting.shop_style == "" || !this.bottom.setting.shop_style) {
      this.bottom.setting.shop_style[0] = "#ff6666";
      this.bottom.setting.shop_style[1] = "#fee6e6";
    }
    util.setCache("main_colors", this.bottom.setting.shop_style[0]);
    util.setCache("help_colors", this.bottom.setting.shop_style[1]);
    var colors = {
      commonColor: this.bottom.setting.shop_style[0], //辅色,
      comColor: mainColor, // 主色
      somColor: util.colorRgb(mainColor, 0.2), //标签背景渐变色,
      linColor: util.colorRgb(mainColor, 0.8), //活动，下载海报按钮背景渐变色
      comColor1: util.colorRgb(mainColor, 1), //主色rgba
      borColor: util.colorRgb(mainColor, 0.4), //item优惠券边框颜色
      speColor: util.colorRgb(mainColor, 0.1), //预售背景色,
    }
    return colors;
  },

  _converBottom(bottom) {
    if (!bottom) return bottom;
    for (var i in bottom.img_list) {
      var item = bottom.img_list[i];
      item.open_type = "";
      if (item.btn == 1) {
        item.open_type = "contact";
      } else if (this._needGetUserInfoBtn() && bottom.show_poster.flag != 1) {
        item.open_type = "getUserInfo";
      }
    }
    console.log(bottom);
    return bottom;
  },

  _wxLogin(options) {
    return new Promise(function (resolve) {
      util.wxLogin(function () {
        resolve()
      }, options);
    });
  },
  _getUserGlobalConfig() {
    return new Promise(function (resolve) {
      if (util.getCache('geographic_location') == 1) {
        util.getUserLocation(function (loc) {
          util.api("/api/wxapp/cfg/bottom", function (d) {
            util.setCache('bottom', d.content);
            resolve(d.content);
          }, {
            lat: loc ? parseFloat(loc.latitude).toFixed(5) : null,
            lng: loc ? parseFloat(loc.longitude).toFixed(5) : null
          }, false, false)
        });
      } else {
        util.api("/api/wxapp/cfg/bottom", function (d) {
          util.setCache('bottom', d.content);
          resolve(d.content);
        }, { lat: null, lng: null }, false, false)
      }
    });
  }
}

module.exports = init;