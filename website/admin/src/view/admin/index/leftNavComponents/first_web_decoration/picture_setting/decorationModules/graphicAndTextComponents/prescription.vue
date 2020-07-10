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
      <div class="prescription-content">
        <div class="prescription-module-title">
          <img :src="$imageHost+'/image/wxapp/prescription-icon1.png'" />
          <span>我的处方</span>
          <span class="more">更多</span>
        </div>
        <div class="prescription-item">
          <div
            class="item-title"
            :style="'background:#26c4bc url('+$imageHost+'/image/wxapp/inedx-prescription-bg.png) no-repeat left top/100% 40px;'"
          >电子处方1</div>
          <div class="item-list-content">
            <div class="item-list">
              <div class="list-item">
                <span class="list-item-dot"></span>
                <div class="list-item-content">
                  诊断：原发性高血压
                </div>
              </div>
              <div class="list-item">
                <span class="list-item-dot"></span>
                <div class="list-item-content">
                  科室：神经科
                </div>
              </div>
            </div>
          </div>
          <div class="doctor-info">
            <span class="doctor-name">医师：张三</span>
            <span class="item-date">日期：2020.06.30</span>
          </div>
        </div>
      </div>
      <!--模块编辑区结束-->
      <div class="item_module_title">
        <span>处方模块</span>
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
.prescription-content {
  padding: 25px 6px 20px;
  display: flex;
  flex-direction: column;
  background: #fff;
  border-radius: 16px 16px 0 0;
}
.prescription-item {
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(13, 19, 24, 0.24), 0 0 0px rgba(10, 16, 20, 0.12);
  position: relative;
}
.prescription-item + .prescription-item {
  margin-top: 15px;
}
.prescription-item > .item-title {
  line-height: 40px;
  color: #fff;
  font-size: 15px;
  text-align: center;
  background-color: skyblue;
  height: 50px;
  border-radius: 16px 16px 0 0;
}
.prescription-item > .item-list-content {
  padding-left: 15px;
  background-color: #fff;
  position: relative;
  border-radius: 16px 16px 0 0;
  margin-top: -10px;
}
.prescription-item > .item-list-content > .item-list {
  display: flex;
  flex-direction: column;
  line-height: 27.5px;
  padding: 15px 0;
  border-bottom: 2px solid #eee;
}
.prescription-item > .item-list-content > .item-list > .list-item {
  display: flex;
  align-items: center;
}
.prescription-item
  > .item-list-content
  > .item-list
  > .list-item
  > .list-item-dot {
  border: 2px solid skyblue;
  border-radius: 50%;
  height: 10px;
  width: 10px;
  margin-right: 10px;
}
.prescription-item
  > .item-list-content
  > .item-list
  > .list-item
  > .list-item-content {
  color: #666;
  font-size: 13px;
}
.prescription-item > .doctor-info {
  height: 40px;
  padding: 0 15px;
  align-items: center;
  background: #fff;
  display: flex;
  color: #999;
  position: relative;
}
.prescription-item > .doctor-info::after {
  content: '';
  position: absolute;
  height: 8px;
  width: 100%;
  bottom: -8px;
  left: 0;
  background-image: radial-gradient(
    5px circle at 12px 8px,
    transparent 7px,
    #fff 7px
  );
  background-size: 20px 10px;
}
.prescription-item > .doctor-info > .doctor-name {
  margin-right: auto;
  font-size: 13px;
}
.prescription-item > .doctor-info > .item-date {
  font-size: 13px;
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
</style>
