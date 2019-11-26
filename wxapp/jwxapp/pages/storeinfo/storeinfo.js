// pages/storeInfo/storeinfo.js
var app = new getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var lat;
var lon;
var info;
var all_serv = {};
var id;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    currentTabsIndex: 0,
    mobile: '',
    dis: 0,
    content: '' // 门店介绍
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    id = options.id;
  },

  preview: function (e) {
    var nowImgUrl = e.target.dataset.src;
    var arr = [];
    for (var i in info.storeImgs) {
      arr.push(info.storeImgs[i]); //属性
    }
    wx.previewImage({
      current: nowImgUrl, // 当前显示图片的http链接
      urls: arr // 需要预览的图片http链接列表
    })
  },
  // 点击选项卡事件
  onTabsItemTap: function (e) {
    var that = this;
    var index = e.target.dataset.index;
    that.setData({
      currentTabsIndex: index
    })
  },
  showMap: function (e) {
    var address = info.address;
    wx.openLocation({
      latitude: lat,
      longitude: lon,
      scale: 28,
      name: address
    })
  },
  imageLoad: function (e) {
    var $width = e.detail.width,    //获取图片真实宽度
      $height = e.detail.height,
      ratio = $width / $height;    //图片的真实宽高比例
    var viewWidth = 718,           //设置图片显示宽度，左右留有16rpx边距
      viewHeight = 718 / ratio;    //计算的高度值
    var image = this.data.images;
    //将图片的datadata-index作为image对象的key,然后存储图片的宽高值
    image = {
      width: viewWidth,
      height: viewHeight
    }
    this.setData({
      images: image
    })
  },
  // 点击立即预约
  nowappoint: function (e) {
    var service_id = e.currentTarget.dataset.id;
    util.navigateTo({
      url: '/pages/appointment/appointment?service_id=' + service_id
    })
  },
  phoneCall: function (e) {
    let that = this;
    wx.makePhoneCall({
      phoneNumber: that.data.mobile //仅为示例，并非真实的电话号码
    })
  },
  changeStore: function () {
    util.navigateTo({
      url: '/pages/storelist/storelist'
    })
  },
  storeRequest: function (that) {
    util.api('/api/wxapp/store/info', function (res) {
      if (res.error === 0) {
        if (res.content.delFlag == 1) {
          util.showModal('提示', '该门店不存在', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        info = res.content;
        console.log(info);
        that.setData({
          info: that.formatData(info)
        });
      }
    }, {
      storeId: Number(id),
      userId: 0
    });
  },
  /**
   * 格式化
   * @param {info} 商家信息 
   */
  formatData: function (info) {
    let that = this;
    info.storeImgs = JSON.parse(info.storeImgs)
    that.setData({
      mobile: info.mobile
    });
    // 计算店铺距离
    wx.getLocation({
      type: 'wgs84',
      success: function (d) {
        var latitude = d.latitude
        var longitude = d.longitude
        lat = Number(info.latitude);
        lon = Number(info.longitude);
        if (lat > 0 && lon > 0) {
          var dis = util.getDistance(lat, lon, latitude, longitude);
          that.setData({
            dis: dis
          });
        }
      },
      fail: function () {
        wx.getSetting({
          success: function (res) {
            if (!res.authSetting['scope.userLocation']) {
              wx.openSetting({
                success: function () {
                  var cfg = util.getCache('cfg');
                  var strRegex = "^((https|http|ftp|rtsp|mms)?://)";
                  var re = new RegExp(strRegex);
                  var n = -1;
                  for (var i = 0; i < cfg.length; i++) {
                    if (!re.test(cfg[i].hover)) {
                      cfg[i].hover = imageUrl + cfg[i].hover;
                      cfg[i].normal = imageUrl + cfg[i].normal;
                    }
                    var str = cfg[i].page;
                    if (str && str.indexOf('storelist/storelist') > -1) {
                      n = i;
                    }
                  }
                  if (n > -1) {
                    util.api('/api/wxapp/store/list', function (res) {
                      var lat = 0;
                      var lon = 0;
                      wx.getLocation({
                        type: 'wgs84',
                        success: function (d) {
                          var latitude = d.latitude;
                          var longitude = d.longitude;
                          var store = 0;
                          var min = 10000;
                          var dis = 0;
                          for (var i = 0; i < res.content.length; i++) {
                            lat = res.content[i].latitude;
                            lon = res.content[i].longitude;
                            dis = app.getDistance(lat, lon, latitude, longitude);
                            if (dis - min < 0 && res.content[i].business_state == 1) {
                              min = dis;
                              store = res.content[i].store_id;
                            }
                          }
                          if (store > 0) {
                            cfg[n].page = 'pages/storeinfo/storeinfo?id=' + store;
                            util.setCache('store', store);
                          } else {
                            cfg[n].page = 'pages/storelist/storelist';
                          }
                          util.setCache('store_page', cfg[n].page);
                          util.setCache('cfg', cfg);
                          that.setData({
                            cfg: cfg,
                          })
                          util.setCache('idx', 0);
                          if (options.redirect_param && options.redirect_param == 'store') {
                            var redirect_store = cfg[n].page.indexOf('?') > -1 ? cfg[n].page + '&' : cfg[n].page + '?';
                            util.redirectTo({
                              url: '../../' + redirect_store + 'template_config_id=' + options.template_config_id,
                            })
                            return;
                          }
                          if (cfg && cfg[0] && cfg[0].page) {
                            util.redirectTo({
                              url: '../../' + cfg[0].page
                            })
                          }
                        },
                        fail: function (m) {
                          cfg[n].page = 'pages/storelist/storelist';
                          util.setCache('store_page', cfg[n].page);
                          util.setCache('cfg', cfg);
                          that.setData({
                            cfg: cfg,
                          })
                          if (cfg && cfg[0] && cfg[0].page) {
                            util.redirectTo({
                              url: '../../' + cfg[0].page
                            })
                          }
                        }
                      })
                    });
                  }
                }
              })
            }
          }
        })
      }
    })
    // 门店介绍
    if (info.content != null) {
      that.setData({ content: util.filterRichText(info.content) })
    }
    if (info.service) {
      info.service = JSON.parse(info.service)
    }
    // 后端返回服务列表，及服务分类下的服务列表
    if (info.serviceCat && info.serviceCat.length > 0) {
      let allServiceCat = {
        catId: 0,
        catName: '全部分类',
        createTime: '',
        serviceList: info.allService
      }
      info.serviceCat.unshift(allServiceCat)
      info.serviceCat.forEach(function (item, i) {
        item.serviceList.forEach(function (item, i) {
          if (item.serviceImg) {
            var imgSrc = item.serviceImg.split('"')
            console.log(imgSrc)
            item.serviceImg = imgSrc[1]
          }
        })
      })
    }
    return info
  },
  // 买单
  toCheckout: function (e) {
    console.log(e)
    var storeId = e.currentTarget.dataset.storeid;
    util.navigateTo({
      url: '/pages/shopcheckout/shopcheckout?storeId=' + storeId
    })
  },
  // 扫码购
  toScanBuy: function (e) {
    var storeId = e.currentTarget.dataset.storeid;
    console.log(store_id)
    util.navigateTo({
      url: '/pages/scancode/scancode?storeId=' + storeId,
      success: function (res) { },
      fail: function (res) { },
      complete: function (res) { },
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.storeRequest(this);
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    // 显示导航栏loading
    wx.showNavigationBarLoading();
    // 调用接口加载数据
    var that = this;
    that.storeRequest(that);
    // 隐藏导航栏loading
    wx.hideNavigationBarLoading();
    // 当处理完数据刷新后，wx.stopPullDownRefresh可以停止当前页面的下拉刷新
    wx.stopPullDownRefresh();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    return {
      title: info.store_name,
      path: 'pages/storeinfo/storeinfo?id=' + id + "&invite_id=" + util.getCache('user_id'),
    }
  }
})