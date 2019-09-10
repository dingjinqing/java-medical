var util = require("../../utils/util.js")
var decorate = require("../common/decorate.js")
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var pictorial;
var posterBase64 = '';
var set_time_out;
var page_id = 0;
global.wxPage({
  mixins: [decorate],
  onLoad: function(options) {
    console.log("onLoad *** ");
    this._options = options;
    this._options.first_onload = 1;
    this.requestDecoratePageData(this._options.page, this._options.scene, this.renderData.bind(this), true);
    this.inviteProcess();
  },
  onShow: function() {
    if (this._options.first_onload == 1) {
      this._options.first_onload = 0;
    } else {
      if (this._options) this.requestDecoratePageData(this._options.page, this._options.scene, this.renderCollectData.bind(this)); // 收藏有礼请求
    }
  },
  inviteProcess() {
    if (this._options.invite_id) {
      this.setData({
        invite_id: this._options.invite_id
      });
    }
  },
  onPullDownRefresh: function() {
    wx.showNavigationBarLoading();
    if (this._options) this.requestDecoratePageData(this._options.page, this._options.scene, this.renderData.bind(this));
    wx.hideNavigationBarLoading();
    wx.stopPullDownRefresh();
  },

  onShareAppMessage: function() {
    var page_id = this._options.page || 0;
    var nickName = util.getCache('nickName');
    var share = {
      path: '/pages/index/index?invite_id=' + util.getCache('user_id') + (page_id > 0 ? "&page=" + page_id : ""),
      title: this.data.pageContent.share_info.share_title ? this.data.pageContent.share_info.share_title : nickName + '分享给你一个好物店铺，快来查看吧!'
    };
    if (this.data.pageContent.share_info.share_img) share.imageUrl = this.data.pageContent.share_info.share_img;
    return share;
  },
  _processSendCoupon(pageContent) {
    if (pageContent.send_coupon == 1) {
      if (pageContent.send_coupon.coupon_list) {
        for (var i in pageContent.send_coupon.coupon_list) {
          if (pageContent.send_coupon.coupon_list[i].denomination > 100) {
            pageContent.send_coupon.coupon_list[i].fontsize1 = '50';
            pageContent.send_coupon.coupon_list[i].fontsize2 = '30';
          }
        }
      }
    }
  },
  _getBaseCfg(pageContent) {
    var pageCfg = pageContent.page_info.page_cfg;
    var showBottomCfg = {};
    page_id = pageContent.page_id;

    // 如果不是首页，且页面堆栈为1，且未显示底部导航，则显示返回首页链接
    if (pageContent.page_type == 0 && getCurrentPages().length == 1 && !this.data.show_bottom) {
      showBottomCfg.show_back = true;
    }

    // 如果是配置显示底部导航，则显示底部导航 且 隐藏返回首页链接
    if (pageCfg && pageCfg.has_bottom == 1 && !this.data.show_bottom) {
      showBottomCfg.show_back = false;
      showBottomCfg.show_bottom = true;
    }

    if (pageContent.page_type == 1) {
      // 首页底部导航选中匹配,如果匹配则显示底部导航、隐藏返回首页链接
      var url = util.parseUrl(this.currentUrl);
      url.query.page = 0;
      var idx = this._bottomPathIndex(util.getPath(url.path, url.query));
      url.query.page = pageContent.page_id;
      var idx2 = this._bottomPathIndex(util.getPath(url.path, url.query));
      this.currentIdx = showBottomCfg.idx = idx != -1 ? idx : (idx2 != -1 ? idx2 : this.currentIdx);
      if (this.currentIdx != -1) {
        showBottomCfg.show_back = false;
        showBottomCfg.show_bottom = true;
      }
    }

    var color = "#f5f5f5";
    if (pageCfg) {
      if (pageCfg.bg_types == 0) {
        color = "background:" + pageCfg.page_bg_color;
      } else if (pageCfg.bg_types == 1) {
        color = "background:url(" + pageCfg.page_bg_image + ") repeat;background-size:100% auto";
      }
    }
     
    return {
      ...showBottomCfg,
      color: color,
      pictorialContent: pageCfg.pictorial,
      isPictorialShow: pageContent.is_pictorial_show
    }
  },
  renderData(pageContent) {
    this.setData({
      page_name: pageContent.page_name
    })
    this._processSendCoupon(pageContent);
    var baseCfg = this._getBaseCfg(pageContent);
    var show_custom = this.check_custom(pageContent);
    var data = {
      pageContent: pageContent,
      show_lottery: pageContent.is_lottery != 0 && pageContent.send_coupon.is_send_coupon == 2,
      show_act_coupon: pageContent.send_coupon.is_send_coupon == 1 && pageContent.is_display != 0,
      ...baseCfg,
      is_first_page: pageContent.is_first_page,
      ...show_custom
    };
    this.setData(data);
    this.renderCollectData(pageContent);
  },
  // 收藏有礼相关
  renderCollectData(pageContent) {
    var now = new Date();
    var collectInfo = pageContent.shop_collect;
    var collect_start_time = collectInfo.start_time ? new Date(collectInfo.start_time.replace(/-/g, '/')).getTime() : 0;
    var collect_end_time = collectInfo.end_time ? new Date(collectInfo.end_time.replace(/-/g, '/')).getTime() : 0;
    var collect_gift = 0;
    var can_gift = util.getCache('can_gift');
    if (pageContent.is_first_page == 1 && collectInfo.on_off == 1 && collectInfo.get_collect_gift == 0 &&
      now > collect_start_time && now < collect_end_time) {
      collect_gift = 1;
      if (util.getCache('is_login') == 1) {
        var lastLoginTime = util.getCache('login_time');
        util.setCache('login_time', now);
        util.setCache('can_gift', 1);
      }
    } else {
      util.setCache('can_gift', 0);
    }

    this.setData({
      collect_gift: collect_gift, // 是否显示收藏小程序按钮
    })

    var _this = this;
    var time2 = now - lastLoginTime;
    if (pageContent.is_first_page == 1 && can_gift == 1 && util.getCache('is_login') == 1 && time2 < 60 * 1000) {
      util.api('/api/wxapp/collect/gift', function(response) {
        if (response.error == 0) {
          if (response.content.send_coupons && response.content.send_coupons.length != 0) {
            response.content.send_coupons.forEach(function(el, index) {
              el.denomination = parseInt(el.denomination);
            })
          }
          // 显示收藏送优惠券积分的弹窗
          _this.setData({
            collect_coupon_display: 1,
            collect_score: response.content.score,
            collect_coupons: response.content.send_coupons
          })
        }
      })
    }
    if (util.getCache('is_login') == 1) {
      util.setCache('is_login', 0);
    }
  },
  bindShowCollectMp(e) {
    this.setData({
      show_collect_mp_tips: true
    });
  },
  closeCollectMp(){
    this.setData({
      collect_gift:0
    })
  },
  check_custom(pageContent){
    let can_show = false;
    let info = '';
    if (pageContent.send_coupon.is_send_coupon == 3){
      if(wx.getStorageSync('activity_id') != pageContent.send_coupon.activity_id){
        wx.setStorageSync('activity_id', pageContent.send_coupon.activity_id)
        can_show = true
        info = {
          link: pageContent.send_coupon.customize_url,
          img_src: pageContent.send_coupon.customize_img_path
        }
      }
    }
    return {
      show_act_custom: can_show,
      custom: {
        ...info
      }
    }
  },
  go_share: function () {
    var that = this;
    wx.showLoading({
      title: '生成中',
    })
    util.api('/api/wxapp/pictorial', function (res) {
      if (res.error == 0) {
        var pictorial = res.content.pictorial;
        if (pictorial) {
          util.api('/api/wxapp/upayyun/image', function (res) {
            if (res.error == 0) {
              pictorial = imageUrl + pictorial + "!big";
              posterBase64 = res.content;
              that.setData({
                pictorial: posterBase64,
              })

              wx.hideLoading();
              that.setData({
                is_share: 1
              })
            }
          }, { image_path: pictorial });          
        }
      } else {
        wx.hideLoading();
        util.toast_fail(res.message);
        return false;
      }
    }, { action: 17,identity_id: page_id })
  },

  saveImgToPhotosAlbumTap: function () {
    var that = this;
    var os_type = '';
    if (posterBase64) {
      util.base64ImageHandle(posterBase64, function (res) {
        wx.getSystemInfo({
          success: function (res) {
            os_type = res.platform
          }
        })
        if (os_type == 'ios') {
          util.toast_success('保存成功');
        } else {
          util.toast_success('图片已保存');
        }
        that.setData({
          is_share: 0
        })
      });
    } else {
      util.toast_fail('正在生成中...')
    }
    setTimeout(function () {
      clearTimeout(set_time_out);
      that.onPullDownRefresh();
    }, 200);
  },
  not_show_share: function () {
    var that = this;
    that.setData({
      is_share: 0
    })
  },
})