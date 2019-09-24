<template>
  <div class="couponDialog">
    <el-dialog
      title="选择优惠卷"
      :visible.sync="dialogVisible"
      width="35%"
    >
      <div class="couponDialogDIiv">
        <div class="couponTop">
          <div class="top_leftDiv">
            <el-input
              v-model="couponInput"
              placeholder="请输入优惠卷名称"
              size="small"
            ></el-input>
            <el-button
              @click="initCouponList (data)"
              type="info"
              plain
              size="small"
              icon="el-icon-search"
            >搜索</el-button>
          </div>
          <span @click="initCouponList (data)"><i class="el-icon-refresh-left"></i>刷新</span>
        </div>
        <div
          v-loading="loading"
          class="couponListDiv"
        >
          <div
            class="couponLi"
            v-for="(item,index) in dialogData"
            :key="index"
            @click="handleToClick(index)"
          >
            <img
              v-if="item.ischeck"
              :src="$imageHost +'/image/admin/shop_beautify/checked_card.png'"
            >
            <div class="coupon_list_top">
              ￥<span>{{item.denomination}}</span>
            </div>
            <div class="coupon_list_center">
              <div class="coupon_center_limit">{{item.useConsumeRestrict > 0 ?`满${item.leastConsume}使用`:'不限制'}}</div>
              <div class="coupon_center_number">剩余<span>{{item.surplus}}</span>张</div>
              <div
                class="coupon_list_bottom"
                :style="`backgroundImage:url('${$imageHost}/image/admin/coupon_border.png')`"
              >领取 </div>
              <div class="coupon_name">{{item.actName}}</div>
            </div>
          </div>
        </div>

      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="handleToSure()"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getCouponSelectComponentData } from '@/api/admin/marketManage/couponGive.js'
export default {
  data () {
    return {
      dialogVisible: false,
      couponInput: '',
      dialogData: [],
      data: '',
      loading: false,
      isSingleElection: false
    }
  },
  props: ['origin', 'singleElection', 'tuneUpCoupon', 'couponBack'],
  mounted () {
    // 初始化
    this.defaultData()
  },
  watch: {
    tuneUpCoupon () {
      this.dialogVisible = true
    },
    couponBack: {
      handler (newData) {
        console.log(newData)
        this.data = newData
        this.initCouponList(newData)
      },
      immediate: true
    }
  },
  methods: {

    defaultData () {
      console.log(this.singleElection)
      if (this.singleElection) {
        this.isSingleElection = this.singleElection
      }

      // this.$http.$on('V-AddCoupon', data => {
      //   this.data = data
      //   this.initCouponList(data)
      //   this.dialogVisible = true
      // })
    },
    initCouponList (data) {
      this.loading = true
      let param = {
        'actName': this.couponInput
      }
      getCouponSelectComponentData(param).then((res) => {
        if (res.error === 0) {
          console.log(res)
          this.dialogData = res.content
          this.dialogData.map((item, index) => {
            this.$set(item, 'ischeck', false)
          })
          this.dialogData.forEach((item, index) => {
            item.ischeck = false
            data.forEach((itemC, indexC) => {
              if (item.id === itemC) {
                item.ischeck = true
              }
            })
          })
        }
      })
      this.loading = false
    },
    // 优惠卷选中
    handleToClick (index) {
      if (this.singleElection) {
        this.dialogData.forEach(item => {
          item.ischeck = false
        })
        this.dialogData[index].ischeck = !this.dialogData[index].ischeck
      } else {
        this.dialogData[index].ischeck = !this.dialogData[index].ischeck
      }
    },
    // 弹窗确定事件
    handleToSure () {
      let arr = []
      this.dialogData.forEach((item, index) => {
        if (item.ischeck) arr.push(item)
      })
      if (arr.length > 5 && !this.origin) {
        this.$message.error('最多只能选择5张优惠卷哦~')
        return
      }
      if (arr.length > 10 && this.origin === 'couponPackage') {
        this.$message.error('最多只能选择10种优惠卷哦~')
        return
      }
      this.$emit('handleToCheck', arr)
      this.$emit('checkReturnFormat', this.formatCoupon(arr))
      this.dialogVisible = false
    },

    // 数据格式化
    formatCoupon (data) {
      let couponArr = []
      let couponData = {
        immediatelyGrantAmount: 0,
        timingEvery: 0,
        timingAmount: 0,
        timingTime: '1',
        timingUnit: '0'
      }
      data.map(item => {
        couponArr.push(Object.assign({}, item, { send_num: '', coupon_set: couponData }))
      })
      return couponArr
    }
  }
}
</script>
<style lang="scss" scoped>
.couponDialog {
  /deep/ .el-dialog__header {
    text-align: center;
    background: #f3f3f3;
  }
  /deep/ .el-dialog__body {
    padding: 20px 20px;
  }
  .couponDialogDIiv {
    height: 300px;
    overflow-y: auto;
    .couponTop {
      display: flex;
      justify-content: space-between;
      /deep/ .el-input {
        width: 80%;
        margin-right: 10px;
      }
      .top_leftDiv {
        display: flex;
      }
      span {
        height: 32px;
        line-height: 32px;
        display: block;
        color: #5a8bff;
        cursor: pointer;
        i {
          display: inline-block;
          width: 20px;
          height: 32px;
          line-height: 32px;
          font-size: 18px;
        }
      }
    }
    .couponListDiv {
      padding-top: 10px;
      .couponLi {
        float: left;
        width: 108px;
        text-align: center;
        margin-right: 10px;
        margin-bottom: 53px;
        cursor: pointer;
        position: relative;
        img {
          position: absolute;
          top: -5px;
          right: -5px;
        }
        .coupon_list_top {
          padding-top: 10px;
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
        .coupon_list_center {
          height: 40px;
          color: #f66;
          font-size: 12px;
          border-left: 1px solid #fbb;
          border-right: 1px solid #fbb;
          .coupon_center_limit {
            padding: 5px 0;
          }
          .coupon_center_number {
            color: #fbb;
            margin-bottom: 5px;
          }
        }
        .coupon_list_bottom {
          background-color: #f66;
          font-size: 12px;
          -webkit-background-size: 12px;
          background-size: 12px;
          height: 24px;
          line-height: 30px;
          color: #fff;
          border-left: 1px solid #fbb;
          border-right: 1px solid #fbb;
          border-bottom-left-radius: 5px;
          border-bottom-right-radius: 5px;
          background-repeat: repeat-x;
          margin-left: -1px;
          width: 102%;
        }
        .coupon_name {
          font-size: 14px;
          line-height: 30px;
          border: 1px solid #fff;
          margin: 0 -1px -1px -1px;
          text-overflow: ellipsis;
          white-space: nowrap;
          overflow: hidden;
          color: #333;
          width: 110px;
        }
      }
    }
  }
}
</style>
