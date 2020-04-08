// pages/distribution/distribution.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    dis_info: [],
    distributor_name: '', // 分销员统称
    page_name: '', // 页面名称
    copy_content: '', // 邀请码
    have_account: 0, // 是否存在余额

    page_id: 0,
    is_bind_mobile: 0, // 绑定手机号
    is_block: 0, // 提现绑定手机号弹窗
    if_show_pic: 0, // 分享框
    if_show_pic_modal: 0, // 海报弹窗
    posterBase64: '', // 分享图片
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    dis_request(that);
  },
  // 去提现记录
  withdraw_record: function () {
    util.jumpLink('/pages/widthdrawrecord/widthdrawrecord');
  },
  // 去规则说明
  toRule: function () {
    // util.api('/api/wxapp/rebate/config', function (res) {
    //   util.jumpToWeb('/wxapp/distribution/help', res.content);
    // }, {});
    util.jumpToWeb('/wxapp/distribution/help');
  },
  // 待返利佣金说明
  a_tips: function () {
    util.showModal("说明", '已返利佣金自动提现到用户余额');
    return false;
  },
  // 去提现
  to_money: function () {
    var that = this;
    if (that.data.dis_info.fanli_cfg.withdraw_status == 0 || that.data.dis_info.fanli_cfg.withdraw_status == null) {
      util.showModal("提示", "系统暂时不支持提现");
      return false
    }
    if (that.data.dis_info.canWithdraw == 0) {
      util.showModal("提示", "暂无可提现余额");
      return false
    }
    if (that.data.is_bind_mobile == 1 && util.getCache('mobile') == '') {
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
      url: '/pages1/inviteduser/inviteduser?user_id=' + util.getCache('user_id'),
    })
  },
  // 分销-返利订单
  toOrder: function () {
    util.navigateTo({
      url: '/pages/distributionorder/distributionorder',
    })
  },
  // 去推广中心
  to_pro_center: function () {
    util.jumpLink('/pages1/promotioncenter/promotioncenter')
  },
  // 去推荐商品详情
  to_item: function (e) {
    let good_id = e.currentTarget.dataset.goods_id;
    util.navigateTo({
      url: "/pages/item/item?good_id=" + good_id
    })
  },
  // 查看更多商品
  to_search: function () {
    util.navigateTo({
      // url: "/pages/searchs/search?is_rebate=1"
    })
  },
  // 去返利排名列表页
  toRank: function () {
    util.navigateTo({
      url: '/pages2/brokeragerank/brokeragerank',
    })
  },
  // 去推广语页面
  toPromotion: function () {
    util.navigateTo({
      url: "/pages/distripromotion/distripromotion"
    })
  },
  // 打开分享框
  show_image: function () {
    this.setData({ if_show_pic: 1 })
  },
  // 关闭分享框
  close_modal: function () {
    this.setData({ if_show_pic: 0 })
  },
  // 下载海报
  show_haibao: function (e) {
    var that = this;
    wx.showLoading({
      title: '生成中',
    })
    var pictorial = that.data.dis_info.invite_image;
    if (pictorial) {
      // util.api('/api/wxapp/upayyun/image', function (res) {
      //   if (res.error == 0) {
      //     pictorial = that.data.imageUrl + pictorial + "!big";
      //     that.setData({
      //       posterBase64: res.content,
      //       // pictorial: posterBase64,
      //       pictorial: res.content,
      //     })
      //     wx.hideLoading();
      //     that.setData({
      //       if_show_pic: 0,
      //       if_show_pic_modal: 1
      //     })
      //   }
      // }, { image_path: pictorial });
    }
  },
  // 关闭海报
  go_no_share: function () {
    this.setData({ if_show_pic_modal: 0 })
  },
  // 保存图片
  saveImgToPhotosAlbumTap: function (e) {
    var that = this;
    if (that.data.posterBase64) {
      util.base64ImageHandle(that.data.posterBase64, function (res) {
        that.save_photo_tips(that);
      });
    } else {
      util.toast_fail('正在生成中...')
    }
  },
  save_photo_tips: function (that) {
    //复制
    var promotion_language = that.data.copy_content;
    if (promotion_language != '') {
      wx.setClipboardData({
        data: promotion_language,
        success: function (res) {
          wx.hideToast();
          that.setData({
            toastInfo: {
              icon: 'success',
              duration: 4000,
              title: '图片已保存到相册',
              content: promotion_language + '　以上邀请码已复制'
            },
            copyComplete: true
          })
        }
      });
    } else {
      that.setData({
        toastInfo: {
          icon: 'success',
          duration: 2000,
          title: '图片已保存到相册'
        },
        copyComplete: true,
      })
    }
    // util.showToast({ title: '图片已保存到相册', icon: 'success' })
    that.setData({
      if_show_pic_modal: 0
    })
  },
  // 去首页或申请
  bindRedirectTo: function (e) {
    util.redirectTo({
      url: e.currentTarget.dataset.page
    })
  },
  // 申请成为分销员
  apply_get: function (e) {
    util.navigateTo({
      url: "/pages/distributionspread/distributionspread",
    })
  },
  preview: function (e) {
    var nowImgUrl = e.currentTarget.dataset.src;
    var arr = [];
    arr[0] = nowImgUrl;
    wx.previewImage({
      current: nowImgUrl, // 当前显示图片的http链接
      urls: arr // 需要预览的图片http链接列表
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    return {
      path: 'pages/distributionspread/distributionspread?invite_id=' + util.getCache('user_id'),
      title: that.data.dis_info.userRebate.username + '邀请你免费赚钱啦，快来申请吧!',
      imageUrl: this.data.imageUrl + '/image/wxapp/share_dis.jpg',
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
      // dis_info.resentRebateList.forEach(item => {
      //   if (item.finishedTime) {
      //     item.finishedTime = item.finishedTime.substring(0, 10);
      //   }
      //   if (item.username.length > 4) {
      //     item.username = item.username.substring(0, 4) + "...";
      //   }
      //   item.fanliMoney = parseFloat(item.fanliMoney).toFixed(2);
      // })
      // 返利排名
      // dis_info.rebateTopThree.forEach(item => {
      //   item.finalMoney = parseFloat(item.finalMoney).toFixed(2);
      // })
      that.setData({
        dis_info: dis_info,
        rebate_center: 1,
        // page_id: page_id,
        // distributor_name: dis_info.fanli_cfg.distributor_name,
        // is_bind_mobile: dis_info.is_bind_mobile,
        copy_content: dis_info.invitationCode ? dis_info.invitationCode : ''
      })
    } 
    // else if (res.message == "您还不是分销员" && res.content.withdraw_money > 0) {
    //   var dis_info = res.content;
    //   dis_info.rebate_info.final_money = parseFloat(dis_info.rebate_info.final_money).toFixed(2);
    //   dis_info.rebate_info.wait_fanli_money = parseFloat(dis_info.rebate_info.wait_fanli_money).toFixed(2);
    //   that.setData({
    //     dis_info: dis_info,
    //     rebate_center: 2,
    //     have_account: 1,
    //     distributor_name: dis_info.fanli_cfg.distributor_name
    //   })
    // } else {
    //   that.setData({
    //     dis_info: res.content,
    //     rebate_center: 2,
    //     have_account: 0,
    //     none_message: res.message,
    //     none_jump_page: res.content.page,
    //     distributor_name: dis_info.fanli_cfg.distributor_name
    //   })
    // }
  });
}

