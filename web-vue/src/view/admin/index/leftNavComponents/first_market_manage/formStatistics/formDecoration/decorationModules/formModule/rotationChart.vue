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
        <div
          class="carousel_module"
          :style="`background-image: url(${$imageHost}/image/admin/shop_beautify/decorate_model.png)`"
        >
          <div v-if="imgLen">
            <img
              class="scroll_image"
              :src="modulesShowData.img_items[imgLen-1].img_url"
              alt=""
            >
            <div class="contain_circle">
              <div
                class="small_circle"
                v-for="(item, index) in modulesShowData.img_items"
                :key="index"
              ></div>
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
      imgLen: 0, // 上传的图片数量
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
          this.imgLen = this.modulesShowData.img_items.length
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

.carousel_module {
  overflow: hidden;
  position: relative;
  min-height: 150px;
  max-height: 317px;
  background: #eaf2ff center no-repeat;
  .scroll_image {
    display: block;
    width: 100%;
    height: 100%;
    max-height: 317px;
    min-height: 150px;
  }
  .contain_circle {
    position: absolute;
    display: flex;
    width: 100%;
    height: 20px;
    bottom: 10px;
    justify-content: center;
    align-items: center;
    background: rgba(0, 0, 0, 0);
    .small_circle {
      width: 10px;
      height: 10px;
      border-radius: 50%;
      margin-right: 5px;
      background-color: #eaf2ff;
      -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
      &:last-child {
        background-color: gray;
      }
    }
  }
}
</style>
