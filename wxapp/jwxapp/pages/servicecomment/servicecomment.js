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
    islogin: true,
    flag: false,//是否匿名评价
    pass_commtag: [],//传递的标签
    star: [
      {
        show: true
      },
      {
        show: true
      },
      {
        show: true
      },
      {
        show: true
      },
      {
        show: true
      }
    ]
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
        that.setData({
          serviceInfo: serviceInfo
        })
      } else {
        util.toast_fail(res.message)
        wx.navigateBack()
      }
    }, { orderSn: this.data.orderSn });
  },

  //显示评论内容页面
  show_com_info: function (e) {
    let serviceInfo = this.data.serviceInfo
    if (serviceInfo.show_info == true) {
      serviceInfo.show_info = false;
      serviceInfo.src = src_down;
    } else {
      serviceInfo.show_info = true;
      serviceInfo.src = src_up;
    }
    this.setData({
      serviceInfo: serviceInfo,
    })
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
      info: that.data.info
    })
  },
  //心得
  comm_note: function (e) {
    var serviceInfo = this.data.serviceInfo;
    serviceInfo.commNote = e.detail.value;
    this.setData({
      serviceInfo: serviceInfo
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
  delImage: function (e) {
    var that = this;
    var index = e.currentTarget.dataset.idx;
    var serviceInfo = that.data.serviceInfo;
    serviceInfo.commImg.splice(index, 1);
    this.setData({
      serviceInfo: serviceInfo
    })
  },
  //匿名评价
  flag: function () {
    var info = this.data.info;
    if (this.data.flag == true) {
      info.anonymousflag = 0;
      this.setData({
        info: info,
        flag: false
      })
    } else {
      info.anonymousflag = 1;
      this.setData({
        info: info,
        flag: true
      })
    }
  },
  //评价并继续
  good_commtag: function (e) {
    let serviceInfo = this.data.serviceInfo
    var that = this;
    var order_sn = this.data.order_sn;
    var info = this.data.info;
    var comm = {};
    var img = {};
    var id = 0;
    info.form_id = e.detail.formId;
    info.open_id = util.getCache("openid");
    if (parseInt(serviceInfo.commstar) == parseInt(0)) {
      util.showModal('提示', '请选择评分', function (res) {
        return false;
      }, false);
      return false;
    }
    if (serviceInfo.commNote == '') {
      util.showModal('提示', '请填写心得', function (res) {
        return false;
      }, false);
      return false;
    }
    if (info.commImg != '') {
      info.commImg = JSON.stringify(info.commImg);
    } else {
      info.commImg = ''
    }
    util.api('/api/wxapp/service/comment/add', function (e) {
      this.get_comment();
    }, info);
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
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

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
    wx.stopPullDownRefresh();
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