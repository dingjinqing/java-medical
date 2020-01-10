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
        class="pictureAds"
        :style="'backgroundColor:'+moduleSavedata.box_color"
      >
        <!--初始占位模块-->
        <div
          class="seizeASeat"
          v-if="!moduleSavedata.image_list.length"
        >
          <p>{{$t('pictureAds.clickEditImageAd')}}</p>
          <p style="font-size:12px;margin-top:5px">{{$t('pictureAds.recommendedPixels')}}</p>
        </div>
        <!--图片列表内容-->
        <div
          v-else
          :style="moduleSavedata.image_type==='1'?'display:flex;flex-wrap:wrap':(moduleSavedata.image_type==='2'||moduleSavedata.image_type==='3'||moduleSavedata.image_type==='4')?'display:flex':''"
        >
          <div
            class="imageList"
            v-for="(item,index) in moduleSavedata.image_list"
            :key="index"
            :style="(moduleSavedata.image_type==='1'&&(index===0||index===1))?`display:flex;width:50%;margin-top:${moduleSavedata.image_space}px`:moduleSavedata.image_type==='1'?'display:flex;width:50%;':(moduleSavedata.image_type==='2'&&index===0)?`display:flex;width:70%;margin-right:${moduleSavedata.image_space}px`:(moduleSavedata.image_type==='2'&&index===1)?'display:flex;width:30%':((moduleSavedata.image_type==='3'||moduleSavedata.image_type==='4')&&index===0)?'display:flex;width:40%':((moduleSavedata.image_type==='3'||moduleSavedata.image_type==='4')&&index===1)?`display:flex;width:40%;margin:0 ${moduleSavedata.image_space}px`:((moduleSavedata.image_type==='3'||moduleSavedata.image_type==='4')&&index===2)?'display:flex;width:20%':(moduleSavedata.image_type==='0'&&index===0)?`margin:${moduleSavedata.image_space}px 0`:`margin-bottom:${moduleSavedata.image_space}px`"
          >
            <img
              :style="moduleSavedata.image_type==='0'?'width:378px;height:378px;':moduleSavedata.image_type==='4'?'width:100%;height:70px':(moduleSavedata.image_type==='1'&&index%2===0)?`width:100%;height:100%;padding-bottom:${moduleSavedata.image_space}px;padding-right:${moduleSavedata.image_space/2}px`:(moduleSavedata.image_type==='1'&&index%2!==0)?`width:100%;height:100%;padding-bottom:${moduleSavedata.image_space}px;padding-left:${moduleSavedata.image_space/2}px`:'width:100%;height:100%'"
              :src="item.image"
              v-if="moduleSavedata.image_type==='2'?!(moduleSavedata.image_type==='2'&&index>1):moduleSavedata.image_type==='3'?!(moduleSavedata.image_type==='3'&&index>2):moduleSavedata.image_type==='4'?!(moduleSavedata.image_type==='4'&&index>2):true"
            >
            <p
              :style="'bottom:'+moduleSavedata.image_space+'px'"
              v-if="item.title"
            >{{item.title}}</p>
          </div>
        </div>
      </div>
      <!--模块编辑区结束-->
      <div class="item_module_title">
        <span>{{$t('pictureAds.pictureAds')}}</span>
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
      hoverTips: 'hoverTips', // 英文适配
      // 模块私有
      moduleSavedata: {

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
          this.moduleSavedata = newData
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

.pictureAds {
  // width: 385px;
  .seizeASeat {
    background: #e8efff;
    text-align: center;
    color: #5a8bff;
    font-size: 14px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    padding: 50px 0;
  }
  .imageList {
    position: relative;
    p {
      display: block;
      position: absolute;
      width: 100%;
      bottom: 0;
      left: 0;
      background: rgba(0, 0, 0, 0.5);
      color: #fff;
      font-size: 12px;
      height: 30px;
      line-height: 30px;
      text-align: center;
      z-index: 100;
    }
  }
}
</style>
