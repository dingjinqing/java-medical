<template>
  <div class="membershipCardDetail">
    <div class="membershipCardDetailMain">
      <div class="leftContainer">
        <showSampleCard :sampleData="sampleCardData" :allData="$data"></showSampleCard>
      </div>
      <div class="rightContainer">
        <div class="rightContainerTop">
          <div class="rightTitle">{{ $t('memberCard.basicSetting') }}</div>
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
        <div class="member-rights">
          <div class="rightTitle">会员权益</div>
          <cardSuiteGoodsCfg
            :val="cardSuiteGoodsCfgData"
            @input="initCardSuiteGoodsCfgData" />
          <!-- 自定义权益 -->
          <cardCustomRights v-bind.sync="customRights" />
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
        <div class="advance-setting">
          <div class="rightTitle">高级设置</div>
          <card-advance-cfg :cardTag="cardTag"
              :cardGive="cardGive"
              :cardType="cardType"/>
        </div>
      </div>
    </div>
    <div class="footer">
      <el-button type="primary" size="small" @click="handleToSave">{{$t('memberCard.save')}}</el-button>
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
    ),
    cardAdvanceCfg: () => import(
      './subcomponents/cardAdvanceCfg'
    ),
    cardCustomRights: () => import(
      './subcomponents/cardCustomRights'
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
        isExchange: 0,
        exchangCount: '',
        everyGoodsMaxNum: '',
        exchangFreight: 0,
        exchangGoods: [{goodsIds: [], maxNum: null}],
        exchangTimeType: 1,
        exchangTimeRadio: '0',
        exchangTimeNum: null
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
        codeAddDivArr: [{ batchName: null, batchId: null, action: null }],
        codeAddDivArrBottom: [{ pwdName: null, pwdId: null, action: null }],
        valid: false
      },
      cardActiveCfgData: {
        activation: '0',
        activationCfgBox: [],
        examine: '0',
        customAction: [],
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
      },
      // 自定义权益
      customRights: {
        customRightsFlag: 'off',
        customRightsAll: []
      },
      cardTag: {
        cardTag: 'off',
        cardTagId: []
      },
      cardGive: {
        cardGiveAway: 'off',
        cardGiveContinue: 'off',
        mostGiveAway: null
      },
      isCanSave: true
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
      if (data.receiveDay !== null) {
        this.cardEffectTime.receiveDay = data.receiveDay
      } else {
        this.cardEffectTime.receiveDay = void 0
      }
      this.cardEffectTime.dateType = data.dateType ? String(data.dateType) : '0'
      // 适用商品
      this.cardSuiteGoodsCfgData.isExchange = data.cardExchangGoods.isExchange
      this.cardSuiteGoodsCfgData.exchangCount = data.cardExchangGoods.exchangCount
      this.cardSuiteGoodsCfgData.everyGoodsMaxNum = data.cardExchangGoods.everyGoodsMaxNum
      this.cardSuiteGoodsCfgData.exchangFreight = data.cardExchangGoods.exchangFreight
      this.cardSuiteGoodsCfgData.exchangGoods = data.cardExchangGoods.exchangGoods
      if (data.cardExchangGoods.exchangTimeType !== null && data.cardExchangGoods.exchangTimeType !== 0) {
        this.cardSuiteGoodsCfgData.exchangTimeType = data.cardExchangGoods.exchangTimeType
      } else {
        this.cardSuiteGoodsCfgData.exchangTimeType = 1
      }

      if (!data.cardExchangGoods.exchangGoods) {
        this.cardSuiteGoodsCfgData.exchangGoods = [{goodsIds: [], maxNum: null}]
      }
      if (data.cardExchangGoods.exchangTimeType) {
        this.cardSuiteGoodsCfgData.exchangTimeRadio = '1'
      } else {
        this.cardSuiteGoodsCfgData.exchangTimeRadio = '0'
      }
      this.cardSuiteGoodsCfgData.exchangTimeNum = data.cardExchangGoods.exchangTimeNum

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
            if (!item.pwdBatch) {
              this.cardReceiveCfgData.codeAddDivArr.push({ batchName: item.name, batchId: item.batchId, action: item.action, disabled: true })
            }
          })
        }
      } else {
        this.cardReceiveCfgData.codeAddDivArr = [{ batchName: null, batchId: null, action: null, disabled: false }]
      }

      if (data.batchList && this.cardReceiveCfgData.receiveAction === '2') {
        if (data.batchList.length > 0) {
          this.cardReceiveCfgData.codeAddDivArrBottom = []
          data.batchList.forEach(item => {
            if (item.pwdBatch) {
              this.cardReceiveCfgData.codeAddDivArrBottom.push({ pwdName: item.name, pwdId: item.batchId, action: item.action, disabled: true })
            }
          })
        }
      } else {
        this.cardReceiveCfgData.codeAddDivArrBottom = [{ pwdName: null, pwdId: null, action: null, disabled: false }]
      }
      this.cardReceiveCfgData.stock = data.stock ? data.stock : 0
      this.cardReceiveCfgData.limits = data.limit ? data.limit : 0

      // 激活条件
      this.cardActiveCfgData.activation = String(data.activation)
      this.cardActiveCfgData.activationCfgBox = data.activationCfgBox ? data.activationCfgBox : []
      this.cardActiveCfgData.examine = String(data.examine)

      // 自定义激活数据
      let action = data.customAction.map(item => {
        return {
          type: item.custom_type,
          title: item.custom_title,
          content: item.option_arr,
          conditionChecked: Boolean(item.option_ver),
          checked: Boolean(item.is_checked),
          pictureNumber: item.pictureNumber
        }
      })
      this.cardActiveCfgData.customAction = action
      // 同步用户标签
      if (data.cardTag) {
        this.cardTag = {
          cardTag: data.cardTag.cardTag,
          cardTagId: data.cardTag.cardTags
        }
      }
      // 转赠卡
      this.cardGive = data.cardGive ? data.cardGive : this.cardGive
      // 自定义权益
      if (data.customRights) {
        this.customRights = data.customRights
      }
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
        // 保存数据
        if (this.isCanSave) {
          this.isCanSave = false // 禁用保存
          this.prepareCardData()
        } else {
          this.$message.warning(this.$t('memberCard.repeatSubmit'))
        }
      } else {
        this.$message.error('保存失败')
      }
    },
    prepareCardData () {
      let pullPath = this.$imageHost + '/'
      if (this.cardNameAndBg.bgImg !== null) {
        if (this.cardNameAndBg.bgImg.includes('https:')) {
          pullPath = 'https:' + pullPath
        } else if (this.cardNameAndBg.bgImg.includes('http:')) {
          pullPath = 'http:' + pullPath
        }
      }
      if (this.cardNameAndBg.bgImg) {
        this.cardNameAndBg.bgImg = this.cardNameAndBg.bgImg.replace(pullPath, '')
      }
      // 处理领取码
      let batchIds = null
      if (Number(this.cardReceiveCfgData.receiveAction) === 1) {
        // 领取码
        batchIds = this.cardReceiveCfgData.codeAddDivArr.map(({ batchId }) => batchId)
      } else {
        // 卡号+密码
        batchIds = this.cardReceiveCfgData.codeAddDivArrBottom.map(({ pwdId }) => pwdId)
      }
      this.dealWithCustomAction()
      this.dealWithCardTag()
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
        'batchIdList': batchIds,
        'stock': this.cardReceiveCfgData.stock,
        'limits': this.cardReceiveCfgData.limits,
        'activation': this.cardActiveCfgData.activation,
        'activationCfgBox': this.cardActiveCfgData.activationCfgBox,
        'examine': this.cardActiveCfgData.examine,
        'customAction': this.cardActiveCfgData.customAction,
        'cardTag': this.cardTag,
        'cardGive': this.cardGive,
        'customRights': this.customRights,
        'cardExchangGoods': this.getCardExchangGoods()
      }
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
        this.isCanSave = true
        console.log(res)
        if (res.error === 0) {
          // success
          // 清除数据，并进行跳转
          this.successOptions()
        } else {
          this.$message.warning(this.$t('memberCard.cardCreateFailed'))
        }
      })
    },
    updateCardInfo (data) {
      updateCardRequest(data).then(res => {
        console.log(res)
        this.isCanSave = true
        if (res.error === 0) {
          // success
          this.successOptions()
        } else {
          this.$message.warning(this.$t('memberCard.cardCreateFailed'))
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
    },
    dealWithCardTag () {
      this.cardTag.cardTagId = this.cardTag.cardTagId.map(({id}) => id)
    },
    dealWithCustomAction () {
      // true/false 转换1/0
      if (this.cardActiveCfgData.customAction) {
        let tmp = this.cardActiveCfgData.customAction
        this.cardActiveCfgData.customAction = tmp.map(item => {
          item.checked = Number(item.checked)
          item.conditionChecked = Number(item.conditionChecked)
          return item
        })
      }
    },
    getCardExchangGoods () {
      let obj = {...this.cardSuiteGoodsCfgData}
      console.log(obj)
      if (obj.exchangTimeRadio === '0') {
        obj.exchangTimeType = 0
      }
      delete obj.exchangTimeRadio
      return obj
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
    .rightContainerTop,
    .member-rights,
    .rightContainerBottom,
    .advance-setting {
      padding: 10px 1%;
      background: #f8f8f8;
      border: 1px solid #e4e4e4;
      margin-bottom: 20px;
      .rightTitle {
        padding-bottom: 10px;
        border-bottom: 1px solid #ddd;
        margin-bottom: 10px;
      }
    }
    .advance-setting{
       margin-bottom: 0px;
    }
  }
  .footer {
    z-index: 100;
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
