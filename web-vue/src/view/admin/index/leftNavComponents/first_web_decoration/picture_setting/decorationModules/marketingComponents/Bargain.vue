<template>
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
      <div class="bargain_content">
        <ul class="bargain_default_ul">
          <li class="bargain_default_li">
            <div
              class="bargain_default_img"
              :style="'background-image:url('+ $imageHost +'/image/admin/shop_beautify/decorate_model.png)'"
            >
              <el-image style="width: 100%; height: 150px;"></el-image>
              <div class="bargain_time_down">
                <div>距结束还剩</div>
                <p>6天23时57分</p>
              </div>
            </div>
            <div class="bargain_default_info">
              <div class="bargain_info_head">
                <span class="bargain_price">￥<span style="font-size: 18px;">0</span></span>
                <span class="bargain_old">￥0</span>
              </div>
              <div class="bargain_info_name"></div>
              <div class="bargain_info_bottom">
                <span class="bargain_num">仅剩10件</span>
                <el-button
                  class="bargin_free_btn"
                  size="small"
                  round
                >去砍价</el-button>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <!-- 模块编辑区结束 -->
      <!-- 移动操作工具栏 -->
      <div
        class="item_module_title"
        :style="hoverTips?'width:140px':''"
      >
        <span>砍价</span>
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
      data: {}
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
.bargain_content {
  box-sizing: border-box;
  .bargain_default_ul {
    padding: 5px;
    background: #f5f5f5;
    overflow: hidden;
    .bargain_default_li {
      width: 50%;
      float: left;
      .bargain_default_img {
        position: relative;
        background: #eaf2ff;
        background-repeat: no-repeat;
        background-position: center center;
        background-size: 24%;
        .bargain_time_down {
          position: absolute;
          width: 100%;
          text-align: center;
          padding: 5px 0;
          bottom: 0;
          background: rgba(0, 0, 0, 0.3);
          color: #fff;
          font-size: 12px;
        }
      }
      .bargain_default_info {
        background: #fff;
        padding: 5px;
        .bargain_price {
          color: #f66;
          font-size: 12px;
          vertical-align: bottom;
        }
        .bargain_old {
          color: #999999;
          text-decoration: line-through;
          font-size: 12px;
          margin-left: 2px;
          width: 40px;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          margin-top: 0px;
          vertical-align: bottom;
        }
        .bargain_info_name {
          min-height: 38px;
          margin: 5px 0;
          width: 100%;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
        }
        .bargain_num {
          font-size: 12px;
          color: #999;
          float: left;
          line-height: 25px;
        }
      }
    }
  }
}
</style>
