// pages/sort/sort.js
var util = require('../../utils/util.js')
// var spec_mixin = require("../goodscommon/spec.js")
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var left_height;
var sort_info = [];
var sort_menu = []; // 左侧数据
var sort_content = []; // 右侧数据
var menuType; // 菜单类型
var menuId; // 菜单id
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
    is_brand: 0,// 推荐品牌
    all_brand: 0,// 全部品牌
    page: 1,
    isSingleGoods: 0,// 商品分类
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


    util.api('/api/wxapp/sort/init', function (res) {
      if (res.error == 0) {
        sort_menu = res.content;
        for (var i = 0; i < sort_menu.length; i++) {
          if (!sort_menu[0].menuContent || sort_menu[0].menuContent != null || sort_menu[0].menuContent != '') {
            sort_content = sort_menu[0].menuContent;
            sort_menu[0].colors = that.data.comColor;
            sort_menu[0].borderright = "6rpx solid " + that.data.comColor;
            sort_menu[0].backgr = "#ffffff";
          }
          if (sort_menu[i].menuType == 1) {
            // sort_menu[i].menuName = that.$t('pages.store.leftMenuA');
            sort_menu[i].menuName = '全部品牌';
          }
          if (sort_menu[i].menuType == 2) {
            // sort_menu[i].menuName = that.$t('pages.store.leftMenuB');
            sort_menu[i].menuName = '推荐品牌';
          }
          if (sort_menu[i].menuType == 5) {
            // sort_menu[i].menuName = that.$t('pages.store.leftMenuC');
            sort_menu[i].menuName = '推荐分类';
          }
        } 
        if (sort_content.menuContentType == 1 || sort_content.menuContentType == 4) {
          // 全部品牌
          that.setData({
            is_brand: 1
          })
          that.setData({
            all_brand: 1
          })

          that.setData({
            is_reco: 0
          })
          that.setData({
            isSingleGoods: 0
          })
        } else if (sort_content.menuContentType == 3) {
          // 推荐品牌
          that.setData({
            is_brand: 1
          })
          that.setData({
            all_brand: 0
          })

          that.setData({
            is_reco: 0
          })
          that.setData({
            isSingleGoods: 0
          })
        } else if (sort_content.menuContentType == 5) {
          // 推荐分类
          that.setData({
            is_reco: 1
          })

          that.setData({
            is_brand: 0
          })
          that.setData({
            all_brand: 0
          })
          that.setData({
            isSingleGoods: 0
          })
        } else if (sort_content.menuContentType == 6) {
          // 其他分类
          that.setData({
            is_reco: 0
          })
          that.setData({
            is_brand: 0
          })
          that.setData({
            all_brand: 0
          })
          that.setData({
            isSingleGoods: 0
          })
        } else if (sort_content.menuContentType == 7) {
          // 商品分类
          that.setData({
            isSingleGoods: 1
          })

          that.setData({
            is_reco: 0
          })
          that.setData({
            is_brand: 0
          })
          that.setData({
            all_brand: 0
          })
        }

        
        that.setData({
          sort_menu: sort_menu,
          sort_content: sort_content,
        });


      }   
    });

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
  changeSort: function (e) {
    menuType = e.currentTarget.dataset.type;
    menuId = e.currentTarget.dataset.id;
    indexs = e.currentTarget.dataset.selected;
    var that = this;
    for (var i = 0; i < sort_menu.length; i++) {
      sort_menu[i].colors = "#333333";
      sort_menu[i].borderright = "6rpx solid #f6f6f6";
      sort_menu[i].backgr = "#f5f5f5";
    }
    sort_menu[indexs].colors = that.data.comColor;
    sort_menu[indexs].borderright = "6rpx solid " + that.data.comColor;
    sort_menu[indexs].backgr = "#ffffff";

    util.api('/api/wxapp/sort/get/content', function (res) {
      if (res.error ==0) {
        sort_content = res.content;
        if (sort_content.menuContentType == 1 || sort_content.menuContentType == 4) {
          // 全部品牌
          that.setData({
            is_brand: 1
          })
          that.setData({
            all_brand: 1
          })

          that.setData({
            is_reco: 0
          })
          that.setData({
            isSingleGoods: 0
          })
        } else if (sort_content.menuContentType == 3) {
          // 推荐品牌
          that.setData({
            is_brand: 1
          })
          that.setData({
            all_brand: 0
          })

          that.setData({
            is_reco: 0
          })
          that.setData({
            isSingleGoods: 0
          })
        } else if (sort_content.menuContentType == 5) {
          // 推荐分类
          that.setData({
            is_reco: 1
          })

          that.setData({
            is_brand: 0
          })
          that.setData({
            all_brand: 0
          })
          that.setData({
            isSingleGoods: 0
          })
        } else if (sort_content.menuContentType == 6) {
          // 其他分类
          that.setData({
            is_reco: 0
          })
          that.setData({
            is_brand: 0
          })
          that.setData({
            all_brand: 0
          })
          that.setData({
            isSingleGoods: 0
          })
        } else if (sort_content.menuContentType == 7) {
          // 商品分类
          that.setData({
            isSingleGoods: 1
          })

          that.setData({
            is_reco: 0
          })
          that.setData({
            is_brand: 0
          })
          that.setData({
            all_brand: 0
          })
        }

        that.setData({
          sort_menu: sort_menu,
          sort_content: sort_content,
        });

      }
    }, {
      menuType: menuType,
      menuId: menuId
    })
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