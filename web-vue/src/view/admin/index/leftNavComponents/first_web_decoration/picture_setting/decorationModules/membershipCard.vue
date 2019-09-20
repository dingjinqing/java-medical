<template>
  <div
    class="membershipCard modules"
    @mouseover="mouseOver"
  >
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
              <img :src="$imageHost+'/image/admin/shop_def_y.png'">
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
          style="cursor:pointer;z-index:1000"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_up_use.png'"
          @click.stop="handleToClickIcon(0)"
        >
        <img
          class="down_img"
          style="cursor:pointer;z-index:1000"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_down.png'"
          @click.stop="handleToClickIcon(1)"
        >
        <img
          class="del_img"
          style="cursor:pointer;z-index:1000"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_close.png'"
          @click.stop="handleToClickIcon(2)"
        >
      </div>
    </div>
    <!--放这里-->
    <div
      class="setHere activeSetHere"
      :class="activeSetHere?'middleModulesActive':''"
    >
      放这里
    </div>
  </div>
</template>
<script>

export default {
  props: {
    flag: Number,
    nowRightShowIndex: Number,
    middleHereFlag: Boolean
  },
  data () {
    return {
      activeBorder: false,
      activeSetHere: false
    }
  },
  watch: {
    nowRightShowIndex (newData) {
      if (this.flag === newData) {
        this.activeBorder = true
      } else {
        this.activeBorder = false
      }
    },
    activeSetHere (newData) {
      console.log(newData)
      if (newData) {
        this.$emit('middleDragData', this.flag)
      }
    },
    activeBorder (newData) {
      console.log(newData, this.index)
      if (newData) {
        this.$http.$emit('nowHightLightModules', this.flag)
      }
    },
    middleHereFlag (newData) {
      if (newData) {
        this.activeSetHere = true
      } else {
        this.activeSetHere = false
      }
    }
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
      // 点击各模块触发事件
      this.$http.$on('modulesClick', res => {
        console.log(this.flag, res)
        if (this.flag === res) {
          this.activeBorder = true
        } else {
          this.activeBorder = false
        }
        console.log(res)
      })
    },
    // 移上、移下、删除统一处理事件
    handleToClickIcon (flag) {
      console.log(flag)
      let obj = {
        direction: '',
        flag: this.flag
      }
      switch (flag) {
        case 0:
          obj.direction = 'up'
          break
        case 1:
          obj.direction = 'down'
          break
        case 2:
          obj.direction = 'delete'
          break
      }
      this.$emit('handleToClickIcon', obj)
    },
    // 模块划过
    mouseOver () {
      this.$emit('middleDragData', this.flag)
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
    border: 2px dashed #fff;
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
      z-index: 100;
    }
    &:hover {
      border: 2px dashed #5a8bff;
      .item_module_title {
        display: block;
      }
      .item_operation {
        display: block;
        z-index: 1000;
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
.activeBorder {
  border: 2px dashed #5a8bff !important;
}
.membershipCard:hover,
.placeholder {
  .middleModulesActive {
    display: block;
  }
}
.placeholder {
  .activeSetHere {
    display: block;
  }
  .showModule {
    border: 2px dashed #5a8bff !important;
  }
}
div {
  cursor: Move;
}
</style>
