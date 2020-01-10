<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('textModule.textModule')}}</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="text">
          <span>{{$t('textModule.text')}}：</span>
          <el-input
            size="small"
            v-model="moduleSaveData.title"
            :maxlength='30'
          ></el-input>
          <div style="margin-left:10px;color:#999">{{$t('textModule.tips')}}</div>
        </div>
        <div class="text">
          <span>{{$t('textModule.fontSize')}}：</span>
          <el-radio
            v-model="moduleSaveData.fonts_size"
            :label="1"
          >{{$t('textModule.large')}}</el-radio>
          <el-radio
            v-model="moduleSaveData.fonts_size"
            :label="2"
          >{{$t('textModule.middle')}}</el-radio>
          <el-radio
            v-model="moduleSaveData.fonts_size"
            :label="3"
          >{{$t('textModule.small')}}</el-radio>
        </div>
        <div class="text">
          <span>{{$t('textModule.fontColor')}}：</span>
          <el-color-picker
            v-model="moduleSaveData.fonts_color"
            show-alpha
            :predefine="predefineColors"
          >
          </el-color-picker>
          <el-button
            size="small"
            @click="handleToReset(0)"
          >{{$t('textModule.reset')}}</el-button>
        </div>
        <div class="text">
          <span>{{$t('textModule.backgroundColor')}}：</span>
          <el-color-picker
            v-model="moduleSaveData.bgs_color"
            show-alpha
            :predefine="predefineColors"
          >
          </el-color-picker>
          <el-button
            size="small"
            @click="handleToReset(1)"
          >{{$t('textModule.reset')}}</el-button>
        </div>
        <div class="text">
          <span>{{$t('textModule.displayPosition')}}：</span>
          <el-radio
            v-model="moduleSaveData.show_pos"
            :label="1"
          >{{$t('textModule.beATheLeftSide')}}</el-radio>
          <el-radio
            v-model="moduleSaveData.show_pos"
            :label="2"
          >{{$t('textModule.centered')}}</el-radio>
          <el-radio
            v-model="moduleSaveData.show_pos"
            :label="3"
          >{{$t('textModule.beAtTheRight')}}</el-radio>
        </div>
        <div class="text">
          <span>{{$t('textModule.link')}}：</span>
          <el-input
            size="small"
            v-model="moduleSaveData.title_link"
          ></el-input>
          <el-button
            size="small"
            @click="handleToCallLinkDialog()"
          >{{$t('textModule.selectLink')}}</el-button>
        </div>
      </div>
      <!--模块私有end-->
    </div>
    <!--选择链接弹窗-->
    <SelectLinks
      :tuneUpSelectLink='tuneUpSelectLink'
      @selectLinkPath='selectLinkPath'
    />
  </div>
</template>
<script>
export default {
  components: {
    SelectLinks: () => import('@/components/admin/selectLinks') // 选择链接弹窗
  },
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      tuneUpSelectLink: false, // 调起选择链接弹窗
      predefineColors: [ // 颜色选择器预定义颜色池
        '#ff4500',
        '#ff8c00',
        '#ffd700',
        '#90ee90',
        '#00ced1',
        '#1e90ff',
        '#c71585',
        'rgba(255, 69, 0, 0.68)',
        'rgb(255, 120, 0)',
        'hsv(51, 100, 98)',
        'hsva(120, 40, 94, 0.5)',
        'hsl(181, 100%, 37%)',
        'hsla(209, 100%, 56%, 0.73)',
        '#c7158577'
      ],
      moduleSaveData: {
        'title': '', // 文本
        'fonts_size': 1, // 字体大小
        'fonts_color': '#333333', // 字体颜色
        'bgs_color': '#ffffff', // 背景颜色
        'show_pos': 1, // 显示位置
        'title_link': '' // 链接
      }

    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        this.moduleSaveData = this.modulesData
      },
      immediate: true
    },
    // 监听数据变换
    moduleSaveData: { // 模块公共
      handler (newData) {
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    }
  },
  methods: {
    // 点击重置
    handleToReset (index) {
      switch (index) {
        case 0:
          this.moduleSaveData.fonts_color = '#333333'
          break
        case 1:
          this.moduleSaveData.bgs_color = '#ffffff'
      }
    },
    // 调起选择链接弹窗
    handleToCallLinkDialog () {
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },
    // 选择链接弹窗选中链接后数据回传
    selectLinkPath (path) {
      this.moduleSaveData.title_link = path
    }
  }
}
</script>
<style lang="scss" scoped>
.rightCommodity {
  .rightCommodityMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
    h2 {
      font-size: 14px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    //模块私有样式  --------------
    .main {
      .text {
        margin: 20px 0;
        display: flex;
        align-items: center;
        /deep/ .el-input {
          width: 150px;
        }
        span {
          display: inline-block;
          width: 100px;
          text-align: right;
        }
        /deep/ .el-button {
          margin-left: 10px;
        }
      }
    }
    //end
  }
}
</style>
