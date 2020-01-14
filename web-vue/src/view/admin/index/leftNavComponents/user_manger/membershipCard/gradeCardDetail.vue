<template>
  <div class="membershipCardDetail">
    <div class="membershipCardDetailMain">
      <div class="leftContainer">
        <showSampleCard :sampleData="sampleCardData"></showSampleCard>
      </div>
      <div class="rightContainer">
        <div class="rightContainerTop">
          <div class="rightTile">{{ $t('memberCard.basicSetting') }}</div>
          <cardAvailableCfg
            :val="cardAvailableCfgData"
            @input="initCardAvailableCfgData"
          ></cardAvailableCfg>
          <cardNameAndBg
            :val="cardNameAndBg"
            @input="initCardNameAndBg"
            ref="cardNameAndBg"
          ></cardNameAndBg>
          <scoreDiscount
            :val="disCountData"
            @input="initDiscountData"
            ref="disCountData"
          ></scoreDiscount>
          <cardScoreCfg
            :val="cardScoreCfgData"
            @input="initCardScoreCfgData"
            ref="cardScoreCfgData"
          ></cardScoreCfg>
          <ownGoods
            :val="ownGoodsData"
            @input="initOwnGoodsData"
          ></ownGoods>
          <cardUsageCfg
            :val="cardUsageCfgData"
            @input="initCardUsageCfgData"
          ></cardUsageCfg>
        </div>
        <div class="rightContainerMiddle">
          <div class="rightTitle">{{$t('memberCard.gradeSetting')}}</div>
          <cardGradeCfg
            :val="cardGradeCfgData"
            @input="initCardGradeCfgData"
            ref="cardGradeCfgData"
          ></cardGradeCfg>
        </div>

        <div class="rightContainerBottom">
          <div class="rightTitle">{{ $t('memberCard.getSetting') }}</div>
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
    cardUsageCfg: () => import(
      './subcomponents/cardUsageCfg'
    ),
    cardActiveCfg: () => import(
      './subcomponents/cardActiveCfg'
    ),
    cardGradeCfg: () => import(
      './subcomponents/cardGradeCfg'
    ),
    cardAvailableCfg: () => import(
      './subcomponents/cardAvailableCfg'
    )
  },
  data () {
    let cardTypeTmp = 0
    let cardScoreCfgDataTmp = {
      powerScore: true,
      score: undefined,
      offSet: '0',
      shopingInputLeft: 100,
      shopingInputRight: 100,
      shopingInputLeftM: 100,
      shopingInputRightM: 100,
      addIntegralArr: [
      ],
      valid: false
    }
    let cardUsageCfgDataTmp = {
      desc: '',
      mobile: ''
    }
    let flagTmp = '2'
    return {
      cardId: null,
      cardType: cardTypeTmp,
      cardNameAndBg: {
        cardName: null,
        bgType: '0',
        bgColor: '',
        bgImg: '',
        valid: false
      },
      disCountData: {
        powerDiscount: true,
        discount: undefined,
        discountGoodsType: '1',
        choosedGoodsId: [],
        choosedStoreId: [],
        choosedPlatformId: [],
        choosedBrandId: [],
        valid: false
      },
      ownGoodsData: {
        powerOwnGoods: false,
        choosedGoodsId: [],
        choosedStoreId: [],
        choosedPlatformId: [],
        choosedBrandId: []
      },
      cardScoreCfgData: cardScoreCfgDataTmp,
      cardCouponCfgData: {
        powerCoupon: true,
        couponType: '1',
        couponIdList: []
      },
      cardUsageCfgData: cardUsageCfgDataTmp,
      cardAvailableCfgData: {
        flag: flagTmp
      },
      cardGradeCfgData: {
        gradeScore: undefined,
        gradeCrash: undefined,
        gradeValue: null,
        valid: false
      },
      cardActiveCfgData: {
        activation: '0',
        activationCfgBox: [],
        examine: '0',
        valid: false
      },
      sampleCardData: {
        cardType: cardTypeTmp,
        cardName: '',
        bgColor: '',
        bgImg: '',
        bgType: '',
        powerDiscount: true,
        discount: '',
        discountGoodsType: '0',
        cardScoreCfgData: cardScoreCfgDataTmp,
        cardChargeCfgData: null,
        cardEffectTime: null,
        cardStoreCfgData: null,
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
      console.log(this.cardId)
      if (this.cardId) {
        // 单张会员卡信息
        this.getCardDetailInfoById(this.cardId)
      }
    },
    getCardDetailInfoById (id) {
      getCardDetailInfoRequest({ id }).then(res => {
        console.log(res)
        if (res.error === 0) {
          // success
          // bind data from backend to frontend
          this.bindBackAndFrontEndData(res.content)
        }
      })
    },
    bindBackAndFrontEndData (data) {
      this.cardAvailableCfgData.flag = String(data.flag)
      this.cardId = data.id
      this.cardNameAndBg.cardName = data.cardName
      this.cardNameAndBg.bgType = String(data.bgType)
      this.cardNameAndBg.bgColor = data.bgColor
      this.cardNameAndBg.bgImg = data.bgImg
      // 折扣
      this.disCountData.powerDiscount = data.powerCount === 1
      this.disCountData.discount = data.disCount
      if (this.isValidValue(data.discountIsAll)) {
        this.disCountData.discountGoodsType = String(data.discountIsAll)
      }
      this.disCountData.choosedGoodsId = data.goodsId
      this.disCountData.choosedStoreId = data.shopCategoryIds
      this.disCountData.choosedPlatformId = data.platformCategoryIds
      this.disCountData.choosedBrandId = data.brandId.map(item => Number(item))
      // 积分
      this.cardScoreCfgData.powerScore = data.powerScore ? data.powerScore === 1 : true
      this.cardScoreCfgData.score = data.score
      this.cardScoreCfgData.offset = data.scoreJson ? String(data.scoreJson.offset) : '0'
      if (data.scoreJson.goodsMoney.length > 0) {
        this.cardScoreCfgData.shopingInputLeft = data.scoreJson.goodsMoney[0]
        this.cardScoreCfgData.shopingInputRight = data.scoreJson.getScores[0]
        data.scoreJson.goodsMoney.splice(0, 1)
        data.scoreJson.getScores.splice(0, 1)
        for (let index in data.scoreJson.goodsMoney) {
          this.cardScoreCfgData.addIntegralArr.push({
            leftInput: data.scoreJson.goodsMoney[index],
            rightInput: data.scoreJson.getScores[index]
          })
        }
      }
      // 专享
      this.ownGoodsData.powerOwnGoods = data.powerPayOwnGood === 'on'
      this.ownGoodsData.choosedGoodsId = data.ownGoodsId
      this.ownGoodsData.choosedStoreId = data.ownStoreCategoryIds
      this.ownGoodsData.choosedPlatformId = data.ownPlatFormCategoryIds
      this.ownGoodsData.choosedBrandId = data.ownBrandId

      // 适用须知
      this.cardUsageCfgData.desc = data.desc
      this.cardUsageCfgData.mobile = data.mobile

      // 升级
      this.cardGradeCfgData.gradeScore = data.gradeConditionJson.gradeScore
      this.cardGradeCfgData.gradeCrash = data.gradeConditionJson.gradeMoney
      this.cardGradeCfgData.gradeValue = data.grade

      // 激活条件
      this.cardActiveCfgData.activation = String(data.activation)
      this.cardActiveCfgData.activationCfgBox = data.activationCfgBox ? data.activationCfgBox : []
      this.cardActiveCfgData.examine = String(data.examine)
    },
    isValidValue (data) {
      return data !== null && data !== undefined
    },
    getMiniLog (item) {
      return 'backgroundImage: url(' + item.backGroundImgUrl + ')'
    },
    initCardAvailableCfgData (val) {
      this.cardAvailableCfgData = val
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
    initCardGradeCfgData (val) {
      this.cardGradeCfgData = val
    },
    initCardUsageCfgData (val) {
      this.cardUsageCfgData = val
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
      console.log('保存')
      this.$refs.cardNameAndBg.$emit('checkRule')

      // 检验通过
      console.log(this.cardNameAndBg)
      this.$refs.disCountData.$emit('checkRule')
      this.$refs.cardGradeCfgData.$emit('checkRule')
      this.$refs.cardActiveCfgData.$emit('checkRule')
      this.$refs.cardScoreCfgData.$emit('checkRule')
      // 权益判断
      if (this.cardScoreCfgData.powerScore || this.ownGoodsData.powerOwnGoods || this.disCountData.powerDiscount) {

      } else {
        this.$message.warning('至少选择一项会员权益')
      }

      if (this.cardNameAndBg.valid && this.disCountData.valid && this.cardGradeCfgData.valid && this.cardActiveCfgData.valid && this.cardScoreCfgData.valid) {
        // 保存数据
        this.prepareCardData()
      }

      // 保存数据
      // this.prepareCardData()
    },
    dealWithDynamicArrayData () {
      // 积分
      if (this.cardScoreCfgData.offSet === '0') {
        let goodsMoney = []
        let getScores = []
        goodsMoney.push(this.cardScoreCfgData.shopingInputLeft)
        getScores.push(this.cardScoreCfgData.shopingInputRight)
        for (let item of this.cardScoreCfgData.addIntegralArr) {
          goodsMoney.push(item.leftInput)
          getScores.push(item.rightInput)
        }
        this.cardScoreCfgData.goodsMoney = goodsMoney
        this.cardScoreCfgData.getScores = getScores
      }
    },
    prepareCardData () {
      this.dealWithDynamicArrayData()
      let pullPath = this.$imageHost + '/'
      if (this.cardNameAndBg.bgImg) {
        this.cardNameAndBg.bgImg = this.cardNameAndBg.bgImg.replace(pullPath, '')
      }
      let obj = {
        'flag': this.cardAvailableCfgData.flag,
        'id': this.cardId,
        'cardType': this.cardType,
        'cardName': this.cardNameAndBg.cardName,
        'bgType': this.cardNameAndBg.bgType,
        'bgColor': this.cardNameAndBg.bgColor,
        'bgImg': this.cardNameAndBg.bgImg,
        'powerCount': this.disCountData.powerDiscount ? 1 : 0,
        'disCount': this.disCountData.discount,
        'discountIsAll': this.disCountData.discountGoodsType,
        'goodsId': this.disCountData.choosedGoodsId,
        'shopCategoryIds': this.disCountData.choosedStoreId,
        'platformCategoryIds': this.disCountData.choosedPlatformId,
        'discountBrandId': this.disCountData.choosedBrandId,
        'powerPayOwnGood': this.ownGoodsData.powerOwnGoods ? 'on' : '',
        'ownGoodsId': this.ownGoodsData.choosedGoodsId,
        'ownStoreCategoryIds': this.ownGoodsData.choosedStoreId,
        'ownPlatFormCategoryIds': this.ownGoodsData.choosedPlatformId,
        'ownBrandId': this.ownGoodsData.choosedBrandId,
        'powerScore': this.cardScoreCfgData.powerScore ? 1 : 0,
        'score': this.cardScoreCfgData.score,
        'scoreJson': {
          'offset': this.cardScoreCfgData.offSet,
          'goodsMoney': this.cardScoreCfgData.goodsMoney,
          'getScores': this.cardScoreCfgData.getScores,
          'perGoodsMoney': this.cardScoreCfgData.shopingInputLeftM,
          'perGetScores': this.cardScoreCfgData.shopingInputRightM
        },
        'desc': this.cardUsageCfgData.desc,
        'mobile': this.cardUsageCfgData.mobile,
        'gradeConditionJson': { gradeScore: this.cardGradeCfgData.gradeScore, gradeMoney: this.cardGradeCfgData.gradeCrash },
        'grade': this.cardGradeCfgData.gradeValue,
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
    // 10- 操作成功后的提示，清除数据，路由跳转
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
    // 5- 根据会员卡类型获取相应url
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
    .rightContainerMiddle {
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
