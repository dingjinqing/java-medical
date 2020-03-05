<template>
  <div
    class="membershipCard"
    :class="modulesShowData.show_type==='1'?'absouBottom':'modules'"
    @mouseover="mouseOver"
  >
    <!--列表模块-->
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':'noBoderColor'"
    >
      <!--模块编辑区-->
      <div
        class="textModule"
        v-if="modulesShowData.show_type==='0'"
        :style="'backgroundColor:'+modulesShowData.background_color"
      >
        <!--右侧无文本输入时占位-->
        <div
          class="textModuleMain"
          :style="modulesShowData.align_type=== '0'?'justify-content: flex-start;':modulesShowData.align_type=== '1'?'justify-content: center;':''"
        >
          <span :style="(modulesShowData.fonts_size==='1'?'font-size:24px':modulesShowData.fonts_size==='2'?'font-size:20px':'font-size:14px')+(';color:'+modulesShowData.color)">
            {{modulesShowData.title}}
          </span>
        </div>
      </div>
      <!--悬浮模块-->
      <div
        class="xf"
        :style="`background:url(${modulesShowData.sps_icon})`"
        v-if="modulesShowData.show_type==='1'"
      >

      </div>
      <!--模块编辑区结束-->
      <div
        class="item_operation"
        v-if="activeBorder"
      >
        <img
          v-if="modulesShowData.show_type==='0'"
          class="up_img"
          style="cursor:pointer;z-index:1000"
          :src="$imageHost+'/image/admin/shop_beautify/add_up_use.png'"
          @click.stop="handleToClickIcon(0)"
        >
        <img
          v-if="modulesShowData.show_type==='0'"
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
.textModuleMain {
  background-color: #ffffff;
  height: 30px;
  line-height: 30px;
  padding-left: 10px;
  display: flex;
}
.absouBottom {
  position: absolute;
  right: 20px;
  bottom: 50px;
  z-index: 1000;
}
.xf {
  width: 45px;
  height: 45px;
  background-size: 45px !important;
}
</style>
