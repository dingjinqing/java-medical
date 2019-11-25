var navigation = require("./components/navigation.js")
var order = require("./pages/order.js")
var checkout = require("./pages/checkout.js")
var comment = require('./page1/commentCn.js')
var decorate = require('./components/decorate/decorate.js')
var zh_CN = {
  "common": {
    "info": {
      "loading": "加载中",
      "loadingFailed": "加载失败",
      "title": "信息提示",
      "ok": "确定",
      "cancel": "取消",
      "openSetting": "打开设置页面",
      "albumOpenAuthority": "需要获取您的相册权限，请到小程序的设置页面打开授权",
      "clearingCacheForSave": "正在清理缓存，请重新保存",
      "uploading": "上传中",
      "uploadFailed": "上传失败",
      "updateTip": "更新提示",
      "newVersionReadyWhetherRestartApp": "新版本已经准备好，是否重启应用？",
      "tip": "提示"
    },
    "video": {
      "sizeGt10M": "视频大于10M",
      "durationGt3Minites": "视频超过3分钟"
    }
  },
  "components": {
    "navigation": navigation,
    'decorate': decorate
  },
  "pages": {
    "bottom": {
      "shopDisabled": "该店铺已禁用",
      "shopExpired": "该店铺已过期",
      "shopClosed": "该店铺已停业",
      "disableLogin": "您已被禁止登录",
      "contactMerchant": "联系商家",
    },
    "usercenter": {
      "available": "今日可领",
      "qrcTitle": "今日已领",
      "qrcTitleB": "积分",
      "signIn": "签到",
      "continuouslySignedIn": "已连续签到",
      "day": "天",
      "signInForPoints ": "签到领积分",
      "checkInToday": "今日签到可获取",
      "checkInTomorrow": "明日连续签到可获取",
      "checkIn": "已签到",
      "membershipCard": "您有一张等级会员卡可领取",
      "collectImmediately ": "立即领取",
      "viewAllOrders": "查看全部订单",
      "waitPay": "待付款",
      "waitDeliver": "待发货",
      "waitReceive": "待收货",
      "waitComment": "评价",
      "refund": "退款中",
      "allOrders": "全部订单",
      "viewAllAppointments": "查看全部预约",
      "appointmentTime": "预约到店时间",
      "collect": "商品收藏",
      "buyHistory": "历史购买",
      "footPrint": "我的足迹",
      "distribution": "分销中心",
      "bargain": "我的砍价",
      "award": "我的奖品",
      "commentList": "我的评价",
      "storeList": "门店列表",
      "userActivate": "会员激活",
      "orderVerify": "扫码核销",
      "presentList": "礼物记录"
    },
    "store": {
      "searchGoods": "搜索商品",
      "noClassification": "暂无分类",
      "leftMenuA": "全部品牌",
      "leftMenuB": "推荐品牌",
      "leftMenuC": "推荐分类"
    },
    "order": order,
    "checkout": checkout
  },
  "page1": {
    "comment": comment
  },
  "page2": {

  }
}
module.exports = zh_CN;