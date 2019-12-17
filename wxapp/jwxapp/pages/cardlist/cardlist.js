let util = require('../../utils/util.js');
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    dataList: null,
    pageParams: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requestList();
  },
  requestList() {
    let currentPage = this.data.pageParams
      ? this.data.pageParams.currentPage
      : 1;
    util.api(
      '/api/card/list',
      res => {
        if (res.error === 0) {
          let dataList = this.formatData(res.content.dataList);
          this.setData({
            pageParams: res.content.page,
            ['dataList[' + (parseInt(currentPage) - 1) + ']']: dataList
          });
        }
        console.log(res);
      },
      {
        currentPage: currentPage,
        pageRows: 4
      }
    );
  },
  // 触摸开始
  handleTouchStart(e) {
    this.startX = e.touches[0].clientX;
    let dataIdx = e.currentTarget.dataset.data_idx;
    let itemIdx = e.currentTarget.dataset.item_idx;
    let target = `dataList[${dataIdx}][${itemIdx}].startRight`;
    this.setData({
      [target]: this.data.dataList[dataIdx][itemIdx].x
        ? this.data.dataList[dataIdx][itemIdx].x
        : 0
    });
  },
  // 触摸移动
  handleTouchMove(e) {
    var touch = e.touches[0];
    let dataIdx = e.currentTarget.dataset.data_idx;
    let itemIdx = e.currentTarget.dataset.item_idx;
    let target = `dataList[${dataIdx}][${itemIdx}].x`;
    let endX = touch.clientX;
    let right = 0;
    if (!this.data.dataList[dataIdx][itemIdx].cardStopImg) return
    if (endX - this.startX < 0) {
      let startRight = this.data.dataList[dataIdx][itemIdx].startRight;
      var change = this.startX - endX;
      startRight += change;
      if (startRight > 60) startRight = 60;
      right = startRight;
    } else {
      var startRight = this.data.dataList[dataIdx][itemIdx].startRight;
      var change = this.startX - endX;
      startRight += change;
      if (startRight < 0) startRight = 0;
      right = startRight;
    }
    this.setData({
      [target]: right
    });
  },
  // 触摸结束
  handleTouchEnd(e) {
    let dataIdx = e.currentTarget.dataset.data_idx;
    let itemIdx = e.currentTarget.dataset.item_idx;
    let target = `dataList[${dataIdx}][${itemIdx}].x`;
    let right = 0;
    this.data.dataList[dataIdx][itemIdx].x > 60 / 2
      ? (right = 60)
      : (right = 0);
    this.setData({
      [target]: right
    });
  },
  // 格式化数据
  formatData(dataList) {
    let newDataList = JSON.parse(JSON.stringify(dataList))
    newDataList.map(item => {
      item.cardTypeName = this.getTypeName(item.cardType);
      item.cardBgStyle = this.getCardBg(item);
      item.cardStopImg = this.getCardStopImage(item);
      item.cardExpireTime = this.getCardExpireTime(item);
      item.buyScore = JSON.parse(item.buyScore)
      item.chargeMoney = JSON.parse(item.chargeMoney)
    });
    return newDataList;
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
  // 获取会员卡停用/删除状态图片
  getCardStopImage(cardItem) {
    if (cardItem.cardType === 2 && cardItem.flag === 2) {
      return `${this.data.imageUrl}image/wxapp/card_stop.png`
    }
    if (cardItem.expire === 1) {
      return `${this.data.imageUrl}image/wxapp/card_out_time.png`
    }
    return ``
  },
  // 获取会员卡过期时间
  getCardExpireTime(cardItem) {
    if (cardItem.cardType === 2) return null
    if (cardItem.expireType === 2) return `永久有效`
    if (cardItem.expire === 1) return `此卡已过期，如需继续使用请联系商家`
    return `${cardItem.startDate} 至 ${cardItem.endDate}`
  },
  // 删除会员卡
  delCard(e) {
    let card_no = e.currentTarget.dataset.card_no;
    util.showModal(
      '',
      '您确定要删除该会员卡？',
      function () {
        var animate = '';
        util.api(
          '/api/wxapp/card/del',
          function (res) {
            if (res.error === 0) {
            }
          },
          { card_no: card_no }
        );
      },
      true,
      '取消',
      '确定'
    );
  },
  // 查看会员卡详情
  checkDetail(e) {
    console.log(111, this.data.dataList[e.currentTarget.dataset.data_idx][e.currentTarget.dataset.item_idx])
    let card_no = this.data.dataList[e.currentTarget.dataset.data_idx][e.currentTarget.dataset.item_idx].cardNo
    util.jumpLink(`pages/cardinfo/cardinfo?cardNo=${card_no}`, 'navigateTo')
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (
      this.data.pageParams &&
      this.data.pageParams.currentPage === this.data.pageParams.lastPage
    )
      return;
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.requestList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () { }
});
