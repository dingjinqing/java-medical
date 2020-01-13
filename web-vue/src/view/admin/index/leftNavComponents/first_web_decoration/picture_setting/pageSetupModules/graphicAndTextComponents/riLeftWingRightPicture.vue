<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('leftWingRightPicture.rightTitle')}}</h2>
      <!--模块私有区域-->
      <div class="main">
        <!--选择样式-->
        <div class="chioseStyle">
          <div class="chioseStyleTitle">{{$t('leftWingRightPicture.choiceStyle')}}：</div>
          <div class="chioseStyleMain">
            <div class="left">
              <div style="display: flex;padding: 10px;">
                <div class="top">
                  <img :src="$imageHost+'/image/admin/shop_beautify/decorate_model.png'">
                </div>
                <p>{{$t('leftWingRightPicture.thisPaper')}}</p>
              </div>
              <div class="bottom">
                <el-radio
                  v-model="moduleSaveData.ti_type"
                  :label="0"
                >{{$t('leftWingRightPicture.styleFirst')}}</el-radio>
              </div>
            </div>
            <div class="right">
              <div style="display: flex;padding: 10px;">
                <p>{{$t('leftWingRightPicture.thisPaper')}}</p>
                <div
                  class="top"
                  style="margin-left:15px;margin-right:0"
                >
                  <img :src="$imageHost+'/image/admin/shop_beautify/decorate_model.png'">
                </div>
              </div>
              <div class="bottom">
                <el-radio
                  v-model="moduleSaveData.ti_type"
                  :label="1"
                >{{$t('leftWingRightPicture.styleSecond')}}</el-radio>
              </div>
            </div>
          </div>
        </div>
        <!--样式大小-->
        <div class="styleSize">
          <div class="styleSizeTop">
            <span>{{$t('leftWingRightPicture.styleSize')}}：</span>
            <el-radio
              v-model="moduleSaveData.img_style"
              :label="0"
            >{{$t('leftWingRightPicture.bigPictureStyle')}}</el-radio>
            <el-radio
              v-model="moduleSaveData.img_style"
              :label="1"
            >{{$t('leftWingRightPicture.smallGraphStyle')}}</el-radio>
          </div>
          <div class="styleSizeBottom">
            <div class="top">
              <span>{{$t('leftWingRightPicture.picture')}}：</span>
              <div
                class="imgDiv"
                @click="handleToCallAddImg()"
              >
                <img
                  :style="moduleSaveData.img_url?'width:100%;height:100%;':''"
                  :src="moduleSaveData.img_url?moduleSaveData.img_url:($imageHost+'/image/admin/shop_beautify/add_decorete.png')"
                >
              </div>
              <div class="tips">{{$t('leftWingRightPicture.recommendedDimensions')}}360px*360px</div>
            </div>
            <div class="bottom">
              <span>{{$t('leftWingRightPicture.link')}}：</span>
              <div class="linkRight">
                <el-input
                  size="small"
                  v-model="moduleSaveData.title_link"
                ></el-input>
                <el-button
                  size="small"
                  @click="handleToCallLinkDialog()"
                >{{$t('leftWingRightPicture.selectLink')}}</el-button>
              </div>
            </div>
          </div>
        </div>
        <!--文本内容-->
        <div class="textContent">
          <div class="title">{{$t('leftWingRightPicture.textContent')}}：</div>
          <div class="edit">
            <TinymceEditor
              ref='edit'
              @input="handleToGetText"
              :value='moduleSaveData.rich_text'
              :height='100'
              :imageSize=[360,360]
            />
          </div>
        </div>
      </div>
      <!--模块私有end-->
    </div>
    <!--添加图片弹窗-->
    <ImageDalog
      :tuneUp='tuneUp'
      @handleSelectImg='handleSelectImg'
      :imageSize='[360,360]'
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
    TinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor'), // 编辑器
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
      moduleSaveData: {
        'ti_type': 0, // 文本样式类型
        'img_style': 0, // 图片样式类型
        'img_url': '', // 图片路径
        'title_link': '', // 链接
        'rich_text': '' // 文本内容
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
    // 获得编辑器输入内容
    handleToGetText (res) {
      console.log(res)
      this.moduleSaveData.rich_text = res
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
      .chioseStyle {
        .chioseStyleTitle {
          padding: 20px 0;
        }
        .chioseStyleMain {
          display: flex;
          .left,
          .right {
            margin-right: 4%;
            width: 48%;
            background-color: #fff;
            border-radius: 6px;
            border: 1px solid #e5e5e5;

            .top {
              margin-right: 15px;
              img {
                width: 58px;
                height: 50px;
              }
            }
            p {
              text-align: justify;
            }
            .bottom {
              height: 23px;
              border-top: 1px solid #e5e5e5;
              display: flex;
              justify-content: center;
              align-items: center;
            }
          }
        }
      }
      .styleSize {
        .styleSizeTop {
          padding: 20px 0;
          display: flex;
        }
        .styleSizeBottom {
          width: 100%;
          background-color: #fff;
          border: 1px solid #e5e5e5;
          height: 151px;
          padding: 10px;
          .top {
            height: 86px;
            display: flex;
            span {
              display: inline-block;
              width: 60px;
              text-align: center;
            }
            .imgDiv {
              width: 70px;
              height: 70px;
              border: 1px solid #e5e5e5;
              margin-right: 20px;
              display: flex;
              justify-content: center;
              align-items: center;
              cursor: pointer;
              img {
                width: 50%;
                height: 50%;
              }
            }
            .tips {
              height: 70px;
              line-height: 70px;
              color: #999;
            }
          }
          .bottom {
            span {
              display: inline-block;
              width: 60px;
              text-align: center;
              height: 32px;
              line-height: 32px;
            }
            display: flex;
            .linkRight {
              display: flex;
              /deep/.el-input {
                width: 100px;
              }
              /deep/ .el-button {
                margin-left: 5px;
              }
            }
          }
        }
      }
      .textContent {
        width: 100%;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        height: 253px;
        padding: 10px;
        margin-top: 10px;
        .edit {
          //   height: 80%;
          margin: 10px 0;
        }
      }
    }
    //end
  }
}
</style>
