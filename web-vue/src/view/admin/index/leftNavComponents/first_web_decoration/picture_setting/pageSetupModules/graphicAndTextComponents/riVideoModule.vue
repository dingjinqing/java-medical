<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('titleModule.videoModule')}}</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="video">
          <span>{{$t('titleModule.video')}}：</span>
          <!--占位-->
          <div
            class="videoZb"
            @click="handleToCallAddVideo()"
          >
            <img :src="moduleSaveData.video_showpath?moduleSaveData.video_showpath:($imageHost+'/image/admin/add_video.png')">
          </div>
        </div>
        <div class="videoTips">
          {{$t('titleModule.videoTips')}}
        </div>
        <div
          class="video"
          style="margin:20px 0"
        >
          <span style="line-height:32px;height:32px">{{$t('titleModule.videoTitle')}}：</span>
          <el-input
            v-model="moduleSaveData.video_title"
            :maxlength='20'
            size="small"
          ></el-input>
          <i style="color:#999;margin-left:10px;height:32px;line-height:32px">{{$t('titleModule.maxLength')}}</i>
        </div>
        <div
          class="video"
          style="margin-top:10px"
        >
          <span>{{$t('titleModule.coverMap')}}：</span>
          <div
            class='radioDiv'
            :class="columnFlag?'columnFlag':''"
          >
            <el-radio
              v-model="moduleSaveData.video_poster"
              label="1"
            >{{$t('titleModule.originalCover')}}</el-radio>
            <el-radio
              v-model="moduleSaveData.video_poster"
              label="2"
            >{{$t('titleModule.customCover')}}</el-radio>
          </div>
        </div>
        <!--选择自定义封面显示的隐藏模块-->
        <div
          class="video"
          style="margin-top:20px"
          v-show="moduleSaveData.video_poster==='2'"
        >
          <span>{{$t('titleModule.uploadCover')}}：</span>
          <div class="imgDiv">

            <img
              :style="moduleSaveData.custom_url?'width:100%;height:100%;':''"
              :src="moduleSaveData.custom_url?moduleSaveData.custom_url:($imageHost+'/image/admin/shop_beautify/add_decorete.png')"
              @click="handleToCallAddImg()"
            >
          </div>
        </div>
      </div>
    </div>
    <!--模块私有end-->
    <!--选择视频组件-->
    <VideoSpaceDialog
      :visible.sync='vidoeVisible'
      @video-click='handleToAddVideo'
    />
    <!--选择图片弹窗-->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp='imgVisible'
      @handleSelectImg='handleSelectImg'
      :imageSize=[355,200]
    />
  </div>
</template>
<script>
export default {
  components: {
    VideoSpaceDialog: () => import('@/components/admin/videoSpace/videoSpaceDialog'), // 选择视频弹窗
    ImageDalog: () => import('@/components/admin/imageDalog') // 添加图片弹窗
  },
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      vidoeVisible: false, // 视频弹窗调起flag
      imgVisible: false, // 图片弹窗flag
      columnFlag: false, // 英文适配
      moduleSaveData: {

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
    // 调起添加图片弹窗
    handleToCallAddImg () {
      console.log(11)
      this.imgVisible = !this.imgVisible
    },
    // 添加图片弹窗后选中图片后回传事件
    handleSelectImg (res) {
      console.log(res)
      this.moduleSaveData.custom_path = res.imgPath
      this.moduleSaveData.custom_url = res.imgUrl
    },
    // 调起添加视频弹窗
    handleToCallAddVideo () {
      this.vidoeVisible = true
    },
    // 选中视频后回传事件
    handleToAddVideo (res) {
      console.log(res)
      // this.moduleSaveData.video_showurl = res.videoUrl
      this.moduleSaveData.video_url = res.videoUrl
      // this.moduleSaveData.video_showpath = res.snapshotUrl
      this.moduleSaveData.video_img = res.snapshotUrl
      this.moduleSaveData.video_size = res.videoSize
      this.moduleSaveData.video_width = res.videoWidth
      this.moduleSaveData.video_height = res.videoHeight
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
      .video {
        display: flex;

        span {
          display: inline-block;
          width: 102px;
          text-align: right;
          margin-right: 10px;
          white-space: nowrap;
          word-break: break-all;
        }
        .videoZb {
          background: #f7f7f7;
          border: 1px solid #ccc;
          width: 150px;
          height: 150px;
          display: flex;
          justify-content: center;
          align-items: center;
          cursor: pointer;
          img {
            max-width: 150px;
            max-height: 150px;
          }
        }
        /deep/ .el-input {
          width: 200px;
        }
        .radioDiv {
          display: flex;
        }
        .columnFlag {
          flex-direction: column;
          /deep/ .el-radio {
            margin-bottom: 5px;
          }
        }
      }
      .videoTips {
        color: #999;
        padding-left: 96px;
        margin: 10px 0;
        line-height: 18px;
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
    //end
  }
}
</style>
