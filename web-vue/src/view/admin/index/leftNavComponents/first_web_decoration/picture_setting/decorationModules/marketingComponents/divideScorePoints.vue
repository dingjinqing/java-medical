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
      <div class="divideScorePoints">
        <div class="main">
          <img :src="data.module_img?data.module_img:`${$imageHost}/image/admin/pin_background.png`">
          <!--图片上浮动模块-->
          <div class="floatModule">
            <div
              class="title"
              :style="data.font_color?`color:${data.font_color}`:''"
            >{{data.pin_title===0?data.pin_title_text:zbTitle}}</div>
            <div
              class="content"
              :style="data.font_color?`color:${data.font_color}`:''"
              v-if="data.hide_active===0"
            >{{data.hide_active===0?content:''}}</div>
            <div
              style="height:14px"
              v-if="data.hide_active!==0"
            ></div>
            <div
              class="date"
              :style="data.font_color?`color:${data.font_color}`:''"
            >{{(data.hide_time===0)?date:''}}</div>
          </div>
        </div>
      </div>
      <!--模块编辑区结束-->
      <div
        class="item_module_title"
        :style="hoverTips?'width:140px':''"
      >
        <span>瓜分积分</span>
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
import { selectGroupIntegration } from '@/api/admin/marketManage/groupIntegrationList.js'
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
      zbTitle: 'xx积分等你拿,购物可抵现金！', // title占位
      content: 'x人瓜分xxx积分', // 内容
      date: 'xx至xx', // 时间
      // 模块私有
      data: {
        'module_name': 'm_pin_integration',
        'act_id': -1, // 活动id
        'pin_title': 1, // 标题 radio 1 0
        'pin_title_text': '', // 标题 自定义内容
        'hide_active': 0, // 隐藏内容 活动内容
        'hide_time': 0, // 隐藏内容 有效期
        'module_bg': 0, // 活动地图radio 0 1
        'module_img': '', // 自定义活动地图
        'font_color': '#fff'
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
          if (newData.act_id !== -1) {
            selectGroupIntegration(newData.act_id).then(res => {
              console.log(res)
              if (res.error === 0) {
                let content = res.content
                this.content = `${content.limitAmount}瓜分${content.inteTotal}`
                this.date = `${content.startTime}至${content.endTime}`
              }
            })
            this.data = newData
          }
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

.divideScorePoints {
  .main {
    position: relative;
    img {
      height: 165px;
      width: 100%;
    }
    .floatModule {
      color: #fff;
      width: 100%;
      height: 165px;
      position: absolute;
      top: 0;
      left: 0;
      .title {
        margin: 30px 0 0 30px;
        font-size: 18px;
        width: 100%;
        height: 60px;
        width: 128px;
        line-height: 26px;
      }
      .content {
        margin-left: 30px;
        font-size: 14px;
      }
      .date {
        margin: 10px 0 0 30px;
        font-size: 12px;
      }
    }
  }
}
</style>
