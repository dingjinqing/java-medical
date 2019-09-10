var app = new getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var pictorial = '';
var posterBase64 = '';
var is_preview = '';
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: imageUrl,
    drawStatus: 0,
    share: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let assess_id = options.assess_id;
    let record_id = options.record_id;
    let share = options.share ? options.share : 0;
    is_preview = options.is_preview == 1 ? 1 : 0;
    this.setData({
      is_preview: is_preview
    })
    if (is_preview == 1){
      wx.hideShareMenu();
    }
    var that = this;
    util.api('/api/wxapp/assess/result', function(res) {
      if (res.error == 0) {
        var giftUrl = '';
        if (res.content.reward_type == 1) {
          giftUrl = imageUrl + '/image/admin/assess_coupon.png'
        } else if (res.content.reward_type == 2) {
          giftUrl = res.content.goods_img
        } else if (res.content.reward_type == 3) {
          giftUrl = imageUrl + '/image/admin/assess_score.png'
        } else if (res.content.reward_type == 4) {
          giftUrl = imageUrl + '/image/admin/assess_account.png'
        } else if (res.content.reward_type == 5) {
          giftUrl = imageUrl + '/image/admin/assess_custon.png'
        }
        let gift_num = JSON.parse(res.content.reward_info).reward_num ? JSON.parse(res.content.reward_info).reward_num : 0
        that.setData({
          resultData: res.content,
          giftUrl: giftUrl,
          assess_id: assess_id,
          result_id: res.content.id,
          record_id: record_id,
          share: share,
          gift_num: gift_num
        })
      }
    }, {
      assess_id: assess_id,
      record_id: record_id,
      is_preview: is_preview
    })
  },
  getGift() {
    var that = this;
    if (is_preview == 1){
      util.showModal('提示', '预览不可领取');
      return false;
    }
    if (!this.data.drawStatus) {
      util.api('/api/wxapp/assess/getAward', function(res) {
        if (res.error == 0) {
          if (res.content.status == 0) {
            let gift_info = '';
            if (that.data.resultData.reward_type == 2) {
              gift_info = { ...res.content
              }
            } else {
              gift_info = res.content.desc
            }
            that.setData({
              is_award: 1,
              gift_info: gift_info,
              drawStatus: 1
            })
          } else {
            util.showModal('提示', '奖品已赠完');
          }
        }
      }, {
        assess_id: this.data.assess_id,
        result_id: this.data.result_id,
        record_id: this.data.record_id
      })
    } else {
      util.showModal('提示', '已领取过奖品');
    }
  },
  toCheckInfo() {
    if (is_preview == 1){
      util.showModal('提示', '预览不可领取');
      return false;
    }
    util.jumpLink('/pages2/assesscheck/assesscheck?condition=' + this.data.resultData.reward_limit_condition + '&assess_id=' + this.data.assess_id + '&result_id=' + this.data.result_id + '&record_id=' + this.data.record_id + '&reward_type=' + this.data.resultData.reward_type, 'redirectTo')
  },
  guandiao1() {
    this.setData({
      is_award: 0
    })
  },
  to_get_gift() {
    var params = {};
    params.goods_id = this.data.gift_info.goods_id;
    params.goods_price = 0;
    params.user_id = util.getCache('user_id');
    params.goods_number = 1;
    params.prd_sn = this.data.gift_info.prd_sn;
    params.prd_id = this.data.gift_info.prd_id;
    params.product_id = this.data.gift_info.prd_id;
    params.lc_id = this.data.gift_info.lc_id;
    var query_param = {};
    query_param.goods_id = this.data.gift_info.goods_id;
    console.log(JSON.stringify(params));
    util.navigateTo({
      url: '/pages/goodsCheckout/goodsCheckout?order_type=assess_present&choose_list=' + JSON.stringify(params) + '&query_param=' + JSON.stringify(query_param),
    })
  },
  giftInfo() {
    const url = new Map([
      [1, ['pages/couponlist/couponlist']],
      [2, ['pages/lotterygift/lotterygift']],
      [3, ['pages/integral/integral']],
      [4, ['pages/account/account']]
    ])
    let action = url.get(this.data.resultData.reward_type)
    util.jumpLink(action[0], 'navigateTo')
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {
    if (this.data.share == 0 && this.data.resultData.reward_type != 0 && this.data.resultData.reward_limit_type == 1) {
      setTimeout(()=>{
        this.getGift();
      },1500)
    }
    return {
      title: util.getCache('nickName') + this.data.resultData.share_title,
      path: '/pages2/assessstart/assessstart?assess_id=' + this.data.assess_id,
      imageUrl: this.data.resultData.cover_img
    }
  },
  go_share() {
    var that = this;
    wx.showLoading({
      title: '生成中',
    })
    util.api('/api/wxapp/pictorial', function(res) {
      if (res.error == 0) {
        console.log(res);
        pictorial = res.content.pictorial;
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
    }, {
      action: 16,
      identity_id: this.data.assess_id
    })
  },
  not_show_share() {
    var that = this;
    that.setData({
      is_share: 0
    })
  },
  saveImgToPhotosAlbumTap() {
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
  },
})