let util = require('../../utils/util.js');
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    canBuyGoodsList: null,
    invalidGoodsList: null,
    totalPrice: 0,
    proMode: true,
    // 切换活动弹框要用到的数组
    activityType: null, // 正在参与的活动类型
    proPurchaseInfo: [],
    cartId: '', // 切换促销活动当前购物车id
    isType: 0, // 判断当前参与活动类型
    repurchaseList: [] // 换购商品列表
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requestCartList()
  },

  // 请求购物车列表
  requestCartList () {
    util.api('/api/wxapp/cart/list', (res) => {
      if (res.error === 0) {
        let { cartGoodsList: canBuyGoodsList = [], invalidCartList: invalidGoodsList = [], isAllCheck = null, totalPrice = null } = res.content || []
        this.selectComponent('#recommend').requestData() //请求推荐商品
        this.setData({
          canBuyGoodsList,
          invalidGoodsList,
          isAllCheck,
          totalPrice
        })


        // 首单特惠添加商品个数限制
        if (res.content.noticeStatus == 1) {
          util.showModal('提示', res.content.notice);
        }

        var repurchaseList = []
        this.data.canBuyGoodsList.forEach((item, index) => {
          item.isSales = 0
          item.isMemPrice = 0
          if (item.cartActivityInfos.length > 0) {
            // 添加不参与活动
            item.cartActivityInfos[item.cartActivityInfos.length] = {
              activityId: null,
              activityType: null,
              status: 1
            }
            item.cartActivityInfos.forEach((val, key) => {
              // 判断是否参加活动
              if ((val.activityType == 7 || val.activityType == 21) && item.activityType != 5 && item.activityType != 6 && item.activityType != 18 && item.activityType != 10) {
                item.isSales = 1
              }
              // 判断是否存在会员价
              if (val.activityType == 22) {
                item.isMemPrice = 1
              }
              // 拼接促销活动
              if (item.activityId == val.activityId) {
                item.ruleList = ""
                if (val.activityType == 21) {
                  val.fullReduction.rules.forEach((ritem, rindex) => {
                    item.ruleList += ritem.name + '或'
                  })
                  if (item.ruleList.length > 0) {
                    item.ruleList = item.ruleList.substr(0, item.ruleList.length - 1);
                  }
                } else if (val.activityType == 7) {
                  val.purchasePrice.purchasePriceRule.forEach((pitem, pindex) => {
                    item.ruleList += pitem.name + '或'
                  })
                  if (item.ruleList.length > 0) {
                    item.ruleList = item.ruleList.substr(0, item.ruleList.length - 1);
                  }
                }
              }
              // 换购列表
              // dataList.forEach((val2, key2) => {
              //   if (val.activityType == 7 && val.activityId == val2.activityId) {
              //     val.cartActivityInfos.push(val2)
              //   }
              // })
            })
          }
          // 换购商品列表
          if (item.activityType == 97) {
            repurchaseList.push(item)
          }
        })
        this.setData({
          canBuyGoodsList: this.data.canBuyGoodsList,
          repurchaseList: repurchaseList
        })
        console.log(this.data.canBuyGoodsList)
        console.log(this.data.repurchaseList)
      }
    })
  },

  // 更改选中状态
  checkedToggle (e) {
    let cartId = e.currentTarget.dataset.cart_id
    let isChecked = e.currentTarget.dataset.is_checked
    util.api('/api/wxapp/cart/switch', res => {
      if (res.error === 0) {
        this.requestCartList()
      }
    }, { cartIds: [cartId], isChecked: isChecked ? 0 : 1 })
  },

  // 更改全选状态
  changeAllChecked () {
    let cartIds = this.data.canBuyGoodsList.map(item => { return item.cartId })
    let isAllCheck = this.data.isAllCheck ? 0 : 1
    util.api('/api/wxapp/cart/switch', res => {
      if (res.error === 0) {
        this.requestCartList()
      }
    }, { cartIds: cartIds, isChecked: isAllCheck })
  },

  // 更改商品数量
  goodsNumChange (e) {
    let type = e.currentTarget.dataset.type;
    let cartId = e.currentTarget.dataset.cart_id;
    let prdId = e.currentTarget.dataset.prd_id;
    let cartNumber = e.currentTarget.dataset.cart_number;
    util.api('/api/wxapp/cart/change', res => {
      if (res.error == 0) {
        this.requestCartList()
      }
    }, {
      cartId: cartId,
      productId: prdId,
      cartNumber: type == 'add' ? cartNumber + 1 : cartNumber - 1
    })
  },

  // 校验商品数量
  checkNumber (e) {
    var that = this;
    var value = Number(e.detail.value)
    var cartId = e.target.dataset.cart_id
    var limit_min = e.target.dataset.limit_min
    var limit_max = e.target.dataset.limit_max
    that.data.canBuyGoodsList.forEach((item, index) => {
      // if (item.prdId == prdId) {
      //   if ((value >= limit_min) && (value <= limit_max)) {
      //     item.cartNumber = value
      //   } else {
      //     item.cartNumber = limit_min
      //   }
      // }
      if (item.cartId == cartId) {
        item.cartNumber = value
        util.api('/api/wxapp/cart/change', res => {
          if (res.error == 0) {
            this.requestCartList()
          }
        }, {
          cartId: cartId,
          productId: item.productId,
          cartNumber: item.cartNumber
        })
      }
    })
  },

  // 删除购物车商品
  delCartGoods (e) {
    const cartId = e.currentTarget.dataset.cart_id
    util.api('/api/wxapp/cart/remove', (res) => {
      console.log(res)
      if (res.error === 0) {
        this.requestCartList()
      }
    }, { cartId: cartId })
  },

  // 清除无效购物车列表
  clearCart () {
    let cartIds = this.data.invalidGoodsList.map(item => { return item.cartId })
    util.api('/api/wxapp/cart/removes', res => {
      if (res.error === 0) {
        this.requestCartList()
      }
    }, { cartIds: cartIds })
  },

  //触摸改变
  handleTouchChange (e) {
    this.moveX = e.detail.x
  },

  //触摸结束
  handleTouchEnd (e) {
    let idx = e.currentTarget.dataset.index
    let target = 'canBuyGoodsList[' + idx + '].x'
    if (this.moveX <= -20) {
      this.setData({
        [target]: -100
      });
    } else {
      this.setData({
        [target]: 0
      });
    }
  },

  //  去结算
  toCheckOut () {
    let goodsList = this.data.canBuyGoodsList.filter(item => item.isChecked === 1).map(item => {
      let { goodsId, prdPrice: prdRealPrice, cartNumber: goodsNum, productId: prdId } = item
      return { goodsId, prdRealPrice, goodsNum, prdId, isCart: 1 }
    })
    if (goodsList.length == 0) {
      util.showModal("提示", "请选择您想要购买的商品");
      return false;
    }
    util.jumpLink(`pages/checkout/checkout?goodsList=${JSON.stringify(goodsList)}`, "navigateTo")
  },

  // 列表无数据跳转
  toIndex () {
    util.jumpLink('pages/index/index', 'navigateTo')
  },
  onReachBottom: function () {
    this.selectComponent('#recommend').requestData()
  },

  // 显示促销活动
  proClcik: function (e) {
    var that = this;
    var cartId = e.currentTarget.dataset.cart_id;
    var activityType = e.currentTarget.dataset.activity_type;
    that.setData({
      activityType: activityType
    })
    that.data.canBuyGoodsList.forEach((item, index) => {
      if (item.cartId == cartId) {
        // 判断参与活动
        if (item.activityType == 7) {
          // 加价购
          that.setData({
            isType: 1
          })
        } else if (item.activityType == 21) {
          // 满折满减
          that.setData({
            isType: 2
          })
        } else {
          // 不参与活动
          that.setData({
            isType: 0
          })
        }
        item.cartActivityInfos.forEach((val, key) => {
          // 判断当前选中活动
          if (item.activityType == val.activityType && item.activityId == val.activityId) {
            val.is_che = 1
          } else {
            val.is_che = 0
          }
        })
        that.setData({
          cartId: cartId,
          proPurchaseInfo: item.cartActivityInfos
        })
      }
    })
    console.log(that.data.proPurchaseInfo)
    // 显示促销弹窗
    that.setData({
      proMode: false,
    })
  },
  // 隐藏促销弹窗
  proCancel: function () {
    this.setData({
      proMode: true
    })
  },
  proActionSheetChange: function () {
    this.setData({
      proMode: true
    })
  },

  // 切换促销活动
  choose_acts: function (e) {
    var that = this;
    var activityId = e.currentTarget.dataset.activity_id; // 选择的活动id
    var activityType = e.currentTarget.dataset.activity_type; // 选择的活动类型
    // 不参与活动
    if (activityId == null) {
      activityId = 0
    }
    if (activityType == null) {
      activityType = 0
    }
    that.data.proPurchaseInfo.forEach((item, index) => {
      item.is_che = 0
      if (item.activityType == activityType) {
        item.is_che = 1
      }
    })
    that.setData({
      proPurchaseInfo: that.data.proPurchaseInfo,
    })
    // 保存切换营销活动
    var ids = []
    ids.push(this.data.cartId)
    util.api('/api/wxapp/cart/switch/activity', function (res) {
      if (res.error == 0) {
        // 隐藏弹窗
        that.setData({
          proMode: true
        })
        that.requestCartList()
      }
    }, {
      cartIds: ids,
      activityId: activityId,
      activityType: activityType,
      isChecked: 1
    })
  },

  // 跳转秒杀抢购
  to_seckill: function (e) {
    util.jumpLink(e.currentTarget.dataset.link);
  },

  // 跳转满折满减商品
  to_fullpage: function (e) {
    var activityId = e.currentTarget.dataset.activity_id;
    var storeId = e.currentTarget.dataset.store_id;
    util.navigateTo({
      url: '/pages/fullprice/fullprice?strategy_id=' + activityId + '&store_id=' + storeId,
    })
  },

  // 跳转加价购商品列表
  to_purchase: function (e) {
    var activityId = e.currentTarget.dataset.activity_id;
    var storeId = e.currentTarget.dataset.store_id;
    // 已选换购规格id
    var pIds = []
    this.data.repurchaseList.forEach(item => {
      if (item.activityId == activityId) {
        pIds.push(item.productId)
      }
    })
    console.log(pIds)
    util.navigateTo({
      url: '/pages/maingoodslist/maingoodslist?identity_id=' + activityId + '&store_id=' + storeId + '&pIds=' + JSON.stringify(pIds),
    })
  },

})