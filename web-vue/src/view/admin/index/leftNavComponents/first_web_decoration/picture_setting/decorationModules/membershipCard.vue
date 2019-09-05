<template>
  <div class="membershipCard modules">
    <!--会员列表模块-->
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':''"
    >
      <div class="carModule">
        <div class="card_back_module">
          <div class="card_type">普通卡</div>
          <div class="card_content clearfix">
            <div class="card_shop_icon">
              <img src="http://miniimg.com.cn/image/admin/shop_def_y.png">
            </div>
            <div class="card_content_right">
              <div>会员卡</div>
              <p>××××××××</p>
            </div>
            <!-- <div
                      class="card_pay_fee"
                      style="display: none;"
                    >￥0.00元</div> -->
          </div>
          <div class="card_bottom">
            更多权益领取后查看<span>我要领卡</span>
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
          @click="handleToClickIcon(0)"
        >
        <img
          class="down_img"
          style="cursor:pointer"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_down.png'"
          @click="handleToClickIcon(1)"
        >
        <img
          class="del_img"
          style="cursor:pointer"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_close.png'"
          @click="handleToClickIcon(2)"
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
      activeBorder: false,
      activeSetHere: false
    }
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
      this.$http.$on('decCard', (data, hereFlag) => {
        console.log(this.flag, data)
        console.log(hereFlag)
        let arr = data.length - 1
        if (this.flag === arr) {
          this.activeBorder = true
        } else {
          this.activeBorder = false
        }
        // if (hereFlag) {
        //   this.activeSetHere = true
        // } else {
        //   this.activeSetHere = false
        // }
      })
    },
    // 移上、移下、删除统一处理事件
    handleToClickIcon (flag) {
      console.log(123)
      let obj = {
        direction: '',
        flag: this.flag
      }
      switch (flag) {
        case 0:
          obj.direction = 'up'
          this.$http.$emit('handleDragIconClick', obj)
          break
        case 1:
          obj.direction = 'dowm'
          this.$http.$emit('handleDragIconClick', obj)
          break
        case 2:
          obj.direction = 'delete'
          this.$http.$emit('handleDragIconClick', obj)
          break
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.membershipCard {
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
    .carModule {
      padding: 12px;
      .card_back_module {
        width: 100%;
        -webkit-border-radius: 8px;
        -moz-border-radius: 8px;
        border-radius: 8px;
        background: #ecc98f;
        color: #fff;
        padding: 0 20px;
        position: relative;
        overflow: hidden;
        font-size: 14px;
        .card_type {
          position: absolute;
          top: 0;
          right: 0;
          background: rgba(0, 0, 0, 0.1);
          width: 60px;
          height: 20px;
          text-align: center;
          line-height: 20px;
          border-bottom-left-radius: 8px;
          font-size: 12px;
        }
        .card_content {
          padding: 20px 0;
          border-bottom: 1px solid #fff;
          -webkit-box-sizing: border-box;
          -moz-box-sizing: border-box;
          position: relative;
          overflow: hidden;
          .card_shop_icon {
            img {
              display: inline-block;
              width: 100%;
            }
            float: left;
            width: 40px;
            height: 40px;
            -webkit-border-radius: 100%;
            -moz-border-radius: 100%;
            border-radius: 100%;
            overflow: hidden;
          }
          .card_content_right {
            float: left;
            margin-left: 10px;
            p {
              font-size: 12px;
              margin: 5px 0 0 0;
            }
          }
        }
        .card_bottom {
          text-align: right;
          line-height: 20px;
          padding: 6px 0;
          font-size: 12px;
          span {
            display: inline-block;
            border: 1px solid #fff;
            border-radius: 12px;
            height: 20px;
            line-height: 20px;
            padding: 0 10px;
            margin-left: 5px;
          }
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
