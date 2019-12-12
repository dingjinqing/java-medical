// 多人拼团详情 pages/groupbuyinfo/groupbuyinfo.js
let util = require('../../utils/util.js')
let app = getApp()
let imageUrl = app.globalData.imageUrl

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    groupId: '',
    pinGroupId: '',
    imageUrl: app.globalData.imageUrl,
    click_more: imageUrl + '/image/wxapp/backward_right.png',
    img_noperson: imageUrl + 'image/wxapp/icon_group2.png',
    showPoster: false, // 下载海报弹窗
    posterImg: '', // 海报图片 base64字符串
    clock: '', // 活动倒计时
    groupbuyInfo: {
      groupBuyDefineInfo: {} // 拼团信息
    }, // 拼团信息
    showSpec: false, // 显示规格弹窗
    specInfo: {}, // 规格信息
    productsInfo: {
      products: [], // 商品规格
    }, // 规格组件需要数据
    islogin: false, // 是否已登录
    isBlock: 0 // 显示绑定手机号
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    let that = this
    let groupId = options.group_id
    let pinGroupId = options.pin_group_id
    this.setData({
      groupId: groupId,
      pinGroupId: pinGroupId
    })
    // 判断用户是否登录
    that.judgeLogin()
    // 获取拼团信息
    util.api('/api/wxapp/groupbuy/info', function (res) {
      if (res.error === 0) {
        let groupbuyInfo = Object.assign({}, res.content);

        // 显示缺少的人数
        if (groupbuyInfo.userInfoList.length > 5) {
          groupbuyInfo.userInfoList = groupbuyInfo.userInfoList.slice(0, 5);
        }
        groupbuyInfo.groupBuyDefineInfo.show_noperson = groupbuyInfo.groupBuyDefineInfo.limitAmount
        if (groupbuyInfo.groupBuyDefineInfo && Object.keys(groupbuyInfo.groupBuyDefineInfo).length > 0) {
          if (groupbuyInfo.groupBuyDefineInfo.limitAmount > 5) {
            groupbuyInfo.groupBuyDefineInfo.show_noperson = 5 - groupbuyInfo.userInfoList.length;
          } else {
            groupbuyInfo.groupBuyDefineInfo.show_noperson = groupbuyInfo.groupBuyDefineInfo.limitAmount - groupbuyInfo.userInfoList.length;
          }
        }

        // 倒计时
        let totalSeconds = groupbuyInfo.hour * 60 * 60 + groupbuyInfo.minute * 60 + groupbuyInfo.second
        that.countdown(totalSeconds)

        // 规格弹窗信息
        let defaultPrd = groupbuyInfo.prdSpecsList.length <= 1 ? true : false;
        groupbuyInfo.groupBuyDefineInfo.defaultPrd = defaultPrd
        let productsInfo = {
          defaultPrd: defaultPrd, // 是否不是多规格,默认只有一个规格
          goodsImgs: [imageUrl + groupbuyInfo.goodsInfo.goodsImg],
          goodsName: groupbuyInfo.goodsInfo.goodsName,
          limitBuyNum: groupbuyInfo.groupBuyDefineInfo.limitBuyNum, // 最少购买
          limitMaxNum: groupbuyInfo.groupBuyDefineInfo.limitMaxNum, // 最多购买
          products: groupbuyInfo.prdSpecsList
        }

        that.setData({
          groupbuyInfo: groupbuyInfo,
          productsInfo: productsInfo
        })
      } else {
        util.showModal('提示', '该活动已失效', function () {
          util.reLaunch({
            url: '/pages/index/index'
          })
        });
        return;
      }
    }, { groupId: 4 })
  },

  // 判断是否登录
  judgeLogin () {
    let user_name = util.getCache('nickName');
    let user_avatar = util.getCache('avatarUrl');
    if (!user_name || user_name == '用户' + parseInt(util.getCache('user_id') + 10000)
      || user_name == util.getCache('openid') || !user_avatar
      || user_avatar.indexOf('image/admin/head_icon.png') > -1) {
      this.setData({
        islogin: false,
      })
    } else {
      this.setData({
        islogin: true,
      })
    }
  },

  // 倒计时
  countdown (totalSeconds) {
    let that = this
    that.setData({
      clock: that.dateformat(totalSeconds)
    })
    if (totalSeconds <= 0) {
      that.setData({
        clock: '已经截止'
      })
      if (timer) {
        clearTimeout(timer)
      }
      return false;
    }
    let timer = setTimeout(function () {
      totalSeconds -= 1
      that.countdown(totalSeconds)
    }, 1000)
  },

  // 时间格式化，如3:25:19 86
  dateformat: function (micro_second) {
    let second = Math.floor(micro_second);
    let hr = Math.floor(second / 3600);
    let min = Math.floor((second - hr * 60 * 60) / 60);
    let sec = second % 60;
    return hr + ':' + min + ":" + sec;
  },

  // 去参团
  toJoin (e) {
    let that = this
    var activityType = this.data.groupbuyInfo.groupBuyDefineInfo.activityType
    let newUser = this.data.groupbuyInfo.newUser
    if (activityType == 2 && !newUser) {
      util.showModal('提示', '您是老用户啦,“老带新团”只有新用户可以参团哦！可以去开个新团享受更多优惠。', function () {
        util.alert('我要开团')
      }, true, '取消', '我要开团')
    } else {
      this.OneClickBuy(e);
    }
  },

  checkSelBuy () {
    if (!this.data.groupbuyInfo.groupBuyDefineInfo.defaultPrd) {
      if (this.data.showSpec) util.alert("请选择规格！")
      this.setData({
        showSpec: true
      })
      return false;
    }
    if (this.data.specInfo.prdNumber <= 0) {
      util.alert('库存不足')
      this.setData({
        showSpec: true
      })
      return false;
    }
    return true;
  },

  OneClickBuy (e) {
    let that = this
    let groupbuyInfo = that.data.groupbuyInfo
    let specInfo = that.data.specInfo
    // 将用户更新为老用户
    // 判断是否要去绑定手机号
    if (this.data.groupbuyInfo.bindMobile && util.getCache('mobile') === '') {
      util.checkSession(function () {
        this.setData({
          isBlock: 1
        })
      })
      return false;
    }
    // 获取规格
    if (!this.checkSelBuy()) return false;
    // 限制数量
    if (groupbuyInfo.goodsInfo.groupBuygoodsNum <= 0) {
      util.showModal('提示', '该商品库存不足，无法购买', function () { }, false)
    } else {
      let goodsList = [{
        goodsId: groupbuyInfo.goodsInfo.goodsId,
        group_id: this.data.groupId,
        pin_group_id: this.data.pinGroupId,
        prdRealPrice: specInfo.prdId ? specInfo.prdRealPrice : groupbuyInfo.goodsInfo.minGroupBuyPrice,
        goodsPrice: specInfo.prdLinePrice ? specInfo.prdLinePrice : groupbuyInfo.goodsInfo.shopPrice,
        goodsNum: specInfo.goodsNum ? specInfo.goodsNum : 1,
        prdId: specInfo.prdId ? specInfo.prdId : groupbuyInfo.goodsInfo.goodsId,
        productId: specInfo.prdId ? specInfo.prdId : groupbuyInfo.goodsInfo.goodsId
      }]
      util.navigateTo({
        url: "/pages/checkout/checkout?order_type=pin_group&goodsList=" + JSON.stringify(goodsList) + "&group_id=" + this.data.groupId
      })
    }
  },

  getUserInfo: function (e) {
    let that = this;
    util.getUserInfoCommon(e, function (userInfo) {
      if (userInfo) {
        console.log(userInfo)
        that.setData({
          islogin: true
        })
      }
    });
    if (e.currentTarget.dataset.ct == 0) {
      that.OneClickBuy(e);
    }
  },

  // 生成海报
  go_share () {
    let that = this
    wx.showLoading({
      title: '生成中'
    })
    this.setData({
      showPoster: true
    })
  },

  // 拼团规则
  toRule: function () {
    util.jumpToWeb('/wxapp/group/help')
  },

  specDialogClose () {
    this.setData({
      showSpec: false
    })
  },

  getProductData (data) {
    console.log(data)
    this.setData({
      specInfo: data
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    let that = this
    let obj = wx.getLaunchOptionsSync()
    console.log('场景值：', obj)
    if (obj.scene === 1036) {
      console.log('从分享进入')
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    let groupbuyInfo = this.data.groupbuyInfo
    return {
      title: groupbuyInfo.groupBuyDefineInfo.limitAmount + '人拼购仅需' + groupbuyInfo.goodsInfo.minGroupBuyPrice + '元，' + groupbuyInfo.goodsInfo.goodsName,
      imageUrl: imageUrl + groupbuyInfo.share_img,
      path: '/pages/groupbuyinfo/groupbuyinfo?group_id=' + group_id + '&pin_group_id=' + groupbuyInfo.groupBuyDefineInfo.id + '&invite_id' + util.getCache('user_id')
    }
  }
})