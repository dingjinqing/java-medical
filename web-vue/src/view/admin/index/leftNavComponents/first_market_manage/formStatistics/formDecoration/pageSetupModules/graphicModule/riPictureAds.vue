<template>
  <div class="riName">
    <div class="riNameMain">
      <h2>广告图片模块<span>图片建议尺寸：宽度630</span></h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="list">
          <span>预览原图：</span>
          <el-radio
            v-model="modulesSaveData.is_preview"
            label="0"
          >否</el-radio>
          <el-radio
            v-model="modulesSaveData.is_preview"
            label="1"
          >是</el-radio>
        </div>
        <div class="list">
          <span></span>
          <div class="tips">选择是，则在图片没有添加链接时，前端用户点击可以预览原图。选择否，则未添加图片链接时，不可预览原图</div>
        </div>
        <div class="list">
          <span>图片：</span>
          <div class="container">
            <div
              class="iconContainer"
              @click="handleToImageDialog()"
              :style="`background:url(${$imageHost}/image/admin/shop_beautify/add_decorete.png) no-repeat`"
            >
              <img
                v-if="modulesSaveData.img_url"
                :src="modulesSaveData.img_url"
              >
            </div>
            <img
              v-if="modulesSaveData.img_url"
              @click="handleToClickDel()"
              :src="$imageHost+'/image/admin/icon_delete.png'"
              class="del_icon"
            >
          </div>

        </div>
        <div class="list">
          <span>文本：</span>
          <el-input
            v-model="modulesSaveData.title"
            size="small"
            :maxlength="19"
          ></el-input>&nbsp;&nbsp;
          <i style="color:#9A9A9A">最多19个字</i>
        </div>
        <div class="list">
          <span>链接：</span>
          <el-input
            v-model="modulesSaveData.title_link"
            size="small"
          ></el-input>&nbsp;&nbsp;
          <el-button
            @click="handleToCallLinkDialog()"
            size="small"
          >选择链接</el-button>
        </div>
        <!--模块私有end-->
      </div>
    </div>
    <!--选择图片弹窗-->
    <ImageDalog
      pageIndex="pictureSpace"
      :tuneUp="imageTuneUp"
      @handleSelectImg="handleSelectImg"
    />
    <!--选择链接弹窗-->
    <SelectLinks
      :tuneUpSelectLink='tuneUpSelectLink'
      @selectLinkPath='handleToGetLinkPath'
    />
  </div>
</template>
<script>
import decMixins from '@/mixins/decorationModulesMixins/decorationModulesMixins'
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog'), // 选择图片弹窗
    SelectLinks: () => import('@/components/admin/selectLinks') // 选择链接弹窗
  },
  mixins: [decMixins],
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      tuneUpSelectLink: false, // 选择链接弹窗调起
      imageTuneUp: false, // 图片选择弹窗调起
      modulesSaveData: {
        'title': '',
        'title_link': '',
        'img_url': '',
        'is_preview': '0'
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
    // 选择弹窗调起
    handleToImageDialog () {
      this.imageTuneUp = !this.imageTuneUp
    },
    // 选择图片弹窗选中数据回传
    handleSelectImg (res) {
      console.log(res)
      this.modulesSaveData.img_url = res.imgUrl
    },
    // 调起选择链接弹窗
    handleToCallLinkDialog (index) {
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },
    // 选择链接弹窗回传数据
    handleToGetLinkPath (path) {
      this.modulesSaveData.title_link = path
    },
    // 点击图片删除icon
    handleToClickDel () {
      this.modulesSaveData.img_url = ''
      console.log(this.modulesSaveData)
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
      margin-bottom: 20px;
      span {
        color: #9a9a9a;
        display: inline-block;
        margin-left: 20px;
      }
    }
    .list {
      .container {
        position: relative;
      }
      margin-bottom: 20px;
      i {
        display: flex;
        align-items: center;
      }
      .del_icon {
        cursor: pointer;
        position: absolute;
        top: -7px;
        right: -5px;
        width: 15px !important;
        height: 15px;
        z-index: 1000;
      }
      span {
        display: inline-block;
        width: 100px;
        display: flex;
        justify-content: flex-end;
        align-items: center;
      }
      display: flex;
      /deep/ .el-input {
        width: 140px;
      }
      .tips {
        color: #a7a7a7;
        font-size: 14px;
        width: 335px;
        line-height: 20px;
      }
      .iconContainer {
        background-size: 45% !important;
        background-position: center !important;
        width: 70px;
        height: 70px;
        border: 1px solid #e5e5e5;
        position: relative;
        cursor: pointer;
        img {
          width: 100%;
        }
      }
      .iconTips {
        color: #a7a7a7;
        font-size: 12px;
        margin-top: 20px;
      }
      .iconSpan {
        align-items: flex-start;
      }
    }
    .sure {
      display: flex;
      justify-content: center;
    }
    //end
  }
}
</style>
