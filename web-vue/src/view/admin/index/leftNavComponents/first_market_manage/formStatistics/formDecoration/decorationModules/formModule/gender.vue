<template>
  <div
    class="membershipCard modules"
    @mouseover="mouseOver"
  >
    <!--列表模块-->
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':'noBoderColor'"
    >
      <!--模块编辑区-->
      <div class="name">
        <div class="nameMain">
          <div class="address_module">
            <div class="choose_area">
              <div class="left">
                <b
                  v-if="modulesShowData.confirm"
                  class="module-star"
                >*</b>
                <div class="address_names">{{modulesShowData.form_title}}</div>
              </div>

              <div class="have_bg">
                <el-radio
                  v-model="sexRadio"
                  disabled
                  label="1"
                >男</el-radio>
                <el-radio
                  v-model="sexRadio"
                  label="2"
                >女</el-radio>
              </div>
            </div>
            <div
              class="area_detail"
              v-if="modulesShowData.show_types"
            >
              <div class="radio">
                <el-radio
                  v-model="sexRadio"
                  disabled
                  label="1"
                >男</el-radio>
              </div>
              <div class="radio bottom">
                <el-radio
                  v-model="sexRadio"
                  label="2"
                >女</el-radio>
              </div>
            </div>
          </div>
        </div>

      </div>
      <!--模块编辑区结束-->
      <div
        class="item_operation"
        v-if="activeBorder"
      >
        <img
          class="up_img"
          style="cursor:pointer;z-index:1000"
          :src="$imageHost+'/image/admin/shop_beautify/add_up_use.png'"
          @click.stop="handleToClickIcon(0)"
        >
        <img
          class="down_img"
          style="cursor:pointer;z-index:1000"
          :src="$imageHost+'/image/admin/shop_beautify/add_down.png'"
          @click.stop="handleToClickIcon(1)"
        >
        <img
          class="del_img"
          style="cursor:pointer;z-index:1000"
          :src="$imageHost+'/image/admin/shop_beautify/add_close.png'"
          @click.stop="handleToClickIcon(2)"
        >
      </div>
    </div>
    <!--中间部分拖动占位-->
    <div
      class="setHere activeSetHere"
      :class="activeSetHere?'middleModulesActive':''"
    >
    </div>
  </div>
</template>
<script>
export default {
  props: {
    flag: Number, // 模块公共
    nowRightShowIndex: Number, // 模块公共
    middleHereFlag: Boolean, // 模块公共
    backData: Object // 模块公共
  },
  data () {
    return {
      activeBorder: false, // 模块公共
      activeSetHere: false, // 模块公共
      hoverTips: 'hoverTips', // 英文适配  模块公共
      sexRadio: '2', // 男女radio
      // 模块私有
      modulesShowData: {

      }
    }
  },
  watch: {
    nowRightShowIndex (newData) { // 模块公共
      console.log(this.flag, newData)
      if (this.flag === newData) {
        this.activeBorder = true
      } else {
        this.activeBorder = false
      }
    },
    activeSetHere (newData) { // 模块公共
      console.log(newData)
      if (newData) {
        this.$emit('middleDragData', this.flag)
      }
    },
    activeBorder (newData) { // 模块公共
      console.log(newData)
      if (newData) {
        this.$http.$emit('nowHightLightModules', this.flag)
      }
    },
    middleHereFlag (newData) { // 模块公共
      console.log(newData)
      if (newData) {
        this.activeSetHere = true
      } else {
        this.activeSetHere = false
      }
    },
    // 右侧模块点击传回中间当前高亮模块的数据
    backData: { // 模块公共
      handler (newData) {
        console.log(newData)
        if (newData) {
          this.modulesShowData = newData
        }
        console.log(newData)
      },
      immediate: true,
      deep: true
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault() // 模块公共
    // 初始化数据
    this.defaultData() // 模块公共
  },
  methods: {
    defaultData () { // 模块公共
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
    handleToClickIcon (flag) { // 模块公共
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
    mouseOver () { // 模块公共
      this.$emit('middleDragData', this.flag)
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/style/admin/formdecorationModules.scss"; // 模块公共

.name {
  background: rgb(238, 238, 238);
  .nameMain {
    display: flex;
    justify-content: space-between;
    position: relative;
    .address_module {
      background: #fff;
      padding-left: 10px;
      padding-top: 5px;
      width: 100%;

      .choose_area {
        padding-bottom: 5px;
        display: flex;
        justify-content: space-between;
        /deep/ .is-disabled {
          .el-radio__inner {
            background-color: #fff;
          }
          .el-radio__label {
            color: #333;
          }
        }
        .left {
          display: flex;
          .module-star {
            color: red;
          }
          .address_names {
            font-size: 14px;
            color: #353535;
            width: 165px;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
          }
        }
        .have_bg {
          font-size: 14px;
          padding-right: 15px;
          color: #999;
          display: flex;
          align-items: center;
          /deep/ .el-radio {
            margin-right: 10px;
          }
        }
      }
      .area_detail {
        padding-top: 10px;
        border-top: 1px solid #eee;
        display: flex;
        flex-direction: column;
        /deep/ .is-disabled {
          .el-radio__inner {
            background-color: #fff !important;
          }
          .el-radio__label {
            color: #333;
          }
        }
        /deep/ .el-radio__inner {
          border-color: #409eff;
          background: #409eff !important;
        }
        /deep/ .el-radio__label {
          color: #333;
        }
        .radio {
          height: 30px;
          line-height: 30px;
        }
        .bottom {
          margin-top: 10px;
          border-top: 1px solid #eee;
        }
      }
    }
  }
}
</style>
