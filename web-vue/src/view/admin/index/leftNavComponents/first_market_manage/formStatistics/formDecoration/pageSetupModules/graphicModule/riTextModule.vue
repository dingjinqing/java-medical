<template>
  <div class="riName">
    <div class="riNameMain">
      <h2>{{$t('textModule.textModule')}}</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="text">
          <span>{{$t('textModule.text')}}：</span>
          <el-input
            size="small"
            v-model="modulesSaveData.title"
            :maxlength='30'
          ></el-input>
          <div style="margin-left:10px;color:#999">{{$t('textModule.tips')}}</div>
        </div>
        <div class="text">
          <span>{{$t('textModule.fontSize')}}：</span>
          <el-radio
            v-model="modulesSaveData.fonts_size"
            label="1"
          >{{$t('textModule.large')}}</el-radio>
          <el-radio
            v-model="modulesSaveData.fonts_size"
            label="2"
          >{{$t('textModule.middle')}}</el-radio>
          <el-radio
            v-model="modulesSaveData.fonts_size"
            label="3"
          >{{$t('textModule.small')}}</el-radio>
        </div>
        <div class="text">
          <span>{{$t('textModule.fontColor')}}：</span>
          <el-color-picker
            v-model="modulesSaveData.fonts_color"
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
            v-model="modulesSaveData.bgs_color"
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
            v-model="modulesSaveData.show_pos"
            label="1"
          >{{$t('textModule.beATheLeftSide')}}</el-radio>
          <el-radio
            v-model="modulesSaveData.show_pos"
            label="2"
          >{{$t('textModule.centered')}}</el-radio>
          <el-radio
            v-model="modulesSaveData.show_pos"
            label="3"
          >{{$t('textModule.beAtTheRight')}}</el-radio>
        </div>
        <div class="text">
          <span>{{$t('textModule.link')}}：</span>
          <el-input
            size="small"
            v-model="modulesSaveData.title_link"
          ></el-input>
          <el-button
            size="small"
            @click="handleToCallLinkDialog()"
          >{{$t('textModule.selectLink')}}</el-button>
        </div>
      </div>
    </div>
    <!--选择链接弹窗-->
    <SelectLinks
      :tuneUpSelectLink='tuneUpSelectLink'
      @selectLinkPath='selectLinkPath'
    />
  </div>
</template>
<script>
import decMixins from '@/mixins/decorationModulesMixins/decorationModulesMixins'
export default {
  components: {
    SelectLinks: () => import('@/components/admin/selectLinks') // 选择链接弹窗
  },
  mixins: [decMixins],
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      imageTuneUp: false, // 图片选择弹窗调起
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
      modulesSaveData: {
        'title': '',
        'fonts_size': '2',
        'fonts_color': '#333333',
        'bgs_color': '#ffffff',
        'show_pos': '1',
        'title_link': ''
      } // 模块保存数据
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        if (this.modulesData !== -1) {
          this.modulesSaveData = this.modulesData
        }
      },
      immediate: true
    },
    // 监听数据变换
    modulesSaveData: { // 模块公共
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
          this.modulesSaveData.fonts_color = '#333333'
          break
        case 1:
          this.modulesSaveData.bgs_color = '#ffffff'
      }
    },
    // 调起选择链接弹窗
    handleToCallLinkDialog () {
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },
    // 选择链接弹窗选中链接后数据回传
    selectLinkPath (path) {
      this.modulesSaveData.title_link = path
    }
  }
}
</script>
<style lang="scss" scoped>
.riName {
  .riNameMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 20px 2%;
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
