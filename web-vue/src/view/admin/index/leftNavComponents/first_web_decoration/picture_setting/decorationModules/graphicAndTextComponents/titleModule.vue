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
      <div class="titleModule">
        <!--右侧无文本输入时占位-->
        <div class="titleModuleMain">
          <div
            class="zbDiv"
            :style="(moduleSaveData.title_model===1)?('backgroundColor:'+moduleSaveData.bg_color):''"
          >
            <div
              class="titleContent"
              :style="(moduleSaveData.tit_center===1?'justify-content: flex-start':'justify-content: center')+(moduleSaveData.title_model===2?';flex-direction: column':'')"
            >
              <img
                v-if="moduleSaveData.img_url&&(moduleSaveData.title_model===1)"
                style="width:25px;height:25px;margin-right:5px"
                :src="moduleSaveData.img_url"
              >
              <span :style="(moduleSaveData.title_model===1)?('color:'+moduleSaveData.color):''+(moduleSaveData.tit_center===1?';justify-content: flex-start':';justify-content: center')">{{moduleSaveData.title?moduleSaveData.title:$t('titleModule.middleZb')}}</span>
              <div
                v-if="moduleSaveData.title_model===2"
                :style="moduleSaveData.tit_center===1?'justify-content: flex-start':'justify-content: center'"
              >
                <i style="color:#999;font-size:12px;margin-top:5px">{{moduleSaveData.title_date}}</i>
                <i style="color:#999;font-size:12px;margin-top:5px;margin-left:10px">{{moduleSaveData.title_author}}</i>
                <i style="color:#999;font-size:12px;margin-top:5px;margin-left:10px;color:#607fa6">{{moduleSaveData.link_title}}</i>
              </div>

            </div>

            <div style="display:flex;align-items:center">
              <span>{{$t('titleModule.more')}}</span>
              <img :src="$imageHost+'/image/admin/shop_beautify/gt.png'">
            </div>
          </div>
        </div>
      </div>
      <!--模块编辑区结束-->
      <div class="item_module_title">
        <span>{{$t('titleModule.middleTitle')}}</span>
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
.titleModule {
  .titleModuleMain {
    .zbDiv {
      display: flex;
      justify-content: space-between;
      padding: 8px 8px;
      .titleContent {
        flex: 1;
        display: flex;
        span {
          color: #333;
          display: flex;
          align-items: center;
        }
      }
      div {
        display: flex;
        span {
          color: #999;
        }
        img {
          height: 13px;
          width: 7px;
        }
      }
    }
  }
}
</style>
