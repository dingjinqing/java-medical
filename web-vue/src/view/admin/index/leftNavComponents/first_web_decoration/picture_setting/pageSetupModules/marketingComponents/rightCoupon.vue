<template>
  <div class="rightCoupon">
    <div class="rightCouponMain">
      <h2>{{$t('coupon.couponModule')}}</h2>
      <!--模块私有-->
      <div class="content">
        <div>
          <div class="couponTop">
            <span>{{$t('coupon.coupon')}}:</span>
            <div class="couponList">
              <div
                v-for="(item,index) in data.coupon_arr"
                :key="index"
                class="coupon_list"
                :style="'border-color:'+backgroundColorTransparent"
              >
                <img
                  class="couponDel"
                  :src="$imageHost+'/image/admin/sign_del.png'"
                  @click="handleToDelCoupon(index)"
                >
                <div
                  class="coupon_list_top"
                  :style="'color:'+backgroundColor"
                >
                  {{item.act_code==='discount'?'':'¥'}}<span>{{item.denomination}}<i style="font-size:14px">{{item.act_code==='discount'?$t('coupon.fracture'):''}}</i></span>
                </div>
                <div class="coupon_list_center">
                  <div
                    class="coupon_center_limit"
                    :style="'color:'+backgroundColor"
                  >
                    {{item.consume_text}}
                  </div>
                  <div
                    class="coupon_center_number"
                    :style="'color:'+backgroundColorTransparent+';margin-top:3px;word-break: break-all'"
                  >{{item.receive_text}}</div>
                </div>
                <div
                  class="coupon_list_bottom new_back"
                  :style="'background-color:'+backgroundColor"
                >
                  {{item.use_score===0?$t('coupon.receive'):item.score_number+$t('coupon.integral')}}
                </div>
              </div>
            </div>
          </div>
          <div
            class="couponAdd"
            :style="hoverTips?'margin-left:67px':''"
            @click="handleToCallCouponDialog()"
            v-if="data.coupon_arr.length !== 6"
          >
            <img :src="$imageHost+'/image/admin/shop_beautify/add_decorete.png'">
            <p>{{$t('coupon.addDiscountVolume')}}</p>
          </div>
        </div>
      </div>
      <!--end-->
    </div>
    <!--优惠券弹窗-->
    <AddCouponDialog
      :tuneUpCoupon='tuneUpCoupon'
      :couponBack='couponBack'
      origin='decCouponPackage'
      @handleToCheck='handleToCheck'
    />
  </div>
