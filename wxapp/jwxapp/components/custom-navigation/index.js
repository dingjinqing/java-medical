var util = require("../../utils/util.js");
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    has_bottom:Boolean,
    page_name:String,
    bgColor:String,
    is_first_page: {
      type: Number,
      value: 0
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    height: 0,
    show_back:false,
    show_bottom:true,
    page_title:""
  },
  ready(){
    let that = this;
    var height = 0;
    if (typeof wx.getMenuButtonBoundingClientRect === 'function') {
      height = wx.getMenuButtonBoundingClientRect().bottom
    } else {
      wx.getSystemInfo({
        success: (res) => {
          height = res.statusBarHeight * 3
        }
      })
    }
    let pages = getCurrentPages();
    let show_back;
    if (pages.length > 1) {
      show_back = true
    } else {
      show_back = false
    }
    this.setData({
      show_back: show_back,
      show_bottom: this.data.has_bottom,
      height: height
    })
    let pageUrl = util.getCurrentPath();
    getTitle(pageUrl, that)
  },
  /**
   * 组件的方法列表
   */
  methods: {
    // 返回上一页面
    _navback() {
      wx.navigateBack()
    },
    //返回到首页
    _backhome() {
      util.jumpLink('/pages/index/index','reLaunch')
    }
  }
})
function getTitle(pageUrl,that){
  let lastindex = pageUrl.lastIndexOf('/');
  pageUrl = pageUrl.substring(lastindex + 1);
  const pageList = {
    "account": "我的余额",
    "agreement": "充值活动协议",
    "appearancecode": "出场码",
    "appointcomment": "评价详情",
    "appointinfo":"预约订单详情",
    "appointlist":"预约列表",
    "appointment":"预约详情",
    "appointorder":"提交预约",
    "auth":"授权",
    "balance":"我的余额",
    "bargaininfo":"砍价详情",
    "bargainitem":"砍价商品详情",
    "bargainlist":"我的砍价",
    "bargainusers":"砍价人列表",
    "brokeragerank":"佣金排行榜",
    "buycardlist":"会员卡列表",
    "cardCheckout":"会员卡结算",
    "cart":"购物车",
    "cardpay":"会员卡充值",
    "collect":"我的收藏",
    "comment":"商品评价",
    "coupon":"优惠券列表",
    "distribution":"分销中心",
    "distributionorder":"返利订单明细",
    "distributionspread":"申请分销员",
    "distrigrade":"分销员等级",
    "distrirecord":"升降级记录",
    "express":"物流信息",
    "form":"表单",
    "formsuccess":"提交成功",
    "fullprice":"满折满减",
    "getcoupon":"优惠券",
    "goodsCheckout":"订单结算",
    "goodscomment":"评价详情",
    "groupbuyinfo":"拼团详情",
    "groupbuyitem":"拼团商品详情",
    "index":"首页",
    "insteadinfo":"好友代付",
    "insteadcheckout":"好友代付",
    "insteadusers":"好友助力",
    "integral":"我的积分",
    "integralitem":"积分商品详情",
    "inviteduser":"我邀请的用户",
    "item":"商品详情",
    "lottery":"幸运大抽奖",
    "lotteryrule":"抽奖记录",
    "maingoodslist":"主商品",
    "manualbarcode":"手动输入条形码",
    "memberinfo":"提交申请",
    "orderinfo":"订单详情",
    "orderlist":"订单列表",
    "packagesalelist":"打包一口价",
    "payment":"订单支付成功",
    "pinintegration":"组队瓜分积分",
    "pinintegrationdetail":"我的活动",
    "pinlotteryinfo":"抽奖详情",
    "pinlotteryitem":"拼团抽奖商品详情",
    "pinlotterylist":"拼团抽奖",
    "presaleitem":"商品详情",
    "return_order_list":"退货中心",
    "returnorder":"退货/退款申请",
    "returnrecord":"协商记录",
    "scancode":"扫码购",
    "codeverification":"扫码核销",
    "search":"店铺商品",
    "seckillitem":"秒杀商品详情",
    "servicecomment":"服务评价",
    "sharerebate":"分享返利",
    "shopcheckout":"买单",
    "sort":"商品分类",
    "splitcoupon":"领取优惠券",
    "splitinfo":"领取详情",
    "storelist":"门店列表",
    "storeinfo":"门店信息",
    "usercardinfo":"会员卡",
    "usercardlist":"会员卡",
    "usercardrecord":"使用记录",
    "usercardup":"升降级记录",
    "usercenter":"个人中心",
    "usercardrenew": "会员卡续费",
    "userinfo":"编辑",
    "userqrcode":"我的二维码",
    "webview":"浏览器",
    "widthdraw":"余额提现",
    "widthdrawrecord":"提现记录",
    "filtergoods":"筛选",
    "login":"绑定手机号",
    "shoporderinfo":"订单详情",
    "useractivation":"会员激活",
    "maingiftlist":"主商品",
    "lotterygift":"我的奖品",
    "newsearch":"商品搜索",
    "getcardpage":"领取会员卡",
    "bottom":"提示",
    "promoteinfo":"好友助力",
    "promotelist":"好友助力",
    "fullship":"包邮商品",
    "pledgeannounce":"服务承诺",
    "assessstart":"测评",
    "assessinfo":"测评",
    "assessend":"测评结果",
    "assesscheck":"填写信息",
    "distripromotion":"分销推广语",
    "fullshiprule":"包邮活动规则",
    "couponpackage":"优惠券礼包",
    "commentcomplete":"评价成功",
    // 生成礼单的页面
    "presentinfo":"好友礼单",
    // 生成完礼单的分享页面
    "presentdetail":"好友礼单",
    // 领取礼单的结算页
    "presentcheckout":"订单结算",
    // 礼物列表
    "presentlist":"礼物记录",
    "presentchoose":"礼物商品",
    // 购买历史和足迹
    "foothistory":"我的足迹",
    "usercardgoods":"适用商品",
    "index":"首页"
  }
  console.log(pageUrl);
  that.setData({
    page_title: pageList[pageUrl]
  })
}