// STOREINFO.JS 2018.03.05
var app = new getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var mobile = '';
var lat;
var lon;
var info;
var all_serv = {};
var id;
// var is_second;
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    currentTabsIndex: 0,
  },

  /**
  * 生命周期函数--监听页面加载
  */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    id = options.id;
    var that = this;

    that.storeRequest(that);
  },
  preview: function (e) {
    var nowImgUrl = e.target.dataset.src;
    var arr = [];
    for (var i in info.store_img) {
      arr.push(info.store_img[i]); //属性
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
      name:address
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
  nowappoint: function(e) {
    var service_id = e.currentTarget.dataset.id;
    util.navigateTo({
      url: '/pages/appointment/appointment?service_id='+service_id
    })
  },
  phoneCall:function(e){
    wx.makePhoneCall({
      phoneNumber: mobile //仅为示例，并非真实的电话号码
    })
  },
  changeStore:function(){
    util.navigateTo({
      url: '/pages/storelist/storelist'
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  storeRequest:function(that){
    util.api('/api/wxapp/store/info', function (res) {
      if (res.content.is_delete == 1) {
        util.showModal('提示', '该门店不存在', function () {
          util.reLaunch({
            url: '/pages/index/index'
          })
        });
        return;
      }
      mobile = res.content.mobile;
      info = res.content;
      wx.getLocation({
        type: 'wgs84',
        success: function (d) {
          var latitude = d.latitude
          var longitude = d.longitude
          lat = Number(res.content.latitude);
          lon = Number(res.content.longitude);
          if (lat > 0 && lon > 0) {
            var dis = app.getDistance(lat, lon, latitude, longitude);
            that.setData({
              dis: dis
            });
          }
        },fail:function(){
          wx.getSetting({
            success: function (res) {
              if (!res.authSetting['scope.userLocation']) {
                wx.openSetting({
                  success:function(){
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
      if (res.content.content != null) {
		  that.setData({content:util.filterRichText(res.content.content)})
      }
      if (info.list.length >= 0) {
        all_serv.cat_id = 0;
        all_serv.cat_name = '全部分类';
        all_serv.services = info.all_service;
        info.list.unshift(all_serv);
        for (var i in info.list) {
          if (info.list[i].services != null && info.list[i].services.length > 0) {
            for (var j in info.list[i].services) {
              info.list[i].services[j].service_img = JSON.parse(info.list[i].services[j].service_img)[0];
            }
          }
        }
      }
      that.setData({
        info: info
      });
    }, { id: id  });
  },
  toCheckout:function(e){
    var store_id = e.currentTarget.dataset.store_id;
    util.navigateTo({
      url: '/pages/shopcheckout/shopcheckout?store_id=' + store_id
    })
  },

  toScanBuy: function (e) {
    var store_id = e.currentTarget.dataset.store_id;
    console.log(store_id)
      util.navigateTo({
        url: '/pages/scancode/scancode?store_id=' + store_id,
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
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    return {
      title: info.store_name,
      path: 'pages/storeinfo/storeinfo?id=' + id + "&invite_id=" + util.getCache('user_id'),
    }
  },
})
