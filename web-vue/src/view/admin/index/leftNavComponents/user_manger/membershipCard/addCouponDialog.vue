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
              type="info"
              plain
              size="small"
              icon="el-icon-search"
            >搜索</el-button>
          </div>
          <span><i class="el-icon-refresh-left"></i>刷新</span>
        </div>
        <div class="couponListDiv">
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
              ￥<span>{{item.price}}</span>
            </div>
            <div class="coupon_list_center">
              <div class="coupon_center_limit">{{item.isLimit?`满${item.nolimitPrice}使用`:'不限制'}}</div>
              <div class="coupon_center_number">剩余<span>{{item.surplus}}</span>张</div>
              <div
                class="coupon_list_bottom"
                :style="`backgroundImage:url('${$imageHost}/image/admin/coupon_border.png')`"
              >领取 </div>
              <div class="coupon_name">{{item.tips}}</div>
            </div>
          </div>
        </div>

      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          icon="el-icon-search"
          @click="dialogVisible = false"
        >取 消</el-button>
        <el-button
          type="primary"
          @click="handleToSure()"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data () {
    return {
      dialogVisible: false,
      couponInput: '',
      dialogData: [
        {
          price: 88,
          isLimit: true,
          nolimitPrice: 2,
          surplus: 99,
          tips: '优惠券每人领取限制',
          ischeck: false,
          id: 0
        },
        {
          price: 222,
          isLimit: false,
          nolimitPrice: 10,
          surplus: 111,
          tips: '年终大促',
          ischeck: false,
          id: 1
        },
        {
          price: 333,
          isLimit: false,
          nolimitPrice: 10,
          surplus: 111,
          tips: '年终大促',
          ischeck: false,
          id: 2
        },
        {
          price: 444,
          isLimit: false,
          nolimitPrice: 10,
          surplus: 111,
          tips: '年终大促',
          ischeck: false,
          id: 3
        },
        {
          price: 555,
          isLimit: false,
          nolimitPrice: 10,
          surplus: 111,
          tips: '年终大促',
          ischeck: false,
          id: 4
        },
        {
          price: 666,
          isLimit: false,
          nolimitPrice: 10,
          surplus: 111,
          tips: '年终大促',
          ischeck: false,
          id: 5
        },
        {
          price: 666,
          isLimit: false,
          nolimitPrice: 10,
          surplus: 111,
          tips: '年终大促',
          ischeck: false,
          id: 6
        },
        {
          price: 666,
          isLimit: false,
          nolimitPrice: 10,
          surplus: 111,
          tips: '年终大促',
          ischeck: false,
          id: 7
        },
        {
          price: 666,
          isLimit: false,
          nolimitPrice: 10,
          surplus: 111,
          tips: '年终大促',
          ischeck: false,
          id: 8
        },
        {
          price: 666,
          isLimit: false,
          nolimitPrice: 10,
          surplus: 111,
          tips: '年终大促',
          ischeck: false,
          id: 9
        },
        {
          price: 666,
          isLimit: false,
          nolimitPrice: 10,
          surplus: 111,
          tips: '年终大促',
          ischeck: false,
          id: 9
        },
        {
          price: 666,
          isLimit: false,
          nolimitPrice: 10,
          surplus: 111,
          tips: '年终大促',
          ischeck: false,
          id: 10
        },
        {
          price: 666,
          isLimit: false,
          nolimitPrice: 10,
          surplus: 111,
          tips: '年终大促',
          ischeck: false,
          id: 11
        }
      ]
    }
  },
  mounted () {
    // 初始化
    this.defaultData()
  },
  methods: {
    defaultData () {
      this.$http.$on('V-AddCoupon', res => {
        console.log(res)
        this.dialogData.forEach((item, index) => {
          item.ischeck = false
          res.couponList.forEach((itemC, indexC) => {
            if (item.id === itemC.id) {
              item.ischeck = true
            }
          })
        })
        this.dialogVisible = res.couponDialogFlag
      })
    },
    // 优惠卷选中
    handleToClick (index) {
      this.dialogData[index].ischeck = !this.dialogData[index].ischeck
    },
    // 弹窗确定事件
    handleToSure () {
      let arr = []
      this.dialogData.forEach((item, index) => {
        if (item.ischeck) arr.push(item)
      })
      console.log(arr.length)
      if (arr.length > 5) {
        this.$message.error('最多只能选择5张优惠卷哦~')
        return
      }
      this.$emit('handleToCheck', arr)
      console.log(arr)
      this.dialogVisible = false
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
