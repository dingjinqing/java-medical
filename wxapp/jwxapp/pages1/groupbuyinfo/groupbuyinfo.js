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
    click_more: imageUrl + 'image/wxapp/backward_right.png',
    img_noperson: imageUrl + 'image/wxapp/icon_group2.png',
    showPoster: false, // 下载海报弹窗
    posterImg: '', // 海报图片 base64字符串
    clock: '', // 活动倒计时
    groupbuyInfo: {
      groupBuyDefineInfo: {} // 拼团信息
    }, // 拼团信息
    showSpec: false, // 显示规格弹窗
    has_spec: false, // 是否有规格
    specInfo: {
      goodsNum: 0
    }, // 规格信息
    productsInfo: {
      products: [], // 商品规格
    }, // 规格组件需要数据
    islogin: false, // 是否已登录
    isBlock: 0, // 显示绑定手机号
    shareImg: '' // 分享的图片
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    console.log(options.invite_id)
    let that = this
    let groupId = options.group_id
    let pinGroupId = options.pin_group_id
    this.setData({
      groupId: Number(groupId),
      pinGroupId: pinGroupId
    })
    // 判断用户是否登录
    that.judgeLogin()

    that.getGroupBuyInfo()

    that.getShareImg()
  },

  getGroupBuyInfo () {
    let that = this
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
        let defaultPrd = groupbuyInfo.prdSpecsList && groupbuyInfo.prdSpecsList.length >= 0 ? false : true;
        groupbuyInfo.groupBuyDefineInfo.defaultPrd = defaultPrd
        // 规格选择组件需要的数据
        let productsInfo = {
          defaultPrd: defaultPrd, // 是否不是多规格,默认只有一个规格
          goodsImgs: [imageUrl + groupbuyInfo.goodsInfo.goodsImg],
          goodsName: groupbuyInfo.goodsInfo.goodsName,
          limitBuyNum: groupbuyInfo.groupBuyDefineInfo.limitBuyNum, // 最少购买
          limitMaxNum: groupbuyInfo.groupBuyDefineInfo.limitMaxNum, // 最多购买
          products: groupbuyInfo.prdSpecsList
        }
        that.data.specInfo.goodsNum = productsInfo.limitBuyNum

        that.setData({
          has_spec: !defaultPrd,
          groupbuyInfo: groupbuyInfo,
          productsInfo: productsInfo,
          specInfo: that.data.specInfo
        })
      } else {
        util.showModal(that.$t('page1.fight.prompt'), that.$t('page1.fight.hasExpired'), function () {
          util.reLaunch({
            url: '/pages/index/index'
          })
        });
        return;
      }
    }, { groupId: this.data.groupId })
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

  getShareImg () {
    let that = this
    util.api('/api/wxapp/groupbuy/share/image', function (res) {
      if (res.error === 0) {
        that.setData({
          shareImg: String(res.content)
        })
      }
    }, { groupId: that.data.groupId })
  },

  // 倒计时
  countdown (totalSeconds) {
    let that = this
    that.setData({
      clock: that.dateformat(totalSeconds)
    })
    if (totalSeconds <= 0) {
      that.setData({
        clock: that.$t('page1.fight.deadline')
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
    var activityType = this.data.groupbuyInfo.groupBuyDefineInfo.activityType // 新老用户
    let newUser = this.data.groupbuyInfo.newUser
    if (activityType == 2 && !newUser) {
      util.showModal(that.$t('page1.fight.prompt'), that.$t('page1.fight.oldHandNew'), function () {
        util.alert(that.$t('page1.fight.Iwant'))
      }, true, that.$t('page1.fight.cancel'), that.$t('page1.fight.psSpec'))
      return false;
    } else {
      if (this.data.has_spec) {
        this.setData({
          showSpec: true
        })
        this.initSpecInfo()
      } else {
        this.OneClickBuy(e);
      }
    }
  },

  initSpecInfo () {
    let specInfo = this.data.specInfo
    let groupbuyInfo = this.data.groupbuyInfo
    if (specInfo.goodsNum === undefined) {
      specInfo.goodsNum = groupbuyInfo.groupBuyDefineInfo.limitBuyNum
    }
    this.setData({
      specInfo: specInfo
    })
  },

  // 在一键开团时再校验规格
  checkSelBuy () {
    let that = this
    if (this.data.has_spec && Object.keys(this.data.specInfo).length == 0) {
      if (this.data.showSpec) util.alert(that.$t('page1.fight.psSpec'))
      this.setData({
        showSpec: true
      })
      return false;
    }
    if (this.data.specInfo.prdNumber <= 0) {
      util.alert(that.$t('page1.fight.inventoryShortage'))
      this.setData({
        showSpec: true
      })
      return false;
    }
    return true;
  },

  // 一键开团
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
      util.showModal(that.$t('page1.fight.prompt'), that.$t('page1.fight.hasExpired'), function () { }, false)
    } else {
      let goodsList = [{
        goodsId: groupbuyInfo.goodsInfo.goodsId,
        prdRealPrice: specInfo.prdId ? specInfo.prdRealPrice : groupbuyInfo.goodsInfo.minGroupBuyPrice,
        goodsPrice: specInfo.prdLinePrice ? specInfo.prdLinePrice : groupbuyInfo.goodsInfo.shopPrice,
        goodsNum: specInfo.goodsNum ? specInfo.goodsNum : 1,
        prdId: specInfo.prdId,
        productId: specInfo.prdId
      }]
      console.log(goodsList)
      util.navigateTo({
        url: "/pages/checkout/checkout?activityType=1&activityId=" + Number(this.data.groupbuyInfo.groupBuyDefineInfo.id) + "&groupid=" + Number(this.data.groupId) + "&goodsList=" + JSON.stringify(goodsList)
      })
    }
  },

  getUserInfo: function (e) {
    let that = this;
    if (util.getUserInfoCommon) {
      util.getUserInfoCommon(e, function (userInfo) {
        if (userInfo) {
          console.log(userInfo)
          that.setData({
            islogin: true
          })
        }
      });
    } else {
      var canIUse = wx.canIUse('button.open-type.getUserInfo');
      if (e.detail.userInfo) {
        if (canIUse) {
          var user_avatar = e.detail.userInfo.avatarUrl;
          var user_name = e.detail.userInfo.nickName;
          util.setCache("nickName", user_name);
          util.setCache("avatarUrl", user_avatar);
          util.api('/api/wxapp/account/updateUser', function (res) {
          }, {
            username: user_name,
            user_avatar: user_avatar
          });
        } else {
          wx.getUserInfo({
            success: res => {
              var user_avatar = e.detail.userInfo.avatarUrl;
              var user_name = e.detail.userInfo.nickName;
              util.setCache("nickName", user_name);
              util.setCache("avatarUrl", user_avatar);
              util.api('/api/wxapp/account/updateUser', function (res) {
              }, {
                username: user_name,
                user_avatar: user_avatar
              });
            }
          })
        }
        that.setData({
          islogin: true
        })
      }
    }
    if (e.currentTarget.dataset.ct == 0) {
      if (that.data.has_spec) {
        this.setData({
          showSpec: true
        })
      } else {
        that.OneClickBuy(e);
      }
    }
  },

  // 生成海报
  go_share () {
    let that = this
    wx.showLoading({
      title: that.$t('page1.fight.generating')
    })
    util.api('/api/wxapp/groupbuy/share/image', function (res) {
      if (res.error === 0 && res.content != '') {
        that.setData({
          posterImg: String(res.content),
          showPoster: true
        })
      } else {
        util.toast_fail(that.$t('page1.fight.failedToGetPoster'))
      }
      wx.hideLoading();
    }, { groupId: that.data.groupId })
  },

  // 拼团规则
  toRule: function () {
    util.jumpToWeb('/wxapp/group/help')
  },

  // 规格关闭
  specDialogClose () {
    this.setData({
      showSpec: false
    })
  },

  getProductData (data) {
    data = Object.assign({}, this.data.specInfo, data.detail)
    this.setData({
      specInfo: data
    })
  },

  // 验证输入数量
  specNumInputChange (e) {
    let value = Number(e.detail.value)
    let that = this
    if (isNaN(value)) {
      util.showModal(that.$t('page1.fight.prompt'), that.$t('page1.fight.enterNum'))
      that.setData({
        'specInfo.goodsNum': this.data.groupbuyInfo.groupBuyDefineInfo.limitBuyNum
      })
      return false
    }
    if (value > this.data.groupbuyInfo.groupBuyDefineInfo.limitBuyNum) {
      util.showModal(that.$t('page1.fight.prompt'), that.$t('page1.fight.cannotExceed'), function () { })
      that.setData({
        'specInfo.goodsNum': this.data.groupbuyInfo.groupBuyDefineInfo.limitBuyNum
      })
      return false
    }
    that.setData({
      'specInfo.goodsNum': value
    })
  },

  bindMinus () {
    let goodsNum = this.data.specInfo.goodsNum
    if (goodsNum <= this.data.groupbuyInfo.groupBuyDefineInfo.limitBuyNum) {
      return false;
    }
    goodsNum--
    this.setData({
      'specInfo.goodsNum': goodsNum
    })
  },
  bindPlus () {
    let goodsNum = this.data.specInfo.goodsNum
    if (goodsNum >= this.data.groupbuyInfo.groupBuyDefineInfo.limitMaxNum) {
      return false;
    }
    goodsNum++
    this.setData({
      'specInfo.goodsNum': goodsNum
    })
  },

  updateSpecGoodsNum () {

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
  onShareAppMessage: function (res) {
    let { groupbuyInfo, shareImg, groupId } = this.data
    let title = groupbuyInfo.groupBuyDefineInfo.limitAmount + this.$t('page1.fight.personToBuy') + groupbuyInfo.goodsInfo.minGroupBuyPrice + this.$t('page1.fight.yuan') + groupbuyInfo.goodsInfo.goodsName
    let path = '/pages/groupbuyinfo/groupbuyinfo?group_id=' + groupId + '&pin_group_id=' + groupbuyInfo.groupBuyDefineInfo.id + '&invite_id' + util.getCache('user_id')
    return {
      title: title,
      imageUrl: imageUrl + shareImg,
      path: path
    }
  }
})