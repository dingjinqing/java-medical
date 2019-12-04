// pages/servicecomment/servicecomment.js
var app = getApp()
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var src_down = imageUrl + 'image/wxapp/down_normal.png';
var src_up = imageUrl + 'image/wxapp/up_normal.png';

global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    orderSn: '',
    storeId: '',
    serviceInfo: {},
    imageUrl: app.globalData.imageUrl,
    comm_flag: 0, // 是否展开
    star: [{
      show: true
    }, {
      show: true
    }, {
      show: true
    }, {
      show: true
    }, {
      show: true
    }]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    this.setData({
      orderSn: options.order_sn,
      storeId: options.store_id
    })
    this.get_comment();
  },

  // 初始化
  get_comment () {
    let that = this
    util.api('/api/wxapp/store/service/reservationComment', function (res) {
      if (res.error === 0) {
        let serviceInfo = res.content;
        serviceInfo.serviceImg = JSON.parse(serviceInfo.serviceImg)[0];
        // 开始不展示评价详情
        serviceInfo.src = src_down; // 展开图标
        serviceInfo.show = false; // 是否展示评价
        if (serviceInfo.flag) {
          serviceInfo.commstar = parseInt(serviceInfo.commstar);
          if (serviceInfo.commImg != '' && serviceInfo.commImg != null) {
            serviceInfo.commImg = JSON.parse(serviceInfo.commImg);
          }
        } else {
          serviceInfo.commstar = 5;
          serviceInfo.commImg = [];
        }
        if (!serviceInfo.storeId) {
          serviceInfo.storeId = that.data.storeId
        }
        if (!serviceInfo.userId) {
          serviceInfo.userId = util.getCache('user_id')
        }
        that.setData({
          serviceInfo: serviceInfo
        })
      } else {
        util.toast_fail(res.message)
        wx.navigateBack()
      }
    }, { orderSn: this.data.orderSn, storeId: this.data.storeId });
  },

  //显示提交评价页面
  com_show: function (e) {
    let serviceInfo = this.data.serviceInfo
    if (serviceInfo.show == true) {
      serviceInfo.show = false;
      serviceInfo.src = src_down;
    } else {
      serviceInfo.show = true;
      serviceInfo.src = src_up;
    }
    this.setData({
      serviceInfo: serviceInfo
    })
  },

  //选择评分
  choose_star: function (e) {
    var that = this;
    var id = e.currentTarget.dataset.id;
    if (this.data.serviceInfo.flag) return false;
    for (var i = 0; i < that.data.star.length; i++) {
      if (i <= id) {
        that.data.star[i].show = true;
      } else {
        that.data.star[i].show = false;
      }
    }
    that.data.serviceInfo.commstar = parseInt(id) + 1;
    this.setData({
      star: that.data.star,
      serviceInfo: that.data.serviceInfo
    })
  },

  // 输入心得
  comm_note: function (e) {
    console.log(e.detail.value)
    this.setData({
      'serviceInfo.commNote': e.detail.value
    })
  },

  //上传图片
  upImage: function () {
    var that = this;
    var serviceInfo = that.data.serviceInfo;
    var url = util.getUrl('/api/wxapp/image/upload');
    let count = 9 - serviceInfo.commImg.length;
    wx.chooseImage({
      count: count, // 默认9
      sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths
        if (res) {
          for (var i = 0; i < tempFilePaths.length; i++) {
            let img = tempFilePaths[i]
            wx.getImageInfo({
              src: img,
              success: function (obj) {
                let params = {
                  needImgWidth: obj.width,
                  needImgHeight: obj.height,
                  imgCatId: -1,
                  userId: util.getCache('user_id')
                }
                util.uploadFile(url, img, params, function (e) {
                  let data = JSON.parse(e.data)
                  if (data.error === 0) {
                    serviceInfo.commImg.push(data.content.imgPath);
                    that.setData({
                      serviceInfo: serviceInfo
                    })
                  }
                }, function (err) {
                  util.toast_fail('上传失败')
                  console.log(err)
                });
              }
            })
          }
        }
      }
    })
  },

  // 删除图片
  delImage: function (e) {
    var that = this;
    var index = e.currentTarget.dataset.idx;
    var serviceInfo = that.data.serviceInfo;
    serviceInfo.commImg.splice(index, 1);
    this.setData({
      serviceInfo: serviceInfo
    })
  },

  // 匿名评价
  anonymousChange: function (e) {
    console.log(e)
    let value = e.detail.value[0]
    if (value) {
      this.setData({
        'serviceInfo.anonymousflag': 1
      })
    } else {
      this.setData({
        'serviceInfo.anonymousflag': 0
      })
    }
  },

  //评价并继续
  good_commtag: function (e) {
    let that = this
    let serviceInfo = this.data.serviceInfo
    if (parseInt(serviceInfo.commstar) == parseInt(0)) {
      util.showModal('提示', '请选择评分');
      return false;
    }
    if (serviceInfo.commNote == '') {
      util.showModal('提示', '请填写心得');
      return false;
    }
    if (serviceInfo.commImg != '') {
      serviceInfo.commImg = JSON.stringify(serviceInfo.commImg);
    } else {
      serviceInfo.commImg = ''
    }
    let params = Object.assign({}, serviceInfo)
    util.api('/api/wxapp/store/service/createComment', function (e) {
      console.log(e)
      if (e.error === 0) {
        that.get_comment();
      } else {

      }
    }, params);
  },

  //隐藏评价
  close_com_info: function (e) {
    let serviceInfo = this.data.serviceInfo
    serviceInfo.show_info = false;
    this.setData({
      serviceInfo: serviceInfo,
    })
  },

  bindPreviewImage (e) {
    let src = e.currentTarget.dataset.src;
    let srcarr = e.currentTarget.dataset.srcarr;
    let arrs = [];
    for (let i in srcarr) {
      arrs.push(srcarr[i])
    }
    if (src) {
      wx.previewImage({
        current: src,
        urls: arrs
      })
    }
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.get_comment();
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

  }
})