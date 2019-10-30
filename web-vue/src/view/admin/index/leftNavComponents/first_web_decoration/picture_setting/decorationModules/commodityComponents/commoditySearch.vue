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
      <div
        class="commoditySearch"
        :style="'backgroundColor:'+data.box_color"
      >
        <div class="commoditySearchMain">
          <div
            class="Search"
            :style="{'border-radius':data.search_style==='1'?'30px':'','height':data.search_font==='1'?'34px':data.search_font==='0'?'40px':'28px','backgroundColor':data.back_color}"
          >
            <span class="el-icon-search"></span>
            <span>{{$t('commoditySearch.placeholderText')}}</span>
          </div>
          <div
            v-if="data.search_sort==='1'"
            class="icon"
            :style="'color:'+data.sort_bg_color"
          >
            <span
              style="display: flex;justify-content: center;"
              class="el-icon-c-scale-to-original"
            ></span>
            <span>{{$t('commoditySearch.classification')}}</span>
          </div>
        </div>

      </div>
      <!--模块编辑区结束-->
      <div
        class="item_module_title"
        :style="hoverTips?'width:140px':''"
      >
        <span>{{$t('commoditySearch.commodity')}}</span>
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
    flag: Number,
    nowRightShowIndex: Number,
    middleHereFlag: Boolean,
    backData: Object
  },
  data () {
    return {
      activeBorder: false,
      activeSetHere: false,
      // 模块私有
      data: {
        search_style: '1',
        search_font: '1',
        box_color: '',
        sort_bg_color: '',
        back_color: '',
        search_sort: ''
      }
    }
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
      console.log(newData, this.index)
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
        }
        console.log(newData)
      },
      immediate: true,
      deep: true
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
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
    handleToClickIcon (flag) {
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
    mouseOver () {
      this.$emit('middleDragData', this.flag)
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/style/admin/decorationModules.scss";

.commoditySearch {
  background: rgb(238, 238, 238);
  padding: 5px 0px;
  .commoditySearchMain {
    display: flex;
    justify-content: space-between;
    position: relative;
    .Search {
      flex: 1;
      // line-height: 24px;
      padding: 0 5px;
      margin: 5px 10px;
      border-color: #ccc;
      border: 1px solid transparent;
      display: flex;
      align-items: center;
      span {
        color: #b4b4b4;
      }
    }
    .icon {
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      margin-right: 10px;
      span:nth-of-type(1) {
        font-size: 30px;
        display: inline-block;
      }
      span:nth-of-type(2) {
        display: inline-block;
        text-align: right;
      }
    }
  }
}
</style>
