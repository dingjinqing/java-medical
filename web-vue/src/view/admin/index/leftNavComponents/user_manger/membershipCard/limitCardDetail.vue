<template>
  <div class="membershipCardDetail">
    <div class="membershipCardDetailMain">
      <div class="leftContainer">
        <showSampleCard :sampleData="sampleCardData"></showSampleCard>
      </div>
      <div class="rightContainer">
        <div class="rightContainerTop">
          <div class="rightTile">{{ $t('memberCard.basicSetting') }}</div>
          <cardNameAndBg
            :val="cardNameAndBg"
            @input="initCardNameAndBg"
            ref="cardNameAndBg"
          ></cardNameAndBg>
          <cardEffectTime
            :val="cardEffectTime"
            @input="initCardEffectTimeData"
            ref="cardEffectTime"
          ></cardEffectTime>
          <cardSuiteGoodsCfg
            :val="cardSuiteGoodsCfgData"
            @input="initCardSuiteGoodsCfgData"
          >
          </cardSuiteGoodsCfg>
          <cardStoreCfg
            :val="cardStoreCfgData"
            @input="initCardStoreCfgData"
            ref="cardStoreCfgData"
          ></cardStoreCfg>
          <cardUsageCfg
            :val="cardUsageCfgData"
            @input="initCardUsageCfgData"
          ></cardUsageCfg>
        </div>
        <div class="rightContainerBottom">
          <div class="rightTitle">{{ $t('memberCard.getSetting') }}</div>
          <cardReceiveCfg
            :val="cardReceiveCfgData"
            @input="initCardReceiveCfgData"
            ref="cardReceiveCfgData"
          ></cardReceiveCfg>
          <cardActiveCfg
            :val="cardActiveCfgData"
            @input="initCardActiveCfgData"
            ref="cardActiveCfgData"
          ></cardActiveCfg>
        </div>
      </div>
    </div>
    <div class="footer">
      <div
        class="save"
        @click="handleToSave"
      >
        {{$t('memberCard.save')}}
      </div>
    </div>
  </div>
