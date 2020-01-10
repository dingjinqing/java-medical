<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>{{$t('pictureAds.pictureAds')}}</h2>
      <!--模块私有区域-->
      <div class="main">
        <!--选择模板-->
        <div class="listStyle">
          <div class="title">{{$t('pictureAds.selectTemplate')}}：</div>
          <div class="content">
            <div
              class="typeContainer"
              v-for="(item,index) in listTypeData"
              :key="index"
              :style="item.isChecked?'border: 1px solid #5c81f4;':''"
              @click="handleToClickType(index)"
            >
              <div class="type">
                <div class="typeTop">
                  <div
                    class="odd_right"
                    :style="index!==0 ?'display:flex;align-items:center':''"
                  >
                    <span :style="index===0?'height:22px;width:100%;margin-bottom:5px':index===1?'height:30px;width:50%;margin-right:5px':index===2?'height:50px;width:70%;margin-right:5px':index===3?'height:30px;width:35%;margin-right:5px':index===4?'height:20px;width:35%;margin-right:5px':''"></span>
                    <span :style="index===0?'height:22px;width:100%':index===1?'height:30px;width:50%':index===2?'height:50px;width:30%':index===3?'height:30px;width:35%;margin-right:5px':index===4?'height:20px;width:35%;margin-right:5px':''"></span>
                    <span
                      v-if="index==3||index===4"
                      :style="index===3?'height:30px;width:20%;':index===4?'height:20px;width:20%;':''"
                    ></span>
                  </div>
                </div>
                <p>{{item.typeName}}</p>
              </div>
            </div>
          </div>
        </div>
        <!--end-->
        <!--预览原图-->
        <div class="listStyle">
          <div class="title">{{$t('pictureAds.previewTheOriginalImage')}}：</div>
          <div class="content">
            <div>
              <el-radio
                v-model="moduleSaveData.is_preview"
                :label="0"
              >{{$t('pictureAds.no')}}</el-radio>
              <el-radio
                v-model="moduleSaveData.is_preview"
                :label="1"
              >{{$t('pictureAds.yes')}}</el-radio>
            </div>
            <div style="color:#999;width:335px;text-align:justify;margin-top:5px;line-height:16px">{{$t('pictureAds.originalGraphTip')}}</div>
          </div>
        </div>
        <!--图片间隙-->
        <div class="listStyle">
          <div class="title pictureGapTitle">{{$t('pictureAds.pictureGap')}}：</div>
          <div class="content pictureGap">
            <el-slider
              v-model="pictureGapValue"
              @input='handleToGetSliderValue'
              :max='20'
            ></el-slider>
            <el-input
              size="small"
              v-model="moduleSaveData.image_space"
              @change='handleToGetSliderInputValue'
            ></el-input>
          </div>
        </div>
        <!--模块标题-->
        <div
          class="listStyle"
          v-if="moduleSaveData.image_type===4"
        >
          <div class="title pictureGapTitle">{{$t('pictureAds.moduleHeader')}}：</div>
          <div
            class="content module_title"
            style="margin-bottom:10px"
          >
            <el-input
              size="small"
              v-model="moduleSaveData.module_title"
            ></el-input>
          </div>
        </div>
        <!--图片列表模块-->
        <div
          class="imageList"
          v-for="(item,index) in moduleSaveData.image_list"
          :key="index"
        >
          <div class="imageListTop">
            <img
              @click="handleToCallSingleImg(index)"
              style="cursor:pointer"
              :src="item.image"
            >
            <div style="margin-left:10px">
              <div class="textTop">
                <span>{{$t('pictureAds.text')}}：</span>
                <div>
                  <el-input
                    size="small"
                    v-model="item.title"
                    :maxlength='6'
                  ></el-input>
                  <div style="font-size:12px;color:#999;margin-top:3px">{{$t('pictureAds.textTips')}}</div>
                </div>

              </div>

              <div class="linkDiv">
                <span>{{$t('pictureAds.link')}}：</span>
                <el-input
                  size="small"
                  v-model="item.link"
                ></el-input>
                <el-button
                  @click="handleToCallLinkDialog(index)"
                  size="small"
                >{{$t('pictureAds.selectLink')}}</el-button>
              </div>
              <div class="operation">
                <div
                  style="height:22px"
                  class="btn"
                  :title="$t('pictureAds.upward')"
                  @click="
                  handleToOperation(0,index)"
                >↑</div>
                <div
                  class="btn"
                  :title="$t('pictureAds.down')"
                  style="margin:0 5px;height:22px"
                  @click="handleToOperation(1,index)"
                >↓</div>
                <div
                  class="btn"
                  style="height:22px"
                  :title="$t('pictureAds.moveOut')"
                  @click="handleToOperation(2,index)"
                >X</div>
                <div
                  class="image_adver_set"
                  style="height:auto;white-space: nowrap;"
                  @click="handleToExpand(index)"
                >{{item.whetherToExpand===0?$t('pictureAds.open'):$t('pictureAds.retract')}}{{$t('pictureAds.moreConfiguration')}}</div>
              </div>
            </div>
          </div>
          <!--点击展开更多配置隐藏模块-->
          <div
            class="imageListBottom"
            v-if="item.whetherToExpand===1"
          >
            <div class="content">
              <span>{{$t('pictureAds.displaySettings')}}：</span>
              <div class="radioContent">
                <el-radio
                  v-model="item.can_show"
                  :label="0"
                >{{$t('pictureAds.visibleToAllUsers')}}</el-radio>
                <el-radio
                  v-model="item.can_show"
                  :label="1"
                >{{$t('pictureAds.notPaidInUsers')}}</el-radio>
              </div>
            </div>
          </div>
        </div>
        <!--图片列表模块end-->
        <!--添加图片占位-->
        <div
          class="addImageContainer"
          @click="handleToCallImgDialog()"
        >
          <div style="color: #5a8bff;font-size: 13px;margin-bottom:5px">
            + {{$t('pictureAds.addPictures')}}
          </div>
          <div style="color: #999;font-size: 13px;">
            {{$t('pictureAds.recommendedWidth')}}{{moduleSaveData.image_type===0?'750':moduleSaveData.image_type===1?'375':moduleSaveData.image_type===2?'670':moduleSaveData.image_type===3?'305':moduleSaveData.image_type===4?'142':''}}px
          </div>
        </div>
        <!--添加图片占位end-->
      </div>
      <!--end-->
    </div>
    <!--选择图片弹窗-->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp='tuneUp'
      :isDraggable='isDraggable'
      @handleSelectImg='handleSelectImg'
    />
    <!--选择链接弹窗-->
    <SelectLinks
      :tuneUpSelectLink='tuneUpSelectLink'
      @selectLinkPath="selectLinkPath"
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
    modulesData: Object,
    sortIndex: Number
  },
  data () {
    return {
      tuneUpSelectLink: false, // 调起选择链接弹窗flag
      tuneUp: false, //  调起添加图片弹窗flag
      isDraggable: false, // 添加商品弹窗是否开启多选底部可拖拽状态
      isAddImgOrChangeFlga: false, // true为添加图片  false为更换列表项中的图片
      changeListImgIndex: null,
      listTypeData: [], // 选择模板列表数据
      pictureGapValue: 0, // 图片间隙slider值
      moduleSaveData: {
        is_preview: 0 //  预览原图radio
      }
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        console.log(newData, this.modulesData)
        if (this.modulesData) {
          this.$nextTick(() => {
            console.log(this.listTypeData, this.listTypeData[Number(this.moduleSaveData.image_type)])
            this.listTypeData.forEach((item, index) => {
              item.isChecked = false
            })
            if (this.modulesData.image_list.length) {
              this.modulesData.image_list.forEach((item, index) => {
                item['whetherToExpand'] = 0
              })
            }
            this.moduleSaveData = this.modulesData
            this.listTypeData[Number(this.moduleSaveData.image_type)].isChecked = true
            this.pictureGapValue = this.moduleSaveData.image_space
          })
        }
      },
      immediate: true
    },
    // 监听数据变换
    data: {
      handler (newData) {
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    },
    lang () {
      this.listTypeData = this.$t('pictureAds.listTypeData')
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 点击列表样式
    handleToClickType (index) {
      this.listTypeData.forEach(item => {
        item.isChecked = false
      })
      this.listTypeData[index].isChecked = true
      this.moduleSaveData.image_type = index
    },
    // 滑动slider触发事件
    handleToGetSliderValue (value) {
      console.log(value)
      this.moduleSaveData.image_space = value
    },
    // slider右侧输入框获取值
    handleToGetSliderInputValue (value) {
      console.log(typeof value)
      let inputVal = Number(value)
      if (inputVal > 20) inputVal = 20
      this.pictureGapValue = inputVal
    },
    //   处理图片列表展开收起
    handleToExpand (index) {
      let flag = this.moduleSaveData.image_list[index].whetherToExpand
      if (flag === 0) {
        this.moduleSaveData.image_list[index].whetherToExpand = 1
      } else {
        this.moduleSaveData.image_list[index].whetherToExpand = 0
      }
      this.$forceUpdate()
    },
    // 点击图片列表icon操作统一处理
    handleToOperation (flag, index) {
      let arr = JSON.parse(JSON.stringify(this.moduleSaveData.image_list))
      let pre, next, temp
      if ((index - 1) < 0) {
        pre = -1
      } else {
        pre = arr[(index - 1)]
      }
      if ((index + 1) > (arr.length - 1)) {
        next = -1
      } else {
        next = arr[(index + 1)]
      }
      temp = arr[index]
      switch (flag) {
        case 0:
          if (pre === -1) return
          arr[index] = pre
          arr[(index - 1)] = temp
          break
        case 1:
          if (next === -1) return
          arr[index] = next
          arr[(index + 1)] = temp
          break
        case 2:
          if (arr.length === 1) {
            this.$message.warning({
              message: '此模块最少添加一张图片'
            })
          } else {
            arr.splice(index, 1)
          }

          break
      }
      this.moduleSaveData.image_list = arr
    },
    // 添加图片弹窗选中图片数据回传
    handleSelectImg (imgData) {
      console.log(imgData)
      if (this.isAddImgOrChangeFlga) {
        imgData.forEach((item, index) => {
          let obj = {
            'image': item.imgUrl, // 图片保存路径
            'width': item.imgWidth, // 图片宽度
            'height': item.imgHeight, // 图片高度
            'title': '', // 文本
            'link': '', //   链接
            'can_show': 0, //  显示设置raido
            'whetherToExpand': 0
          }
          console.log(this.moduleSaveData)
          this.moduleSaveData.image_list.push(obj)
        })
      } else {
        console.log(imgData.imgUrl)
        this.moduleSaveData.image_list[this.changeListImgIndex].image = imgData.imgUrl
      }
      console.log(this.moduleSaveData)
    },
    // 点击添加图片
    handleToCallImgDialog () {
      this.isAddImgOrChangeFlga = true
      this.tuneUp = !this.tuneUp
      this.isDraggable = true
    },
    //  点击图片列表项添加图片
    handleToCallSingleImg (index) {
      this.changeListImgIndex = index
      this.isAddImgOrChangeFlga = false
      this.isDraggable = false
      this.tuneUp = !this.tuneUp
    },
    //  调起选择链接弹窗
    handleToCallLinkDialog (index) {
      this.changeListImgIndex = index
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },
    // 选择链接选中链接后回传数据
    selectLinkPath (path) {
      console.log(path)
      this.moduleSaveData.image_list[this.changeListImgIndex].link = path
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
    //模块私有样式
    .main {
      .listStyle {
        margin-top: 10px;
        display: flex;
        .title {
          margin-bottom: 10px;
        }
        .content {
          display: flex;
          flex-wrap: wrap;
          width: 73%;
          // background-color: #fff;
          padding: 0 10px 0;
          // border: 1px solid #e5e5e5;
          cursor: pointer;
          /deep/ .el-slider {
            width: 40%;
          }
          .typeContainer {
            padding: 8px 5px;
            width: 30%;
            margin-right: 3%;
            // height: 96px;
            border: 1px solid #e5e5e5;
            margin-bottom: 10px;
            .type {
              p {
                margin-top: 10px;
                text-align: center;
              }
              .typeTop {
                padding: 5px 5px 0 5px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                span {
                  background: #e8efff;
                }
                .odd_right {
                  height: 50px;
                  width: 100%;
                  span {
                    display: block;
                  }
                }
              }
            }
          }
        }
        .pictureGap {
          display: flex;
          margin-left: 5px;
          /deep/ .el-input {
            width: 80px;
            margin-left: 10px;
          }
        }
        .pictureGapTitle {
          display: flex;
          align-items: center;
        }
        .module_title {
          /deep/ .el-input {
            width: 160px;
          }
        }
      }
      .addImageContainer {
        background: #fff;
        width: 420px;
        padding: 30px 0;
        text-align: center;
        border: 1px dashed #ddd;
        cursor: pointer;
      }
      .imageList {
        background: #fff;
        width: 420px;
        // margin-left: 10px;
        padding: 15px;
        margin-bottom: 20px;
        border: 1px dashed #ddd;
        .imageListTop {
          display: flex;
          img {
            width: 80px;
            height: 80px;
          }
          /deep/ .el-input {
            width: 145px;
          }
          .textTop {
            display: flex;
            span {
              padding-top: 10px;
            }
            margin-bottom: 10px;
          }
          .linkDiv {
            /deep/ .el-input {
              margin-left: -4px;
            }
          }
          .operation {
            display: flex;
            margin-top: 10px;
            .btn {
              font-weight: 400;
              text-align: center;
              cursor: pointer;
              border: 1px solid transparent;
              border-radius: 2px;
              user-select: none;
              color: #333;
              background-color: #fff;
              border-color: #ccc;
              padding: 3px 12px;
            }
            .image_adver_set {
              margin-left: 10px;
              color: #5a8bff;
              font-size: 14px;
              cursor: pointer;
              height: 22px;
              line-height: 22px;
            }
          }
        }
        .imageListBottom {
          padding-left: 90px;
          margin-top: 12px;
          border-top: 1px dashed #e5e5e5;
          .content {
            padding-top: 10px;
            display: flex;
            span {
              white-space: nowrap;
            }
            .radioContent {
              display: flex;
              flex-direction: column;
              height: 42px;
              justify-content: space-between;
            }
          }
        }
      }
    }
    //end
  }
}
</style>
