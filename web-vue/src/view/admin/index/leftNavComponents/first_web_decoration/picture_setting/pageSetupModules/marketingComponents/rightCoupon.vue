<template>
  <div class="rightCoupon">
    <div class="rightCouponMain">
      <h2>优惠卷模块</h2>
      <!--模块私有-->
      <div class="content">
        <div>
          <div class="couponTop">
            <span>优惠卷:</span>
            <div class="couponList">
              <div
                v-for="(item,index) in 3"
                :key="index"
                class="coupon_list"
                style="border-color: rgba(255, 102, 102, 0.4);"
              >
                <div
                  class="coupon_list_top"
                  style="color: rgb(255, 102, 102);"
                >
                  ¥<span>××</span>
                </div>
                <div class="coupon_list_center">
                  <div
                    class="coupon_center_limit"
                    style="color: rgb(255, 102, 102);"
                  >满××使用</div>
                  <div
                    class="coupon_center_number"
                    style="color: rgba(255, 102, 102, 0.4);"
                  >剩余<span>××</span>张</div>
                </div>
                <div
                  class="coupon_list_bottom new_back"
                  style="background-color: rgb(255, 102, 102);"
                >
                  领取
                </div>
              </div>
            </div>
          </div>
          <div class="couponAdd"></div>
        </div>
      </div>
      <el-button @click='handleToSelectCuopon()'>会员模块测试</el-button>
      <!--end-->
    </div>

  </div>
</template>
<script>
export default {
  props: {
    modulesData: Object,
    sortIndex: Number
  },
  data () {
    return {
      data: {

      }
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        console.log(newData, this.modulesData)
      },
      immediate: true
    }
  },
  methods: {
    // 测试点击数据回传
    handleToSelectCuopon () {
      let obj = {
        name: '帅飞优惠卷',
        backgroundColor: '#111'
      }
      this.$emit('handleToBackData', obj)
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
            overflow: hidden;
            .coupon_list_top {
              margin-top: 10px;
              color: #f66;
              font-size: 14px;
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
            }
          }
        }
      }
    }
  }
}
</style>