</template>
<script>
import { createMemberCardRequest, getCardDetailInfoRequest, updateCardRequest } from '@/api/admin/memberManage/memberCard.js'
export default {
  components: {
    showSampleCard: () => import(
      './subcomponents/showSampleCard'
    ),
    cardNameAndBg: () => import(
      './subcomponents/cardNameAndBg'
    ),
    scoreDiscount: () => import(
      './subcomponents/scoreDiscount'
    ),
    ownGoods: () => import(
      './subcomponents/ownGoods'
    ),
    cardScoreCfg: () => import(
      './subcomponents/cardScoreCfg'
    ),
    cardChargeCfg: () => import(
      './subcomponents/cardChargeCfg'
    ),
    cardEffectTime: () => import(
      './subcomponents/cardEffectTime'
    ),
    cardSuiteGoodsCfg: () => import(
      './subcomponents/cardSuiteGoodsCfg'
    ),
    cardCouponCfg: () => import(
      './subcomponents/cardCouponCfg'
    ),
    cardStoreCfg: () => import(
      './subcomponents/cardStoreCfg'
    ),
    cardUsageCfg: () => import(
      './subcomponents/cardUsageCfg'
    ),
    cardReceiveCfg: () => import(
      './subcomponents/cardReceiveCfg'
    ),
    cardActiveCfg: () => import(
      './subcomponents/cardActiveCfg'
    )
  },
  computed: {

  },
  data () {
    let cardScoreCfgDataTmp = {
      powerScore: true,
      score: 100,
      offSet: '0',
      shopingInputLeft: 100,
      shopingInputRight: 100,
      shopingInputLeftM: 100,
      shopingInputRightM: 100,
      addIntegralArr: [
        {
          leftInput: 10,
          rightInput: 20
        }
      ]
    }
    let cardChargeCfgDataTmp = {
      powerCard: true,
      sendMoney: '',
      offset: '0',
      chargeInputLeft: 100,
      chargeInputRight: 100,
      chargeInputLeftM: 100,
      chargeInputRightM: 100,
      addChargeArr: [
        {
          leftInput: 300,
          rightInput: 300
        }
      ]
    }
    let cardEffectTimeTmp = {
      expiredType: '0',
      fixedDate: null,
      receiveDay: undefined,
      dateType: '0',
      valid: false
    }
    let cardStoreCfgDataTmp = {
      cardType: 1,
      storeListType: '0',
      useTime: '0',
      count: undefined,
      choosedStore: [
      ],
      valid: false
    }
    let cardUsageCfgDataTmp = {
      desc: null,
      mobile: null
    }
    return {
      cardType: null,
      cardId: null,
      cardNameAndBg: {
        cardName: null,
        bgType: '0',
        bgColor: '',
        bgImg: '',
        valid: false
      },
      disCountData: {
        powerDiscount: true,
        discount: '',
        discountGoodsType: '1',
        choosedGoodsId: [],
        choosedStoreId: [],
        choosedPlatformId: [],
        choosedBrandId: []
      },
      ownGoodsData: {
        powerOwnGoods: false,
        choosedGoodsId: [],
        choosedStoreId: [],
        choosedPlatformId: [],
        choosedBrandId: []
      },
      cardScoreCfgData: cardScoreCfgDataTmp,
      cardChargeCfgData: cardChargeCfgDataTmp,
      cardCouponCfgData: {
        powerCoupon: true,
        couponType: '1',
        couponIdList: []
      },
      cardEffectTime: cardEffectTimeTmp,
      cardStoreCfgData: cardStoreCfgDataTmp,
      cardUsageCfgData: cardUsageCfgDataTmp,
      cardSuiteGoodsCfgData: {
        isExchange: '0',
        exchangCount: '',
        exchangFreight: '0',
        exchangGoods: []
      },
      cardReceiveCfgData: {
        cardType: 1,
        isPay: '0',
        payType: '0',
        payMoney: undefined,
        payScore: undefined,
        receiveAction: '1',
        stock: undefined,
        limits: undefined,
        hasSend: 0,
        codeAddDivArr: [{ batchName: null, batchId: null }],
        codeAddDivArrBottom: [{ pwdName: null, pwdId: null }],
        valid: false
      },
      cardActiveCfgData: {
        activation: '0',
        activationCfgBox: [],
        examine: '0',
        valid: false
      },
      sampleCardData: {
        cardName: '',
        bgColor: '',
        bgImg: '',
        bgType: '',
        powerDiscount: true,
        discount: '',
        discountGoodsType: '0',
        cardScoreCfgData: null,
        cardChargeCfgData: null,
        cardEffectTime: cardEffectTimeTmp,
        cardStoreCfgData: cardStoreCfgDataTmp,
        cardUsageCfgData: cardUsageCfgDataTmp
      }
    }
  },
  mounted () {
    this.dataDefault()
  },
  methods: {
    dataDefault () {
      this.cardType = Number(this.$route.query.cardType)
      this.cardId = Number(this.$route.query.cardId)
      if (this.cardId) {
        // 单张会员卡信息
        this.getCardDetailInfoById(this.cardId)
      }
    },
    getCardDetailInfoById (id) {
      getCardDetailInfoRequest({ id }).then(res => {
        if (res.error === 0) {
          // success
          // bind data from backend to frontend
          this.bindBackAndFrontEndData(res.content)
        }
      })
    },
    bindBackAndFrontEndData (data) {
      this.cardId = data.id
      this.cardNameAndBg.cardName = data.cardName
      this.cardNameAndBg.bgType = String(data.bgType)
      this.cardNameAndBg.bgColor = data.bgColor
      this.cardNameAndBg.bgImg = data.bgImg

      // 日期
      this.cardEffectTime.expiredType = String(data.expireType)
      this.cardEffectTime.fixedDate = [data.startTime, data.endTime]
      if (data.startTime === null) { this.cardEffectTime.fixedDate = '' }
      console.log(this.cardEffectTime.fixedDate)
      this.cardEffectTime.receiveDay = data.receiveDay
      this.cardEffectTime.dateType = data.dateType ? String(data.dateType) : '0'
      // 适用商品
      this.cardSuiteGoodsCfgData.exchangCount = data.exchangCount
      this.cardSuiteGoodsCfgData.isExchange = String(data.isExchange)
      this.cardSuiteGoodsCfgData.exchangFreight = String(data.exchangFreight)
      this.cardSuiteGoodsCfgData.exchangGoods = data.exchangGoods ? data.exchangGoods : []

      // 门店
      this.cardStoreCfgData.storeListType = String(data.storeListType)
      let storeListTmp = []
      data.storeList.forEach(item => {
        storeListTmp.push({
          storeId: item.value,
          storeName: item.label
        })
      })
      this.cardStoreCfgData.choosedStore = storeListTmp
      this.cardStoreCfgData.useTime = String(data.useTime)
      this.cardStoreCfgData.count = data.count

      // 使用须知、
      this.cardUsageCfgData.desc = data.desc
      this.cardUsageCfgData.mobile = data.mobile

      // 领取设置
      this.cardReceiveCfgData.isPay = String(data.isPay)
      this.cardReceiveCfgData.payType = String(data.payType)
      this.cardReceiveCfgData.payMoney = data.payMoney
      this.cardReceiveCfgData.payScore = data.payScore
      this.cardReceiveCfgData.hasSend = data.hasSend
      this.cardReceiveCfgData.receiveAction = data.receiveAction === 0 ? '1' : String(data.receiveAction)
      if (data.batchList && this.cardReceiveCfgData.receiveAction === '1') {
        if (data.batchList.length > 0) {
          this.cardReceiveCfgData.codeAddDivArr = []
          data.batchList.forEach(item => {
            this.cardReceiveCfgData.codeAddDivArr.push({ batchName: item.name, batchId: item.batchId })
          })
        }
      } else {
        this.cardReceiveCfgData.codeAddDivArr = [{ batchName: null, batchId: null }]
      }

      if (data.batchList && this.cardReceiveCfgData.receiveAction === '2') {
        if (data.batchList.length > 0) {
          this.cardReceiveCfgData.codeAddDivArrBottom = []
          data.batchList.forEach(item => {
            this.cardReceiveCfgData.codeAddDivArrBottom.push({ pwdName: item.name, pwdId: item.batchId })
          })
        }
      } else {
        this.cardReceiveCfgData.codeAddDivArrBottom = [{ pwdName: null, pwdId: null }]
      }
      this.cardReceiveCfgData.stock = data.stock ? data.stock : 0
      this.cardReceiveCfgData.limits = data.limit ? data.limit : 0

      // 激活条件
      this.cardActiveCfgData.activation = String(data.activation)
      this.cardActiveCfgData.activationCfgBox = data.activationCfgBox ? data.activationCfgBox : []
      this.cardActiveCfgData.examine = String(data.examine)
    },
    getMiniLog (item) {
      return 'backgroundImage: url(' + item.backGroundImgUrl + ')'
    },
    initCardNameAndBg (val) {
      this.initSampleCardData(val)
      this.cardNameAndBg = val
    },
    initDiscountData (val) {
      this.initSampleCardDiscountData(val)
      this.disCountData = val
    },
    initOwnGoodsData (val) {
      this.ownGoodsData = val
    },

    initCardScoreCfgData (val) {
      this.cardScoreCfgData = val
    },
    initCardChargeCfgData (val) {
      this.cardChargeCfgData = val
    },
    initCardEffectTimeData (val) {
      this.cardEffectTime = val
    },
    initCardSuiteGoodsCfgData (val) {
      this.cardSuiteGoodsCfgData = val
    },
    initCardCouponCfgData (val) {
      this.cardCouponCfgData = val
    },
    initCardStoreCfgData (val) {
      console.log('门店获取', val)
      this.cardStoreCfgData = val
    },
    initCardUsageCfgData (val) {
      this.cardUsageCfgData = val
    },
    initCardReceiveCfgData (val) {
      this.cardReceiveCfgData = val
    },
    initCardActiveCfgData (val) {
      this.cardActiveCfgData = val
    },
    initSampleCardData (val) {
      this.sampleCardData.bgColor = val.bgColor
      this.sampleCardData.cardName = val.cardName
      this.sampleCardData.bgImg = val.bgImg
      this.sampleCardData.bgType = val.bgType
    },
    initSampleCardDiscountData (val) {
      this.sampleCardData.powerDiscount = val.powerDiscount
      this.sampleCardData.discount = val.discount
      this.sampleCardData.discountGoodsType = val.discountGoodsType
    },
    handleToSave () {
      // 检验通过
      this.$refs.cardNameAndBg.$emit('checkRule')
      this.$refs.cardEffectTime.$emit('checkRule')
      this.$refs.cardStoreCfgData.$emit('checkRule')
      this.$refs.cardReceiveCfgData.$emit('checkRule')
      this.$refs.cardActiveCfgData.$emit('checkRule')
      if (this.cardNameAndBg.valid && this.cardEffectTime.valid && this.cardStoreCfgData.valid &&
           this.cardReceiveCfgData.valid && this.cardActiveCfgData.valid) {
        this.prepareCardData()
      } else {
        this.$message.error('保存失败')
      }
    },
    prepareCardData () {
      let obj = {
        'id': this.cardId,
        'cardType': this.cardType,
        'cardName': this.cardNameAndBg.cardName,
        'bgType': this.cardNameAndBg.bgType,
        'bgColor': this.cardNameAndBg.bgColor,
        'bgImg': this.cardNameAndBg.bgImg,
        'expiredType': this.cardEffectTime.expiredType,
        'startTime': this.cardEffectTime.fixedDate ? this.cardEffectTime.fixedDate[0] : null,
        'endTime': this.cardEffectTime.fixedDate ? this.cardEffectTime.fixedDate[1] : null,
        'receiveDay': this.cardEffectTime.receiveDay,
        'dateType': this.cardEffectTime.dateType,
        'isExchange': this.cardSuiteGoodsCfgData.isExchange,
        'exchangCount': this.cardSuiteGoodsCfgData.exchangCount,
        'exchangFreight': this.cardSuiteGoodsCfgData.exchangFreight,
        'exchangGoods': this.cardSuiteGoodsCfgData.exchangGoods,
        'storeListType': this.cardStoreCfgData.storeListType,
        'storeList': this.cardStoreCfgData.choosedStore.map(({ storeId }) => storeId),
        'useTime': this.cardStoreCfgData.useTime,
        'count': this.cardStoreCfgData.count,
        'desc': this.cardUsageCfgData.desc,
        'mobile': this.cardUsageCfgData.mobile,
        'isPay': this.cardReceiveCfgData.isPay,
        'payType': this.cardReceiveCfgData.payType,
        'payMoney': this.cardReceiveCfgData.payMoney,
        'payScore': this.cardReceiveCfgData.payScore,
        'receiveAction': this.cardReceiveCfgData.receiveAction,
        'batchIdList': this.cardReceiveCfgData.codeAddDivArr.map(({ batchId }) => batchId),
        'stock': this.cardReceiveCfgData.stock,
        'limits': this.cardReceiveCfgData.limits,
        'activation': this.cardActiveCfgData.activation,
        'activationCfgBox': this.cardActiveCfgData.activationCfgBox,
        'examine': this.cardActiveCfgData.examine
      }
      console.log(obj)
      if (this.cardId) {
        // 更新会员卡
        console.log('更新会员卡')
        this.updateCardInfo(obj)
      } else {
        // 创建会员卡
        console.log('创建会员卡')
        this.createMemberCard(obj)
      }
    },
    // 2- 创建会员卡接口
    createMemberCard (data) {
      createMemberCardRequest(data).then(res => {
        console.log(res)
        if (res.error === 0) {
          // success
          // 清除数据，并进行跳转
          this.successOptions()
        }
      })
    },
    updateCardInfo (data) {
      updateCardRequest(data).then(res => {
        console.log(res)
        if (res.error === 0) {
          // success
          this.successOptions()
        }
      })
    },
    // 操作成功后的提示，清除数据，路由跳转
    successOptions () {
      this.$message.success(this.$t('memberCard.cardCreatedSuccess'))
      // this.clearInputData()
      let urlCard = this.getCardPageUrl()
      console.log(urlCard)
      // 页面跳转
      this.$router.push({
        name: urlCard
      })
    },
    // 根据会员卡类型获取相应url
    getCardPageUrl () {
      console.log(this.cardType, typeof this.cardType)
      switch (Number(this.cardType)) {
        case 0:
          return 'user_card'
        case 1:
          return 'limitTimes'
        case 2:
          return 'GradeCard'
        default:
          break
      }
    }
  }

}
</script>
<style scoped lang="scss">
.membershipCardDetail {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-x: hidden;

  .membershipCardDetailMain {
    position: relative;
    background-color: #fff;
    overflow-x: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    // height: 100%;
    display: flex;
    padding-bottom: 57px;
  }
  .leftContainer {
    width: 300px;
    margin-right: 20px;
    height: 594px;
    overflow-y: auto;
    border: 1px solid #ccc;
    background: #f5f5f5;
  }
  .rightContainer {
    width: 70%;
    font-size: 13px;
    margin-bottom: 10px;
    .rightContainerTop {
      padding: 10px 1%;
      background: #f8f8f8;
      border: 1px solid #e4e4e4;
      margin-bottom: 20px;
      .rightTile {
        padding-bottom: 10px;
        border-bottom: 1px solid #ddd;
        margin-bottom: 10px;
      }
    }
    .rightContainerBottom {
      background: #f8f8f8;
      border: 1px solid #e4e4e4;
      padding: 10px 1%;
      .rightTitle {
        padding-bottom: 10px;
        border-bottom: 1px solid #ddd;
        margin-bottom: 10px;
      }
    }
  }
  .footer {
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    position: fixed;
    bottom: 0;
    padding: 10px 0;
    left: 0;
    right: 0;
    .save {
      color: #fff;
      width: 70px;
      height: 30px;
      line-height: 30px;
      border: none;
      background: #5a8bff;
      margin: auto;
      cursor: pointer;
    }
  }
}
</style>
