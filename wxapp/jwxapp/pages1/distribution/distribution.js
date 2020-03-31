// pages/distribution/distribution.js
var util = require('../../utils/util.js')
var app = getApp()
var dis_info = [];
// var decorate = require("../../pages/common/decorate.js")
// var spec_mixin = require("../../pages/goodscommon/spec.js");
global.wxPage({
  // mixins: [decorate, spec_mixin],
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    dis_info: [],
    img_save_url: '',
    is_block: 0, // 绑定手机号弹窗
    this_dis_name: '',
    is_bind_mobile: 0, // 绑定手机号
    posterBase64: '', // 分享图片

    is_second: 0,
    page_id: 0,
    if_show_pic: 0, // 分享框
    if_show_pic_modal: 0, // 海报弹窗
    have_account: 0,
    // 邀请码
    copy_content: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    that.setData({
      this_dis_name: options.names,
    })
    dis_request(that);
  },
  // 去提现记录
  withdraw_record: function () {
    util.jumpLink('/pages/widthdrawrecord/widthdrawrecord');
  },
  // 去规则说明
  toRule: function () {
    // util.api('/api/wxapp/rebate/config', function (res) {
    //   var rule_info = res.content;
    //   util.jumpToWeb('/wxapp/distribution/help', rule_info);
    // }, {});
  },
  // 待返利佣金说明
  a_tips: function () {
    util.showModal("说明", '已返利佣金自动提现到用户余额');
    return false;
  },
  // 去提现
  to_money: function () {
    var that = this;
    if (dis_info.fanli_cfg.withdraw_status == 0 || dis_info.fanli_cfg.withdraw_status == null) {
      util.showModal("提示", "系统暂时不支持提现");
      return false
    }
    if (dis_info.canWithdraw == 0) {
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
      url: "/pages/searchs/search?is_rebate=1"
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
    var pictorial = dis_info.invite_image;
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
      title: dis_info.user_rebate.username + '邀请你免费赚钱啦，快来申请吧!',
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
      // // that.page_id = page_id;
      // if (page_id > 0) {
      //   that.requestDecoratePageData(page_id, 0, that.processWindowData.bind(that));
      // }
      that.setData({
        dis_info: dis_info,
        // page_id: page_id,
        rebate_center: 1,
        // page_name: that.data.this_dis_name,
        // is_block: that.data.is_block,
        // distributor_name: dis_info.fanli_cfg.distributor_name
      })
      // var marqueen_tex = [];
      // // img_save_url = that.data.imageUrl + dis_info.invite_image;
      // that.setData({
      //   img_save_url: that.data.imageUrl + dis_info.invite_image
      // })
      // for (var i = 0; i < dis_info.resent_rebate_list.length; i++) {
      //   if (dis_info.resent_rebate_list[i].finished_time != null) {
      //     dis_info.resent_rebate_list[i].finished_time = dis_info.resent_rebate_list[i].finished_time.substring(0, 10);
      //   }
      //   if (dis_info.resent_rebate_list[i].username.length > 4) {
      //     dis_info.resent_rebate_list[i].username = dis_info.resent_rebate_list[i].username.substring(0, 4) + "...";
      //   }
      //   dis_info.resent_rebate_list[i].fanli_money = parseFloat(dis_info.resent_rebate_list[i].fanli_money).toFixed(2);
      // }
      // dis_info.rebate_info.final_money = parseFloat(dis_info.rebate_info.final_money).toFixed(2);
      // dis_info.rebate_info.wait_fanli_money = parseFloat(dis_info.rebate_info.wait_fanli_money).toFixed(2);
      // for (var i in dis_info.rebate_top_three) {
      //   dis_info.rebate_top_three[i].final_money = parseFloat(dis_info.rebate_top_three[i].final_money).toFixed(2);
      // }
      // that.setData({
      //   is_bind_mobile: dis_info.is_bind_mobile
      // })
      // if (dis_info.invitation_code && dis_info.invitation_code != "" && dis_info.invitation_code != 0) {
      //   that.data.copy_content = dis_info.invitation_code
      // } else {
      //   that.data.copy_content = ''
      // }

      // that.setData({
      //   dis_info: dis_info
      // })
    } 
    // else if (res.message == "您还不是分销员" && res.content.withdraw_money > 0) {
    //   dis_info = res.content;
    //   dis_info.rebate_info.final_money = parseFloat(dis_info.rebate_info.final_money).toFixed(2);
    //   dis_info.rebate_info.wait_fanli_money = parseFloat(dis_info.rebate_info.wait_fanli_money).toFixed(2);
    //   that.setData({
    //     dis_info: dis_info,
    //     rebate_center: 2,
    //     have_account: 1,
    //     distributor_name: dis_info.fanli_cfg.distributor_name
    //   })
    // } else {
    //   dis_info = res.content;
    //   that.setData({
    //     rebate_center: 2,
    //     have_account: 0,
    //     none_message: res.message,
    //     none_jump_page: res.content.page,
    //     distributor_name: dis_info.fanli_cfg.distributor_name
    //   })
    // }
  });
}

