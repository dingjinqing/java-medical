<template>
  <div class="card-coupon">
    <el-form
      :model="ruleForm"
      status-icon
      ref="ruleForm"
      label-width="100px"
    >
      <el-form-item class="coupon-item">
        <div>
          <el-checkbox v-model="ruleForm.powerCoupon">
            {{ $t('memberCard.openCardSendVolume') }}
          </el-checkbox>
          <span class="coupon-info">
            {{ $t('memberCard.volumeActiveInfo') }}
          </span>
        </div>
      </el-form-item>
      <el-form-item class="coupon-item">
        <div
          v-if="ruleForm.powerCoupon"
          class="send-coupon"
        >
          <div class="coupon-div-top">

            <div>
              <el-radio
                v-model="ruleForm.couponType"
                label="1"
              >
                {{ $t('memberCard.offerVolume') }}
              </el-radio>
              <span class="coupon-info">
                {{ $t('memberCard.offerInfo') }}
              </span>
              <span v-if="couponError"  class="coupon-error-tip">请选择开卡送优惠券</span>
            </div>
            <div
              v-if="ruleForm.couponType==='1'"
              class="coupon-list"
            >
              <div
                v-for="(item,index) in ruleForm.couponList"
                :key="index"
                class="coupon-list-item"
              >
                <img
                  :src="$imageHost +'/image/admin/sign_del.png'"
                  @click="handlToDelCouList(index)"
                >
                <div class="coupon-list-top">
                  {{item.actCode==='discount'?'':'¥'}}
                  <span>
                    {{item.denomination}}
                    <i style="font-size:14px">
                      {{item.actCode==='discount'?'折':''}}
                    </i>
                  </span>
                </div>
                <div class="coupon-list-center">
                  <div class="coupon-center-limit">
                    {{
                      item.useConsumeRestrict>0?
                    `${$t('memberCard.enough')} ${item.leastConsume}${$t('memberCard.usage')}`
                    :`${$t('memberCard.unlimit')}`
                    }}
                  </div>
                  <div class="coupon-center-number">
                    {{ $t('memberCard.remaining') }}
                    <span>{{item.surplus}}</span>
                    {{ $t('memberCard.unitZhang') }}
                  </div>
                </div>
                <div
                  class="coupon-list-bottom"
                  :style="`backgroundImage:url('${$imageHost}/image/admin/coupon_border.png')`"
                >
                  {{ $t('memberCard.receive') }}
                </div>
              </div>
              <!-- end -->
              <div
                class="card-add-clickDiv"
                @click="handleToCallDialog()"
                v-if="ruleForm.couponList.length<5"
              >
                <div class="card-add-click">
                  <img :src="$imageHost +'/image/admin/shop_beautify/add_decorete.png'">
                  <p>{{ $t('memberCard.addVolume') }}</p>
                </div>
              </div>
            </div>
          </div>
          <div class="coupon-div-bottom">
            <div>
              <el-radio
                v-model="ruleForm.couponType"
                label="2"
              >{{ $t('memberCard.sendCoupon') }}
              </el-radio>
            </div>
          </div>
        </div>
      </el-form-item>
    </el-form>
    <!--添加优惠券-->
    <AddCouponDialog
      :tuneUpCoupon="couponDialogVisable"
      :couponBack="couponBack"
      @handleToCheck="handleCouponList"
    />
  </div>
</template>
<script>
export default {
  components: {
    AddCouponDialog: () => import('@/components/admin/addCouponDialog')
  },
  props: {
    val: {
      type: Object,
      required: true
    }
  },
  computed: {
    ruleForm: {
      get () {
        return this.val
      },
      set () {
        this.$emit('input', this.ruleForm)
      }
    }
  },
  watch: {
    'ruleForm': {
      handler (newName, oldName) {
        this.val = this.ruleForm
        this.ruleForm = this.val
      },
      deep: true
    }
  },
  mounted () {
    this.$on('checkRule', () => {
      if (this.ruleForm.powerCoupon) {
        if (this.ruleForm.couponType === '1') {
          if (this.ruleForm.couponIdList.length > 0) {
            this.ruleForm.valid = true
          } else {
            this.ruleForm.valid = false
            this.$message.warning('请选择开卡送优惠券')
            this.couponError = true
          }
        } else {
          this.ruleForm.valid = true
        }
      } else {
        this.ruleForm.valid = true
      }
    })
  },
  data () {
    return {
      couponDialogVisable: false,
      couponError: false,
      couponBack: []
    }
  },
  methods: {
    handleToCallDialog () {
      console.log('弹出优惠券窗口')
      this.couponDialogVisable = !this.couponDialogVisable
      this.couponBack = this.ruleForm.couponIdList
    },
    handleCouponList (val) {
      console.log('添加优惠券')
      // this.couponList = val
      this.ruleForm.couponList = val
      let res = val.map(({ id }) => id)
      this.ruleForm.couponIdList = res

      this.couponError = false
    },
    handlToDelCouList (index) {
      console.log('删除优惠券', index)
      this.ruleForm.couponList.splice(index, 1)
      this.ruleForm.couponIdList = this.couponList.map(({ id }) => id)
    }
  }
}
</script>
<style scoped lang="scss">
.card-coupon {
  /deep/ .el-form-item {
    margin-bottom: 2px;
  }
  .coupon-item {
    padding-left: 100px;
    .coupon-info {
      color: #9d9d9d;
    }
    .coupon-error-tip{
      color: #F56C6C;
      font-size: 12px;
    }
    .send-coupon {
      padding-left: 71px;
      display: flex;
      flex-direction: column;
      .coupon-div-top {
        width: 385px;
        .coupon-list {
          margin-left: 30px;
          .coupon-list-item {
            float: left;
            width: 108px;
            text-align: center;
            margin-right: 10px;
            margin-bottom: 15px;
            cursor: pointer;
            position: relative;
            img {
              position: absolute;
              top: -5px;
              right: -5px;
            }
            .coupon-list-top {
              height: 20px;
              color: #f66;
              font-size: 14px;
              border: 1px solid #fbb;
              border-top-left-radius: 5px;
              border-top-right-radius: 5px;
              border-bottom: none;
              span {
                font-size: 20px;
                font-weight: bold;
                display: inline-block;
              }
            }
            .coupon-list-center {
              color: #f66;
              font-size: 12px;
              border-left: 1px solid #fbb;
              border-right: 1px solid #fbb;
              .coupon-center-limit {
                height: 20px;
                line-height: 20px;
                margin-top: 0px;
                padding-top: 10px;
                margin-bottom: 5px;
              }
              .coupon-center-number {
                color: #fbb;
              }
            }
            .coupon-list-bottom {
              background-color: #f66;
              font-size: 12px;
              background-size: 12px;
              height: 24px;
              line-height: 30px;
              color: #fff;
              border-left: 1px solid #fbb;
              border-right: 1px solid #fbb;
              border-bottom-left-radius: 5px;
              border-bottom-right-radius: 5px;
              width: 100%;
              background-repeat: repeat-x;
              margin-top: -9px;
            }
          }
          .card-add-clickDiv {
            .card-add-click {
              background: #fff;
              border: 1px solid #e4e4e4;
              cursor: pointer;
              width: 108px;
              height: 100px;
              display: flex;
              justify-content: center;
              align-items: center;
              flex-direction: column;
              .img {
                margin-top: 14px;
              }
              p {
                color: #999;
                font-size: 12px;
                margin: 8px 0 0 0;
              }
            }
          }
        }
      }
    }
  }
}
</style>
