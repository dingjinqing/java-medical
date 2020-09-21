// pages/distribution/distribution.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    hasBottom: false,
    dis_info: [],
    distributor_name: '', // 分销员统称
    page_name: '', // 页面名称
    copy_content: '', // 邀请码
    have_account: 0, // 是否存在余额

    page_id: 0,
    is_bind_mobile: 0, // 绑定手机号
    is_block: 0, // 提现绑定手机号弹窗
    posterBase64: '', // 分享图片

    shareGood: false, // 分享弹窗
    shareData: {} // 下载海报信息
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    wx.createSelectorQuery().select('.bottom').boundingClientRect().exec(function (reda) {
      that.setData({
        hasBottom: !reda || !reda[0] ? false : true
      })
    })
    dis_request(that);
  },
  // 去提现记录
  withdraw_record: function () {
    util.jumpLink('/pages/widthdrawrecord/widthdrawrecord');
  },
  // 去规则说明
  toRule: function () {
    util.jumpToWeb('/wxapp/wxapp/distribution/help');
  },
  // 待返利佣金说明
  a_tips: function () {
    util.showModal("说明", '已返利佣金自动提现到用户余额');
    return false;
  },
  // 去提现
  to_money: function () {
    var that = this;
    // 提现限制条件
    if (that.data.dis_info.withdrawStatus == 0 || that.data.dis_info.withdrawStatus == null) {
      util.showModal("提示", "系统暂时不支持提现");
      return false
    }
    if (that.data.dis_info.canWithdraw == 0) {
      util.showModal("提示", "暂无可提现余额");
      return false
    }
    if (util.getCache('mobile') == '') {
      util.checkSession(function () {
        that.setData({
          is_block: 1
        })
      })
      return false;
    }
    util.navigateTo({
      url: '/pages/widthdraw/widthdraw',
    })
  },
  // 去分销员等级
  to_grade: function () {
    util.navigateTo({
      url: '/pages/distrigrade/distrigrade',
    })
  },
  // 复制邀请码
  toCopy: function (e) {
    var content = e.currentTarget.dataset.content;
    wx.setClipboardData({
      data: content,
      success: function (res) {
        util.toast_success('复制成功');
      }
    });
  },
  // 分销-邀请用户
  toUser: function () {
    util.navigateTo({
      url: '/pages1/inviteduser/inviteduser',
    })
  },
  // 分销-返利订单
  toOrder: function () {
    util.navigateTo({
      url: '/pages1/distributionorder/distributionorder',
    })
  },
  // 去独立推广中心
  to_own_pro_center: function () {
    util.jumpLink('/pages1/promotioncenter/promotioncenter?type=1&userId=' + util.getCache('user_id'))
    util.setCache('distributionType', 1); // 独立商品页面类型
  },
  // 去推广中心
  to_pro_center: function () {
    util.jumpLink('/pages1/promotioncenter/promotioncenter')
    util.setCache('distributionType', 0); // 独立商品页面类型
  },
  // 去设置二维码
  to_qrCode: function () {
    util.jumpLink('/pages1/distributionCode/distributionCode')
  },
  // 去推荐商品详情
  to_item: function (e) {
    let goodId = e.currentTarget.dataset.goods_id;
    util.navigateTo({
      url: "/pages/item/item?gid=" + goodId
    })
  },
  // 查看更多商品
  to_search: function () {
    // util.jumpLink(`/pages/search/search${util.getUrlParams({
    //   pageFrom:20,
    //   outerPageParam:JSON.stringify({
    //   })
    // })}`);
  },
  // 去返利排名列表页
  toRank: function () {
    util.navigateTo({
      url: '/pages1/brokeragerank/brokeragerank',
    })
  },
  // 去推广语页面
  toPromotion: function () {
    util.navigateTo({
      url: "/pages1/distripromotion/distripromotion"
    })
  },
  // 打开分享框
  shareGoods: function () {
    this.setData({ shareGood: true })
  },
  // 保存图片回调函数
  handleDownloadCb () {
    // 复制推广语
    // let toast = this.selectComponent('#toast')
    // if(!this.data.currentData.promotionLanguage) {
    //   toast.showToast({
    //     title: '图片已保存到相册',
    //     duration:2000
    //   })
    // } else {
    //   wx.setClipboardData({
    //     data: this.data.currentData.promotionLanguage,
    //     success: (res) => {
    //       wx.hideToast();
    //       toast.showToast({
    //         title: '图片已保存到相册',
    //         content:`${this.data.currentData.promotionLanguage} 以上推广语已复制`,
    //         duration:4000
    //       })
    //     }
    //   });
    // }
  },
  // 去首页或申请
  bindRedirectTo: function (e) {
    var type = e.currentTarget.dataset.type;
    if (type == 0) {
      util.navigateTo({
        url: "/pages/index/index",
      })
    } else {
      util.navigateTo({
        url: "/pages/distributionspread/distributionspread",
      })
    }
  },
  // 申请成为分销员
  apply_get: function (e) {
    util.navigateTo({
      url: "/pages/distributionspread/distributionspread",
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    //用户分享行为数据收集埋点
    util.handleToBuryPoint(5)
    var that = this;
    return {
      path: 'pages/distributionspread/distributionspread?inviteId=' + util.getCache('user_id'),
      title: util.getCache('nickName') + '邀请你免费赚钱啦，快来申请吧!',
      imageUrl: that.data.imageUrl + '/image/wxapp/share_dis.jpg',
    }
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    var that = this;
    if (that.data.page_id > 0) {
      this.requestDecoratePageData(that.data.page_id, 0, this.processWindowData.bind(this));
    }
    wx.stopPullDownRefresh();
  },
  // 自定义模板加载
  processWindowData: function (pageContent) {
    this.setData({
      pageContent: pageContent
    })
  },
})
function dis_request(that) {
  util.api('/api/wxapp/distribution/rebateCenter', function (res) {
    if (res.error == 0) {
      var dis_info = res.content;
      // var page_id = dis_info.fanli_cfg.rebate_page_id;
      // if (page_id > 0) {
      //   that.requestDecoratePageData(page_id, 0, that.processWindowData.bind(that));
      // }
      
      // 轮播返利信息
      if (dis_info.rebateOrderList && dis_info.rebateOrderList.length > 0) {
        dis_info.rebateOrderList.forEach(item => {
          if (item.finishedTime) {
            item.finishedTime = item.finishedTime.substring(0, 10);
          }
          if (item.username && item.username.length > 4) {
            item.username = item.username.substring(0, 4) + "...";
          }
          item.fanliMoney = parseFloat(item.fanliMoney || 0).toFixed(2);
        })
      }
      // 返利排名
      if (dis_info.rebateRankingTop && dis_info.rebateRankingTop.length > 0) {
        dis_info.rebateRankingTop.forEach(item => {
          item.finalMoney = parseFloat(item.finalMoney || 0).toFixed(2);
        })
      }
      if (dis_info.userRebate && dis_info.userRebate.finalMoney) {
        dis_info.userRebate.finalMoney = parseFloat(dis_info.userRebate.finalMoney || 0).toFixed(2);
      }
      // 推荐商品
      if (dis_info.recommendGoods && dis_info.recommendGoods.length > 0) {
        dis_info.recommendGoodsNum = dis_info.recommendGoods.length
        // if (dis_info.recommendGoods.length > 10) {
        //   dis_info.recommendGoods = dis_info.recommendGoods.slice(0, 10)
        // }
        dis_info.recommendGoods.forEach(item => {
          item.highRebate = parseFloat(item.highRebate || 0).toFixed(2)
        })
      }
      // 金额
      dis_info.canWithdraw = parseFloat(dis_info.canWithdraw).toFixed(2);
      dis_info.totalWithdraw = parseFloat(dis_info.totalWithdraw).toFixed(2);
      dis_info.waitWithdraw = parseFloat(dis_info.waitWithdraw).toFixed(2);
      dis_info.totalCanFanliMoney = parseFloat(dis_info.totalCanFanliMoney).toFixed(2);
      if (dis_info.userRebate && dis_info.userRebate.finalMoney) {
        dis_info.userRebate.finalMoney = parseFloat(dis_info.userRebate.finalMoney || 0).toFixed(2);
      }
      that.setData({
        dis_info: dis_info,
        rebate_center: dis_info.isDistributor == 1 ? 1 : 2,
        have_account: dis_info.canWithdraw > 0 ? 1 : 0,
        // page_id: page_id,
        // distributor_name: dis_info.fanli_cfg.distributor_name,
        // is_bind_mobile: dis_info.is_bind_mobile,
        copy_content: dis_info.invitationCode ? dis_info.invitationCode : ''
      })
    } 
  });
}
