<template>
  <div
    class="membershipCard modules"
    @mouseover="mouseOver"
  >
    <!--优惠卷模块-->
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
        <span>优惠卷</span>
      </div>
      <div class="item_operation">
        <img
          class="up_img"
          style="cursor:pointer"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_up_use.png'"
          @click.stop="handleToClickIcon(0)"
        >
        <img
          class="down_img"
          style="cursor:pointer"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_down.png'"
          @click.stop="handleToClickIcon(1)"
        >
        <img
          class="del_img"
          style="cursor:pointer"
          :src="$imageHost+'/image/admin/new_shop_beautify/add_close.png'"
          @click.stop="handleToClickIcon(2)"
        >
      </div>
    </div>
    <!--end-->
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
    middleHereFlag: Boolean,
    backData: Object
  },
  data () {
    return {
      activeBorder: null,
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
    },
    backData: {
      handler (newData) {
        console.log(newData)
      },
      immediate: true
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
        console.log(this.flag, res)
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
      // console.log(this.flag)
      this.$emit('middleDragData', this.flag)
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/style/admin/decorationModules.scss";
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
        url(../../../../../../../../assets/adminImg/coupon_border.png) repeat-x
        top;
      -webkit-background-size: 12px;
      background-size: 12px;
      height: 24px;
      line-height: 30px;
      color: #fff;
    }
  }
}
</style>
