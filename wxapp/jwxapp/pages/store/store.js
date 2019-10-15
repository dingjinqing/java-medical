// pages/store/store.js
var util = require('../../utils/util.js')
// var spec_mixin = require("../goodscommon/spec.js")
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var left_height;
// var sort_info = [];
var sort_info = {
  del_market: 2,
  show_all_brand: 1,
  show_rcommend_brand: 0,
  recomment_sort: {
    recomment_img_link: "",
    recomment_sort_img: "",
    recomment_sort_status: "1"
  },
  show_cart: {
    cart_type: "0",
    show_cart: 0
  },
  menu: [{
    img_link: "",
    sort_id: -3,
    sort_name: "全部品牌"
  }, {
      img_link: "",
      sort_id: -1,
      sort_name: "推荐分类"
  }, {
      create_time: "2019-08-06 17:12:12",
      first: 7,
      has_child: 1,
      img_link: "test/test001",
      is_third: 0,
      keywords: "223",
      level: 1,
      parent_id: 0,
      sort_desc: "",
      sort_id: 223,
      sort_img: "http://mpdevimg2.weipubao.cn/upload/0/image/20190726/crop_FCm0xyVYimX2dT9X.png",
      sort_name: "鞋子",
      type: 0
  }, {
      create_time: "2019-07-26 18:20:24",
      first: 1,
      has_child: 1,
      img_link: "",
      is_third: 0,
      keywords: "",
      level: 1,
      parent_id: 0,
      sort_desc: "",
      sort_id: 210,
      sort_img: "http://mpdevimg2.weipubao.cn/upload/0/image/20190726/crop_KlSCyopcS0G9xyHR.jpeg",
      sort_name: "服装",
      type: 0
  }, {
      create_time: "2019-07-26 18:22:08",
      first: 1,
      has_child: 1,
      img_link: "",
      is_third: 0,
      keywords: "",
      level: 1,
      parent_id: 0,
      sort_desc: "",
      sort_id: 213,
      sort_img: "http://mpdevimg2.weipubao.cn/upload/0/image/20190726/crop_GrUfey853q5ew4xx.jpeg",
      sort_name: "箱包",
      type: 0
  }],
  sort_content: {
    content: {
      A: [{
        add_time: "2019-04-11 09:36:10",
        brand_name: "阿迪达斯",
        classify_id: 1,
        desc: "",
        e_name: "",
        first: 10,
        id: 26,
        initials: "A",
        is_delete: 0,
        is_recommend: 1,
        is_third: 0,
        logo: "http://mpdevimg2.weipubao.cn/upload/4748160/image/20190411/crop_bgxFsoQDCYlTwhJ2.jpeg",
        update_time: "2019-04-12 09:26:22"
      }, {
          add_time: "2019-08-29 13:48:17",
          brand_name: "爱马仕",
          classify_id: 0,
          desc: null,
          e_name: "",
          first: 0,
          id: 42,
          initials: "A",
          is_delete: 0,
          is_recommend: 0,
          is_third: 1,
          logo: null,
          update_time: null
      }],
      D: [{
        add_time: "2019-08-16 09:29:13",
        brand_name: "第三方品牌",
        classify_id: 0,
        desc: null,
        e_name: "",
        first: 2,
        id: 40,
        initials: "D",
        is_delete: 0,
        is_recommend: 0,
        is_third: 1,
        logo: "http://mpdevimg2.weipubao.cn/upload/0/image/20190814/crop_RK35wnkYqYqPx03n.jpeg",
        update_time: "2019-08-16 09:29:13"
      }],
      G: [{
        add_time: "2019-08-29 11:01:26",
        brand_name: "古驰",
        classify_id: 0,
        desc: null,
        e_name: "",
        first: 0,
        id: 41,
        initials: "G",
        is_delete: 0,
        is_recommend: 0,
        is_third: 1,
        logo: null,
        update_time: null
      }]
    }
  }
};
var is_reco;
var page_id;
var content;
var page_cfg;
var input_height;
var bottom_height;
var page;
var sort_id;
var indexs;
var goodsArry = [];
var isSingleGoods;
var last_page;






