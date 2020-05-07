<template>
    <div class="coupon-container">
        <div class="title" :style="getBackgroundImg">
            <span class="title-info">{{$t('memberCard.openCardSendVolume')}}</span>
        </div>
        <div class="show-detail" v-if="couponType === '1'">
            <p class="show-tip">{{$t('memberCard.sendCouponVol')}}</p>
            <div>
                <ul>
                    <li class="coupon-detail" v-for="(item,index) in couponList" :key="index">
                        <div class="col-left">
                               <span>
                                    <span>￥</span>
                                    <span class="denomination">{{item.denomination}}</span>
                                </span>
                                <div class="restrict">
                                    <p v-if="item.useConsumeRestrict===0">
                                        {{$t('memberCard.noThreshold')}}
                                    </p>
                                    <p v-else-if="item.useConsumeRestrict===1">
                                        {{$t('memberCard.enough')}}{{item.leastConsume}}{{$t('memberCard.usage')}}
                                    </p>
                                </div>
                        </div>
                        <div class="col-right">
                            <!-- 商品类型 -->
                            <p>{{$t('memberCard.allGoodsCanUse')}}</p>
                            <p class="actname">{{item.actName}}</p>
                            <p class="time">
                                {{dealWithTime(item)}}
                            </p>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="show-detail show-pack-detail" v-else-if="couponType === '2'">
            <p class="show-tip">{{$t('memberCard.sendCouponPack')}}</p>
            <p class="pack-name">{{couponPack.actName}}</p>
        </div>
    </div>
</template>

<script>
export default {
  props: {
    coupon: {
      type: Object,
      required: true
    }
  },
  computed: {
    couponList () {
      return this.coupon.couponList
    },
    couponType () {
      return this.coupon.couponType
    },
    couponPack () {
      return this.coupon.couponPack
    },
    getBackgroundImg () {
      let imgPath = this.$imageHost + '/image/admin/card_send_coupon.png'
      return `backgroundImage: url('${imgPath}')`
    }
  },
  mounted () {
    this.langDefault()
  },
  methods: {
    dealWithTime (coupon) {
      if (coupon.startTime && coupon.endTime) {
        let startTime = coupon.startTime.split(' ')[0]
        let endTime = coupon.endTime.split(' ')[0]
        return startTime + '--' + endTime
      }
      return '开发中'
    }
  }
}
</script>

<style lang="scss" scoped>
    .coupon-container{
        .title{
            background-repeat: no-repeat;
            background-size: 6%;
            margin-left: 12px;
            .title-info{
                padding-left: 26px;
                font-size: 13px;
                color: #333;
            }
        }
        .show-detail{
            background: #fff;
            .show-tip{
                color: #878787;
                margin: 10px 0 0 37px;
            }
        }

        .show-pack-detail{
            margin-right: 10px;
            padding-left: 10px;
            .pack-name{
               margin: 10px 0 0 37px;
               background-color: rgba(126, 86, 197, 0.3);
               color: rgb(126, 86, 197);
               padding: 10px;
               font-weight: bold;
               border-radius: 4px;
            }
        }

    }
    .coupon-detail{
        height: 80px;
        margin-top: 10px;
        margin-left: 26px;
        position: relative;
        box-sizing: border-box;
        display: flex;
        background-color: #fff;
        .col-left{
            display: flex;
            background-color: #7e56c5;
            border-top-left-radius: 3px;
            border-bottom-left-radius: 3px;
            flex: 1;
            flex-direction: column;
            align-items: center;
            justify-content: center;

        }
        span{
            color: #fff;
        }
        span.denomination{
            font-size: 20px;
            margin-left: -5px;
        }
        div.restrict{
            color: #fff;
            margin-top: 10px;
        }
        .col-left::after{
            content: "";
            display: block;
            position: absolute;
            border-left:2px dashed #fff;
            height: 100%;
            width: 2px;
            top: 0%;
            left: 30%;
        }
        .col-right{
            box-sizing: border-box;
            margin-right: 10px;
            padding-left: 10px;
            display: flex;
            flex-direction: column;
            align-items: right;
            justify-content: center;
            flex: 2;
            border-width: 1px 1px 1px 0;
            border-style: solid;
            border-color: #878787;
            border-top-right-radius: 3px;
            border-bottom-right-radius: 3px;
            p{
                margin-top: 5px;
            }

            p.actname{
                font-size: 13px;
                font-weight: 600;
                color: #333;
            }
            p.time{
                font-size: 12px;
                color: #999;
            }
        }

    }
</style>
