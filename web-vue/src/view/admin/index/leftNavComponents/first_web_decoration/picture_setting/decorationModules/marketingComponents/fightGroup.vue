<template>
  <!-- 拼团抽奖 -->
  <div
    class="membershipCard modules"
    @mouseover="mouseOver"
  >
    <!-- 列表模块 -->
    <div
      class="showModule"
      :class="activeBorder?'activeBorder':''"
    >
      <!-- 模块编辑区 -->
      <div class="fight-group-module">
        <div
          class="center-pin-draw"
          :style="'background:url('+ module_img +')0% 0% / 100% 100%'"
        >
          <div
            class="pin-draw"
            :style="'color:'+ data.font_color"
          >
            <p class="group-name">{{module_name}}</p>
            <p
              v-show="data.show_clock === '1'"
              class="group-time"
            >
              <span v-show="data.group_draw_endtime">{{$t('fightGroup.letEnd')}}{{lastTime}}</span>
              <span v-show="!data.group_draw_endtime">{{$t('fightGroup.sorryLetEnd')}}</span>
            </p>
          </div>
        </div>
      </div>
      <!-- 模块编辑区结束 -->
      <!-- 移动操作工具栏 -->
      <div
        class="item_module_title"
        :style="hoverTips?'width:140px':''"
      >
        <span>{{$t('fightGroup.puzzleDraw')}}</span>
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
import vm from '@/main'
export default {
  name: 'FightGroup',
  props: {
    flag: Number, // 模块公共
    nowRightShowIndex: Number, // 模块公共
    middleHereFlag: Boolean, // 模块公共
    backData: Object // 模块公共,左侧传递过来的数据
  },
  data () {
    return {
      activeBorder: false, // 模块公共
      activeSetHere: false, // 模块公共
      hoverTips: 'hoverTips', // 英文适配  模块公共
      bgColor: '', // 主题色
      data: {
        group_draw_id: '',
        name_set: '0',
        group_draw_name: this.$t('fightGroup.puzzleDraw'),
        show_clock: '0',
        font_color: '#ffffff',
        module_bg: '0',
        module_img: ''
      },
      default_module_img: this.$imageHost + '/image/admin/fighting_group_draw1.jpg'
    }
  },
  computed: {
    module_img: function () {
      if (this.data.module_img) {
        return this.data.module_img
      } else {
        return this.default_module_img
      }
    },
    module_name: function () {
      if (this.data.name_set === 1) {
        if (this.data.group_draw_name) {
          return this.data.group_draw_name
        }
      }
      return this.$t('fightGroup.puzzleDraw')
    },
    lastTime: function () {
      if (this.data.group_draw_endtime) {
        let date = new Date(this.data.group_draw_endtime)
        let now = new Date()
        let time1 = date - now // 相差毫秒数
        if (time1 <= 0) {
          return vm.$t('bargain.over')
        }
        let oneDay = 24 * 60 * 60 * 1000
        let days = Math.floor(time1 / oneDay)
        let time2 = time1 - days * oneDay // 1天，相差毫秒数
        let hours = Math.floor(time2 / (60 * 60 * 1000))
        let time3 = time2 - hours * (60 * 60 * 1000) // 1 小时，相差毫秒数
        let minutes = Math.floor(time3 / (60 * 1000))
        let time4 = time3 - minutes * 60 * 1000
        let seconds = Math.floor(time4 / 1000)
        console.log('date:', date, days, hours, minutes, seconds)
        return days + vm.$t('bargain.day') + hours + vm.$t('bargain.hour') + minutes + vm.$t('bargain.minute')
      }
      return ''
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
      if (newData) {
        this.$emit('middleDragData', this.flag)
      }
    },
    activeBorder (newData) { // 模块公共
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
          this.data = newData
        }
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

    this.bgColor = localStorage.getItem('V-backgroundColor') || '#f66'
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
.fight-group-module {
  .center-pin-draw {
    width: 100%;
    height: 130px;
    padding-left: 20px;
    .group-name {
      font-size: 18px;
      padding-top: 40px;
    }
    .group-time {
      font-size: 12px;
    }
  }
}
</style>
