// pages/index/index.js
var util = require('../../utils/util.js');
var decorate = require('../common/decorate.js');
var getAwardInfo = require('../common/get_award_info')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
global.wxPage({
  mixins: [decorate],
  /**
   * 页面的初始数据
   */
  data: {
    collectContent: {},
    collect_gift: 0, // 是否显示收藏小程序按钮
    collect_coupon_display: 0, // 显示收藏送优惠券积分的弹窗
    collect_score: null,
    collect_coupons: null,
    page_id: null,
    noPatient: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad (options) {
    console.log(options, this.data);
    this._options = options;
    this._options.first_onload = 1;
    this.requestDecoratePageData(
      this._options.page,
      this._options.scene,
      this.renderData.bind(this),
      true
    );
    
    
    // 初始化收藏有礼
    this.renderCollectData()
    // 初始化开屏有礼clearInterval
    this.openGiftRequest()
    // 获取病历有弹窗
    this.getPatientNum()
    let flag = false
    let timer = setInterval(() => {
      if (flag) clearInterval(timer)
      if (this.data.pageContent && !flag) {
        flag = true
        var pageCfg = this.data.pageContent.page_info.page_cfg;
        var color = "#f5f5f5";
        if (pageCfg) {
          if (pageCfg.bg_types == 0) {
            color = "background:" + pageCfg.page_bg_color;
          } else if (pageCfg.bg_types == 1) {
            color = "background:url(" + pageCfg.page_bg_image + ") repeat;background-size:100% auto";
          }
        }
        console.log(color)
        this.setData({
          color: color
        })
      }
    }, 200)
  },

  //  渲染装修模块
  renderData (pageContent) {
    this.setData({
      page_id: pageContent.page_id,
      pageContent: pageContent,
      page_name: pageContent.page_name
    });
  },

  // 收藏有礼相关
  renderCollectData () {
    var _this = this
    util.api('/api/wxapp/collectGift/switch', function (res) {
      if (res.error == 0) {
        _this.setData({
          collectContent: res.content,
        })
        console.log(_this.data.collectContent)
        _this.collectCheck()
      }
    })
  },

  collectCheck () {
    var _this = this;
    var now = new Date();
    var collectInfo = _this.data.collectContent;
    var collect_start_time = collectInfo.start_time ? new Date(collectInfo.start_time.replace(/-/g, '/')).getTime() : 0;
    var collect_end_time = collectInfo.end_time ? new Date(collectInfo.end_time.replace(/-/g, '/')).getTime() : 0;
    var collect_gift = 0;
    var can_gift = util.getCache('can_gift');
    if (collectInfo.on_off == 1 && now > collect_start_time && now < collect_end_time) {
      collect_gift = 1;
      if (util.getCache('is_login') == 1) {
        var lastLoginTime = util.getCache('login_time');
        util.setCache('login_time', now);
        util.setCache('can_gift', 1);
        console.log(util.getCache('can_gift'))
      }
    } else {
      util.setCache('can_gift', 0);
    }

    _this.setData({
      collect_gift: collect_gift, // 是否显示收藏小程序按钮
    })

    var time2 = now - lastLoginTime;
    if (_this.data.pageContent.is_first_page == 1 && collectInfo.on_off == 1 && can_gift == 1 && util.getCache('is_login') == 1 && time2 < 60 * 1000) {
      util.api('/api/wxapp/collectGift/setRewards', function (res) {
        if (res.error == 0) {
          // if (res.content && res.content.length != 0) {
          //   res.content.send_coupons.forEach(function (el, index) {
          //     el.denomination = parseInt(el.denomination);
          //   })
          // }
          // 显示收藏送优惠券积分的弹窗
          _this.setData({
            collect_coupon_display: 1,
            collect_score: res.content.score,
            collect_coupons: res.content.couponDetail
          })
        }
      })
    }
    if (util.getCache('is_login') == 1) {
      util.setCache('is_login', 0);
    }
  },

  // 收藏提示弹窗
  bindShowCollectMp (e) {
    this.setData({
      show_collect_mp_tips: true
    });
  },

  // 奖励是否领取
  closeCollectMp () {
    this.setData({
      collect_gift: 0
    })
  },

  // 开屏有礼
  openGiftRequest () {
    util.api('/api/wxapp/enterpolitely/index', (res) => {
      if (res.error === 0 && res.content && res.content.awardType !== 0) {
        console.log(res, 'openGift')
        let { show } = res.content
        console.log(res.content, 'res.content')
        let awardInfo = getAwardInfo(res.content)
        console.log(awardInfo, '---')
        this.setData({
          openGiftDialog: true,
          awardInfo: {
            action: 'index',
            ...awardInfo
          }
        })
        console.log(this.data.awardInfo, 'this.data.awardInfo')
      } else {

      }
    }, {
      userId: util.getCache('user_id')
    })
  },
  getPatientNum() {
    util.api('/api/wxapp/user/patient/list', res => {
      if (res.error == 0) {
        if(res.content.length > 0){
          this.data.noPatient = 0
        }else{
          this.data.noPatient = 1
        }
        this.setData({
          noPatient: this.data.noPatient
        })
      }else{
        util.showModal('提示',res.message)
        return false
      }
    },{
      userId: util.getCache('user_id')
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () { },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () { },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.requestDecoratePageData(
      this._options.page,
      this._options.scene,
      this.renderData.bind(this),
      true
    );
    // 初始化收藏有礼
    this.renderCollectData()
    // 初始化开屏有礼clearInterval
    this.openGiftRequest()
    // 获取病历有弹窗
    this.getPatientNum()
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () { },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () { 
    let page_id = this.data.page_id || 0;
    let nickName = util.getCache('nickName');
    var share = {
      path: '/pages/index/index?inviteId=' + util.getCache('user_id') + (page_id > 0 ? "&page=" + page_id : ""),
      title: this.data.pageContent.share_info.share_title ? this.data.pageContent.share_info.share_title : nickName + '分享给你一个好物店铺，快来查看吧!'
    };
    return share
   }
});
