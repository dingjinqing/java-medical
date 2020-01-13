<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('titleModule.middleTitle')}}</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="module">
          <span>{{$t('titleModule.title')}}：</span>
          <el-input
            v-model="moduleSaveData.title"
            :maxlength='20'
            size="small"
          ></el-input>
          <i>{{$t('titleModule.tip')}}</i>
        </div>
        <div class="module">
          <span><i>{{$t('titleModule.titleTemplate')}}：</i></span>
          <el-radio
            v-model="moduleSaveData.title_model"
            :label="1"
          >{{$t('titleModule.basicStyle')}}</el-radio>
          <el-radio
            v-model="moduleSaveData.title_model"
            :label="2"
          >{{$t('titleModule.newsTemplateStyle')}}</el-radio>
        </div>
        <div class="module">
          <span><i>{{$t('titleModule.displayPosition')}}：</i></span>
          <el-radio
            v-model="moduleSaveData.tit_center"
            :label="1"
          >{{$t('titleModule.beAtTheLeftSide')}}</el-radio>
          <el-radio
            v-model="moduleSaveData.tit_center"
            :label="2"
          >{{$t('titleModule.centered')}}</el-radio>
        </div>
        <!--模板标题选中基础样式显示模块-->
        <div
          class="hiddenModule"
          v-if="moduleSaveData.title_model===1"
        >
          <div class="module">
            <span>{{$t('titleModule.Icon')}}：</span>
            <div class="imgDiv">
              <img
                :style="moduleSaveData.img_url?'width:100%;height:100%;':''"
                :src="moduleSaveData.img_url?moduleSaveData.img_url:($imageHost+'/image/admin/shop_beautify/add_decorete.png')"
                @click="handleToCallAddImg()"
              >
              <i
                class="delIcon"
                :style="'background:url('+$imageHost+'/image/admin/icon_delete.png) no-repeat'"
                v-if="moduleSaveData.img_url"
                @click="handleToDelImg()"
              ></i>
            </div>
          </div>
          <div class="module">
            <span>{{$t('titleModule.fontColor')}}：</span>
            <el-color-picker
              v-model="moduleSaveData.color"
              show-alpha
              :predefine="predefineColors"
              size="small"
            >
            </el-color-picker>
            <el-button
              @click="handleToReset(0)"
              size="small"
            >{{$t('titleModule.reset')}}</el-button>
          </div>
          <div class="module">
            <span><i style="text-align:right">{{$t('titleModule.backgroundColor')}}：</i></span>
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
            >{{$t('titleModule.reset')}}</el-button>
          </div>

        </div>
        <!--模板标题选中新闻标题样式显示模块-->
        <div
          class="hiddenModule"
          v-if="moduleSaveData.title_model===2"
        >
          <div class="module">
            <span>日期：</span>
            <el-date-picker
              v-model="moduleSaveData.title_date"
              size="small"
              type="date"
              placeholder="选择日期"
              value-format='yyyy-MM-dd'
            >
            </el-date-picker>
          </div>
          <div class="module">
            <span>作者：</span>
            <el-input
              size="small"
              v-model="moduleSaveData.title_author"
            ></el-input>
          </div>
          <div class="module">
            <span>链接标题：</span>
            <el-input
              size="small"
              v-model="moduleSaveData.link_title"
              :maxlength='20'
            ></el-input>
            <i>最多20个字</i>
          </div>
        </div>
        <div class="module">
          <span>{{$t('titleModule.link')}}：</span>
          <el-input
            size="small"
            v-model="moduleSaveData.title_link"
          ></el-input>
          <el-button
            @click="handleToCallLinkDialog()"
            size="small"
          >{{$t('titleModule.selectLink')}}</el-button>
        </div>
      </div>
      <!--模块私有end-->
    </div>
    <!--添加图片弹窗-->
    <ImageDalog
      :tuneUp='tuneUp'
      @handleSelectImg='handleSelectImg'
      pageIndex='pictureSpace'
    />
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
    ImageDalog: () => import('@/components/admin/imageDalog'), // 选择图片弹窗
    SelectLinks: () => import('@/components/admin/selectLinks') // 选择链接弹窗
  },
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      tuneUp: false, // 调起添加图片弹窗
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
        'module_name': 'm_title', // 模块名称
        'title': '啦啦啦', // 标题
        'title_model': 1, // 标题模板
        'title_link': '', // 链接
        'tit_center': 1, // 显示位置
        'color': '#333333', // 字体颜色
        'bg_color': '#ffffff', // 背景颜色
        'title_date': '', // 日期
        'title_author': '', // 作者
        'link_title': '' // 链接标题
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
    modulesData: { // 模块公共
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
          this.moduleSaveData.color = '#333333'
          break
        case 1:
          this.moduleSaveData.bg_color = '#ffffff'
      }
    },
    //  调起添加图片弹窗
    handleToCallAddImg () {
      this.tuneUp = !this.tuneUp
    },
    // 添加图片弹窗选择中举回传
    handleSelectImg (res) {
      console.log(res.imgUrl)
      this.moduleSaveData.img_url = res.imgUrl
    },
    // 调起选择链接弹窗
    handleToCallLinkDialog () {
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },
    // 选择链接弹窗选中链接数据回传
    selectLinkPath (path) {
      this.moduleSaveData.title_link = path
    },
    // 删除图片
    handleToDelImg () {
      this.moduleSaveData.img_url = ''
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
      .module {
        display: flex;
        margin-top: 20px;
        span {
          display: inline-block;
          width: 20%;
          line-height: 32px;
          height: 32px;
          display: flex;
          justify-content: flex-end;
          i {
            color: #333;
            text-align: center;
            line-height: 12px;
            padding-left: 10px;
            display: flex;
            align-items: center;
          }
        }
        /deep/ .el-input {
          width: 150px;
          margin-right: 5px;
        }
        /deep/ .el-radio {
          display: flex;
          align-items: center;
        }
        i {
          color: #999;
          display: inline-block;
          height: 32px;
          line-height: 32px;
        }
        .imgDiv {
          width: 70px;
          height: 70px;
          border: 1px solid #e5e5e5;
          margin-right: 20px;
          display: flex;
          justify-content: center;
          align-items: center;
          position: relative;
          cursor: pointer;
          img {
            width: 70%;
            height: 70%;
          }
          .delIcon {
            width: 15px;
            height: 15px;
            position: absolute;
            right: -5px;
            top: -5px;
            background-size: 100%;
            z-index: 9;
          }
        }
      }
    }
    //end
  }
}
</style>
