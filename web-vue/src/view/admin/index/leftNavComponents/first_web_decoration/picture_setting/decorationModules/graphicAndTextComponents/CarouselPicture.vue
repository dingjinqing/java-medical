<template>
  <div
    class="membershipCard modules"
    @mouseover="mouseOver"
  >
    <!-- 轮播图模块 -->
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':''"
    >
      <!-- 模块编辑区 -->
      <div
        class="carousel_module"
        :style="`background-image: url(${$imageHost}/image/admin/shop_beautify/decorate_model.png)`"
      >
        <div v-if="imgLen">
          <img
            class="scroll_image"
            :src="data.img_items[imgLen-1].img_url"
            alt=""
          >
          <div class="contain_circle">
            <div
              class="small_circle"
              v-for="(item, index) in data.img_items"
              :key="index"
            ></div>
          </div>
        </div>
      </div>
      <!--模块编辑区结束-->
      <div class="item_module_title">
        <span>{{$t('carouselPicture.rotationChart')}}</span>
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
      {{$t('carouselPicture.putItHere')}}
    </div>
  </div>
</template>

<script>
export default {
  props: {
    flag: Number, // 中间模块展示的第几个
    nowRightShowIndex: Number, // 右侧模块展示的第几个
    middleHereFlag: Boolean, // 中间元素是否拖动
    backData: Object // 创建后保存的数据
  },
  data () {
    return {
      activeBorder: false, // 展示虚线
      activeSetHere: false,
      // 模块私有
      data: {}, // 接受右侧配置信息
      imgLen: 0 // 上传的图片数量
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    this.defaultData()
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
    // 右侧模块点击传回中间当前高亮模块的数据
    backData: {
      handler (newData) {
        if (newData) {
          this.data = newData
          this.imgLen = this.data.img_items.length
        }
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    defaultData () {
      // 点击各模块触发事件
      this.$http.$on('modulesClick', res => {
        if (this.flag === res) {
          this.activeBorder = true
        } else {
          this.activeBorder = false
        }
      })
    },
    // 移上、移下、删除统一处理事件
    handleToClickIcon (flag) {
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
@import "@/style/admin/decorationModules.scss";
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