</template>
<script>
export default {
  components: {
    AddCouponDialog: () => import('@/components/admin/addCouponDialog')
  },
  props: {
    modulesData: Object,
    sortIndex: Number
  },
  data () {
    return {
      couponBack: [], // 回显数据
      tuneUpCoupon: false,
      nowShowCouponList: [],
      backgroundColor: '',
      backgroundColorTransparent: '',
      noThreshold: '', // 无门槛  文字
      full: '', // 满 文字
      available: '', // 可用  文字
      surplus: '', // 剩余  文字
      zhang: '', // 张 文字
      data: {
        coupon_arr: [
          // 选中的优惠券数组
          // {
          //   'act_code': 'voucher', // 是否是打折卷  discount：打折卷   voucher不是打折卷
          //   'denomination': '5', // 面额
          //   'consume_text': '无门槛', // 使用门槛
          //   'receive_text': '剩余93张', // 卡卷剩余数
          //   'coupon_id': -1, // 优惠券id
          //   'use_score': '0', // 是否可以积分兑换
          //   'score_number': '' // 需要积分数
          // }
        ]
      },
      hoverTips: 'hoverTips'
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        console.log(newData, this.modulesData)
        let flag = false
        if (this.modulesData) {
          console.log(this.modulesData)
          Object.keys(this.modulesData).forEach((item, index) => {
            console.log(11)
            flag = true
          })
          console.log(flag)
          if (flag) {
            console.log(typeof this.modulesData.coupon_arr.length)
            if (this.modulesData.coupon_arr.length !== 0) {
              if (this.modulesData.coupon_arr[0].coupon_id === -1) {
                this.modulesData.coupon_arr = []
              }
            }
            this.data = this.modulesData
          }
          console.log(this.modulesData)
        }
      },
      immediate: true
    },
    // 监听数据变换
    data: {
      handler (newData) {
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    },
    lang () {
      this.noThreshold = this.$t('coupon.noThreshold')
      this.full = this.$t('coupon.full')
      this.available = this.$t('coupon.available')
      this.surplus = this.$t('coupon.surplus')
      this.zhang = this.$t('coupon.zhang')
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 点击添加优惠券按钮
    handleToCallCouponDialog () {
      this.couponBack = []
      this.data.coupon_arr.forEach((item, index) => {
        this.couponBack.push(item.coupon_id)
      })
      this.tuneUpCoupon = !this.tuneUpCoupon
    },
    // 选择优惠券弹窗数据后回调函数
    handleToCheck (res) {
      // let saveObj = {
      //   'module_name': 'm_coupon', // 模块名称
      //   'coupon_arr': [ // 选中的优惠券数组
      //     {
      //       'act_code': 'voucher', // 是否是打折卷  discount：打折卷   voucher不是打折卷
      //       'denomination': '5', // 面额
      //       'consume_text': '无门槛', // 使用门槛
      //       'receive_text': '剩余93张', // 卡卷剩余数
      //       'coupon_id': '432', // 优惠券id
      //       'use_score': '0', // 是否可以积分兑换
      //       'score_number': '' // 需要积分数
      //     }
      //   ]
      // }
      this.data.coupon_arr = []
      res.forEach((item, index) => {
        let useConsumeRestrict = ''
        // 判断优惠券是否已被选过
        let isExistence = this.data.coupon_arr.filter((itemC, indexC) => {
          return itemC.coupon_id === item.id
        })

        if (item.useConsumeRestrict === 0) {
          useConsumeRestrict = this.noThreshold
        } else {
          useConsumeRestrict = `${this.full}${item.leastConsume}${this.available}`
        }
        let obj = {

          'act_code': item.actCode, // 是否是打折卷  discount：打折卷   voucher不是打折卷
          'denomination': item.denomination, // 面额
          'consume_text': useConsumeRestrict, // 使用门槛
          'receive_text': `${this.surplus}${item.surplus}${this.zhang}`, // 卡卷剩余数
          'coupon_id': item.id, // 优惠券id
          'use_score': item.useScore, // 是否可以积分兑换
          'score_number': item.scoreNumber, // 需要积分数
          'limitSurplusFlag': item.limitSurplusFlag
        }
        console.log(obj)
        if (isExistence.length === 0) {
          this.data.coupon_arr.push(obj)
        }
        console.log(this.data.coupon_arr)
      })
      // let obj = {
      //   actCode: 'discount',
      //   actName: '打折测试',  优惠券名称
      //   denomination: 5,    面额
      //   endTime: '2019-09-20 00:00:00',  优惠券有效期结束时间
      //   id: 41,   id
      //   ischeck: true,
      //   leastConsume: 500,    满多少可用
      //   recommendCatId: null,  指定平台分类可用的平台分类ID串，为空时为全部平台分类可用
      //   recommendGoodsId: null,   指定商品可用的商品ID串，为空时为全部商品可用
      //   recommendSortId: null,   指定商家分类可用的商家分类ID串，为空时为全部商品商家分类可用
      //   startTime: '2019-09-10 00:00:00',    优惠券有效期开始时间
      //   surplus: 22,  //剩余数量
      //   useConsumeRestrict: 1,   使用限制0：无限制   1:有限制
      //   validity: 0,  优惠券有效天数
      //   validityHour: 0,  优惠券有效小时数
      //   validityMinute: 0,  优惠券有效分钟数
      //   validityType: 0,  优惠券有效期类型标记
      //   useScore:'',   是否可以积分兑换
      //   scoreNumber:''  需要积分数
      // }
      this.backgroundColor = localStorage.getItem('V-backgroundColor') || 'rgb(255, 102, 102)'
      this.backgroundColorTransparent = this.backgroundColor.split(')')[0] + ',0.4)'
      console.log(res)
      this.nowShowCouponList = res
    },
    // 点击优惠券右上角删除icon
    handleToDelCoupon (index) {
      this.data.coupon_arr.splice(index, 1)
    }
  }
}
</script>
<style lang="scss" scoped>
.rightCoupon {
  .rightCouponMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
    h2 {
      font-size: 14px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    .content {
      margin: 20px 0 0 20px;
      .couponTop {
        display: flex;
        span {
          margin-right: 10px;
        }
        .couponList {
          display: flex;
          flex-wrap: wrap;
          width: 370px;
          .coupon_list {
            width: 100px;
            margin-right: 20px;
            margin-bottom: 10px;
            border: 1px solid #fbb;
            -webkit-border-radius: 110px;
            -moz-border-radius: 10px;
            border-radius: 10px;
            text-align: center;
            position: relative;
            .couponDel {
              position: absolute;
              top: -5px;
              right: -5px;
              cursor: pointer;
            }
            .coupon_list_top {
              margin-top: 10px;
              color: #f66;
              font-size: 14px;
              height: 34px;
              span {
                font-size: 20px;
                font-weight: bold;
                display: inline-block;
              }
            }
            .coupon_list_center {
              height: 40px;
              color: #f66;
              font-size: 12px;
            }
            .coupon_list_bottom {
              font-size: 12px;
              background: #f66
                url(../../../../../../../../assets/adminImg/coupon_border.png)
                repeat-x top;
              -webkit-background-size: 12px;
              background-size: 12px;
              height: 24px;
              line-height: 30px;
              color: #fff;
              border-radius: 0 0 7px 7px;
              margin-left: -1px;
            }
          }
        }
      }
      .couponAdd {
        margin-left: 57px;
        width: 100px;
        height: 90px;
        background: #fff;
        border: 1px solid #e4e4e4;
        text-align: center;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        cursor: pointer;
        p {
          color: #999;
          font-size: 12px;
          margin-top: 17px;
        }
      }
    }
  }
}
</style>
