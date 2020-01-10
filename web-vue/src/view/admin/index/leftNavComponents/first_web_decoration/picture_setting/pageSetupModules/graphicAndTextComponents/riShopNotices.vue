<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('shopNotices.shopAnnouncementModule')}}</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="content">
          <span>{{$t('shopNotices.content')}}：</span>
          <el-input
            type="textarea"
            :placeholder="$t('shopNotices.shopAnnouncementModule')"
            v-model="moduleSaveData.shop_text"
            maxlength="120"
            show-word-limit
            resize='none'
            :rows='5'
          >
          </el-input>
        </div>
        <div class='content'>
          <span>{{$t('shopNotices.fontColor')}}：</span>
          <el-color-picker
            v-model="moduleSaveData.font_color"
            show-alpha
            :predefine="predefineColors"
            size="small"
          >
          </el-color-picker>
          <el-button
            @click="handleToReset(0)"
            size="small"
          >{{$t('shopNotices.reset')}}</el-button>
        </div>
        <div class='content'>
          <span :style="columnFlag?'line-height:32px':'line-height:16px'">{{$t('shopNotices.backgroundColor')}}：</span>
          <el-color-picker
            v-model="moduleSaveData.bg_color"
            show-alpha
            :predefine="predefineColors"
            size="small"
          >
          </el-color-picker>
          <el-button
            @click="handleToReset(1)"
            size="small"
          >{{$t('shopNotices.reset')}}</el-button>
        </div>
        <div class="content link">
          <span>{{$t('shopNotices.link')}}：</span>
          <el-input
            size="small"
            v-model="moduleSaveData.title_link"
          ></el-input>
          <el-button
            @click="handleToSelectLinkPath()"
            size="small"
          >{{$t('shopNotices.selectLink')}}</el-button>
        </div>
        <div class="content">
          <span :style="columnFlag?'line-height:32px':'line-height:16px'">{{$t('shopNotices.displayPosition')}}：</span>
          <el-radio
            v-model="moduleSaveData.announce_position"
            :label="0"
          >{{$t('shopNotices.generalStyle')}}</el-radio>
          <el-radio
            v-model="moduleSaveData.announce_position"
            :label="1"
          >{{$t('shopNotices.scrollToTopFixed')}}</el-radio>
        </div>
      </div>
      <!--模块私有end-->
    </div>
    <!--选择链接弹窗-->
    <SelectLinks
      :tuneUpSelectLink='tuneUpSelectLink'
      @selectLinkPath="handleToGetLinkPath"
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
      tuneUpSelectLink: false, // 调起选择链接弹窗flag
      predefineColors: [ // 预定义颜色池
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
        'shop_text': ''
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
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 点击重置
    handleToReset (index) {
      switch (index) {
        case 0:
          this.moduleSaveData.font_color = '#333333'
          break
        case 1:
          this.moduleSaveData.bg_color = '#fcf9dd'
      }
    },
    // 调起选择链接弹窗
    handleToSelectLinkPath () {
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },
    // 选中链接回传事件
    handleToGetLinkPath (path) {
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
      .content {
        display: flex;
        margin: 20px 0;
        span {
          display: inline-block;
          width: 100px;
          text-align: right;
          height: 32px;
          line-height: 32px;
        }
        /deep/ .el-textarea {
          width: 300px;
        }
        /deep/ .el-button {
          margin-left: 10px;
        }
        /deep/ .el-radio {
          display: flex;
          align-items: center;
        }
      }
      .link {
        /deep/ .el-input {
          width: 170px;
        }
      }
    }
    //end
  }
}
</style>