global.wxPage({
  // mixins: [spec_mixin],
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    is_reco: 0,// 推荐分类
    is_brand: 0,// 全部品牌
    page: 1,
    isSingleGoods: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    goodsArry = [];
    var info = wx.getSystemInfoSync();
    var screen_height = wx.getSystemInfoSync().windowHeight;
    var screen_width = info.windowWidth;
    // var pxPerRPX = screen_width / 750;

    wx.createSelectorQuery().select('.input_waimian').boundingClientRect().exec(function (rect) {
      if (!rect || !rect[0]) return;
      rect = rect[0] ? rect[0] : rect;
      var h = screen_height - rect.height;
      if (that.data.isIpx == 1) {
        h = h - 25
      }
      wx.createSelectorQuery().select('.bottom').boundingClientRect().exec(function (reda) {
        console.log(reda)
        console.log(h)
        if (!reda || !reda[0]) return;
        reda = reda[0] ? reda[0] : reda;
        h = h - reda.height;
        that.setData({
          left_height: h,
          right_height: h
        });
      })
      h = h - wx.getMenuButtonBoundingClientRect().bottom - 8;
      that.setData({
        left_height: h,
        right_height: h
      });
    });


    // 模拟数据
    sort_id = sort_info.menu[0].sort_id;
    if (sort_info.menu != 0) {
      sort_info.menu[0].colors = that.data.comColor;
      sort_info.menu[0].borderright = "6rpx solid " + that.data.comColor;
      sort_info.menu[0].backgr = "#ffffff";
      if (sort_info.sort_content.goods != '' && sort_info.sort_content.goods != null) {
        isSingleGoods = 1;
        last_page = sort_info.sort_content.last_page;
      } else {
        isSingleGoods = 0;
      }
      if (sort_info.sort_content.goods) {
        goodsArry = goodsArry.concat(sort_info.sort_content.goods);
      }
      if (sort_info.menu[0].sort_id == -1) {
        that.setData({
          is_reco: 1
        })
      } else if (sort_info.menu[0].sort_id == -2 || sort_info.menu[0].sort_id == -3) {
        that.setData({
          is_brand: 1
        })
        if (sort_info.menu[0].sort_id == -2) {
          that.setData({
            all_brand: 0
          })
        } else {
          that.setData({
            all_brand: 1
          })
        }
      }
      that.setData({
        sort_info: sort_info,
        goodsArry: goodsArry,
        isSingleGoods: isSingleGoods,
      });
    }

    // util.api('/api/wxapp/sort/show', function (res) {
    //   sort_info = res.content;
    //   sort_id = sort_info.menu[0].sort_id;
    //   if (sort_info.menu != 0) {
    //     sort_info.menu[0].colors = that.data.comColor;
    //     sort_info.menu[0].borderright = "6rpx solid " + that.data.comColor;
    //     sort_info.menu[0].backgr = "#ffffff";
    //     if (sort_info.sort_content.goods != '' && sort_info.sort_content.goods != null) {
    //       isSingleGoods = 1;
    //       last_page = sort_info.sort_content.last_page;
    //     } else {
    //       isSingleGoods = 0;
    //     }
    //     if (sort_info.sort_content.goods) {
    //       goodsArry = goodsArry.concat(sort_info.sort_content.goods);
    //     }
    //     if (sort_info.menu[0].sort_id == -1) {
    //       that.setData({
    //         is_reco: 1
    //       })
    //     } else if (sort_info.menu[0].sort_id == -2 || sort_info.menu[0].sort_id == -3) {
    //       that.setData({
    //         is_brand: 1
    //       })
    //       if (sort_info.menu[0].sort_id == -2) {
    //         that.setData({
    //           all_brand: 0
    //         })
    //       } else {
    //         that.setData({
    //           all_brand: 1
    //         })
    //       }
    //     }
    //   }
    //   that.setData({
    //     sort_info: sort_info,
    //     goodsArry: goodsArry,
    //     isSingleGoods: isSingleGoods,

    //   });
    // });
    
  },
  changeSort: function(e) {
    
  },
  // changeSort: function (e) {
  //   sort_id = e.currentTarget.dataset.sort_id;
  //   indexs = e.currentTarget.dataset.selected;
  //   goodsArry = [];
  //   if (sort_id == -2 || sort_id == -3) {
  //     this.setData({
  //       is_brand: 1
  //     })
  //     if (sort_id == -2) {
  //       this.setData({
  //         all_brand: 0
  //       })
  //     } else if (sort_id == -3) {
  //       this.setData({
  //         all_brand: 1
  //       })
  //     }
  //   } else {
  //     this.setData({
  //       is_brand: 0,
  //       all_brand: 0
  //     })
  //   }
  //   var that = this;
  //   util.api('/api/wxapp/sort/show', function (res) {
  //     sort_info = res.content;
  //     for (var i = 0; i < sort_info.menu.length; i++) {
  //       sort_info.menu[i].colors = "#333333";
  //       sort_info.menu[i].borderright = "6rpx solid #f6f6f6";
  //       sort_info.menu[i].backgr = "#f5f5f5";
  //     }
  //     sort_info.menu[indexs].colors = that.data.comColor;
  //     sort_info.menu[indexs].borderright = "6rpx solid " + that.data.comColor;
  //     sort_info.menu[indexs].backgr = "#ffffff";
  //     if (sort_info.sort_content.goods != '' && sort_info.sort_content.goods != null) {
  //       isSingleGoods = 1;
  //       last_page = sort_info.sort_content.last_page;
  //     } else {
  //       isSingleGoods = 0;
  //     }
  //     if (sort_info.sort_content.goods) {
  //       goodsArry = goodsArry.concat(sort_info.sort_content.goods);
  //     }
  //     if (sort_id == -1) {
  //       that.setData({
  //         is_reco: 1
  //       })
  //     } else {
  //       that.setData({
  //         is_reco: 0
  //       })
  //     }
  //     that.setData({
  //       sort_info: sort_info,
  //       isSingleGoods: isSingleGoods,
  //       goodsArry: goodsArry,
  //       page: 1,
  //     })
  //   }, {
  //       sort_id: sort_id,
  //     });
  // },
  go_where: function (e) {
    var links = e.currentTarget.dataset.link;
    if (links != null && links != "") {
      util.navigateTo({
        url: '../../' + links,
      })
    }
  },
  go_search: function (e) {
    var sort_ids = e.currentTarget.dataset.sord_ids;
    var sort_names = e.currentTarget.dataset.sort_names;
    util.navigateTo({
      url: '/pages/searchs/search?sort_id=' + sort_ids + "&sort_names=" + sort_names + "&type=" + 1,
    })
  },
  onSearchConfirm: function (event) {
    var search_text = event.detail.value;
    util.navigateTo({
      url: '../searchs/search?search=' + search_text,
    })
  },
  go_tiaozhuan: function (e) {
    var link = e.currentTarget.dataset.link;
    util.navigateTo({
      url: "../../" + link,
    })
  },
  to_search() {
    util.jumpLink('/pages/newsearch/newsearch', 'navigateTo')
  },
  bindTopre: function (e) {
    var d = this.eventData(e);
    util.jumpLink('pages/presaleitem/presaleitem?presale_id=' + d.presale_id, 'navigateTo');
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

  },
  bindAddCart(e) {
    var that = this;
    util.api('/api/wxapp/popup/goods/data', function (res) {
      console.log(res)
      if (res.error == 0) {
        that.setData({
          goodsData: res.content
        })
        console.log(that.showSpec(0, 1))
      } else {
        util.showModal('提示', res.message);
      }
    }, {
        goods_id: e.currentTarget.dataset.goods_id
      }, 0, 1)
  },
  // 滑动底部加载
  lower: function () {
    var that = this;
    page = that.data.page + 1;
    if (page > last_page) return;
    that.setData({
      page: page,
    })
    var isSingleGoods = that.data.isSingleGoods;
    if (isSingleGoods == 1) {
      that.requestData();
    }
  },

  requestData: function () {
    var that = this;
    util.api('/api/wxapp/sort/goods', function (res) {
      sort_info = res.content;
      if (sort_id == -1) {
        that.setData({
          is_reco: 1
        })
      } else {
        that.setData({
          is_reco: 0
        })
      }
      goodsArry = goodsArry.concat(sort_info.goods);
      that.setData({
        goodsArry: goodsArry,
      })
    }, {
        sort_id: sort_id,
        page: that.data.page,
      });

  },

})