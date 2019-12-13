let util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl("")
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let cardNo = options.cardNo ? options.cardNo : null
    let cardId = options.cardId ? options.cardId : null
    this.requestCardInfo(cardNo, cardId)
  },
  requestCardInfo(cardNo, cardId) {
    if (cardNo) {  //  从个人中心会员卡列表进入
      util.api('/api/card/detail', res => {
        let cardInfo = res.content
        cardInfo.cardExpireTime = this.getCardExpireTime(cardInfo);
        cardInfo.cardBgStyle = this.getCardBg(cardInfo);
        cardInfo.cardTypeName = this.getTypeName(cardInfo.cardType);
        cardInfo.buyScore = JSON.parse(cardInfo.buyScore)
        cardInfo.chargeMoney = JSON.parse(cardInfo.chargeMoney)
        cardInfo.storeList = cardInfo.storeList ? JSON.parse(cardInfo.storeList) : []
        this.getUpgradeCondition(cardInfo)
        this.setData({
          cardInfo: res.content
        })
      }, { cardNo: cardNo })
    } else if (cardId) {  // 从首页进入
      util.api('/api/card/judgement', function (res) {
        console.log(res.content);
        if (res.content.card_info.is_delete == 1) {
          util.showModal('提示', '该会员卡已失效', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (res.content.card_info.flag == 2) {
          util.showModal('提示', '该会员卡已停用', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (res.content.card_info.flag == 3) {
          util.showModal('提示', '该会员卡已过期', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        let cardInfo = res.content
        cardInfo.cardExpireTime = this.getCardExpireTime(cardInfo);
        cardInfo.cardBgStyle = this.getCardBg(cardInfo);
        cardInfo.cardTypeName = this.getTypeName(cardInfo.cardType);
        cardInfo.buyScore = JSON.parse(cardInfo.buyScore)
        cardInfo.chargeMoney = JSON.parse(cardInfo.chargeMoney)
        cardInfo.storeList = cardInfo.storeList ? JSON.parse(cardInfo.storeList) : []
        this.getUpgradeCondition(cardInfo)
        this.setData({
          cardInfo: res.content
        })
      }, { cardId: cardId })
    }

  },
  // 获取会员卡过期时间
  getCardExpireTime(cardItem) {
    if (cardItem.cardType === 2) return null
    if (cardItem.expireType === 2) return `永久有效`
    if (cardItem.expire === 1) return `此卡已过期，如需继续使用请联系商家`
    return `${cardItem.startDate} 至 ${cardItem.endDate}`
  },
  // 获取会员卡背景
  getCardBg(cardItem) {
    console.log(cardItem);
    switch (cardItem.bgType) {
      case 0:
        return `background-color:${cardItem.bgColor};`;
      case 1:
        return `background:url('${this.data.imageUrl}${cardItem.bgImg}') no-repeat top left / 100% 100%;`;
    }
  },
  // 获取卡类型
  getTypeName(cardType) {
    switch (cardType) {
      case 0:
        return '普通卡';
      case 1:
        return '限次卡';
      case 2:
        return '等级卡';
    }
  },
  // 获取会员卡领取状态
  getCardStatus(cardItem) {

  },
  // 展示二维码
  showQrCode() {
    let qrCode = [this.data.cardInfo.qrCode]
    this.setData({
      qrCode,
      showQrcode: true
    })
  },
  // 等级卡  等级详情
  getUpgradeCondition(cardInfo) {
    if (cardInfo.cardType !== 2 || !cardInfo.nextGradeCard) return
    if (cardInfo.nextGradeCard.gradeConditionJson.gradeScore) {
      this.setData({
        percentage: Math.round(cardInfo.cumulativeScore / cardInfo.nextGradeCard.gradeConditionJson.gradeScore * 10000) / 100.00,
        currentCondition: cardInfo.cumulativeScore,
        unit: '分'
      })
      console.log(this.data.percentage)
    } else {
      this.setData({
        percentage: Math.round(cardInfo.cumulativeConsumptionAmounts / cardInfo.nextGradeCard.gradeConditionJson.gradeScore * 10000) / 100.00,
        currentCondition: cardInfo.cumulativeConsumptionAmounts,
        unit: '元'
      })
    }

  },
  // 拨号
  phoneCall: function (e) {
    wx.makePhoneCall({
      phoneNumber: this.data.cardInfo.mobile
    })
  }
})