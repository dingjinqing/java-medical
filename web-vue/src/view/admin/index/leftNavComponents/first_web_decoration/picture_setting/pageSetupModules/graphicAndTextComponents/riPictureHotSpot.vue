<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('pictureHotSpot.pictureHotAreaModule')}}</h2>
      <!--模块私有区域-->
      <div class="main">
        <div
          class="haveImg"
          v-if="modulesSaveData.data.bg_img_url"
        >
          <div style="position: relative">
            <img
              :src="modulesSaveData.data.bg_img_url"
              style="max-height:300px;max-width:300px"
            >
            <div
              class="change-img"
              style="cursor: pointer"
              @click="handleToCallImageDialog()"
            >{{$t('pictureHotSpot.replacePictures')}}</div>
            <div class="change-img mask">

            </div>
          </div>

        </div>

        <!--占位-->
        <div
          class="setBgImage"
          style="cursor: pointer"
          @click="handleToCallImageDialog()"
          v-else
        >
          <div>+{{$t('pictureHotSpot.addABackgroundImage')}}</div>
          <h4>{{$t('pictureHotSpot.addABackgroundImageTips1')}}</h4>
          <h4>{{$t('pictureHotSpot.addABackgroundImageTips2')}}</h4>
        </div>

        <!--底部-->
        <div class="operation-area">
          <div class="hot-number">{{$t('pictureHotSpot.numberOfHotAreas')}}<span class="hot-number-span">{{modulesSaveData.data.rectangles.length}}个</span></div>

          <el-button
            @click="handleToSetHotArea()"
            size="small"
          >{{$t('pictureHotSpot.hotZoneSet')}}</el-button>

        </div>
      </div>
      <!--模块私有end-->
    </div>
    <!--图片弹窗-->
    <ImageDalog
      pageIndex="pictureSpace"
      :tuneUp="imageTuneUp"
      @handleSelectImg="handleSelectImg"
    />
    <!--热区弹窗-->
    <SetHotAreaDialog
      :hotDialogVisible.sync="hotDialogVisible"
      :imgUrl="modulesSaveData.data.bg_img_url"
      @handleToGetHotData="handleToGetHotData"
      :hotData='modulesSaveData.data.rectangles'
    />
  </div>
</template>
<script>
export default {
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog'), // 图片弹窗
    SetHotAreaDialog: () => import('./setHotAreaDialog') // 热区弹窗
  },
  data () {
    return {
      imageTuneUp: false, // 图片弹窗调起
      hotDialogVisible: false, //  热区弹窗调起
      modulesSaveData: {
        data: {
          'bg_img_url': '',
          'rectangles': [

          ],
          'bg_img_width': '',
          'bg_img_height': ''
        }
      }

    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        this.modulesSaveData = this.modulesData
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
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 图片弹窗调起
    handleToCallImageDialog () {
      this.imageTuneUp = !this.imageTuneUp
    },
    // 图片弹窗选中回传
    handleSelectImg (res) {
      console.log(res)
      this.modulesSaveData.data.bg_img_url = res.imgUrl
    },
    // 调起设置热区弹窗
    handleToSetHotArea () {
      if (this.modulesSaveData.data.bg_img_url) {
        this.hotDialogVisible = true
      } else {
        this.$message.error('请选择图片')
      }
    },
    // 热区弹窗数据回传
    handleToGetHotData (res) {
      console.log(res)
      this.modulesSaveData.data.rectangles = res
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
      margin-top: 20px;
      .haveImg {
        position: relative;
        display: flex;
        .change-img {
          width: 100%;
          color: #fff;
          z-index: 3;
          position: absolute;
          bottom: 0;
          height: 25px;
          text-align: center;
          line-height: 25px;
          font-size: 12px;
        }
        .mask {
          background-color: #333;
          opacity: 0.5;
          z-index: 1;
        }
      }
      .setBgImage {
        width: 100%;
        height: 150px;
        background-color: #fff;
        border: 2px dashed #e5e5e5;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        div {
          color: #5a8bff;
          padding-bottom: 12px;
        }
        h4 {
          color: #999;
          padding-bottom: 2px;
        }
      }
      .operation-area {
        margin-top: 10px;
        .hot-number {
          color: #0c0c0c;
          margin-bottom: 5px;
        }
      }
    }
    //end
  }
}
</style>
