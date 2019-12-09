<template>
  <div>
    <div class="left-Top"></div>
    <div class="left-middle">
      <div
        class="example-card"
        v-bind:style="getBgStyle()"
      >
        <div class="card-detail">
          <div>
            <img :src="$imageHost+'/image/admin/img_home/testImg.jpeg'">
            <span>{{cardName}}</span>
          </div>
        </div>
        <div v-if="cardEffectTime">
          <div
            v-if="cardEffectTime.expiredType === '0'"
            class="effect-date"
          >
            <p>
              {{$t("memberCard.effectiveTime")}}:
              {{startTime}}
              {{$t("memberCard.to")}}
              {{endTime}}
            </p>
          </div>
          <div
            v-else-if="cardEffectTime.expiredType === '1'"
            class="effect-date"
          >
            <p>
              {{$t("memberCard.effectiveTime")}}:
              {{$t('memberCard.fromDate')}}
              {{cardEffectTime.receiveDay}}
              <span v-if="cardEffectTime.dateType==='0'"> {{$t('memberCard.timeDay')}}</span>
              <span v-if="cardEffectTime.dateType==='1'"> {{$t('memberCard.timeWeek')}}</span>
              <span v-if="cardEffectTime.dateType==='2'"> {{$t('memberCard.timeMonth')}}</span>
            </p>
          </div>
          <div
            v-else-if="cardEffectTime.expiredType === '2'"
            class="effect-date"
          >
            {{$t("memberCard.ForeverEffective")}}
          </div>
        </div>

      </div>
      <div
        class="member-power"
        v-for="(item,index) in leftNavData"
        :key="index"
      >

        <div
          class="s-power-detail"
          v-if="index===0"
        >
          <div
            v-if="cardUsageCfgData.mobile"
            class="s-power-title"
            v-bind:style="getMiniLog(item)"
          >
            {{item.title}}
          </div>
          <div
            v-if="powerDiscount"
            class="s-power-detail-unit"
            style="display: block;"
          >
            <p v-if="discountGoodsType==='0'">{{item.children[0]}}
              <span v-if="discount !== ''">
                {{discount}}{{$t('memberCard.discount')}}
              </span>
            </p>
            <p v-else-if="discountGoodsType==='1'">{{item.children[1]}}
              <span v-if="discount !== ''">
                {{discount}}{{$t('memberCard.discount')}}
              </span>
            </p>
          </div>
        </div>
        <div v-if="index===1 && cardScoreCfgData">
          <div
            v-if="cardUsageCfgData.mobile"
            class="s-power-title"
            v-bind:style="getMiniLog(item)"
          >
            {{item.title}}
          </div>
          <div
            v-if="cardScoreCfgData.powerScore"
            class="s-power-detail-unit"
          >
            <div>
              <p>{{item.children[0]}}{{cardScoreCfgData.score}}{{$t('memberCard.score')}}</p>
            </div>
            <div v-if="cardScoreCfgData.offSet==='0'">
              <p>
                {{$t('memberCard.shopFull')}}{{cardScoreCfgData.shopingInputLeft}}{{$t('memberCard.send')}}{{cardScoreCfgData.shopingInputRight}}{{$t('memberCard.score')}}
              </p>
              <p
                v-for="(scoreItem,scoreIndex) in cardScoreCfgData.addIntegralArr"
                :key="scoreIndex"
              >
                {{$t('memberCard.shopFull')}}{{scoreItem.leftInput}}{{$t('memberCard.send')}}{{scoreItem.rightInput}}{{$t('memberCard.score')}}
              </p>
            </div>
            <div v-else-if="cardScoreCfgData.offSet==='1'">
              <p>
                {{$t('memberCard.shopEachFull')}}
                {{cardScoreCfgData.shopingInputLeftM}}
                {{$t('memberCard.send')}}
                {{cardScoreCfgData.shopingInputRightM}}
                {{$t('memberCard.score')}}
              </p>
            </div>
          </div>
        </div>

        <div v-if="index===2 && cardChargeCfgData">
          <div
            v-if="cardUsageCfgData.mobile"
            class="s-power-title"
            v-bind:style="getMiniLog(item)"
          >
            {{item.title}}
          </div>
          <div
            v-if="cardChargeCfgData.powerCard"
            class="s-power-detail-unit"
          >
            <div>
              <p>{{$t('memberCard.openCardSend')}}{{cardChargeCfgData.sendMoney}}{{$t('memberCard.yuan')}}</p>
            </div>
            <div v-if="cardChargeCfgData.offset==='2'">
              <p>{{$t('memberCard.justCharge')}}</p>
            </div>
            <div v-if="cardChargeCfgData.offset==='0'">
              <p>
                {{$t('memberCard.chargeFull')}}
                {{cardChargeCfgData.chargeInputLeft}}
                {{$t('memberCard.send')}}
                {{cardChargeCfgData.chargeInputRight}}
                {{$t('memberCard.yuan')}}
              </p>
              <p
                v-for="(chargeItem,chargeIndex) in cardChargeCfgData.addChargeArr"
                :key="chargeIndex"
              >
                {{$t('memberCard.chargeFull')}}
                {{chargeItem.leftInput}}
                {{$t('memberCard.send')}}
                {{chargeItem.rightInput}}
                {{$t('memberCard.yuan')}}
              </p>

            </div>
            <div v-if="cardChargeCfgData.offset==='1'">
              <p>
                {{$t('memberCard.chargeEachFull')}}
                {{cardChargeCfgData.chargeInputLeftM}}
                {{$t('memberCard.send')}}
                {{cardChargeCfgData.chargeInputRightM}}
                {{$t('memberCard.yuan')}}
              </p>
            </div>

          </div>
        </div>
        <div v-if="index===3">
          <div
            v-if="cardUsageCfgData.mobile"
            class="s-power-title"
            v-bind:style="getMiniLog(item)"
          >
            {{item.title}}
          </div>
          <div
            v-if="cardUsageCfgData.mobile"
            class="s-power-detail-unit"
          >
            <p>{{cardUsageCfgData.mobile}}</p>
          </div>
        </div>
        <div v-if="index===4">
          <div
            v-if="cardUsageCfgData.mobile"
            class="s-power-title"
            v-bind:style="getMiniLog(item)"
          >
            {{item.title}}
          </div>
          <div class="s-power-detail-unit card-desc">
            <p>{{cardUsageCfgData.desc}}</p>
          </div>
        </div>
        <div v-if="index===5 && cardStoreCfgData">
          <div
            v-if="cardUsageCfgData.mobile"
            class="s-power-title"
            v-bind:style="getMiniLog(item)"
          >
            {{item.title}}
          </div>
          <div class="s-power-detail-unit">
            <div v-if="cardStoreCfgData.storeListType==='0'">
              {{$t('memberCard.allStores')}}
            </div>
            <div
              v-if="cardStoreCfgData.storeListType==='1'"
              class="store-list"
            >
              <span
                v-for="(storeItem,storeIndex) in cardStoreCfgData.choosedStore"
                :key="storeIndex"
              >
                {{storeItem.storeName}}
                <span v-if="storeIndex<cardStoreCfgData.choosedStore.length-1">
                  ,
                </span>
              </span>
            </div>
            <div v-if="cardStoreCfgData.storeListType==='-1'">
              {{$t('memberCard.banStore')}}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    sampleData: {
      type: Object,
      required: true
    }
  },
  computed: {
    cardType () {
      return this.sampleData.cardType
    },
    startTime () {
      if (this.sampleData.cardEffectTime) {
        if (this.sampleData.cardEffectTime.fixedDate) {
          let date = this.sampleData.cardEffectTime.fixedDate[0].split(' ')
          console.log(date)
          return date[0]
        }
      }
      return null
    },
    endTime () {
      if (this.sampleData.cardEffectTime) {
        if (this.sampleData.cardEffectTime.fixedDate) {
          let date = this.sampleData.cardEffectTime.fixedDate[1].split(' ')
          console.log(date)
          return date[0]
        }
      }
      return null
    },
    bgType () {
      return this.sampleData.bgType
    },
    bgStyleComputed () {
      console.log('模型卡背景色检测')
      let bg = `background-color:${this.sampleData.bgColor}`
      return bg
    },
    cardName () {
      return this.sampleData.cardName
    },
    bgImg () {
      return this.sampleData.bgImg
    },
    powerDiscount () {
      return this.sampleData.powerDiscount
    },
    discount () {
      return this.sampleData.discount
    },
    discountGoodsType () {
      return this.sampleData.discountGoodsType
    },
    cardScoreCfgData () {
      return this.sampleData.cardScoreCfgData
    },
    cardChargeCfgData () {
      return this.sampleData.cardChargeCfgData
    },
    cardEffectTime () {
      return this.sampleData.cardEffectTime
    },
    cardStoreCfgData () {
      return this.sampleData.cardStoreCfgData
    },
    cardUsageCfgData () {
      return this.sampleData.cardUsageCfgData
    }

  },
  data () {
    return {
      leftNavData: [
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/discount.png',
          title: this.$t('memberCard.memberAd'),
          children: ['指定商品折扣', '全部商品折扣']
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/score_mem.png',
          title: this.$t('memberCard.memberDiscountD'),
          children: [this.$t('memberCard.openCardSend'), this.$t('memberCard.detailSend')]
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/charge_icon.png',
          title: this.$t('memberCard.chargeRule'),
          children: [this.$t('memberCard.justCharge')]
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/card_phone.png',
          title: this.$t('memberCard.contactPhone'),
          children: []
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/article.png',
          title: this.$t('memberCard.cardUsage'),
          children: []
        },
        {
          backGroundImgUrl: this.$imageHost + '/image/admin/store_icon.png',
          title: this.$t('memberCard.usingStore'),
          children: [this.$t('memberCard.allStores')]
        }
      ]
    }
  },
  methods: {
    getMiniLog (item) {
      return 'backgroundImage: url(' + item.backGroundImgUrl + ')'
    },
    getBgStyle () {
      console.log(this.bgImg)
      if (this.bgType === '1' && this.bgImg) {
        return `backgroundImage:url(${this.$imageHost}/${this.bgImg});backgroundRepeat:no-repeat;background-size: 100% 100%;`
      } else {
        return this.bgStyleComputed
      }
    }
  }

}
</script>
<style scoped lang="scss">
.left-Top {
  height: 50px;
  width: 100%;
  background: url(../../../../../../../assets/adminImg/mem_card.png) no-repeat;
  background-size: 100%;
}
.left-middle {
  .example-card {
    width: 90%;
    margin: 10px auto;
    border-radius: 10px;
    background: #e6cb96;
    color: #ffffff;
    padding: 10px 12px;
    .card-detail {
      margin-bottom: 30px;
      img {
        vertical-align: middle;
        width: 50px;
        height: 50px;
        border-radius: 50%;
        margin-right: 10px;
      }
    }
    .effect-date {
      font-size: 13px;
    }
  }
  .member-power {
    background-color: #fff;
    .s-power-title {
      padding: 10px 0;
      background: url(../../../../../../../assets/adminImg/score_mem.png)
        no-repeat;
      background-size: 6%;
      background-position: center left;
      padding-left: 25px;
      font-size: 13px;
      color: #333;
      margin-left: 12px;
    }
    .s-power-detail-unit {
      color: #878787;
      padding: 0 0 10px;
      padding-left: 25px;
      margin-left: 12px;
      p {
        padding-top: 10px;
        color: #878787;
        padding: 0 0 10px;
      }
      .store-list {
        width: 170px;
      }
    }
    .card-desc {
      p {
        width: 200px;
        overflow-wrap: break-word;
      }
    }
  }
}
</style>
