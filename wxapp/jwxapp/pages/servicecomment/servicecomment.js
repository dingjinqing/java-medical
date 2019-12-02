// pages/servicecomment/servicecomment.js
var app = getApp()
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var Url = app.globalData.baseUrl;
var order_sn;
var store_id;
var src_down = imageUrl + 'image/wxapp/down_normal.png';
var src_up = imageUrl + 'image/wxapp/up_normal.png';
var serviceInfo = [];
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    orderSn: '',
    storeId: '',
    serviceInfo: {},
    imageUrl: app.globalData.imageUrl,
    img_len: 0,
    image: false,//是否显示晒单的图片
    islogin: true,
    info: {
      anonymousflag: 0,
      comm_img: [],
      commstar: 0,
      comm_note: '',

    },//提交的信息
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
    order_sn = options.order_sn;
    store_id = options.store_id;
    this.setData({
      orderSn: options.order_sn,
      storeId: options.store_id
    })
    this.get_comment();
  },

  //显示评论内容页面
  show_com_info: function (e) {
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
    var shop_id = e.currentTarget.dataset.shop_id;
    var osn = e.currentTarget.dataset.osn;
    var service_id = e.currentTarget.dataset.service_id;
    var store_id = e.currentTarget.dataset.store_id;
    var technician_id = e.currentTarget.dataset.technician_id;
    var star = this.data.star;
    var info = this.data.info;
    info.shop_id = shop_id;
    info.order_sn = osn;
    if (technician_id == null) {
      info.technician_id = 0;
    } else {
      info.technician_id = technician_id;
    }
    info.store_id = store_id;
    info.service_id = service_id;
    if (serviceInfo.show == true) {
      serviceInfo.show = false;
      serviceInfo.src = src_down;
    } else {
      serviceInfo.show = true;
      serviceInfo.src = src_up;
    }
    info.anonymousflag = 0;
    info.comm_img = [];
    info.comm_note = '';
    info.commstar = 5;
    star = [
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
    ];
    this.setData({
      serviceInfo: serviceInfo,
      info: info,
      star: star
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
    that.data.info.commstar = parseInt(id) + 1;
    this.setData({
      star: that.data.star,
      info: that.data.info
    })
  },
  //心得
  comm_note: function (e) {
    var info = this.data.info;
    info.comm_note = e.detail.value;
    this.setData({
      info: info
    })
  },
  //上传图片
  upImage: function (e) {
    var that = this;
    var info = that.data.info;
    var url = util.getUrl('/api/wxapp/image/upload');
    let count = 9 - info.comm_img.length;
    wx.chooseImage({
      count: count, // 默认9
      sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths
        if (res) {
          for (var i = 0; i < tempFilePaths.length; i++) {
            util.uploadFile(url, tempFilePaths[i], { img_cat_id: -1 }, function (e) {
              console.log(e)
              var data = JSON.parse(e.data);
              info.comm_img.push(data.content[0].img_url);
              var img_len = parseInt(info.comm_img.length);
              that.setData({
                info: info,
                image: true,
                img_len: img_len
              })
            });
          }
        }
      }
    })
  },
  delImage: function (e) {
    var that = this;
    var index = e.currentTarget.dataset.idx;
    var info = that.data.info;
    info.comm_img.splice(index, 1);
    var img_len = parseInt(info.comm_img.length);
    this.setData({
      info: info,
      img_len: img_len
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
    var that = this;
    var order_sn = this.data.order_sn;
    var info = this.data.info;
    var comm = {};
    var img = {};
    var id = 0;
    info.form_id = e.detail.formId;
    info.open_id = util.getCache("openid");
    if (parseInt(info.commstar) == parseInt(0)) {
      util.showModal('提示', '请选择评分', function (res) {
        return false;
      }, false);
      return false;
    }
    if (info.comm_note == '') {
      util.showModal('提示', '请填写心得', function (res) {
        return false;
      }, false);
      return false;
    }
    if (info.comm_img != '') {
      info.comm_img = JSON.stringify(info.comm_img);
    } else {
      info.comm_img = ''
    }
    util.api('/api/wxapp/service/comment/add', function (e) {
      this.get_comment();
    }, info);
  },
  //隐藏评价
  close_com_info: function (e) {
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
  get_comment () {
    let that = this
    util.api('/api/wxapp/store/service/reservationComment', function (res) {
      let serviceInfo = res.content;
      serviceInfo.service_img = JSON.parse(serviceInfo.service_img);
      serviceInfo.service_img = serviceInfo.service_img[0];
      serviceInfo.src = src_down;
      serviceInfo.show = false;
      if (serviceInfo.comment) {
        serviceInfo.comment.commstar = parseInt(serviceInfo.comment.commstar);
        if (parseInt(serviceInfo.comment.anonymousflag) == 0) {
          serviceInfo.comment.anonymousflag = false;
        } else {
          serviceInfo.comment.anonymousflag = true;
        }
        if (serviceInfo.comment.comm_img != '' || serviceInfo.comment.comm_img != null) {
          serviceInfo.comment.comm_img = JSON.parse(serviceInfo.comment.comm_img);
        }
      }
      that.setData({
        serviceInfo: serviceInfo
      })
    }, { orderSn: this.data.orderSn, storeId: this.data.storeId });
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