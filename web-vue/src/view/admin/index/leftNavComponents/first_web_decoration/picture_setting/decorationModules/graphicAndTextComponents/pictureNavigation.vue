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
      <div class="pictureNavigation">
        <div class="pictureNavList">
          <div style="backgroundColor:#eaf2ff;border-radius: 50%;">
            <img src="">
          </div>
          <p>导航一</p>
        </div>
        <div class="pictureNavList">
          <div style="backgroundColor:#eaf2ff;border-radius: 50%;">
            <img src="">
          </div>
          <p>导航二</p>
        </div>
        <div class="pictureNavList">
          <div style="backgroundColor:#eaf2ff;border-radius: 50%;">
            <img src="">
          </div>
          <p>导航三</p>
        </div>
        <div class="pictureNavList">
          <div style="backgroundColor:#eaf2ff;border-radius: 50%;">
            <img src="">
          </div>
          <p>导航四</p>
        </div>
      </div>
      <!--模块编辑区结束-->
      <div class="item_module_title">
        <span>图片导航</span>
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
      放这里
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
.pictureNavigation {
  height: 97px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  .pictureNavList {
    margin: 5px;
    div {
      width: 56px;
      height: 56px;
      margin: 5px auto;
    }
    p {
      color: #92b0e4;
      text-align: center;
      font-size: 12px;
      margin-bottom: 5px;
      height: 16px;
      &:hover {
        text-decoration: underline;
      }
    }
  }
}
</style>
