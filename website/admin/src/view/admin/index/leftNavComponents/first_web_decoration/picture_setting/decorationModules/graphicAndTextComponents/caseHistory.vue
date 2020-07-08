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
      <div class="case-history-content">
        <div class="prescription-module-title">
          <img :src="$imageHost + '/image/wxapp/prescription-icon1.png'" />
          <span>我的病历</span>
          <span class="more">更多</span>
        </div>
        <div class="case-history-list">
          <div class="list-item title">电子病历1</div>
          <div class="list-item">
            <div class="item-line">
              <span class="item-dot"></span>
              <span class="item-title">诊断：</span>
              <span>（J98.800X002）呼吸道感染</span>
            </div>
          </div>
          <div class="list-item">
            <div class="item-line">
              <span class="item-dot"></span>
              <span class="item-title">就诊科室：</span>
              <span>内科</span>
            </div>
          </div>
          <div class="list-item">
            <div class="item-line">
              <span class="item-dot"></span>
              <span class="item-title">诊疗处理意见：</span>
            </div>
            <div class="item-info">
              <span>处方：金嗓散胶囊，口服，0.8g/二次/日金嗓散胶囊，口服，0.8g/二次/日</span>
              <span>检查：呼吸科检查一氧化碳呼气测定</span>
              <span>检验: 血常规</span>
            </div>
          </div>
          <div class="list-item">
            <div class="doctor-info">
              <span>医师：张三</span>
              <span>日期：2020.06.30</span>
            </div>
          </div>
        </div>
      </div>
      <!--模块编辑区结束-->
      <div class="item_module_title">
        <span>病历模块</span>
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
          //   let render = JSON.parse(JSON.stringify(newData))
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
@import '@/style/admin/decorationModules.scss'; // 模块公共
.case-history-content {
  padding: 15px 7.5px 0;
  display: flex;
  flex-direction: column;
  border-radius: 8px 8px 0 0;
  background-color: #fff;
}
.case-history-list {
  background: #fff;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
}
.list-item {
  padding: 7.5px 15px;
  display: flex;
  flex-direction: column;
  line-height: 30px;
}
.list-item + .list-item {
  border-top: 1px solid #eee;
}
.list-item.title {
  font-size: 14px;
  color: #333;
  font-weight: 600;
  align-self: center;
}
.list-item > .item-line {
  display: flex;
  align-items: center;
}
.list-item > .item-line > text {
  color: #333;
}
.list-item > .item-line > .item-dot {
  border: 2px solid skyblue;
  border-radius: 50%;
  height: 5px;
  width: 5px;
  margin-right: 10px;
}
.list-item > .item-info {
  display: flex;
  flex-direction: column;
}
.list-item > .item-line > .item-title {
  font-weight: 600;
  color: #333;
}
.prescription-module-title {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
}
.prescription-module-title > img {
  width: 17px;
  height: 17px;
  margin-right: 12px;
}
.prescription-module-title > span {
  font-size: 16px;
  color: #333;
  font-weight: 600;
}
.prescription-module-title > span:first-of-type {
  margin-right: auto;
}
.prescription-module-title > span.more {
  font-size: 13px;
  color: #656565;
  font-weight: 400;
}
.item-info {
  padding-left: 18px;
}
.item-info > span {
  color: #333;
}
.doctor-info {
  display: flex;
}
.doctor-info > span {
  color: #999;
}
.doctor-info > span:first-of-type {
  margin-right: auto;
}
</style>
