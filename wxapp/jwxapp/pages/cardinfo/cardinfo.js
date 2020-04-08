let util = require('../../utils/util.js');
var is_fullprice = 0
var code = 0;
var seckillId = 0;
var goods_id = 0;
var gift_id = 0;
var card_info = [];
var card_code = '';
var card_num = '';
var card_pwd = '';
var card_activation = 0;
var custom_power = [];
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl(""),
    carStatus: '', // 卡状态
    bottomBtnText: '', // 底部按钮文字
    btnType: null, // 底部按钮类型
    get_type: 0,
    cardId: null,
    give_card: 0,
    rebate_show: false // 转赠弹窗flag
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options, '腾飞测试++++++++++++++++++')
    if (!util.check_setting(options)) return;
    wx.hideShareMenu();
    let cardNo = options.cardNo ? options.cardNo : null
    let cardId = options.cardId ? options.cardId : null
    let giveCard = options.give_card ? options.give_card : 0;
    if (options.scene) {
      let scene = decodeURIComponent(options.scene).split('&')
      cardId = scene[0].split('=')[1]
    }
    var card_list = options.card_list;
    this.setData({ cardId: cardId, give_card: giveCard })
    this.requestCardInfo(cardNo, cardId, card_list)
    if (options.is_fullprice) {  // 
      is_fullprice = options.is_fullprice;
    } else {
      is_fullprice = 0
    }
    seckillId = options.seckillId ? options.seckillId : 0;
    code = options.code ? options.code : 0;
    goods_id = options.goods_id ? options.goods_id : 0;
    gift_id = options.gift_id ? options.gift_id : 0;

    console.log(cardNo, cardId)
  },
  requestCardInfo (cardNo, cardId, card_list) {
    let that = this
    if (cardNo) {
      util.api('/api/card/detail', res => {
        console.log(res)
        let cardInfo = res.content
        // 模拟转赠数据can_give_away
        cardInfo.card_give_away = 0
        cardInfo.give_away_status = 1
        cardInfo.can_give_away = 1
        // end
        if ((!cardInfo.activation || (cardInfo.activation && cardInfo.activationTime)) && ((!cardInfo.examine) || (cardInfo.cardVerifyStatus === 2))) {
          that.setData({
            carStatus: "已领取"
          })
        } else if (cardInfo.examine && cardInfo.cardVerifyStatus === 1) {
          that.setData({
            carStatus: "审核中"
          })
        } else if (cardInfo.examine && cardInfo.cardVerifyStatus === 3) {

          that.setData({
            carStatus: "审核失败"
          })
        } else {
          that.setData({
            carStatus: "未激活"
          })
        }
        cardInfo.cardExpireTime = that.getCardExpireTime(cardInfo);
        cardInfo.cardBgStyle = that.getCardBg(cardInfo);
        cardInfo.cardTypeName = that.getTypeName(cardInfo.cardType);
        cardInfo.cardStopImg = this.getCardStopImage(cardInfo);
        cardInfo.buyScore = JSON.parse(cardInfo.buyScore)
        cardInfo.chargeMoney = JSON.parse(cardInfo.chargeMoney)
        if (cardInfo.activation) {
          card_activation = card_info.activation;
        }
        that.getUpgradeCondition(cardInfo)
        // 自定义测试数据
        // cardInfo.custom_options = "[{'custom_type':'2','custom_title':'fsfsdfds','option_ver':1,'is_checked':1}]"
        // cardInfo.custom_rights = [
        //   {
        //     cright_content: "one for test",
        //     cright_image: "http://mpdevimg2.weipubao.cn/upload/0/image/20190708/crop_pADgmTm2w2az2bMu.jpeg",
        //     cright_name: "one"
        //   },
        //   {
        //     cright_content: "two for test",
        //     cright_image: "http://mpdevimg2.weipubao.cn/upload/0/image/20200206/crop_4LfGH88XPGhulaRI.jpeg",
        //     cright_name: "two"
        //   }
        // ]
        cardInfo.custom_rights_flag = 1
        cardInfo.give_away_status = 0

        that.setData({
          cardInfo: cardInfo
        })
        console.log(cardInfo)
        that.handleToJudgementBottom(cardInfo) // 判断底部按钮
        if (cardInfo.status == 1 && cardInfo.flag == 1) {
          that.setData({
            is_block: 0
          })
        }
        if (card_list) {
          that.setData({
            card_list: card_list
          })
        }


      }, { cardNo: cardNo })
    } else if (cardId) {
      that.setData({
        get_type: 1
      })
      util.api('/api/card/judgement', function (res) {
        console.log(res);
        if (res.content.cardInfo.delFlag == 1) {
          util.showModal('提示', '该会员卡已失效', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (res.content.cardInfo.flag == 2) {
          util.showModal('提示', '该会员卡已停用', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (res.content.cardInfo.status == -1) {
          util.showModal('提示', '该会员卡已过期', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        let cardInfo = res.content.cardInfo
        that.handleToJudgementBottom(cardInfo) // 判断底部按钮
        console.log(cardInfo)
        if (!cardInfo.cardNo) {
          that.setData({
            carStatus: "未领取"
          })
        } else if ((!cardInfo.activation || (cardInfo.activation && cardInfo.activationTime)) && ((!cardInfo.examine) || (cardInfo.cardVerifyStatus === 2))) {
          that.setData({
            carStatus: "已领取"
          })
        } else if (cardInfo.examine && cardInfo.cardVerifyStatus === 1) {
          that.setData({
            carStatus: "审核中"
          })
        } else if (cardInfo.examine && cardInfo.cardVerifyStatus === 3) {

          that.setData({
            carStatus: "审核失败"
          })
        } else {
          that.setData({
            carStatus: "未激活"
          })
        }
        cardInfo.cardExpireTime = that.getCardExpireTime(cardInfo);
        cardInfo.cardBgStyle = that.getCardBg(cardInfo);
        cardInfo.cardTypeName = that.getTypeName(cardInfo.cardType);
        cardInfo.buyScore = JSON.parse(cardInfo.buyScore)
        cardInfo.chargeMoney = JSON.parse(cardInfo.chargeMoney)
        cardInfo.storeList = cardInfo.storeList
        if (cardInfo.activation) {
          card_activation = card_info.activation;
        }
        that.getUpgradeCondition(cardInfo)
        that.setData({
          cardInfo: cardInfo
        })
        if (cardInfo.status == 1 && cardInfo.flag == 1) {
          that.setData({
            is_block: 0
          })
        }
      }, { cardId: cardId })
    }
  },
  // 获取会员卡过期时间
  getCardExpireTime (cardItem) {
    if (cardItem.expireType === 0) {
      // 从领取之日起
      let reDateType = ['日', '周', '月']
      let i = cardItem.dateType === null ? 0 : Number(cardItem.dateType)
      return `自领取之日起${cardItem.receiveDay}${reDateType[i]}内有效`

    }
    if (cardItem.cardType === 2) return null
    if (cardItem.expireType === 2) return `永久有效`
    return `${cardItem.startDate} 至 ${cardItem.endDate}`
  },
  // 获取会员卡停用/删除状态图片
  getCardStopImage (cardItem) {
    if (cardItem.cardType === 2 && cardItem.flag === 2) {
      return `${this.data.imageUrl}image/wxapp/card_stop.png`
    }
    if (cardItem.expire === 1) {
      return `${this.data.imageUrl}image/wxapp/card_out_time.png`
    }
    return ``
  },
  // 获取会员卡背景
  getCardBg (cardItem) {
    console.log(cardItem, this.data.imageUrl);
    switch (cardItem.bgType) {
      case 0:
        return `background-color:${cardItem.bgColor};`;
      case 1:
        return `background:url('${cardItem.bgImg}') no-repeat top left / 100% 100%;`;
    }
  },
  // 获取卡类型
  getTypeName (cardType) {
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
  getCardStatus (cardItem) {

  },
  // 展示二维码
  showQrCode () {
    let qrCode = [this.data.cardInfo.qrCode]
    console.log(qrCode)
    this.setData({
      qrCode,
      showQrcode: true
    })
  },
  // 等级卡  等级详情
  getUpgradeCondition (cardInfo) {
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
  },
  bindGetPhoneNumberOk: function (e) {
    mobile = e.detail.phoneNumber;
  },
  // 判断底部按钮
  handleToJudgementBottom (carInfo) {
    console.log(carInfo)
    let text = ''
    let type = null
    if (carInfo.isPay == 1 && !carInfo.cardNo) {//判断是否立即开通
      text = '立即开通'
      type = 1
    } else if (carInfo.status) {
      if (!carInfo.cardNo && (carInfo.isPay == 0 || carInfo.isPay == 2)) {
        text = '领取会员卡'
        type = 2
      } else if (carInfo.activation == 1 && carInfo.activationTime == null && carInfo.cardNo) {
        text = '激活会员卡'
        type = 3
      } else if (carInfo.isDefault == 0 && carInfo.cardNo) {
        text = '设置默认会员卡'
        type = 4
      } else {
        text = '默认会员卡'
      }
    }
    if (!carInfo.cardNo && carInfo.cardType == 2 && carInfo.status) {
      text = '领取会员卡'
      type = 2
    }
    console.log(text, type)
    this.setData({
      bottomBtnText: text,
      btnType: type
    })
  },
  // 处理底部按钮点击
  handleToBtn (e) {
    console.log(e)
    switch (e.currentTarget.dataset.btntype) {
      case 1:
        this.getCard(e.currentTarget.dataset.cardid)
        break
      case 2:
        this.getUserCard()
        break
      case 3:
        this.getUsing(e)
        break
      case 4:
        this.setDefault(e)
        break
    }
  },
  // 立即开通
  getCard (cardId) {
    console.log(this.data.bindMobile)
    var that = this;
    if (this.data.bindMobile == 1 && util.getCache('mobile') == '') {
      util.checkSession(function () {
        that.setData({
          is_block: is_block = 1
        })
      })
      return false;
    }
    util.api('/api/card/judgement', function (res) {
      if (res.error == 0) {
        if (res.content.status == 0) {
          util.toast_fail('此卡已存在');
        } else {
          if (is_fullprice == 0 && code == 0 && seckillId == 0 && goods_id == 0) {
            util.redirectTo({
              url: '/pages1/virtualCheckout/virtualCheckout?cardId=' + cardId
            })
          } else {
            util.redirectTo({
              url: '/pages1/virtualCheckout/virtualCheckout?cardId=' + cardId + "&isFullprice=" + is_fullprice + "&code=" + code + "&seckillId=" + seckillId + '&goodsId=' + goods_id,
            })
          }
        }
      }
    }, { cardId: cardId })
  },
  // 领取会员卡
  getUserCard () {
    var that = this;
    that.data.cardInfo.buyScore = '';
    var cardId = that.data.cardInfo.cardId;
    var card = {};
    card.cardId = cardId;
    card_info = JSON.stringify(card);
    if (that.data.cardInfo.isPay == 2) {
      if (that.data.cardInfo.receiveAction == 1) {
        var receiveAction = 1
      } else {
        var receiveAction = 2
      }
      that.setData({
        is_receive: 1,
        receive_action: receiveAction
      })
    } else {
      util.api('/api/card/getCard', function (res) {
        console.log(res)
        if (res.error == 0) {
          if (res.content.isMostGrade) {
            util.toast_fail('当前等级已最高');
            return;
          } else if (res.content.gradeCard) {
            var text = '没有达到该卡的条件';
            if (res.content.gradeCard.score > 0) {
              text = '累积积分未达到' + res.content.gradeCard.score + '积分';
            }
            if (res.content.gradeCard.amount > 0) {
              text = text + ' 累积消费金额未达到' + res.content.gradeCard.amount + '元';
            }
            util.showModal('提示', text, function (res) {
              return false;
            }, false);
            return;
          } else if (res.content == -1) {
            util.toast_fail('此卡已存在');
          } else {
            util.toast_success('领取成功', function () {
              console.log(card_activation, is_fullprice, code, seckillId, goods_id, gift_id)
              if (card_activation == 1) {
                setTimeout(function () {
                  util.navigateTo({
                    url: '/pages/memberinfo/memberinfo?act=1&cardNo=' + res.content.cardNo + '&examine=' + that.data.cardInfo.examine + "&is_fullprice=" + is_fullprice + "&code=" + code + "&seckillId=" + seckillId + '&goods_id=' + goods_id + "&gift_id=" + gift_id
                  })
                }, 2000);
              } else {
                if (is_fullprice == 0 && code == 0 && seckillId == 0 && goods_id == 0 && gift_id == 0) {
                  setTimeout(function () {
                    util.redirectTo({
                      url: '/pages/cardlist/cardlist',
                    })
                  }, 2000);
                } else if (parseInt(gift_id)) {
                  util.redirectTo({
                    url: 'pages1/presentchoose/presentchoose?gift_id=' + gift_id,
                  })
                } else if (parseInt(is_fullprice)) {
                  util.redirectTo({
                    url: '/pages/fullprice/fullprice?identity_id=' + is_fullprice,
                  })
                } else if (parseInt(seckillId)) {
                  util.redirectTo({
                    url: '/pages/seckillitem/seckillitem?sk_id=' + seckillId,
                  })
                } else if (parseInt(goods_id)) {
                  util.redirectTo({
                    url: '/pages/item/item?gid=' + goods_id,
                  })
                } else {
                  console.log(111)
                  util.redirectTo({
                    url: '/pages/getcoupon/getcoupon?code=' + code,
                  })
                }
              }
            });
          }
        } else {
          util.toast_fail('领取失败');
        }
      }, { cardId: Number(that.data.cardId), getType: that.data.get_type })
    }
  },
  // 激活会员卡
  getUsing (e) {
    console.log(this.data.cardInfo.examine)
    if (is_fullprice == 0 && code == 0 && seckillId == 0 && goods_id == 0) {
      util.redirectTo({
        url: '/pages/memberinfo/memberinfo?act=1&cardNo=' + e.currentTarget.dataset.cardno + '&examine=' + this.data.cardInfo.examine
      })
    } else {
      util.redirectTo({
        url: '/pages/memberinfo/memberinfo?act=1&cardNo=' + e.currentTarget.dataset.cardno + '&examine=' + this.data.cardInfo.examine + "&is_fullprice=" + is_fullprice + "&code=" + code + "&seckillId=" + seckillId + '&goods_id=' + goods_id
      })
    }
  },
  // 设置默认会员卡
  setDefault (e) {
    console.log(e.currentTarget.dataset)
    var that = this;
    util.api('/api/wxapp/card/default', function (res) {
      console.log(res)
      if (res.error == 0) {
        card_info.is_default = 1;
        that.setData({
          card_info: card_info
        })
        util.toast_success('设置成功');
      } else {
        util.toast_fail('设置失败');
      }
    }, { cardNo: e.currentTarget.dataset.cardno })
  },
  closeIpt () {
    this.setData({
      is_receive: 0,
      receive_action: 0
    })
  },
  bindCode (e) {
    card_code = e.detail.value;
  },
  bindCardNum (e) {
    card_num = e.detail.value;
  },
  bindCardPwd (e) {
    card_pwd = e.detail.value;
  },
  show_rebate: function () {
    wx.showModal({
      title: '会员卡已转赠',
      content: cardInfo.give_away_time + '转赠\r\n给好友' + cardInfo.give_username,
      showCancel: false,
      confirmText: '关闭',
      confirmColor: '#000'
    })
  },
  custom_click: function (e) {
    var index = e.currentTarget.dataset.index;
    if (custom_power[index]) {
      custom_power[index] = false;
    } else {
      custom_power[index] = true;
    }
    this.setData({
      custom_power: custom_power,
    })
  },
  fetchCard () {
    var that = this;
    console.log(card_code, that.data.cardInfo)
    util.api('/api/wxapp/card/code/receive', function (res) {
      console.log(res)
      if (res.error == 0) {
        util.toast_success('领取成功', function () {
          setTimeout(function () {
            util.redirectTo({
              url: '/pages/cardlist/cardlist',
            })
          }, 2000);
        });
      } else {
        util.showModal('', res.message, function () {
          return false;
        }, false);
      }
    }, { cardId: that.data.cardId, code: card_code, cardNo: card_num, cardPwd: card_pwd })
  },
  // 点击使用门店
  toStoreList () {
    util.jumpLink('/pages/storelist/storelist?cardId=' + this.data.cardInfo.cardId)
  },
  // 点击使用记录
  toCardRecord (e) {
    console.log(e)
    util.navigateTo({
      url: '/pages1/usercardrecord/usercardrecord?cardNo=' + e.currentTarget.dataset.cardno,
    })
  },
  // 点击升降级记录
  toUserCardUp () {
    util.navigateTo({
      url: '/pages/usercardup/usercardup',
    })
  },
  checkMore: function (e) {
    let d = e.currentTarget.dataset;
    util.jumpLink('/pages1/usercardgoods/usercardgoods?cardNo=' + d.card_no + '&cardId=' + d.card_id);
  },
  to_goods: function (e) {
    let goods_id = e.currentTarget.dataset.goods_id;
    let is_list = e.currentTarget.dataset.is_list;
    // util.api('/api/card/exchange/judge', function (res) {
    //   if (res.error == 0) {
    //     util.navigateTo({
    //       url: '/pages/item/item?good_id=' + goods_id + '&from_count_card=1&card_no=' + card_no,
    //     })
    //   } else {
    //     util.showModal('提示', res.message, function () {
    //       util.jumpLink('/pages/item/item?good_id=' + goods_id, 'navigateTo')
    //     }, true, '取消', '原价购买')
    //   }
    // }, {
    //   card_no: card_no,
    //   goods_id: goods_id,
    //   is_list: is_list
    // })
  },
  // 点击优惠卷
  viewCoupon (e) {
    let coupon_id = e.currentTarget.dataset.coupon_id;
    util.jumpLink('/pages/getCoupon/getCoupon?code=' + coupon_id, 'navigateTo')

  },
  to_cou_package (e) {
    let pack_id = e.currentTarget.dataset.pack_id;
    util.jumpLink("/pages/couponpackage/couponpackage?pack_id=" + pack_id)
  },
  // 点击转赠
  to_rebate (e) {
    var that = this;
    var status = e.currentTarget.dataset.status;
    let cardInfo = this.data.cardInfo
    if (status == 0) {
      if (cardInfo.can_give_away == 1) {
        that.setData({
          rebate_show: true,
        })
      } else {
        wx.showModal({
          title: '转赠会员卡',
          content: '当前有未完成的兑换商品订单，\r\n 暂时不能将本卡转赠好友',
          cancelText: '查看订单',
          confirmText: '知道了',
          confirmColor: '#000',
          cancelColor: util.getCache('main_colors'),
          success (res) {
            if (res.cancel) {
              var order_sn = cardInfo.card_order_sn;
              util.jumpLink("pages/orderinfo/orderinfo?order_sn=" + order_sn)
            }
          }
        })
      }
    } else if (status == 1) {
      wx.showModal({
        title: '会员卡转赠中',
        content: '会员卡转赠中,如果' + cardInfo.give_deadline + '前好友未领取,则会员卡可以继续使用！',
        showCancel: false,
        confirmText: '知道了',
        confirmColor: '#000'
      })
    } else {
      wx.showModal({
        title: '会员卡已转赠',
        content: cardInfo.give_away_time + '转赠\r\n给好友' + cardInfo.give_username,
        showCancel: false,
        confirmText: '关闭',
        confirmColor: '#000'
      })
    }

  },
  // 取消转赠
  cancel_rebtae: function () {
    var that = this;
    that.setData({
      rebate_show: false,
    })
  },
  onShareAppMessage: function () {
    console.log(this.data)
    var that = this;
    let cardInfo = that.data.cardInfo
    // util.api("/api/card/giveAway/record", function (d) {
    //   util.api('/api/card/detail', function (res) {
    //     card_info = res.content;
    //     that.loadData(card_info, 0, that);
    //     that.setData({
    //       card_list: card_list
    //     })
    //   }, {
    //     card_no: card_no
    //   })
    //   that.setData({
    //     rebate_show: false,
    //   })
    // }, {
    //   card_no: card_info.card_no,
    //   card_id: card_info.card_id,
    //   user_id: card_info.user_id
    // });
    return {
      title: util.getCache('nickName') + '赠送给您一张会员卡',
      path: 'pages/cardinfo/cardinfo?cardNo=' + cardInfo.cardNo + "&cardId=" + cardInfo.cardId + "&give_card=1",
      imageUrl: that.data.imageUrl,
      complete: function () {

      }
    }

  },
})