let util = require('../../utils/util.js');
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    cartGoodsList: null, // 普通商品列表
    invalidCartList: null, // 失效商品列表
    purchaseList: null, // 加价购列表
    fullList: null, // 瞒折满减列表
    isAllCheck: 0, // 是否全选
    totalPrice: 0, // 金额

    proMode: true, // 促销弹窗
    isType: 0, // 判断当前参与活动类型(1加价购, 2满折满减, 0不参与)
    proPurchaseInfo: [], // 促销信息

    activityType: null, // 正在参与的活动类型
    cartId: '', // 切换促销活动当前购物车id

    maxRight: 0,  // 删除 
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requestCartList()

    // 计算最大maxRight
    var info = wx.getSystemInfoSync();
    var screen_height = wx.getSystemInfoSync().windowHeight;
    var screen_width = info.windowWidth;
    var pxPerRPX = screen_width / 750;
    this.setData({
      maxRight: pxPerRPX * 120
    })
    
  },

  // 请求购物车列表
  requestCartList () {
    util.api('/api/wxapp/cart/list', (res) => {
      if (res.error == 0) {
        this.setData({
          cartGoodsList: res.content.cartGoodsList.length == 0 ? null : res.content.cartGoodsList,
          invalidCartList: res.content.invalidCartList.length == 0 ? null : res.content.invalidCartList,
          fullList: Object.keys(res.content.fullReductionGoodsMap || {}).length == 0 ? null : res.content.fullReductionGoodsMap,
          purchaseList: Object.keys(res.content.purchasePriceGoodsMap || {}).length == 0 ? null : res.content.purchasePriceGoodsMap,
          isAllCheck: res.content.isAllCheck,
          totalPrice: res.content.totalPrice,
          couponPackage: res.content.couponPackage
        })

        if (this.data.cartGoodsList && this.data.cartGoodsList.length > 0) {
          this.data.cartGoodsList.forEach((item, index) => {
            item.isSales = 0
            item.isSeckill = 0 // 秒杀
            item.isExclusive = 0 // 专享
            item.isAdvance = 0 // 预售
            item.isMember = 0 // 会员价
            item.isReduce = 0 // 限时降价
            item.isFirstOrder = 0 // 首单特惠
            item.limitMinStyle = 0
            item.limitMaxStyle = 0
            if (item.cartActivityInfos && item.cartActivityInfos.length > 0) {
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
                // 营销活动(首单特惠 / 限时降价 / 会员价)差价
                if (val.activityType == 6 || val.activityType == 18 || val.activityType == 22) {
                  item.diffPrice = parseFloat(item.originalPrice - val.productPrice).toFixed(2)
                }
                // 是否是秒杀活动
                if (val.activityType == 5) {
                  item.isSeckill = 1
                }
                // 是否是专享商品
                if (val.activityType == 23) {
                  item.isExclusive = 1
                }
                // 是否是预售商品
                if (val.activityType == 10) {
                  item.isAdvance = 1
                }
                // 是否是会员价
                if (val.activityType == 22) {
                  item.isMember = 1
                }
                // 是否是限时降价
                if (val.activityType == 6) {
                  item.isReduce = 1
                }
                // 是否是首单特惠
                if (val.activityType == 18) {
                  item.isFirstOrder = 1
                }
                // 拼接促销活动
                // if (item.activityId == val.activityId) {
                //   item.ruleList = ""
                //   if (val.activityType == 21) {
                //     val.fullReduction.rules.forEach((ritem, rindex) => {
                //       item.ruleList += ritem.name + '或'
                //     })
                //     if (item.ruleList.length > 0) {
                //       item.ruleList = item.ruleList.substr(0, item.ruleList.length - 1);
                //     }
                //   } else if (val.activityType == 7) {
                //     val.purchasePrice.purchasePriceRule.forEach((pitem, pindex) => {
                //       item.ruleList += pitem.name + '或'
                //     })
                //     if (item.ruleList.length > 0) {
                //       item.ruleList = item.ruleList.substr(0, item.ruleList.length - 1);
                //     }
                //   }
                // }
                // 换购列表
                // dataList.forEach((val2, key2) => {
                //   if (val.activityType == 7 && val.activityId == val2.activityId) {
                //     val.cartActivityInfos.push(val2)
                //   }
                // })
              })
            }
            // 限购样式
            if ((item.cartNumber >= item.prdNumber) || (item.activityLimitMaxNum != null && (item.cartNumber >= item.activityLimitMaxNum)) || (item.activityLimitMaxNum == null && item.limitMaxNum != 0 && (item.cartNumber >= item.limitMaxNum))) {
              item.limitMaxStyle = 1
            }
            if ((item.cartNumber <= 1) || (item.activityLimitMinNum != null && (item.cartNumber <= item.activityLimitMinNum)) || (item.activityLimitMaxNum == null && item.limitBuyNum != 0 && (item.cartNumber <= item.limitBuyNum))) {
              item.limitMinStyle = 1
            }
          })
        }
        if (this.data.fullList) {
          for(let key in this.data.fullList) {
            this.data.fullList[key].forEach((item, index) => {
              item.isSeckill = 0 // 秒杀
              item.isExclusive = 0 // 专享
              item.isAdvance = 0 // 预售
              item.isMember = 0 // 会员价
              item.isReduce = 0 // 限时降价
              item.isFirstOrder = 0 // 首单特惠
              item.limitMinStyle = 0
              item.limitMaxStyle = 0
              if (item.cartActivityInfos && item.cartActivityInfos.length > 0) {
                // 添加不参与活动
                item.cartActivityInfos[item.cartActivityInfos.length] = {
                  activityId: null,
                  activityType: null,
                  status: 1
                }
                item.cartActivityInfos.forEach((val, key) => {
                  // 营销活动(首单特惠 / 限时降价 / 会员价)差价
                  if (val.activityType == 6 || val.activityType == 18 || val.activityType == 22) {
                    item.diffPrice = parseFloat(item.originalPrice - val.productPrice).toFixed(2)
                  }
                  // 是否是秒杀活动
                  if (val.activityType == 5) {
                    item.isSeckill = 1
                  }
                  // 是否是专享商品
                  if (val.activityType == 23) {
                    item.isExclusive = 1
                  }
                  // 是否是预售商品
                  if (val.activityType == 10) {
                    item.isAdvance = 1
                  }
                  // 是否是会员价
                  if (val.activityType == 22) {
                    item.isMember = 1
                  }
                  // 是否是限时降价
                  if (val.activityType == 6) {
                    item.isReduce = 1
                  }
                  // 是否是首单特惠
                  if (val.activityType == 18) {
                    item.isFirstOrder = 1
                  }
                  // 拼接促销活动
                  if (item.activityId == val.activityId) {
                    item.ruleList = ""
                    item.currentRule = val.fullReduction // 当前规则
                    val.fullReduction.rules.forEach((ritem, rindex) => {
                      item.ruleList += ritem.name + '或'
                    })
                    if (item.ruleList.length > 0) {
                      item.ruleList = item.ruleList.substr(0, item.ruleList.length - 1);
                    }
                  }
                })
              }
              // 限购样式
              if ((item.cartNumber >= item.prdNumber) || (item.activityLimitMaxNum != null && (item.cartNumber >= item.activityLimitMaxNum)) || (item.activityLimitMaxNum == null && item.limitMaxNum != 0 && (item.cartNumber >= item.limitMaxNum))) {
                item.limitMaxStyle = 1
              }
              if ((item.cartNumber <= 1) || (item.activityLimitMinNum != null && (item.cartNumber <= item.activityLimitMinNum)) || (item.activityLimitMaxNum == null && item.limitBuyNum != 0 && (item.cartNumber <= item.limitBuyNum))) {
                item.limitMinStyle = 1
              }
            })
            this.data.fullList[key].push(this.data.fullList[key][0].currentRule)
          }
        }
        if (this.data.purchaseList) {
          for(let key in this.data.purchaseList) {
            this.data.purchaseList[key].forEach((item, index) => {
              item.isSeckill = 0 // 秒杀
              item.isExclusive = 0 // 专享
              item.isAdvance = 0 // 预售
              item.isMember = 0 // 会员价
              item.isReduce = 0 // 限时降价
              item.isFirstOrder = 0 // 首单特惠
              item.limitMinStyle = 0
              item.limitMaxStyle = 0
              if (item.cartActivityInfos && item.cartActivityInfos.length > 0) {
                // 添加不参与活动
                item.cartActivityInfos[item.cartActivityInfos.length] = {
                  activityId: null,
                  activityType: null,
                  status: 1
                }
                item.cartActivityInfos.forEach((val, key) => {
                  // 营销活动(首单特惠 / 限时降价 / 会员价)差价
                  if (val.activityType == 6 || val.activityType == 18 || val.activityType == 22) {
                    item.diffPrice = parseFloat(item.originalPrice - val.productPrice).toFixed(2)
                  }
                  // 是否是秒杀活动
                  if (val.activityType == 5) {
                    item.isSeckill = 1
                  }
                  // 是否是专享商品
                  if (val.activityType == 23) {
                    item.isExclusive = 1
                  }
                  // 是否是预售商品
                  if (val.activityType == 10) {
                    item.isAdvance = 1
                  }
                  // 是否是会员价
                  if (val.activityType == 22) {
                    item.isMember = 1
                  }
                  // 是否是限时降价
                  if (val.activityType == 6) {
                    item.isReduce = 1
                  }
                  // 是否是首单特惠
                  if (val.activityType == 18) {
                    item.isFirstOrder = 1
                  }
                  // 拼接促销活动
                  if (item.activityId == val.activityId) {
                    item.ruleList = ""
                    item.currentRule = val.purchasePrice // 当前规则
                    val.purchasePrice.purchasePriceRule.forEach((pitem, pindex) => {
                      item.ruleList += pitem.name + '或'
                    })
                    if (item.ruleList.length > 0) {
                      item.ruleList = item.ruleList.substr(0, item.ruleList.length - 1);
                    }
                  }
                })
              }
              // 限购样式
              if ((item.cartNumber >= item.prdNumber) || (item.activityLimitMaxNum != null && (item.cartNumber >= item.activityLimitMaxNum)) || (item.activityLimitMaxNum == null && item.limitMaxNum != 0 && (item.cartNumber >= item.limitMaxNum))) {
                item.limitMaxStyle = 1
              }
              if ((item.cartNumber <= 1) || (item.activityLimitMinNum != null && (item.cartNumber <= item.activityLimitMinNum)) || (item.activityLimitMaxNum == null && item.limitBuyNum != 0 && (item.cartNumber <= item.limitBuyNum))) {
                item.limitMinStyle = 1
              }
            })
            // 找到加价购活动
            var ele = this.data.purchaseList[key].findIndex(item => item.activityType === 7)
            this.data.purchaseList[key].push(this.data.purchaseList[key][ele].currentRule)
          }
        }
        
        this.setData({
          cartGoodsList: this.data.cartGoodsList,
          fullList: this.data.fullList,
          purchaseList: this.data.purchaseList,
        })
      } else {
        util.showModal('提示', res.message)
        return false
      }
    })

    this.selectComponent('#recommend').requestData() //推荐商品请求
  },

  // 更改选中状态
  checkedToggle (e) {
    let cartId = e.currentTarget.dataset.cart_id
    let isChecked = e.currentTarget.dataset.is_checked
    let buyStatus = e.currentTarget.dataset.buy_status
    if (buyStatus == 0) {
      util.showModal('提示', '当前商品不可购买');
      return false
    }
    util.api('/api/wxapp/cart/switch', res => {
      if (res.error === 0) {
        this.requestCartList()
      }
    }, { cartIds: [cartId], isChecked: isChecked ? 0 : 1 })
  },

  // 更改全选状态
  changeAllChecked () {
    // 普通商品
    let checkFlag1 = 0
    let cartIds1 = []
    if (this.data.cartGoodsList && this.data.cartGoodsList.length > 0) {
      checkFlag1 = this.data.cartGoodsList.findIndex(item => item.buyStatus == 0) == -1 ? 0 : 1
      cartIds1 = this.data.cartGoodsList.map(item => { return item.cartId })
    }
    
    // 满折满减
    let checkFlag2 = 0
    let cartIds2 = []
    if (this.data.fullList) {
      let data = []
      for (var key in this.data.fullList) {
        this.data.fullList[key].forEach(item => {
          if (item.cartId) { data.push(item) }
        })
      }
      checkFlag2 = data.findIndex(item => item.buyStatus == 0) == -1 ? 0 : 1
      cartIds2 = data.map(item => { return item.cartId })
    }
    
    // 加价购
    let checkFlag3 = 0
    let cartIds3 = []
    if (this.data.purchaseList) {
      let data = []
      for (var key in this.data.purchaseList) {
        this.data.purchaseList[key].forEach(item => {
          if (item.cartId && item.activityType == 7) { data.push(item) }
        })
      }
      checkFlag3 = data.findIndex(item => item.buyStatus == 0) == -1 ? 0 : 1
      cartIds3 = data.map(item => { return item.cartId })
    }
    if ((checkFlag1 == 1 || checkFlag2 == 1 || checkFlag3 == 1) && this.data.isAllCheck == 0) {
      util.showModal('提示', '当前清单不可全选');
      return false
    }
    let isAllCheck = this.data.isAllCheck ? 0 : 1
    let cartIds = []
    cartIds1.forEach(item => { cartIds.push(item) })
    cartIds2.forEach(item => { cartIds.push(item) })
    cartIds3.forEach(item => { cartIds.push(item) })
    util.api('/api/wxapp/cart/switch', res => {
      if (res.error === 0) {
        this.requestCartList()
      } else {
        util.showModal('提示', res.message)
        return false
      }
    }, { cartIds: cartIds, isChecked: isAllCheck })
  },

  // 更改商品数量
  goodsNumChange (e) {
    let type = e.currentTarget.dataset.type;
    let cart_id = e.currentTarget.dataset.cart_id;
    let prdId = e.currentTarget.dataset.prd_id;
    let cartNumber = e.currentTarget.dataset.cart_number;
    let activityType = e.currentTarget.dataset.activity_type;
    let activityId = e.currentTarget.dataset.activity_id;

    let item = e.currentTarget.dataset.item
    let value = 0
    if (type == 'add') {
      value = cartNumber + 1
    } else {
      value = cartNumber - 1
    }

    if (value > item.prdNumber) {
      util.showModal('提示', '购买数量不能大于商品库存');
      return false
    }
    if (item.activityLimitMaxNum != null && value > item.activityLimitMaxNum) {
      util.showModal('提示', '最大限购量为' + item.activityLimitMaxNum + '个');
      return false
    }
    if (item.activityLimitMinNum != null && value < item.activityLimitMinNum) {
      if(type != 'add'){
        util.showModal('提示', '最小限购量为' + item.activityLimitMinNum + '个，是否要删除当前商品',()=>{
          this.delCartGoods({currentTarget:{dataset:{cart_id}}})
        },true,'取消','删除');
      } else {
        util.showModal('提示', '最小限购量为' + item.activityLimitMinNum + '个');
      }
      return false
    }
    if (item.activityLimitMaxNum == null && item.limitMaxNum != 0 && value > item.limitMaxNum) {
      util.showModal('提示', '最大限购量为' + item.limitMaxNum + '个');
      return false
    }
    if (item.activityLimitMaxNum == null && item.limitBuyNum != 0 && value < item.limitBuyNum) {
      if(type != 'add'){
        util.showModal('提示', '最小限购量为' + item.limitBuyNum + '个，是否要删除当前商品',()=>{
          this.delCartGoods({currentTarget:{dataset:{cart_id}}})
        },true,'取消','删除');
      } else {
        util.showModal('提示', '最小限购量为' + item.limitBuyNum + '个');
      }
      return false
    }
    if (value < 1) {
      util.showModal('提示', '最小限购量为1个，是否要删除当前商品',()=>{
        this.delCartGoods({currentTarget:{dataset:{cart_id}}})
      },true,'取消','删除');
      return false
    }

    util.api('/api/wxapp/cart/add', res => {
      if (res.error == 0) {
        this.requestCartList()
      } else {
        util.showModal('提示', res.message)
        return false
      }
    }, {
      goodsNumber: type == 'add' ? cartNumber + 1 : cartNumber - 1,
      prdId,
      activityType,
      activityId,
      type: 1
    })
  },

  // 校验商品数量
  checkNumber (e) {
    var value = Number(e.detail.value)
    var cartId = e.currentTarget.dataset.cart_id
    var prdId = e.currentTarget.dataset.prd_id
    var activityType = e.currentTarget.dataset.activity_type
    var activityId = e.currentTarget.dataset.activity_id

    var item = e.currentTarget.dataset.item
    var re = /^[1-9]\d*$/
    if (!value || !re.test(value)) {
      util.showModal('提示', '购买数量输入不正确');
      this.requestCartList()
      return false
    }
    if (value > item.prdNumber) {
      util.showModal('提示', '购买数量不能大于商品库存');
      this.requestCartList()
      return false
    }
    if (item.activityLimitMaxNum != null && value > item.activityLimitMaxNum) {
      util.showModal('提示', '最大限购量为' + item.activityLimitMaxNum + '个');
      this.requestCartList()
      return false
    }
    if (item.activityLimitMinNum != null && value < item.activityLimitMinNum) {
      util.showModal('提示', '最小限购量为' + item.activityLimitMinNum + '个');
      this.requestCartList()
      return false
    }
    if (item.activityLimitMaxNum == null && item.limitMaxNum != 0 && value > item.limitMaxNum) {
      util.showModal('提示', '最大限购量为' + item.limitMaxNum + '个');
      this.requestCartList()
      return false
    }
    if (item.activityLimitMaxNum == null && item.limitBuyNum != 0 && value < item.limitBuyNum) {
      util.showModal('提示', '最小限购量为' + item.limitBuyNum + '个');
      this.requestCartList()
      return false
    }

    util.api('/api/wxapp/cart/add', res => {
      if (res.error == 0) {
        this.requestCartList()
      } else {
        util.showModal('提示', res.message)
        return false
      }
    }, {
      goodsNumber: value,
      prdId,
      activityType,
      activityId,
      type: 1
    })
  },

  //触摸改变
  drawStart (e) {
    var dataId = e.currentTarget.dataset.id;
    var touch = e.changedTouches[0];
    var check_action = e.currentTarget.dataset.check_action;
    if (check_action == 0) {
      var cardTeams = this.data.cartGoodsList;
    } else if (check_action == 1) {
      var activity_ids = e.currentTarget.dataset.activity_ids;
      var cardTeams = this.data.purchaseList[activity_ids];
    } else if (check_action == 2) {
      var activity_ids = e.currentTarget.dataset.activity_ids;
      var cardTeams = this.data.fullList[activity_ids];
    } else if (check_action == 99) {
      var cardTeams = this.data.invalidCartList;
    }
    for (var i in cardTeams) {
      var data = cardTeams[i];
      if (data.cartId == dataId) {
        data.startX = touch.clientX;
        data.startY = touch.clientY;
        data.lastX = touch.clientX;
      }
    }
    this.direction = 0;  // 0 初始化,1 上下 2 左右
  },
  drawMove (e) {
    var dataId = e.currentTarget.dataset.id;
    var touch = e.changedTouches[0];
    var check_action = e.currentTarget.dataset.check_action;
    if (check_action == 0) {
      var cardTeams = this.data.cartGoodsList;
    } else if (check_action == 1) {
      var activity_ids = e.currentTarget.dataset.activity_ids;
      var cardTeams = this.data.purchaseList[activity_ids];
    } else if (check_action == 2) {
      var activity_ids = e.currentTarget.dataset.activity_ids;
      var cardTeams = this.data.fullList[activity_ids];
    } else if (check_action == 99) {
      var cardTeams = this.data.invalidCartList;
    }
    for (var i in cardTeams) {
      var data = cardTeams[i];
      if (data.cartId == dataId) {
        if (this.direction == 0) {
          var x = Math.abs(data.startX - touch.clientX);
          var y = Math.abs(data.startY - touch.clientY);
          if (x > y && x > 5) {
            this.direction = 2; // 左右移动距离超过5px，且大于上下移动距离，则为左右移动方向
          }
          if (x < y && y > 5) {
            this.direction = 1;
          }
        }
        if (this.direction == 1 || this.direction == 0) {
          continue;
        }

        var r = parseFloat(data.right);
        if (isNaN(r)) r = 0.0;
        var tmp = data.lastX - touch.clientX;
        data.lastX = touch.clientX;
        if (tmp > 0) {
          // 左移
          if (-r == this.data.maxRight) continue;
          data.right = -1 * (-r + tmp);
          if (- data.right > this.data.maxRight) data.right = -this.data.maxRight;
        } else {
          if (r == 0) continue;
          data.right = -1 * (-r + tmp);
          if (- data.right < 0) data.right = 0;
        }
      }
    }
    this.setData({
      cartGoodsList: this.data.cartGoodsList,
      fullList: this.data.fullList,
      purchaseList: this.data.purchaseList,
      invalidCartList: this.data.invalidCartList
    });
  },
  // 触摸结束
  drawEnd (e) {
    if (this.direction != 2) {
      this.direction = 0;
      return;
    }
    this.direction = 0;
    var dataId = e.currentTarget.dataset.id;
    var touch = e.changedTouches[0];
    var check_action = e.currentTarget.dataset.check_action;
    if (check_action == 0) {
      var cardTeams = this.data.cartGoodsList;
    } else if (check_action == 1) {
      var activity_ids = e.currentTarget.dataset.activity_ids;
      var cardTeams = this.data.purchaseList[activity_ids];
    } else if (check_action == 2) {
      var activity_ids = e.currentTarget.dataset.activity_ids;
      var cardTeams = this.data.fullList[activity_ids];
    } else if (check_action == 99) {
      var cardTeams = this.data.invalidCartList;
    }
    for (var i in cardTeams) {
      var data = cardTeams[i];
      if (data.cartId == dataId) {
        if (data.startX > touch.clientX) {
          data.right = -1 * this.data.maxRight;
        } else {
          data.right = 0;
        }
      }
    }
    this.setData({
      cartGoodsList: this.data.cartGoodsList,
      fullList: this.data.fullList,
      purchaseList: this.data.purchaseList,
      invalidCartList: this.data.invalidCartList
    });
  },

  // 删除购物车商品
  delCartGoods (e) {
    let cartId = e.currentTarget.dataset.cart_id
    util.api('/api/wxapp/cart/remove', (res) => {
      if (res.error == 0) {
        this.requestCartList()
      }
    }, { cartId: cartId })
  },

  // 清除无效购物车列表
  clearCart () {
    let cartIds = []
    if (this.data.invalidCartList && this.data.invalidCartList.length > 0) {
      cartIds = this.data.invalidCartList.map(item => { return item.cartId })
    }
    util.api('/api/wxapp/cart/removes', res => {
      if (res.error === 0) {
        this.requestCartList()
      }
    }, { cartIds: cartIds })
  },

  // 显示促销弹窗
  proClcik: function (e) {
    var that = this;
    var cartId = e.currentTarget.dataset.cart_id;
    var activityType = e.currentTarget.dataset.activity_type;
    // 活动id
    var value = e.currentTarget.dataset.value ? e.currentTarget.dataset.value : null;
    that.setData({
      activityType: activityType
    })
    if (activityType == null) {
      // 不参与活动
      that.setData({ isType: 0 })
      that.data.cartGoodsList.forEach((item, index) => {
        if (item.cartId == cartId) {
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
    } else if (activityType == 21) {
      // 满折满减
      that.setData({ isType: 2 })
      for (var key in that.data.fullList) {
        if (key == value) {
          that.data.fullList[key].forEach(item => {
            if (item.cartId == cartId) {
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
        }
      }
    } else if (activityType == 7) {
      // 加价购
      that.setData({ isType: 1 })
      for (var key in that.data.purchaseList) {
        if (key == value) {
          that.data.purchaseList[key].forEach(item => {
            if (item.cartId == cartId) {
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
        }
      }
    }
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
  // 关闭促销弹窗
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

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    this.selectComponent('#recommend').requestData()
  },

  // 列表无数据跳转
  toIndex () {
    util.jumpLink('pages/index/index', 'navigateTo')
  },

  // 去商品详情
  toGoods: function (e) {
    var goodsId = e.currentTarget.dataset.goods_id;
    util.navigateTo({
      url: '/pages/item/item?gid='+ goodsId,
    })
  },

  //  去结算
  toCheckOut () {
    // 普通商品
    let goodsList1 = []
    if (this.data.cartGoodsList && this.data.cartGoodsList.length > 0) {
      goodsList1 = this.data.cartGoodsList.filter(item => item.isChecked === 1).map(item => {
        let { goodsId, prdPrice: prdRealPrice, cartNumber: goodsNum, productId: prdId } = item
        return { goodsId, prdRealPrice, goodsNum, prdId, isCart: 1 }
      })
    }
    // 满折满减
    let goodsList2 = []
    if (this.data.fullList) {
      let data = []
      for (var key in this.data.fullList) {
        this.data.fullList[key].forEach(item => {
          if (item.cartId) { data.push(item) }
        })
      }
      goodsList2 = data.filter(item => item.isChecked === 1).map(item => {
        let { goodsId, prdPrice: prdRealPrice, cartNumber: goodsNum, productId: prdId } = item
        return { goodsId, prdRealPrice, goodsNum, prdId, isCart: 1 }
      })
    }
    // 加价购
    let goodsList3 = []
    if (this.data.purchaseList) {
      let data = []
      for (var key in this.data.purchaseList) {
        this.data.purchaseList[key].forEach(item => {
          if (item.cartId) { data.push(item) }
        })
      }
      goodsList3 = data.filter(item => item.isChecked === 1).map(item => {
        let { goodsId, prdPrice: prdRealPrice, cartNumber: goodsNum, productId: prdId } = item
        return { goodsId, prdRealPrice, goodsNum, prdId, isCart: 1 }
      })
    }
    let length = goodsList1.length + goodsList2.length + goodsList3.length
    if (length == 0) {
      util.showModal("提示", "请选择您想要购买的商品");
      return false;
    }
    let goodsList = []
    goodsList1.forEach(item => { goodsList.push(item) })
    goodsList2.forEach(item => { goodsList.push(item) })
    goodsList3.forEach(item => { goodsList.push(item) })
    util.jumpLink(`pages/checkout/checkout?goodsList=${JSON.stringify(goodsList)}`, "navigateTo")
  },

  // 跳转秒杀抢购
  toSeckill: function (e) {
    util.jumpLink(e.currentTarget.dataset.link);
  },

  // 跳转满折满减商品
  toFullpage: function (e) {
    var activityId = e.currentTarget.dataset.activity_id;
    var storeId = e.currentTarget.dataset.store_id;
    util.navigateTo({
      url: '/pages/fullprice/fullprice?strategy_id=' + activityId + '&store_id=' + storeId,
    })
  },

  // 跳转加价购商品列表
  toPurchase: function (e) {
    var activityId = e.currentTarget.dataset.activity_id;
    var storeId = e.currentTarget.dataset.store_id;
    util.navigateTo({
      url: '/pages/maingoodslist/maingoodslist?identity_id=' + activityId + '&store_id=' + storeId,
    })
  },
  // 打开优惠券礼包弹窗
  viewCouponPackage(){
    this.setData({
      showCouponPackageDialog:true
    })
  },
  onShow: function () {
    this.requestCartList()
  }
})