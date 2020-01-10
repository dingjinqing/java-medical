<template>
  <div
    class="membershipCard modules"
    @mouseover="mouseOver"
  >
    <!--列表模块-->
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':''"
    >
      <!--模块编辑区-->
      <div class="leftWingRightPicture">
        <!--右侧不选择图片占位-->
        <div
          :style="moduleSaveData.img_style===0?'':'height:110px;'"
          class="leftWingRightPictureMain"
        >
          <img
            :style="moduleSaveData.img_style===0?'':'height:90px;width:90px'"
            v-if="moduleSaveData.ti_type===0"
            :src="moduleSaveData.img_url?moduleSaveData.img_url:($imageHost+'/image/admin/shop_beautify/decorate_model.png')"
          >
          <div
            :style="moduleSaveData.img_style===0?'':'height:90px'"
            class="textContainer"
            v-html="moduleSaveData.rich_text?moduleSaveData.rich_text:$t('leftWingRightPicture.middleZb')"
          >
          </div>
          <img
            :style="moduleSaveData.img_style===0?'':'height:90px;width:90px'"
            v-if="moduleSaveData.ti_type===1"
            :src="moduleSaveData.img_url?moduleSaveData.img_url:($imageHost+'/image/admin/shop_beautify/decorate_model.png')"
          >
        </div>
      </div>
      <!--模块编辑区结束-->
      <div
        class="item_module_title"
        :style="hoverTips?'width:140px':''"
      >
        <span>{{$t('leftWingRightPicture.middleTips')}}</span>
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
      {{$t('commoditySearch.putItHere')}}
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
      moduleSaveData: {
        'ti_type': 0, // 文本样式类型
        'img_style': 0, // 图片样式类型
        'img_url': '', // 图片路径
        'title_link': '', // 链接
        'rich_text': '' // 文本内容
      }
    }
  },
  watch: {
    nowRightShowIndex (newData) { // 模块公共
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
      console.log(newData, this.index)
      if (newData) {
        this.$http.$emit('nowHightLightModules', this.flag)
      }
    },
    middleHereFlag (newData) { // 模块公共
      if (newData) {
        this.activeSetHere = true
      } else {
        this.activeSetHere = false
      }
    },
    // 右侧模块点击传回中间当前高亮模块的数据
    backData: { // 模块公共
      handler (newData) {
        if (newData) {
          this.moduleSaveData = newData
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
@import "@/style/admin/decorationModules.scss"; // 模块公共

.leftWingRightPicture {
  background-color: #f5f5f5;
  .leftWingRightPictureMain {
    margin: 0 10px;
    background-color: #fff;
    height: 170px;
    border-radius: 6px;
    padding: 10px;
    display: flex;
    img {
      width: 150px;
      height: 150px;
      float: left;
      margin-right: 10px;
    }
    .textContainer {
      width: 60%;
      overflow: hidden;
      word-break: break-word;
      height: 150px;
    }
  }
}
</style>
