<template>
  <div class="Coupon modules">
    <!--会员卷模块-->
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':''"
    >
      <div class="coupon_module">
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
      <div class="item_module_title">
        <span>会员卡</span>
      </div>
      <div class="item_operation">
        <img
          class="up_img"
          style="cursor:pointer"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_up_use.png'"
        >
        <img
          class="down_img"
          style="cursor:pointer"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_down.png'"
        >
        <img
          class="del_img"
          style="cursor:pointer"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_close.png'"
        >
      </div>
    </div>
    <!--放这里-->
    <div class="setHere activeSetHere">
      放这里
    </div>
  </div>
</template>
<script>
export default {
  props: {
    index: {
      type: Number
    },
    flag: {
      type: Number
    }
  },
  data () {
    return {
      activeBorder: null
    }
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
      this.$http.$on('decCard', data => {
        console.log(this.flag, data)
        let arr = data.length - 1
        if (this.flag === arr) {
          this.activeBorder = true
        } else {
          this.activeBorder = false
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.Coupon {
  .showModule {
    min-height: 10px;
    background-color: #ffffff;
    position: relative;
    .item_module_title {
      position: absolute;
      top: 0px;
      left: 0px;
      display: none;
      z-index: 8;
      width: 88px;
      height: 30px;
      background-color: #9ebbff;
      color: #fff;
      text-align: center;
      line-height: 30px;
      border-bottom-right-radius: 15px;
      -webkit-border-bottom-right-radius: 15px;
      -moz-border-bottom-right-radius: 15px;
      border-top-right-radius: 15px;
    }
    .item_operation {
      position: absolute;
      right: 8px;
      top: 2px;
      display: none;
      z-index: 8;
    }
    &:hover {
      border: 2px dashed #5a8bff;
      .item_module_title {
        display: block;
      }
      .item_operation {
        display: block;
      }
    }
    .coupon_module {
      padding: 10px;
      background: #fff;
      display: flex;
      justify-content: space-between;
      .coupon_list {
        width: 32%;
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
            url(../../../../../../../assets/adminImg/coupon_border.png) repeat-x
            top;
          -webkit-background-size: 12px;
          background-size: 12px;
          height: 24px;
          line-height: 30px;
          color: #fff;
        }
      }
    }
  }
  .activeBorder {
    border: 2px dashed #5a8bff;
  }
  .setHere {
    height: 40px;
    text-align: center;
    line-height: 40px;
    background-color: #8baeff;
    font-size: 14px;
    color: #fff;
    border: 1px dashed #2589ff;
  }
  .activeSetHere {
    display: none;
  }
}
.placeholder {
  .activeSetHere {
    display: block;
  }
}
</style>
