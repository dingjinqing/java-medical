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
    storePayInfo: {
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
    orderInfo: {
      totalPrice: '', // 总金额
      cardChargeDis: 0, // 会员卡折扣抵扣金额
      card_money: 0, // 会员卡余额抵扣金额
      scoreDis: 0, // 积分抵扣金额
      accountDis: 0, // 余额抵扣金额
      orderAmount: '', // 订单金额
      invoiceId: '', // 发票id
      formId: '', // 微信formId
      remark: '' // 留言
    },

    showCardDialog: false, // 会员卡弹窗是否显示
    useCard: {} // 选中的会员卡
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

    // 请求用户信息
    let params = {
      storeId: storeId,
      userId: util.getCache('user_id')
    }
    util.api('/api/wxapp/store/info', function (res) {
      console.log(res)
      var info = res.content;
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
      if (info.storeBusinessState == 0) {
        util.showModal('提示', '该店铺未营业，随便逛逛', function () {
          util.reLaunch({
            url: '/pages/index/index'
          })
        });
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
      var bg;
      if (info.memberCardList && info.memberCardList.length > 0) {
        info.memberCardList.forEach((item, i) => {
          if (item.discount == null) {
            item.discount = 10
          }
          item.src_yes = src_yes
          item.src_no = src_no
        })
      }
      for (var i in card_arr) {
        if (card_arr[i].discount == null) {
          card_arr[i].discount = 10;
        }
        card_arr[i].src_yes = src_yes;
        card_arr[i].src_no = src_no;
        card_arr[i].card_src = src_no;
        card_arr[i].bg = bg;
        card_arr[i].bg_img = that.data.imageUrl + card_arr[i].bg_img;
        card_arr[i].shop_logo = info.shop_logo;
        if (info.shop_logo == null) {//如果店铺头像为空
          card_arr[i].shop_logo = that.data.imageUrl + 'image/wxapp/shop_logo_default.png';
        }
        if (card_arr[i].bg_type == 1) {//会员卡的背景显示
          card_arr[i].bg = "url('" + card_arr[i].bg_img + "') no-repeat";
        } else {
          card_arr[i].bg = card_arr[i].bg_color;
        }
        if (card_arr[i].is_default == 1) {
          useCard.card_name = card_arr[i].card_name;
          useCard.discount = (1 - card_arr[i].discount / 10).toFixed(3);
          card_arr[i].card_src = src_yes;
          if (card_arr[i].money && card_arr[i].money > 0) {
            useCard.money = card_arr[i].money;
          }
          create_order.card_no = card_arr[i].card_no;
        } else {
          useCard.card_name = card_arr[0].card_name;
          useCard.discount = (1 - card_arr[0].discount / 10).toFixed(3);
          card_arr[0].card_src = src_yes;
          if (card_arr[0].money && card_arr[0].money > 0) {
            useCard.money = card_arr[0].money;
          }
          create_order.card_no = card_arr[0].card_no;
        }
        if (card_arr[i].card_type == 0 && card_arr[i].buy_score) {
          card_arr[i].buy_score = JSON.parse(card_arr[i].buy_score);
          if (card_arr[i].buy_score[0].value == 0) {
            var arrlen = card_arr[i].buy_score.length;
            if (card_arr[i].buy_score[arrlen - 1].each_money) {
              card_arr[i].buy_score1 = card_arr[i].buy_score.slice(1, arrlen - 1);
            } else {
              card_arr[i].buy_score1 = card_arr[i].buy_score.slice(1);
            }
          }
          if (card_arr[i].buy_score[0].value == 1) {
            card_arr[i].buy_score1 = card_arr[i].buy_score[card_arr[i].buy_score.length - 1];
          }
        }
        if (card_arr[i].expire_time != null) {
          card_arr[i].expire_time = card_arr[i].expire_time.substring(0, 10);
        }
      }
      //发票
      invoice_info.invoice_title = '';
      that.setData({
        storePayInfo: info,
        useCard: info.memberCardList && info.memberCardList.length > 0 ? info.memberCardList[0] : {}
      })
    }, params)
  },

  /**
   * 支付金额输入回调
   */
  totalSpendingInput (e) {
    console.log(e.detail);
    var price = Number(e.detail.value);
    // 限制只能输入一个点
    if (price) {
      var price_arr = price.toString().split('.');
      if (price_arr.length > 2) {
        price = price_arr[0] + '.';
      }
    }
    //小数点后只能输2位
    if (price) {
      var temp = price.toString().split('.');
      if (temp.length == 2) {
        if (temp[1].length >= 3) {
          price = temp[0] + '.' + temp[1].substring(0, 2);
        }
      }
    }
    this.setData({
      totalPrice: price
    })
    if (price) {
      this.setData({
        PointPaymentVisible: true,
        discountVisible: true
      });
    } else {
      this.setData({
        PointPaymentVisible: false,
        discountVisible: false
      })
    }
  },
  chooseCard: function (e) {//选择会员卡
    var name = e.currentTarget.dataset.name
    var card_no = e.currentTarget.dataset.card_no;
    var id = e.currentTarget.dataset.id;
    var discount = e.currentTarget.dataset.discount;
    for (var i in card_arr) {
      card_arr[i].card_src = card_arr[i].src_no;
      if (i == id) {
        card_arr[i].card_src = card_arr[i].src_yes;
        create_order.card_no = card_arr[i].card_no;
        useCard.card_name = card_arr[i].card_name;
        useCard.discount = (1 - card_arr[i].discount / 10).toFixed(3);
        if (card_arr[i].money && card_arr[i].money > 0) {
          useCard.money = card_arr[i].money;
        } else {
          useCard.money = 0;
        }
      }
    }
    this.setData({
      card_arr: card_arr,
      useCard: useCard
    })
  },
  cardConfirm: function (e) {
    if (ipt_money > 0) {
      var start_nomey = parseFloat(ipt_money);
      card_money = (start_nomey * useCard.discount).toFixed(2);
      start_nomey = start_nomey - card_money;
      this.data.info.total_price = start_nomey.toFixed(2);
    }
    if (create_order.account_dis) {
      this.data.info.total_price = (this.data.info.total_price - create_order.account_dis).toFixed(2);
    }
    if (create_order.card_charge_dis) {
      this.data.info.total_price = (this.data.info.total_price - create_order.card_charge_dis).toFixed(2);
    }
    if (create_order.score_dis) {
      this.data.info.total_price = (this.data.info.total_price - create_order.score_dis).toFixed(2);
    }
    //重新选择会员卡后最多可使用的积分数
    if (start_nomey / 2 * score_ratio >= score_num) {
      score_enable = parseInt(score_num) - parseInt(score_num) % score_ratio;
    }
    else if (parseInt(start_nomey / 2 * score_ratio) >= score_ratio) {
      score_enable = parseInt(start_nomey / 2 * score_ratio) - parseInt(start_nomey / 2 * score_ratio) % score_ratio;
    } else {
      score_enable = 0;
    }
    //重新选择会员卡后，是否可以使用积分
    if (start_nomey >= 2) {
      score_block = 1;
      this.setData({
        score_block: score_block
      })
    } else {
      score_block = 0;
      score_money_input = '';
      score_money = 0;
      create_order.score_dis = score_money;
      if (create_order.account_dis) {
        this.data.info.total_price = (start_nomey - create_order.account_dis).toFixed(2);
      }
      if (create_order.card_charge_dis) {
        this.data.info.total_price = (start_nomey - create_order.card_charge_dis).toFixed(2);
      }
      if (!create_order.account_dis && !create_order.card_charge_dis) {
        this.data.info.total_price = start_nomey.toFixed(2);
      }
      this.setData({
        score_block: score_block,
        score_money_input: score_money_input,
        score_money: score_money,
      })
    }
    //重新选择会员卡后，如果已使用的积分大于目前可使用的积分，则清空积分支付
    if (score_money_input > score_enable) {
      score_money_input = '';
      score_money = 0;
      create_order.score_dis = score_money;
      if (create_order.account_dis) {
        this.data.info.total_price = (start_nomey - create_order.account_dis).toFixed(2);
      }
      if (create_order.card_charge_dis) {
        this.data.info.total_price = (start_nomey - create_order.card_charge_dis).toFixed(2);
      }
      if (!create_order.account_dis && !create_order.card_charge_dis) {
        this.data.info.total_price = start_nomey.toFixed(2);
      }
      this.setData({
        score_money_input: score_money_input,
        score_money: score_money,
      })
    }
    //重新选择会员卡后，金额变小支付金额小于时清空积分支付和余额支付
    if (this.data.info.total_price < 0) {
      score_money_input = '';
      score_money = 0;
      create_order.score_dis = score_money;
      user_account_input = '';
      account_money = 0;
      create_order.account_dis = account_money;
      card_discount_input = '';
      charge_money = 0;
      create_order.card_charge_dis = charge_money;
      this.data.info.total_price = start_nomey.toFixed(2);
      this.setData({
        score_money_input: score_money_input,
        score_money: score_money,
        user_account_input: user_account_input,
        account_money: account_money,
        card_discount_input: card_discount_input,
        charge_money: charge_money
      })
    }
    this.data.info.discount_money = (Number(ipt_money) - Number(this.data.info.total_price)).toFixed(2);//总的折扣数
    this.setData({
      info: this.data.info,
      useCard: useCard,
      cardMode: true,
      card_money: card_money,
      score_enable: score_enable
    })
    create_order.card_dis = card_money;
  },
  cardClick: function (e) {//会员卡弹框显示
    if (ipt_money == 0) {
      util.showModal('', '请输入消费金额');
      return;
    }
    this.setData({
      // cardMode: false
      showCardDialog: true
    })
  },
  cardCancel: function (e) {//关闭会员卡弹框
    this.setData({
      cardMode: true
    })
  },
  /**
   * 输入要支付的金额
   */
  inputMoney: function (e) {
    ipt_money = e.detail.value;
    if (ipt_money) {//限制只能输入一个点
      var ipt_money_arr = ipt_money.toString().split('.');
      if (ipt_money_arr.length > 2) {
        ipt_money = ipt_money_arr[0] + '.';
      }
    }
    //小数点后只能输2位
    var isNumeric = ipt_money;
    if (isNumeric) {
      var temp = isNumeric.toString().split('.');
      if (temp.length == 2) {
        if (temp[1].length >= 3) {
          isNumeric = temp[0] + '.' + temp[1].substring(0, 2);
        }
      }
    }
    ipt_money = isNumeric;
    this.setData({
      ipt_money: ipt_money
    })
    if (ipt_money == '') {
      ipt_money = 0;
      score_block = 0;
      this.setData({
        score_block: score_block
      })
    }
    create_order.order_amount = ipt_money;
    this.data.info.total_price = Number(ipt_money).toFixed(2);
    this.data.info.ipt_money = ipt_money;
    //会员卡默认折扣
    if (card_arr.length > 0) {
      if (ipt_money >= 0) {
        var start_nomey = ipt_money;
        card_money = (start_nomey * useCard.discount).toFixed(2);
        start_nomey = start_nomey - card_money;
      }
      this.data.info.ipt_money = ipt_money;
      this.data.info.total_price = Number(start_nomey).toFixed(2);
      create_order.card_dis = card_money;
      //最多可使用的积分数
      if (start_nomey / 2 * score_ratio >= score_num) {
        score_enable = parseInt(score_num) - parseInt(score_num) % score_ratio;
      }
      else if (parseInt(start_nomey / 2 * score_ratio) >= score_ratio) {
        score_enable = parseInt(start_nomey / 2 * score_ratio) - parseInt(start_nomey / 2 * score_ratio) % score_ratio;
      } else {
        score_enable = 0;
      }
    } else {
      //最多可使用的积分数
      if (ipt_money / 2 * score_ratio >= score_num) {
        score_enable = parseInt(score_num) - parseInt(score_num) % score_ratio;
      }
      else if (parseInt(ipt_money / 2 * score_ratio) >= score_ratio) {
        score_enable = parseInt(ipt_money / 2 * score_ratio) - parseInt(ipt_money / 2 * score_ratio) % score_ratio;
      } else {
        score_enable = 0;
      }
    }
    if (create_order.account_dis) {
      this.data.info.total_price = (this.data.info.total_price - create_order.account_dis).toFixed(2);
    }
    if (create_order.card_charge_dis) {
      this.data.info.total_price = (this.data.info.total_price - create_order.card_charge_dis).toFixed(2);
    }
    if (create_order.score_dis) {
      this.data.info.total_price = (this.data.info.total_price - create_order.score_dis).toFixed(2);
    }
    //结算金额修改后，金额变小支付金额小于时清空积分支付和余额支付
    if (this.data.info.total_price < 0) {
      score_block = 0;
      score_money_input = '';
      score_money = 0;
      create_order.score_dis = score_money;
      user_account_input = '';
      account_money = 0;
      create_order.account_dis = account_money;
      card_discount_input = '';
      charge_money = 0;
      create_order.card_charge_dis = charge_money;
      if (card_arr.length > 0) {
        this.data.info.total_price = start_nomey.toFixed(2);
      } else {
        this.data.info.total_price = Number(ipt_money).toFixed(2);
        discount_block = 0;
        this.setData({
          discount_block: discount_block
        })
      }
      this.setData({
        score_money_input: score_money_input,
        score_money: score_money,
        user_account_input: user_account_input,
        account_money: account_money,
        score_block: score_block,
        card_discount_input: card_discount_input,
        charge_money: charge_money
      })
    }
    //积分是否可使用
    //只有在订单金额(会员卡折扣后)大于等于2且可用积分大于等于100的时候，才能使用积分去抵扣金额
    if (this.data.info.total_price >= 2 && score_num >= 100) {
      score_block = 1;
      this.setData({
        score_block: score_block
      })
    } else {
      score_block = 0;
      this.setData({
        score_block: score_block
      })
      if (create_order.score_dis) {
        score_money_input = '';
        score_money = 0;
        this.data.info.total_price = (Number(this.data.info.total_price) + Number(create_order.score_dis)).toFixed(2);
        create_order.score_dis = score_money;
        this.setData({
          score_money: score_money,
          score_money_input: score_money_input
        })
      }
    }
    this.data.info.discount_money = (Number(ipt_money) - Number(this.data.info.total_price)).toFixed(2);//总的折扣数
    this.data.info.total_price = Number(this.data.info.total_price).toFixed(2);
    this.setData({
      info: this.data.info,
      score_enable: score_enable,
      card_money: card_money
    });
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
    card_discount_input = e.detail.value;
    if (isNaN(card_discount_input) || !isNaN(card_discount_input) && card_discount_input < 0) {
      util.showModal('', "请输入正确的余额");
      this.setData({
        card_discount_input: '',
        charge_money: 0
      })
      if (create_order.card_charge_dis) {
        this.data.info.total_price = Number(this.data.info.total_price) + Number(create_order.card_charge_dis);
        create_order.card_charge_dis = 0;
        this.data.info.total_price = this.data.info.total_price.toFixed(2);
        this.setData({
          info: this.data.info,
        })
      }
      return false;
    }
    else {
      if (card_discount_input > parseInt(useCard.money)) {
        var tishi3 = "最多可以使用：" + useCard.money + "会员卡余额";
        util.showModal('提示', tishi3);
        this.setData({
          card_discount_input: '',
          charge_money: 0
        })
        if (create_order.card_charge_dis) {
          this.data.info.total_price = Number(this.data.info.total_price) + Number(create_order.card_charge_dis);
          create_order.card_charge_dis = 0;
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
    if (this.data.charge_money > 0) {
      this.data.info.total_price = parseFloat(this.data.info.total_price) + parseFloat(this.data.charge_money);
      this.data.info.total_price = (this.data.info.total_price).toFixed(2);
      charge_money = 0;
    }
    if (card_discount_input != '') {
      if (parseFloat(this.data.info.total_price) - parseFloat(card_discount_input) < 0) {
        util.showModal('提示', '使用余额不得超过支付金额');
        this.setData({
          card_discount_input: '',
          charge_money: 0
        })
        if (create_order.card_charge_dis) {
          this.data.info.total_price = Number(this.data.info.total_price).toFixed(2);
          create_order.card_charge_dis = 0;
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
      this.data.info.total_price = parseFloat(this.data.info.total_price) - parseFloat(card_discount_input);
      this.data.info.total_price = Number(this.data.info.total_price).toFixed(2);
      charge_money = Number(card_discount_input).toFixed(2);
    }
    this.data.info.discount_money = (Number(ipt_money) - Number(this.data.info.total_price)).toFixed(2);//总的折扣数
    this.setData({
      info: this.data.info,
      charge_money: charge_money
    })
    create_order.card_charge_dis = charge_money;
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
  textArea: function (e) {
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
    if (ipt_money == '' || !ipt_money) {
      util.showModal('', '请输入消费金额');
      return;
    }
    if (ipt_money == 0) {
      util.showModal('', '支付金额不能为0');
      return;
    }
    create_order.total_price = this.data.info.total_price;
    create_order.openid = util.getCache('openid');
    create_order.form_id = e.detail.formId;
    var orderinfo = JSON.stringify(create_order);
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
                  url: '/pages/shoporderinfo/shoporderinfo?order_sn=' + order_sn,
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
            url: '/pages/shoporderinfo/shoporderinfo?order_sn=' + order_sn,
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
    }, { orderinfo: orderinfo })
  },
  look_detail: function () {
    if (discount_block == 0) {
      discount_block = 1;
    } else {
      discount_block = 0;
    }
    this.setData({
      discount_block: discount_block
    })
  },

  getSelectCard (e) {
    console.log(e)
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