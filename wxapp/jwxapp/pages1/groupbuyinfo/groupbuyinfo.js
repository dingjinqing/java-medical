// 多人拼团详情 pages/groupbuyinfo/groupbuyinfo.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    groupId: '',
    imageUrl: app.globalData.imageUrl,
    click_more: imageUrl + '/image/wxapp/backward_right.png',
    img_noperson: imageUrl + 'image/wxapp/icon_group2.png',
    showPoster: false,
    clock: '', // 活动倒计时

    groupbuyInfo: {
      is_delete: 0, // 是否已删除
      is_bind_mobile: 0, // 是否需要绑定手机号
      have_order_flag: 1, // activity_type = 2 && = 1判断是不是老用户
      canPinGroup: {
        status: 3,
        msg: '已经参与该团',
      },
      goodsInfo: {
        goodsId: 1, // 商品id
        goodsImg: 'http://mpdevimg2.weipubao.cn/upload/4748160/image/20191126/O1CN01FeIrjy25DqHsuOrSN_!!0-item_pic.jpg',
        goodsName: '商品名称',
        goodsNumber: 5,
        pinShopPrice: 0.00, // 带~和不带~ 拼团最低价  请求shareimg接口用 返回shareImg
        shopPrice: 200.00, // 带~和不带~ 拼团最高价 请求shareimg接口用 返回shareImg
        pinGoodsNum: 100, // 拼团的库存
        specs: { // 拼团规格
          spec_list: {
            2024: {
              index: 0,
              spec_id: 2024,
              spec_name: '规格1',
              spec_vals: {
                5189: '黑色'
              },
              val: [
                '黑色'
              ]
            },
            2025: {
              index: 1,
              spec_id: 2025,
              spec_name: '规格2',
              spec_vals: {
                5190: 'M',
                5191: 'L'
              },
              val: [
                'M',
                'L'
              ]
            }
          },
          prd_list: {
            '2024: 5189!!2025: 5190': {
              prd_id: 5792,
              goods_id: 1058,
              prd_market_price: 0.00,
              prd_sn: 'E8812KM50806001S/M',
              prd_codes: '',
              prd_specs: '2024: 5189!!2025: 5190',
              low_shop_price: 0.00,
              prd_img: 'http://mpdevimg2.weipubao.cn/upload/4748160/image/20191126/O1CN01FeIrjy25DqHsuOrSN_!!0-item_pic.jpg',
              prd_number: 17,
              prd_price: 0.00,
              stock: 17,
              prd_name: '黑色  M',
              spec_val_id: '5189: 5190'
            },
            '2024: 5189!!2025: 5191': {
              prd_id: 5793,
              goods_id: 1058,
              prd_market_price: 0.00,
              prd_sn: 'E8812KM50806001L / XL',
              prd_codes: '',
              prd_specs: '2024: 5189!!2025: 5191',
              low_shop_price: 0.00,
              prd_img: 'http://mpdevimg2.weipubao.cn/upload/4748160/image/20191126/O1CN01FeIrjy25DqHsuOrSN_!!0-item_pic.jpg',
              prd_number: 22,
              prd_price: 0.00,
              stock: 22,
              prd_name: '黑色  L',
              spec_val_id: '5189: 5191'
            }
          },
          spec_name: '规格1  规格2'
        }
      },
      pinInfo: {
        id: 1,
        limitAmount: 3,
        activityType: 1,
        limitNum: 2
      },
      pinUserList: [ // 最大为5个
        {
          id: 917,
          user_avatar: 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIbe6gDxSDZqhOAI9ibKbicUsN3bREjZ9cC8VlbNsjqMPtMfghXiasdsDzCJL0RbbLFWjTZWNVk1fZ4A/132'
        }
      ],
      remainingTime: { // 拼团倒计时
        hour: 23,
        minute: 56,
        second: 10
      }
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    let groupId = options.group_id
    this.setData({
      groupId: groupId
    })
    util.api('/api/wxapp/groupbuy/info', function (res) {
      if (res.error === 0) {
        // 倒计时
        var groupbuyInfo = res.content;
        if (groupbuyInfo.isDelete)
          var remainingTime = groupbuyInfo.remainingTime
        var totalSeconds = remainingTime.hour * 60 * 60 + remainingTime.minute * 60 + remainingTime.second
        that.countdown(totalSeconds)
        this.setData({
          groupbuyInfo: res.content
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

  // 时间格式化输出，如3:25:19 86。每10ms都会调用一次
  dateformat: function (micro_second) {
    // 秒数
    var second = Math.floor(micro_second);
    // 小时位
    var hr = Math.floor(second / 3600);
    // 分钟位
    var min = Math.floor((second - hr * 60 * 60) / 60);
    // 秒位
    var sec = second % 60;
    return hr + ':' + min + ":" + sec;
  },

  OneClickBuy (e) {
    let that = this
    // 将用户更新为老用户
    // 判断是否要去绑定手机号
    if (this.data.groupbuyInfo.is_bind_mobile && util.getCache('mobile') === '') {
      util.checkSession(function () {
        this.setData({
          is_block: 1
        })
      })
      return false;
    }
    // 获取规格
    if (!this.checkSelBuy()) return false;
    // 关闭规格弹窗
    this.bindCloseSpec();
    // 限制数量
    if (groupbuyInfo.goodsInfo.pinGoodsNum == 0) {
      util.showModal('提示', '该商品库存不足，无法购买', function () { }, false)
    } else {
      var choose_list = this.getChooseList();
      util.navigateTo({
        url: "/pages/goodsCheckout/goodsCheckout?order_type=pin_group&choose_list=" + JSON.stringify(choose_list) + "&group_id=" + group_id
      })
    }
  },

  getUserInfo: function (e) {
    var that = this;
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
        nickName: user_name,
      })
    }
    if (e.currentTarget.dataset.ct == 0) {
      that.OneClickBuy(e);
    }
  },

  // 拼团规则
  toRule: function () {
    util.jumpToWeb('/wxapp/group/help')
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
    return {
      title: groupbuyInfo.pinInfo.limitAmount + '人拼购仅需' + groupbuyInfo.goodsInfo.pinShopPrice + '元，' + groupbuyInfo.goodsInfo.goodsName,
      imageUrl: imageUrl + groupbuyInfo.share_img,
      path: '/pages/groupbuyinfo/groupbuyinfo?group_id=' + group_id + '&pin_group_id=' + groupbuyInfo.pinInfo.id + '&invite_id' + util.getCache('user_id')
    }
  }
})