// pages/index/index.js
var util = require('../../utils/util.js');
var decorate = require('../common/decorate.js');
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
    collect_coupons: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad (options) {
    console.log(options);
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
    // 初始化开屏有礼
    this.openGiftRequest()
  },

  //  渲染装修模块
  renderData (pageContent) {
    console.log(pageContent);

    this.setData({
      pageContent: pageContent
    });
    // 初始化收藏有礼
    this.renderCollectData()
  },

  // 收藏有礼相关
  renderCollectData () {
    var _this = this
    util.api('/api/wxapp/collectGift/switch', function (res) {
      if (res.error == 0) {
        _this.setData({
          collectContent: res.content,
        })
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
        let awardInfo = this.getAwardInfo(res.content)
        console.log(awardInfo, '---')
        this.setData({
          openGiftDialog: true,
          awardInfo
        })
        console.log(this.data.awardInfo, 'this.data.awardInfo')
      } else {

      }
    }, {
      userId: util.getCache('user_id')
    })
  },
  getAwardInfo (awardInfo) {
    const needParams = {
      0: null,
      1: ['couponView', 'bg_img', 'title'],
      2: ['couponView', 'bg_img', 'title'],
      3: ['lotteryId'],
      4: ['account'],
      5: ['product'],
      6: ['scoreNumber'],
      7: ['customImage', 'customImage'],
    }
    let formatObj = {
      1: (() => {
        return {
          couponView: awardInfo.awardContent
        }
      })(),
      2: (() => {
        return {
          couponView: awardInfo.awardContent
        }
      })(),
      3: (() => {
        return {
          lotteryId: awardInfo.awardContent
        }
      })(),
      4: (() => {
        return {
          account: awardInfo.awardContent
        }
      })(),
      5: (() => {
        return {
          product: awardInfo.awardContent
        }
      })(),
      6: (() => {
        return {
          scoreNumber: awardInfo.awardContent
        }
      })(),
      7: (() => {
        return {
          customLink: awardInfo.awardContent,
          customImage: awardInfo.extContent && awardInfo.extContent.customize_img_path || null
        }
      })()
    }
    const Type = {
      1: 1,
      2: 3,
      3: 7,
      4: 6,
      5: 4,
      6: 2
    }
    console.log(Type[awardInfo.awardType])
    console.log(formatObj[Type[awardInfo.awardType]])
    return {
      giftInfo: {
        giftType: Type[awardInfo.awardType],
        awardInfo: {
          ...this.filterObj(formatObj[Type[awardInfo.awardType]], needParams[Type[awardInfo.awardType]])
        }
      }
    }
  },

  // 收藏有礼相关
  renderCollectData () {
    var _this = this
    util.api('/api/wxapp/collectGift/switch', function (res) {
      if (res.error == 0) {
        _this.setData({
          collectContent: res.content,
        })
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
        let awardInfo = this.getAwardInfo(res.content)
        console.log(awardInfo, '---')
        this.setData({
          openGiftDialog: true,
          awardInfo
        })
        console.log(this.data.awardInfo, 'this.data.awardInfo')
      } else {

      }
    }, {
      userId: util.getCache('user_id')
    })
  },
  getAwardInfo (awardInfo) {
    const needParams = {
      0: null,
      1: ['couponView'],
      2: ['couponView'],
      3: ['lotteryId'],
      4: ['account'],
      5: ['product'],
      6: ['scoreNumber'],
      7: ['customImage', 'customImage'],
    }
    let formatObj = {
      1: (() => {
        return {
          couponView: awardInfo.awardContent
        }
      })(),
      2: (() => {
        return {
          couponView: awardInfo.awardContent
        }
      })(),
      3: (() => {
        return {
          lotteryId: awardInfo.awardContent
        }
      })(),
      4: (() => {
        return {
          account: awardInfo.awardContent
        }
      })(),
      5: (() => {
        return {
          product: awardInfo.awardContent
        }
      })(),
      6: (() => {
        return {
          scoreNumber: awardInfo.awardContent
        }
      })(),
      7: (() => {
        return {
          customLink: awardInfo.awardContent,
          customImage: awardInfo.extContent && awardInfo.extContent.customize_img_path || null
        }
      })()
    }
    const Type = {
      1: 1,
      2: 3,
      3: 7,
      4: 6,
      5: 4,
      6: 2
    }
    console.log(Type[awardInfo.awardType])
    console.log(formatObj[Type[awardInfo.awardType]])
    return {
      giftInfo: {
        giftType: Type[awardInfo.awardType],
        awardInfo: {
          ...this.filterObj(formatObj[Type[awardInfo.awardType]], needParams[Type[awardInfo.awardType]])
        }
      }
    }
  },
  // 过滤需要的参数
  filterObj (obj, arr) {
    console.log(obj, 'obj--arr', arr, 'arr')

    if (typeof obj !== "object" || !Array.isArray(arr)) {
      throw new Error("参数格式不正确");
    }
    const result = {};
    Object.keys(obj)
      .filter(key => arr.includes(key))
      .forEach(key => {
        result[key] = obj[key];
      });
    return result;
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
  onPullDownRefresh: function () { },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () { },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () { }
});
