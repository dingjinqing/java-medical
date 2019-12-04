// pages1/shopcheckout/shopcheckout.js
// 门店买单
var util = require('../../utils/util.js');

global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    storeId: '', // 门店id
    userId: '', // 用户id
    orderInfo: {
      score: '', // 用户积分
      account: '', // 用户余额
      invoiceSwitch: false, // 是否默认选中发票开关
      memberCardList: [], // 可用会员卡列表
      shopLogo: '', // 店铺logo
      shopBusinessState: 1, // 店铺营业状态
      storeBusinessState: 1, // 门店营业状态
      storeBuy: 1, // 门店是否支持买单开关
      delFlag: 0, // 门店是否已删除 
    },
    payInfo: {
      orderAmount: '', // 总金额
      cardDisAmount: 0, // 会员卡折扣抵扣金额
      cardAmount: 0, // 会员卡余额抵扣金额
      scoreAmount: 0, // 积分抵扣金额
      balanceAmount: 0, // 余额抵扣金额
      moneyPaid: '', // 订单金额
      totalDiscount: 0, // 总折扣
      invoiceId: '', // 发票id
      formId: '', // 微信formId
      remark: '' // 留言
    },

    showCardDialog: false, // 会员卡弹窗是否显示
    useCard: {}, // 选中的会员卡
    invoiceInfo: {}, // 选择的发票
    discount_block: 0 // 是否展开折扣详情
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    let that = this
    let storeId = options.storeId
    this.setData({
      storeId: storeId
    })
    this.initOrderInfo()
  },

  initOrderInfo () {
    // 请求用户信息
    let params = {
      storeId: this.data.storeId,
      userId: util.getCache('user_id')
    }
    let that = this
    util.api('/api/wxapp/store/payOrder', function (res) {
      if (res.error === 0) {
        console.log(res)
        let info = res.content;
        if (info.storeBuy === 0) {
          util.showModal('提示', '商家已禁止使用门店买单功能', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (info.delFlag == 1) {
          util.showModal('提示', '该门店已删除', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (info.shopBusinessState == 0) {
          util.showModal('提示', '该店铺未营业，随便逛逛', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (info.storeBusinessState == 0) {
          util.showModal('提示', '该门店未营业，随便逛逛', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
        }
        //会员卡
        var src_yes = that.data.imageUrl + 'image/wxapp/selected.png';
        var src_no = that.data.imageUrl + 'image/wxapp/icon_rectangle.png';
        var useCard = {}
        if (info.memberCardList && info.memberCardList.length > 0) {
          info.memberCardList.forEach((item, i) => {
            if (item.discount == null) {
              item.discount = 10
            }
            item.src_yes = src_yes
            item.src_no = src_no
          })
          useCard = info.memberCardList.find(item => item.isDefault == 1)
          if (typeof (useCard) === 'undefined') {
            useCard = info.memberCardList[0]
          }
        }
        // 发票开关
        if (info.invoiceSwitch) {
          that.initInvoice()
        }
        that.setData({
          orderInfo: info,
          useCard: useCard
        })
      }
    }, params)
  },

  // 获取发票
  initInvoice () {

  },

  // 输入要支付的金额
  inputMoney: function (e) {
    let money = e.detail.value;
    if (money) {
      //限制只能输入一个点
      var ipt_money_arr = money.toString().split('.');
      if (ipt_money_arr.length > 2) {
        money = ipt_money_arr[0] + '.' + ipt_money_arr[1];
      }
      //小数点后只能输2位
      var temp = money.toString().split('.');
      if (temp.length == 2) {
        if (temp[1].length >= 3) {
          money = temp[0] + '.' + temp[1].substring(0, 2);
        }
      }
    } else if (money == '') {
      money = 0;
    }
    this.setData({
      'payInfo.orderAmount': money
    })
    this.computedMoney(money)
  },

  // 计算金额
  computedMoney: function () {
    let payInfo = this.data.payInfo
    let money = payInfo.orderAmount
    let useCard = this.data.useCard
    // 如果会员卡有折扣
    if (useCard) {
      let discount = this.data.useCard.discount
      if (discount) {
        payInfo.cardDisAmount = ((1 - discount / 10) * money).toFixed(2)
      }
    }
    payInfo.totalDiscount = parseFloat(payInfo.cardDisAmount + payInfo.cardAmount + payInfo.scoreAmount + payInfo.balanceAmount).toFixed(2)
    payInfo.moneyPaid = parseFloat(payInfo.orderAmount - payInfo.totalDiscount).toFixed(2)
    this.setData({
      payInfo: payInfo
    })
  },

  // 查看折扣详情
  look_detail () {
    let discount_block = this.data.discount_block
    if (discount_block == 0) {
      discount_block = 1;
    } else {
      discount_block = 0;
    }
    this.setData({
      discount_block: discount_block
    })
  },

  // 选择会员卡弹窗 
  cardClick () {
    if (!this.data.payInfo.orderAmount) {
      util.showModal('', '请输入消费金额')
      return false
    }
    this.setData({
      showCardDialog: true
    })
  },

  // 选择会员卡回调
  getSelectCard (e) {
    let cardNo = e.detail
    let useCard = this.data.orderInfo.memberCardList.find(item => item.cardNo === cardNo)
    console.log(useCard)
    this.setData({
      useCard: useCard
    })
    this.computedMoney()
  },

  score_money: function (e) {
    score_money_input = e.detail.value;
    if (isNaN(score_money_input) || !isNaN(score_money_input) && score_money_input < 0 || score_money_input % score_ratio != 0) {
      var tishi1 = "积分数量必须大于等于" + score_ratio + "的整数倍";
      util.showModal('', tishi1);
      this.setData({
        score_money_input: '',
        score_money: 0
      })
      if (create_order.score_dis) {
        this.data.info.total_price = Number(this.data.info.total_price) + Number(create_order.score_dis);
        create_order.score_dis = 0;
        this.data.info.total_price = this.data.info.total_price.toFixed(2);
        this.setData({
          info: this.data.info,
        })
      }
      return false;
    } else {
      if (score_money_input > parseInt(score_enable)) {
        var tishi2 = "最多可以使用：" + parseInt(score_enable) + "积分";
        util.showModal('', tishi2);
        this.setData({
          score_money_input: '',
          score_money: 0
        })
        if (create_order.score_dis) {
          this.data.info.total_price = Number(this.data.info.total_price) + Number(create_order.score_dis);
          create_order.score_dis = 0;
          this.data.info.total_price = this.data.info.total_price.toFixed(2);
          this.setData({
            info: this.data.info,
          })
        }
        return false;
      }
    }
    if (this.data.score_money > 0) {
      this.data.info.total_price = parseFloat(this.data.info.total_price) + parseFloat(score_money);
      score_money = 0;
    }
    if (score_money_input != '') {
      if (this.data.info.total_price - parseFloat(score_money_input / score_ratio) < 0) {
        util.showModal('提示', '积分抵扣金额不得大于支付金额');
        this.setData({
          score_money_input: '',
          score_money: 0
        })
        return false;
      }
      this.data.info.total_price -= parseFloat(score_money_input / score_ratio);
      score_money = (score_money_input / score_ratio).toFixed(2);
    }
    this.data.info.total_price = Number(this.data.info.total_price).toFixed(2);
    this.data.info.discount_money = (Number(ipt_money) - Number(this.data.info.total_price)).toFixed(2);//总的折扣数
    this.setData({
      info: this.data.info,
      score_money: score_money,
      score_money_input: score_money_input
    })
    create_order.score_dis = score_money;
  },
  user_account: function (e) {
    user_account_input = e.detail.value;
    if (isNaN(user_account_input) || !isNaN(user_account_input) && user_account_input < 0) {
      util.showModal('', "请输入正确的余额");
      this.setData({
        user_account_input: '',
        account_money: 0
      })
      if (create_order.account_dis) {
        this.data.info.total_price = Number(this.data.info.total_price) + Number(create_order.account_dis);
        create_order.account_dis = 0;
        this.data.info.total_price = this.data.info.total_price.toFixed(2);
        this.setData({
          info: this.data.info,
        })
      }
      return false;
    }
    else {
      if (user_account_input > parseInt(this.data.info.account)) {
        var tishi3 = "最多可以使用：" + this.data.info.account + "余额";
        util.showModal('提示', tishi3);
        this.setData({
          user_account_input: '',
          account_money: 0
        })
        if (create_order.account_dis) {
          this.data.info.total_price = Number(this.data.info.total_price) + Number(create_order.account_dis);
          create_order.account_dis = 0;
          this.data.info.total_price = this.data.info.total_price.toFixed(2);
          this.setData({
            info: this.data.info,
          })
        }
        this.data.info.discount_money = (Number(ipt_money) - Number(this.data.info.total_price)).toFixed(2);//总的折扣数
        this.setData({
          info: this.data.info,
        })
        return false;
      }
    }
    if (this.data.account_money > 0) {
      this.data.info.total_price = parseFloat(this.data.info.total_price) + parseFloat(this.data.account_money);
      this.data.info.total_price = (this.data.info.total_price).toFixed(2);
      account_money = 0;
    }
    if (user_account_input != '') {
      if (parseFloat(this.data.info.total_price) - parseFloat(user_account_input) < 0) {
        util.showModal('提示', '使用余额不得超过支付金额');
        this.setData({
          user_account_input: '',
          account_money: 0
        })
        if (create_order.account_dis) {
          this.data.info.total_price = Number(this.data.info.total_price).toFixed(2);
          create_order.account_dis = 0;
          this.setData({
            info: this.data.info,
          })
        }
        this.data.info.discount_money = (Number(ipt_money) - Number(this.data.info.total_price)).toFixed(2);//总的折扣数
        this.setData({
          info: this.data.info,
        })
        return false;
      }
      this.data.info.total_price = parseFloat(this.data.info.total_price) - parseFloat(user_account_input);
      this.data.info.total_price = Number(this.data.info.total_price).toFixed(2);
      account_money = Number(user_account_input).toFixed(2);
    }
    this.data.info.discount_money = (Number(ipt_money) - Number(this.data.info.total_price)).toFixed(2);//总的折扣数
    this.setData({
      info: this.data.info,
      account_money: account_money
    })
    create_order.account_dis = account_money;
  },
  card_discount: function (e) {
    let value = e.detail.value
    console.log(value)
    if (value) {

    }
  },
  chooseInvoice: function () {
    var that = this;
    wx.chooseInvoiceTitle({
      success (res) {
        invoice_info.invoice_title = res.title;
        var ress = JSON.stringify(res);
        util.api('/api/wxapp/invoice/choose', function (e) {
          create_order.invoice = e.content;
        }, { invoice_info: ress })
        that.setData({
          invoice_info: invoice_info
        })
      },
      fail () {
        util.showModal('', "获取发票信息失败");
      }
    })
  },
  remarkInput: function (e) {
    text_area = e.detail.value;
    create_order.remark = text_area;
  },
  toConfirm: function (e) {
    if (this.data.info.shopBusinessState == 0) {
      util.showModal('提示', '该店铺未营业，随便逛逛', function () {
        util.reLaunch({
          url: '/pages/index/index'
        })
      });
      return false;
    }
    if (this.data.info.storeBusinessState == 0) {
      util.showModal('提示', '该门店未营业，随便逛逛', function () {
        util.reLaunch({
          url: '/pages/index/index'
        })
      });
      return false;
    }
    if (this.data.payInfo.orderAmount == '' || !ipt_money) {
      util.showModal('', '请输入消费金额');
      return;
    }
    if (this.data.payInfo.orderAmount == 0) {
      util.showModal('', '支付金额不能为0');
      return;
    }
    create_order.total_price = this.data.info.total_price;
    create_order.openid = util.getCache('openid');
    create_order.form_id = e.detail.formId;
    var payInfo = JSON.stringify(create_order);
    var _this = this;
    if (is_submit) return;
    is_submit = true;
    util.api('/api/wxapp/store/checkout', function (res) {
      if (res.error == 0) {
        if (create_order.total_price > 0) {
          var order_sn = res.content.order_sn;
          if (typeof (res.content.timeStamp) != 'undefined') {
            wx.requestPayment({
              'timeStamp': res.content.timeStamp,
              'nonceStr': res.content.nonceStr,
              'package': res.content.package,
              'signType': typeof res.content.signType == "undefined" ? 'MD5' : res.content.signType,
              'paySign': res.content.paySign,
              'success': function (res) {
                util.toast_success('支付成功');
                util.redirectTo({
                  url: '/pages/shoppayInfo/shoppayInfo?order_sn=' + order_sn,
                })
              },
              'fail': function (res) {
                discount_block = 0;
                _this.setData({
                  discount_block: discount_block
                })
                util.toast_fail('支付失败');
                var re_load = { store_id: create_order.store_id }
                _this.onLoad(re_load)
              },
              'complete': function (res) {
              }
            });
          } else {
            discount_block = 0;
            _this.setData({
              discount_block: discount_block
            })
            util.toast_fail('支付失败');
            var re_load = { store_id: create_order.store_id }
            _this.onLoad(re_load)

          }
        } else {
          var order_sn = res.content;
          util.toast_success('支付成功');
          util.redirectTo({
            url: '/pages/shoppayInfo/shoppayInfo?order_sn=' + order_sn,
          })
        }
      } else {
        util.showModal('提示', res.message, function () {
          util.reLaunch({
            url: '/pages/index/index',
          })
        }, false);
      }
      is_submit = false;
    }, { payInfo: payInfo })
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      path: "/pages/shopcheckout/shopcheckout?store_id=" + create_order.store_id + "&invite_id=" + util.getCache('user_id')
    }
  }
})